package tz.game.service;

import tz.xml.Item;
import tz.xml.NameCategory;
import tz.xml.ShopCategory;

import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
public interface PublicFactoryListener {
    void categoriesLoaded(Set<ShopCategory> categories);

    void itemsLoaded(NameCategory category, int page, int itemCount, List<Item> items);
}
