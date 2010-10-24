package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class UserServiceImpl extends AbstractService implements UserService {
    public static final Logger LOG = Logger.getLogger(UserService.class);
    @Inject
    private GameState gameState;

    private List<Item> items;

    private Search lastSearch;

    private MyParameters myParameters;

    @Intercept(InterceptionType.SERVER)
    boolean onLoginOk(LoginOk loginOk) {
        gameState.setLogin(loginOk.getLogin());
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onMyParam(MyParameters myParameters) {
        if (this.myParameters == null) {
            this.myParameters = myParameters;
        }
        if (myParameters.getItems() != null && myParameters.getItems().size() > 0) {
            gameState.setItems(myParameters.getItems());
            items = myParameters.getItems();
        }
        if (myParameters.getOd() != null) {
            gameState.setOd(myParameters.getOd());
        }
        return false;
    }

    @Intercept(InterceptionType.CLIENT)
    boolean onTakeOn(TakeOn takeOn) {
        Item item = getItem(takeOn.getId());
        if (item != null) {
            LOG.debug("Taking on " + takeOn.getId() + " bound to " + takeOn.getSlot());
            item.setUsedSlot(takeOn.getSlot());
        } else {
            LOG.error("Item: " + takeOn.getId() + " not found");
        }
        return false;
    }

    @Intercept(InterceptionType.CLIENT)
    boolean onTakeOff(TakeOff takeOff) {
        Item item = getItem(takeOff.getId());
        if (item != null) {
            LOG.debug("Taking off " + takeOff.getId());
            item.setUsedSlot(null);
        } else {
            LOG.error("Item: " + takeOff.getId() + " not found");
        }
        return false;

    }
    @Intercept(InterceptionType.CLIENT)
    boolean onJoin(Join join) {
        Item source = getItem(join.getSourceId());
        if (source == null) {
            LOG.error("Source item: " + join.getSourceId() + " not found");
            return false;
        }
        Item destination = getItem(join.getDestinationId());
        if (destination == null) {
            LOG.error("Destination item: " + join.getDestinationId() + " not found");
            return false;
        }
        destination.setCount(source.getCount() + destination.getCount());
        deleteItem(source);
        return false;
    }


    @Intercept(InterceptionType.CLIENT)
    boolean onPickup(Search search) {
        if (search.getTakeId() != null) {
            for (Item item : lastSearch.getItems()) {
                if (item.getId().equals(search.getTakeId())) {
                    LOG.debug("Pickup item : " + item.getText() + " bound to new id: " + getNextId());
                    item.setId(getNextId());
                    items.add(item);
                    return false;
                }
            }
            LOG.error("No pickup item found: " + search.getTakeId());
        } else if (search.getDropId() != null) {
            for (Item item : items) {
                if (item.getId().equals(search.getDropId())) {
                    items.remove(item);
                    return false;
                }
            }
            LOG.error("No drop (search) item found: " + search.getDropId());
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onSearch(Search search) {
        lastSearch = search;
        return false;
    }

    @Intercept(InterceptionType.CLIENT)
    boolean onNewId(NewId newId) {
        if (myParameters.getIdOffset() != null) {
            myParameters.setIdOffset(myParameters.getIdOffset() + 1);
        }
        return false;
    }

    @Intercept(InterceptionType.CLIENT)
    boolean onNop(Nop nop) {
        if (nop.getIdOffset() != null) {
            if (nop.getIdOffset().intValue() != myParameters.getIdOffset().intValue()) {
                LOG.error("New id unsynchornization. My : " + myParameters.getIdOffset() + " client:" + myParameters.getIdOffset());
            }
            myParameters.setIdOffset(nop.getIdOffset());
        }
        return false;
    }

    private String getNextId() {
        int index = myParameters.getIdRangeStart().indexOf(".");
        return (Long.parseLong(myParameters.getIdRangeStart().substring(0, index)) + myParameters.getIdOffset()) +
                myParameters.getIdRangeStart().substring(index);
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
