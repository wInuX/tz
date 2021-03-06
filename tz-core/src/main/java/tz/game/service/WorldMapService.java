package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.remote.RemoteProxy;
import tz.xml.Building;
import tz.xml.LocationDirection;

/**
 * @author Dmitry Shyshkin
 */
@RemoteProxy
@ImplementedBy(WorldMapServiceImpl.class)
public interface WorldMapService {
    void move(LocationDirection direction);

    void enterBuilding(int id);

    void exitBuilding();

    void addListener(WorldMapListener listener);

    void removeListener(WorldMapListener listener);

    long getWaitTime();

    Building getBuilding();

    int getLocationX();

    int getLocationY();

    WorldMap getWorldMap();

}
