package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class TurnPlannerServiceImpl extends AbstractService implements TurnPlannerService {
    @Inject
    private BattleService battleService;

    @Inject
    private UserService userService;

    private List<BattleAction> actions = new ArrayList<BattleAction>();

    private int remainedOD;

    private Position position;

    @Override
    public void initialize() {
        super.initialize();
        battleService.addListener(new AbstractBattleListener() {
            @Override
            public void turnStarted(int turnNumber) {
                actions.clear();
                remainedOD = getMaxOD();
                position = battleService.getPlayer().getPosition();
            }
        });
    }

    public boolean changePosition(Position position) {
        return addAction(new ActionPosition(position));
    }

    public boolean move(Direction direction) {
        return addAction(new ActionGo(direction));
    }

    public boolean reload(Item weapon, Item ammo) {
        return addAction(new ActionReload(weapon.getId(), ammo.getId(), 1));  // TODO 1???
    }

    public boolean fire(Item weapon, ShotType type, User target) {
        return addAction(new ActionFire(weapon.getId(), target.getLogin(), type ,2)); // TODO: 2 ???
    }

    public int getMaxOD() {
        return userService.getParameters().getOd();
    }

    public int getRemainedOD() {
        return remainedOD;
    }

    public int[] getCrawlMovement() {
        return new int[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int[] getWalkMovement() {
        return new int[]{2, 4, 6, 8, 10, 12, 14};
    }

    public int[] getRunMovement() {
        return new int[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void makeTurn() {
        server(new BattleStart(battleService.getTurnNumber() + 1));
        for (BattleAction action : actions) {
            server(action);
        }
        server(new BattleEnd());
    }

    public boolean addAction(BattleAction action) {
        int od = action.accept(new BattleActionVisitor<Integer, IllegalStateException>() {
            public Integer visitMove(ActionGo action) throws IllegalStateException {
                int[] moves;
                switch (position) {
                    case HIDE:
                    case SEAT:
                        moves = getCrawlMovement();
                        break;
                    case WALK:
                        moves = getRunMovement();
                        break;
                    case RUN:
                        moves = getRunMovement();
                        break;
                    default:
                        throw new IllegalStateException();
                }
                return moves[0];
            }

            public Integer visitPosition(ActionPosition action) throws IllegalStateException {
                position = action.getPosition();
                return 3;
            }

            public Integer visitReload(ActionReload action) throws IllegalStateException {
                Item weapon = userService.getItem(action.getWeaponId());
                return weapon.getReloadOD();
            }

            public Integer visitFire(ActionFire action) throws IllegalStateException {
                Item weapon = userService.getItem(action.getWeaponId());
                for (ShotDefinition shotDefinition : weapon.getShotDefinitions()) {
                    if (shotDefinition.getType() == action.getType()) {
                        return shotDefinition.getOd();
                    }
                }
                throw new IllegalStateException("Wrong shot type");
            }

            public Integer visitPickup(ActionPickup action) throws IllegalStateException {
                return 2;
            }
        });
        if (od > remainedOD) {
            return false;
        }
        actions.add(action);
        return true;
    }
}
