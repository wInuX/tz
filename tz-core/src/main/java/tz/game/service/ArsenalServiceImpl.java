package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.game.InterceptorPriority;
import tz.xml.Item;
import tz.xml.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class ArsenalServiceImpl extends AbstractService implements ArsenalService {
    @Inject
    private WorldMapService worldMapService;

    private List<Item> items;

    private List<ArsenalListener> listeners = new ArrayList<ArsenalListener>();

    private ArsenalListener notificator = Notificator.createNotificator(ArsenalListener.class, listeners);

    @Override
    public void initialize() {
        worldMapService.addListener(new AbstractWorldMapListener() {
            @Override
            public void buildingExited() {
                items = null;
            }
        });
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onSearch(Search search) {
        items = search.getItems();
        notificator.itemsLoaded();
        return false;
    }

    public void takeItem(String itemId, int count) {
        Search search = new Search();
        search.setDropId(itemId);
        search.setCount(count);
        server(search);
    }

    public void putItem(String itemId, int count) {
        Search search = new Search();
        search.setDropId(itemId);
        search.setCount(count);
        server(search);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addListener(ArsenalListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ArsenalListener listener) {
        listeners.remove(listener);
    }
}
