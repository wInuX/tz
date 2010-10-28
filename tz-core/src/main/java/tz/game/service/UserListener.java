package tz.game.service;

import tz.xml.Item;
import tz.xml.MyParameters;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public interface UserListener {
    void parameterChanged(MyParameters parameters);

    void itemsChanged(List<Item> items);
}
