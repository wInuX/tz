package tz.game.service;

import tz.xml.Id;
import tz.xml.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class Items {
    private Map<Id, Item> items = new HashMap<Id, Item>();
    private Map<Id, Id> owners = new HashMap<Id, Id>();

    public Items(Collection<Item> items) {
        for (Item parent: items) {
            items.add(parent);
            for (Item child : parent.getInsertions()) {
                if (child.getInsertions() != null) {
                    throw new IllegalStateException("Not implemented");
                }
                owners.put(child.getId(), parent.getId());
            }
        }
    }

    public void remove(Item item) {

    }

    public void add(Item item) {

    }

    public void update(Item item) {
        
    }
}
