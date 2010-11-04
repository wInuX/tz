package tz.game.service;

import tz.xml.Id;
import tz.xml.NameCategory;

/**
 * @author Dmitry Shyshkin
 */
public interface ShopService {
    void openPage(NameCategory category, int page);

    void sale(Id id, int count);

    void askPrice(Id id);
}
