package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.Item;
import tz.xml.MyParameters;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
    Item getItem(String id);

    List<Item> getItems();

    void updateItem(String id, Item newItem);

    void deleteItem(Item item);

    void addListener(UserListener listener);

    void removeListener(UserListener listener);

    MyParameters getParameters();
}
