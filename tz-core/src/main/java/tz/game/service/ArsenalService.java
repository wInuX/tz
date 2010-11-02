package tz.game.service;

import tz.xml.Item;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public interface ArsenalService {
    List<Item> getItems();

    void takeItem(String itemId, int count);

    void putItem(String itemId, int count);

    void addListener(ArsenalListener listener);

    void removeListener(ArsenalListener listener);
}
