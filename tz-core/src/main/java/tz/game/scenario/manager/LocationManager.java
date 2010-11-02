package tz.game.scenario.manager;

import com.google.inject.ImplementedBy;
import tz.xml.LocationDirection;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(LocationManagerImpl.class)
public interface LocationManager {
    /**
     * wait and move to adjecent location
     * @param direction direction
     * @throws IllegalStateException
     * @throws InterruptedException
     */
    void move(LocationDirection direction) throws InterruptedException;

    void move(int targetX, int targetY) throws InterruptedException;

    void exitBuilding() throws InterruptedException;

    void enterBuilding(int id) throws InterruptedException;
}
