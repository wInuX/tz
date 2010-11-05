package tz.game.scenario.manager;

import tz.game.scenario.Condition;
import tz.game.service.AbstractPublicFactoryListener;
import tz.game.service.PublicFactoryListener;
import tz.game.service.PublicFactoryService;
import tz.xml.Item;
import tz.xml.NameCategory;
import tz.xml.ShopCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
public class PublicFactoryManagerImpl implements PublicFactoryManager {
    private PublicFactoryService pfService;

    public Set<ShopCategory> getCategories() throws InterruptedException {
        if (pfService.getCategories() != null) {
            return pfService.getCategories();
        }
        final Condition condition = new Condition();
        PublicFactoryListener listener = new AbstractPublicFactoryListener(){
            @Override
            public void categoriesLoaded(Set<ShopCategory> categories) {
                condition.signal();
            }
        };
        pfService.addListener(listener);
        try {
            condition.await();
        } finally {
            pfService.removeListener(listener);
        }
        return pfService.getCategories();
    }

    public List<Item> listItems(NameCategory category) throws InterruptedException {
        List<Item> r = new ArrayList<Item>();
        int count = 0;
        for (ShopCategory sc : pfService.getCategories()) {
            if (sc.getCategory() == category) {
                count = sc.getItemCount();
                break;
            }
        }
        for (int i = 0; i < count; i += 8) {
            r.addAll(listItems(category, i / 8));
        }
        return r;
    }

    public List<Item> listItems(NameCategory category, int page) throws InterruptedException {
        final List<Item> items = new ArrayList<Item>();
        final Condition condition = new Condition();
        PublicFactoryListener listener = new AbstractPublicFactoryListener() {
            @Override
            public void itemsLoaded(NameCategory category, int page, int itemCount, List<Item> r) {
                items.addAll(r);
                condition.signal();
            }
        };
        pfService.addListener(listener);
        try {
            pfService.requestPage(category, page);
            condition.await();
        } finally {
            pfService.removeListener(listener);
        }
        return items;
    }
}
