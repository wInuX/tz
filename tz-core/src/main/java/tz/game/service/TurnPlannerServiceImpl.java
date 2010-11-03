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

    @Inject
    private CopyService copyService;

    private List<TurnFrame> frames = new ArrayList<TurnFrame>();

    @Override
    public void initialize() {
        super.initialize();
        battleService.addListener(new AbstractBattleListener() {
            @Override
            public void turnStarted(int turnNumber) {
                TurnFrame frame = new TurnFrame();
                frame.setMyParameters(userService.getParameters());
                frame.setMyItems(userService.getItems());
                frame.setItems(battleService.getItems());
                frame.setRemainedOD(getMaxOD());
                frame.setPosition(battleService.getPlayer().getPosition());
                frame.setStep(1);
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
        return top().getRemainedOD();
    }

    public int[] getCrawlMovement() {
        int[] r = new int[20];
        for (int i = 0; i < r.length; ++i) {
            r[i] = top().getStep() * 4;
        }
        return r;
    }

    public int[] getWalkMovement() {
        int[] r = new int[20];
        for (int i = 0; i < r.length; ++i) {
            r[i] = top().getStep() * 2;
        }
        return r;
    }

    public int[] getRunMovement() {
        int[] r = new int[20];
        for (int i = 0; i < r.length; ++i) {
            r[i] = top().getStep();
        }
        return r;
    }

    public void makeTurn() {
        server(new BattleStart(battleService.getTurnNumber() + 1));
        for (TurnFrame frame : frames) {
            if (frame.getBattleAction() != null) {
                server(frame.getBattleAction());
            }
        }
        server(new BattleEnd());
    }

    public boolean addAction(BattleAction action) {
        final TurnFrame frame = copyService.copy(top());
        frame.setBattleAction(action);

        int od = action.accept(new BattleActionVisitor<Integer, IllegalStateException>() {
            public Integer visitMove(ActionGo action) throws IllegalStateException {
                int od;
                switch (top().getPosition()) {
                    case HIDE:
                        frame.setPosition(Position.SEAT);
                        od = 3 + getCrawlMovement()[0];
                        break;
                    case SEAT:
                        od = getCrawlMovement()[0];
                        break;
                    case WALK:
                        od = getWalkMovement()[0];
                        break;
                    case RUN:
                        od = getRunMovement()[0];
                        break;
                    default:
                        throw new IllegalStateException();
                }
                int nx = action.getDirection().moveX(top().getX(), top().getY());
                int ny = action.getDirection().moveY(top().getX(), top().getY());
                frame.setX(nx);
                frame.setY(ny);
                frame.setStep(top().getStep() + 1);
                return od;
            }

            public Integer visitPosition(ActionPosition action) throws IllegalStateException {
                frame.setPosition(action.getPosition());
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
        if (od > top().getRemainedOD()) {
            return false;
        }
        frame.setRemainedOD(top().getRemainedOD() - od);
        frames.add(frame);
        return true;
    }

    private TurnFrame top() {
        return frames.get(frames.size() - 1);
    }
}
