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

    List<Item> getItems();
}
