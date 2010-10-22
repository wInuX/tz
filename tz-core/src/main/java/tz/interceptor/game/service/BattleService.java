package tz.interceptor.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.Item;
import tz.xml.User;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(BattleServiceImpl.class)
public interface BattleService {
    boolean isInBattle();

    User getPlayer();

    List<User> getUsers();

    User getUser(String login);

    List<Item> getItems();

    int getMapWidth();

    int getMapHeight();

    BattleMapCell getMapCell(int x, int y);

    int getTurnNumber();

    void addListener(BattleListener listener);

    void removeListener(BattleListener listener);

}
