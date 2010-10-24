package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.interceptor.game.InterceptorPriority;
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
    @Inject
    private ChatService chatService;

    private List<Item> items;

    private Search lastSearch;

    private MyParameters myParameters;

    @Override
    public void initialize() {
        chatService.addCommand("items", new CommandListener() {
            public void onCommand(String command, String[] parameters) {
                StringBuilder sb = new StringBuilder();
                for (Item item : items) {
                    sb.append(item.getId()).append(" '").append(item.getText()).append("'").append(" ").append(item.getCount()).append("\n");
                }
                chatService.display(sb.toString());
            }
        });
    }

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

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
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

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
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
    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
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


    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onPickup(Search search) {
        if (search.getTakeId() != null) {
            for (Item item : lastSearch.getItems()) {
                if (item.getId().equals(search.getTakeId())) {
                    boolean createNew = false; // TODO: condition
                    if (createNew) {
                        LOG.debug("Pickup item : " + item.getText() + " bound to new id: " + getNextId());
                        item.setId(getNextId());
                    } else {
                        LOG.debug("Pickup item : " + item.getText() + " id: " + item.getId());
                    }
                    lastSearch.getItems().remove(item);
                    items.add(item);
                    return false;
                }
            }
            LOG.error("No pickup item found: " + search.getTakeId());
        } else if (search.getDropId() != null) {
            for (Item item : items) {
                if (item.getId().equals(search.getDropId())) {
                    lastSearch.getItems().remove(item);
                    items.remove(item);
                    LOG.debug("Pickup item dropped : " + item.getText() + ", id: " + item.getId());
                    return false;
                }
            }
            LOG.error("No drop (search) item found: " + search.getDropId());
        }
        return false;
    }

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onDrop(Drop drop) {
        Item item = getItem(drop.getId());
        if (item == null) {
            LOG.error("No drop item found:" + drop.getId());
            return false;
        }
        if (item.getCount() != null && drop.getCount() != null) {
            if (item.getCount().intValue() == drop.getCount().intValue()) {
                deleteItem(item);
            } else {
                Item newItem = new Item();
                newItem.setId(drop.getId());
                newItem.setCount(item.getCount() - drop.getCount());
                updateItem(item.getId(), newItem);
            }
        } else {
            deleteItem(item);
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onSearch(Search search) {
        lastSearch = search;
        return false;
    }

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onNewId(NewId newId) {
        if (myParameters.getIdOffset() != null) {
            myParameters.setIdOffset(myParameters.getIdOffset() + 1);
        }
        return false;
    }

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onNop(Nop nop) {
        if (nop.getIdOffset() != null) {
            if (nop.getIdOffset().intValue() != myParameters.getIdOffset().intValue()) {
                LOG.warn("New id unsynchornization. My : " + myParameters.getIdOffset() + " client:" + myParameters.getIdOffset());
            }
            myParameters.setIdOffset(nop.getIdOffset());
            myParameters.setIdRangeStart(nop.getIdRangeStart());
            myParameters.setIdRangeEnd(nop.getIdRangeStart());
        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.LATE)
    boolean onAddOne(AddOne addOne) {
        if (addOne.getItems() != null) {
            for (Item item : addOne.getItems()) {
                items.add(item);
            }
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
