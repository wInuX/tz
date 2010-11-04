package tz.game.service;

import tz.xml.Id;
import tz.xml.Item;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public interface ArsenalService {
    List<Item> getItems();

    void takeItem(Id itemId, int count);

    void putItem(Id itemId, int count);

    void addListener(ArsenalListener listener);

    void removeListener(ArsenalListener listener);
}
