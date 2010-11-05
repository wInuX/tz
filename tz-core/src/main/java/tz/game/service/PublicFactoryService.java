package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.NameCategory;
import tz.xml.ShopCategory;

import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(PublicFactoryServiceImpl.class)
public interface PublicFactoryService {
    Set<ShopCategory> getCategories();

    void requestPage(NameCategory category, int page);

    void addListener(PublicFactoryListener listener);

    void removeListener(PublicFactoryListener listener);
}
