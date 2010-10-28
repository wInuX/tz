package tz.game.service;

import tz.xml.Item;
import tz.xml.MyParameters;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public abstract class AbstractUserListener implements UserListener {
    public void parameterChanged(MyParameters parameters) {

    }

    public void itemsChanged(List<Item> items) {

    }
}
