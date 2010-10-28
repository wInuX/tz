package tz.game.service;

import tz.xml.Location;

import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
public class MapCell implements Serializable {
    private static final long serialVersionUID = -1343303346720022257L;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
