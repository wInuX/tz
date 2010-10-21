package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class BattleServiceImpl extends AbstractService implements BattleService {
    @Inject
    private GameState state;

    private Battle battle;

    private User player;

    private List<User> users;

    private List<Item> items;

    private Map<String, User> userMap;

    private Map<String, Item> itemMap;

    @Intercept(InterceptionType.SERVER)
    boolean onBattle(Battle battle) {
        this.battle = battle;
        this.users = new ArrayList<User>();
        userMap = new HashMap<String, User>();
        for (User user : battle.getUsers()) {
            userMap.put(user.getLogin(), user);
            if (user.getLogin().equals(state.getLogin())) {
                player = user;
            } else {
                users.add(user);
            }
        }
        this.items = new ArrayList<Item>();
        itemMap = new HashMap<String, Item>();
        for (Item item : battle.getItems()) {
            items.add(item);
            itemMap.put(item.getId(), item);
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onTurn(Turn turn) {
        for (User user : turn.getUsers()) {
            processUser(user);
        }
        processUser(player);
        return false;
    }

    private void processUser(User turnUser) {
        if (turnUser.getActions() == null) {
            return;
        }
        User user = userMap.get(turnUser.getLogin());
        for (Action action : turnUser.getActions()) {
            switch (action.getMode()) {
                case TURN:
                    user.setDirection(action.getDirection());
                    break;
                case MOVE: {
                    int x = user.getX();
                    int y = user.getY();
                    Direction direction = user.getDirection();
                    user.setXY(direction.moveX(x, y), direction.moveY(x, y));
                    break;
                }
                case GET_ITEM: {
                    Item item = itemMap.get(action.getId());
                    int count = item.getCount() - action.getCount();
                    item.setCount(count);
                    if (count == 0) {
                        itemMap.remove(item.getId());
                    }
                    items.remove(item);
                    break;
                }
                case DIED: {
                    userMap.remove(user.getLogin());
                    users.remove(user);
                    break;
                }
            }
        }
    }

    public User getPlayer() {
        return player;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isInBattle() {
        return battle != null;
    }
}
