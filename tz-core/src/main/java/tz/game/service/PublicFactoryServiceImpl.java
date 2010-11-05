package tz.game.service;

import com.google.inject.Singleton;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.game.InterceptorPriority;
import tz.xml.NameCategory;
import tz.xml.PublicFactory;
import tz.xml.ShopCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class PublicFactoryServiceImpl extends AbstractService implements PublicFactoryService {
    private List<PublicFactoryListener> listeners = new ArrayList<PublicFactoryListener>();

    private PublicFactoryListener notificator = Notificator.createNotificator(PublicFactoryListener.class, listeners);

    private Set<ShopCategory> categories;

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    void onPublicFactory(PublicFactory pf) {
        if (pf.getCategories() != null) {
            categories = pf.getCategories();
            notificator.categoriesLoaded(categories);
        }
        if (pf.getPage() != null) {
            notificator.itemsLoaded(pf.getCategory(), pf.getPage(), pf.getItemCount(), pf.getItems());
        }
    }

    public Set<ShopCategory> getCategories() {
        return categories;
    }

    public void requestPage(NameCategory category, int page) {
        PublicFactory publicFactory = new PublicFactory();
        publicFactory.setCategory(category);
        publicFactory.setItemCount(page);
        server(publicFactory);
    }

    public void addListener(PublicFactoryListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PublicFactoryListener listener) {
        listeners.remove(listener);
    }
}
