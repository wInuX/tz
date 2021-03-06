package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.game.GameModule;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.logging.SequenceLogger;
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
    private UserService userService;

    @Inject
    private GameModule gameModule;

    private Battle battle;

    private User player;

    private List<User> users;

    private List<Item> items;

    private Map<String, User> userMap;

    private Map<Id, Item> itemMap;

    private BattleMapCell[][] map;

    private int turnNumber;

    private List<BattleListener> listeners = new ArrayList<BattleListener>();

    private BattleListener notificator = Notificator.createNotificator(BattleListener.class, listeners);

    private SequenceLogger battleLogger;

    @Intercept(InterceptionType.SERVER)
    boolean onBattle(String original, Battle battle) {
        this.battle = battle;
        this.users = new ArrayList<User>();
        userMap = new HashMap<String, User>();
        for (User user : battle.getUsers()) {
            userMap.put(user.getLogin(), user);
            if (user.getLogin().equals(userService.getLogin())) {
                player = user;
            } else {
                users.add(user);
            }
        }
        battleLogger = new SequenceLogger(gameModule.getSessionId(), "battle-" + player.getBattleId());
        battleLogger.append("server", original, battle);
        this.items = new ArrayList<Item>();
        itemMap = new HashMap<Id, Item>();
        for (Item item : battle.getItems()) {
            items.add(item);
            itemMap.put(item.getId(), item);
        }
        map = new BattleMapCell[battle.getMap().get(0).getContent().length()][battle.getMap().size()];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                map[i][j] = new BattleMapCell(battle.getMap().get(j).getContent().charAt(i));
            }
        }
        turnNumber = 0;

        notificator.battleStarted();
        notificator.turnStarted(turnNumber  + 1);

        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onTurn(String original, Turn turn) {
        if (!isInBattle()) {
            // someone is viewing battle
            return false;
        }
        battleLogger.append("server", original, turn);
        turnNumber = turn.getTurn();
        if (turn.getUsers() != null) {
            for (User user : turn.getUsers()) {
                processUser(user);
            }
        }
        if (turn.getItems() != null) {
            for (Item item : turn.getItems()){
                processItem(item);
            }
        }
        notificator.turnStarted(turnNumber + 1);
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onBattleEnd(String original, BattleEnd end) {
        battleLogger.append("server", original, end);
        notificator.battleEnd();
        battleLogger.close();
        battle = null;
        items = null;
        users = null;
        player = null;
        map = null;
        userMap = null;
        itemMap = null;
        return false;
    }

    private void processItem(Item item) {
        items.add(item);
        itemMap.put(item.getId(), item);
    }

    private void processUser(User turnUser) {
        if (turnUser.getLogin().equals(player.getLogin())) {
            if (turnUser.getItems() != null) {
                for (Item item : turnUser.getItems()) {
                    if (item.isEmpty()) {
                        userService.deleteItem(item);
                    } else {
                        userService.updateItem(item.getId(), item);
                    }
                }
            }
        }
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
                case POSITION: {
                    user.setPosition(action.getPosition());
                    break;
                }
                case GET_ITEM: {
                    if (action.getId() != null) {
                        Item item = itemMap.get(action.getId());
                        if (item == null) {
                            throw new IllegalStateException("No item with id:" + action.getId());
                        }
                        int count =  item.getCount() - action.getCount();
                        item.setCount(count);
                        if (count == 0) {
                            itemMap.remove(item.getId());
                        }
                        items.remove(item);

                    } else if (action.getX() != null && action.getY() != null) {
                        boolean removed = false;
                        int x = action.getX();
                        int y = action.getY();
                        for (Item item: new ArrayList<Item>(items)) {
                            if (item.getX() == x && item.getY() == y) {
                                itemMap.remove(item.getId());
                                items.remove(item);
                                removed = true;
                            }
                        }
                        if (!removed) {
                            throw new IllegalStateException("No items at:" + (char)('A' + y) + (x + 1));
                        }
                    } else {
                        throw new IllegalStateException();
                    }
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

    public User getUser(String login) {
        return userMap.get(login);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isInBattle() {
        return battle != null;
    }

    public int getMapWidth() {
        return map.length;
    }

    public int getMapHeight() {
        return map[0].length;
    }

    public BattleMapCell getMapCell(int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return null;
        }
        return map[x][y];
    }

    public Item getItem(int x, int y) {
        for(Item item : items) {
            if (item.getX() == null || item.getY() == null) {
                continue;
            }
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        return null;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void addListener(BattleListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BattleListener listener) {
        listeners.remove(listener);
    }
}
