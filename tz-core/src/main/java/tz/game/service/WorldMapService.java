package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.LocationDirection;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(WorldMapServiceImpl.class)
public interface WorldMapService {
    void gotoLocation(int x, int y, Runnable callback);

    void move(LocationDirection direction);

    void enterBuilding(int id);

    void exitBuilding();

    void addListener(WorldMapListener listener);

    void removeListener(WorldMapListener listener);
}
