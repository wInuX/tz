package tz.game.scenario.manager;

import com.google.inject.ImplementedBy;
import tz.xml.Item;
import tz.xml.NameCategory;
import tz.xml.ShopCategory;

import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(PublicFactoryManagerImpl.class)
public interface PublicFactoryManager {

    Set<ShopCategory> getCategories() throws InterruptedException;

    List<Item> listItems(NameCategory category) throws InterruptedException;

    List<Item> listItems(NameCategory category, int page) throws InterruptedException;
}
