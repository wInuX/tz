package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.game.InterceptorPriority;
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
    private ChatService chatService;

    private List<Item> items;

    private Search lastSearch;

    private MyParameters myParameters;

    private IdRange idRange;

    private Item lastNewItem;

    private List<UserListener> listeners = new ArrayList<UserListener>();

    private long timeShift;

    private String login;

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
        login = loginOk.getLogin();
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onMyParam(MyParameters myParameters) {
        if (this.myParameters == null) {
            this.myParameters = myParameters;
            notifyParameterChanged(myParameters);
        }
        if (myParameters.getItems() != null && myParameters.getItems().size() > 0) {
            items = myParameters.getItems();
            notifyItemsChanged();
        }
        if (myParameters.getOd() != null) {
            this.myParameters.setOd(myParameters.getOd());
        }
        if (myParameters.getTime() != null) {
            timeShift = System.currentTimeMillis() / 1000 - myParameters.getTime();
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
            notifyItemsChanged();
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
        notifyItemsChanged();
        return false;
    }


    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onPickup(Search search) {
        if (search.getTakeId() != null) {
            for (Item item : lastSearch.getItems()) {
                if (item.getId().equals(search.getTakeId())) {
                    boolean createNew = false;
                    if (createNew) {
                        LOG.debug("Pickup item : " + item.getText() + " bound to new id: " + getNextId());
                        item.setId(getNextId());
                    } else {
                        LOG.debug("Pickup item : " + item.getText() + " id: " + item.getId());
                    }
                    lastSearch.getItems().remove(item);
                    lastNewItem = item;
                    items.add(item);
                    notifyItemsChanged();
                    return false;
                }
            }
            LOG.error("No pickup item found: " + search.getTakeId());
        } else if (search.getDropId() != null) {
            for (Item item : items) {
                if (item.getId().equals(search.getDropId())) {
                    lastSearch.getItems().remove(item);
                    items.remove(item);
                    notifyItemsChanged();
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
                LOG.debug("droped item:" + drop.getId() + " " + item.getText());
                deleteItem(item);
            } else {
                LOG.debug("droped" + drop.getCount() + "item:" + drop.getId() + " " + item.getText());

                Item newItem = new Item();
                newItem.setId(drop.getId());
                newItem.setCount(item.getCount() - drop.getCount());
                updateItem(item.getId(), newItem);
            }
            notifyItemsChanged();
        } else {
            LOG.debug("droped item:" + drop.getId() + " " + item.getText());
            deleteItem(item);
            notifyItemsChanged();
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
        if (lastNewItem != null) {
            lastNewItem.setId(getNextId());
            notifyItemsChanged();
            LOG.debug("Set new id " + getNextId() + " to item " + lastNewItem.getText());
        }
        if(idRange != null) {
            idRange.setOffset(idRange.getOffset() + 1);
        }
        return false;
    }

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onNop(Nop nop) {
        if (nop.getIdRange() != null) {
            if (nop.getIdRange().getOffset().intValue() != idRange.getOffset().intValue()) {
                LOG.warn("New id unsynchornization. My : " + idRange.getOffset() + " client:" + nop.getIdRange().getOffset());
            }
            idRange.setOffset(nop.getIdRange().getOffset());
            idRange.setStart(nop.getIdRange().getStart());
            idRange.setEnd(nop.getIdRange().getStart());
        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onAddOne(AddOne addOne) {
        if (addOne.getItems() != null) {
            for (Item item : addOne.getItems()) {
                items.add(item);
            }
            notifyItemsChanged();
        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onChangeOne(ChangeOne changeOne) {
        Item item = getItem(changeOne.getId());
        if (item == null) {
            LOG.error("Can't change item. No item: " + changeOne.getId());
            return false;
        }
        if (changeOne.getUsedSlot() != null) {
            item.setUsedSlot(changeOne.getUsedSlot());
        }
        notifyItemsChanged();
        return false;
    }

    @Intercept(value = InterceptionType.CLIENT, priority = InterceptorPriority.LATE)
    boolean onShop(Shop shop) {
        if (shop.getSaleId() != null) {
            Item item = getItem(shop.getSaleId());
            if (item == null) {
                LOG.error("Can't sale item. No item: " + shop.getSaleId());
                return false;
            }
            deleteItem(item);
            notifyItemsChanged();
        }
        return false;
    }

    private Id getNextId() {
        return new Id(idRange.getStart().getNumber() + idRange.getOffset(), idRange.getStart().getServer());
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(Id id) {
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

    public void updateItem(Id id, Item newItem) {
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

    public MyParameters getParameters() {
        return myParameters;
    }

    public String getLogin() {
        return login;
    }

    public long currentTime() {
        return System.currentTimeMillis() / 1000 - timeShift;
    }

    public void addListener(UserListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UserListener listener) {
        listeners.remove(listener);
    }

    private void notifyParameterChanged(MyParameters myParameters) {
        for (UserListener listener : new ArrayList<UserListener>(listeners)) {
            listener.parameterChanged(myParameters);
        }
    }

    private void notifyItemsChanged() {
        for (UserListener listener : new ArrayList<UserListener>(listeners)) {
            listener.itemsChanged(items);
        }
    }
}
