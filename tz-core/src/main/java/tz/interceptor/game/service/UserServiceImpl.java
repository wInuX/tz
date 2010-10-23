package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.Item;
import tz.xml.LoginOk;
import tz.xml.MyParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class UserServiceImpl extends AbstractService implements UserService {
    @Inject
    private GameState gameState;

    private List<Item> items;

    @Intercept(InterceptionType.SERVER)
    boolean onLoginOk(LoginOk loginOk) {
        gameState.setLogin(loginOk.getLogin());
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onMyParam(MyParameters myParameters) {
        if (myParameters.getItems() != null && myParameters.getItems().size() > 0) {
            gameState.setItems(myParameters.getItems());
            items = myParameters.getItems();
        }
        if (myParameters.getOd() != null) {
            gameState.setOd(myParameters.getOd());
        }
        return false;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
            if (item.getInsertions() != null) {
                for (Item insertion : item.getInsertions()) {
                    if (insertion.getId().equals(id)) {
                        return insertion;
                    }
                }
            }
        }
        return null;
    }

    public void updateItem(String id, Item newItem) {
        Item original = getItem(id);
        if (original == null) {
            System.out.println("new item: " + newItem.getId());
            items.add(newItem);
            return;
        }
        System.out.println("updating item: " + newItem.getId());
        if (newItem.getInsertTo() != null) {
            Item container = getItem(newItem.getInsertTo());
            if (container.getInsertions() == null) {
                container.setInsertions(new ArrayList<Item>());
                container.getInsertions().add(original);
                items.remove(original);
            }
        }
        if (newItem.getCount() != null) {
            original.setCount(newItem.getCount());
        }
        if (newItem.getQuality() != null) {
            original.setQuality(newItem.getQuality());
        }
    }

    public void deleteItem(Item oldItem) {
        System.out.println("removing item: " + oldItem.getId());

        for (Item item : items) {
            if (item.getId().equals(oldItem.getId())) {
                items.remove(item);
                return;
            }
            if (item.getInsertions() != null) {
                for (Item insertion: item.getInsertions()) {
                    if (insertion.getId().equals(oldItem.getId())) {
                        item.getInsertions().remove(insertion);
                        return;
                    }
                }
            }
        }
        System.err.println("Item not found: " + oldItem.getId());
    }
}
