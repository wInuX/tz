package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.Id;
import tz.xml.Item;
import tz.xml.MyParameters;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
    Item getItem(Id id);

    List<Item> getItems();

    void updateItem(Id id, Item newItem);

    void deleteItem(Item item);

    void addListener(UserListener listener);

    void removeListener(UserListener listener);

    MyParameters getParameters();

    String getLogin();

    long currentTime();
}
