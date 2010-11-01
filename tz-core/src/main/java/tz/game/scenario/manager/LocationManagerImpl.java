package tz.game.scenario.manager;

import com.google.inject.Inject;
import tz.game.scenario.Condition;
import tz.game.service.AbstractWorldMapListener;
import tz.game.service.WorldMapService;
import tz.xml.LocationDirection;

/**
 * @author Dmitry Shyshkin
 */

public class LocationManagerImpl implements LocationManager {
    @Inject
    private WorldMapService worldMapService;

    public void move(LocationDirection direction) throws InterruptedException {
        // TODO: wait
        final Condition condition = new Condition();
        AbstractWorldMapListener listener = new AbstractWorldMapListener() {
            @Override
            public void locationChanged(int x, int y) {
                condition.signal();
            }
        };
        worldMapService.addListener(listener);
        try {
            worldMapService.move(direction);
            condition.await();
        } finally {
            worldMapService.removeListener(listener);
        }
    }

    public void exitBuilding() throws InterruptedException {
        final Condition condition = new Condition();
        AbstractWorldMapListener listener = new AbstractWorldMapListener() {
            @Override
            public void buildingExited() {
                condition.signal();
            }
        };
        worldMapService.addListener(listener);
        try {
            worldMapService.exitBuilding();
            condition.await();
        } finally {
            worldMapService.removeListener(listener);
        }
    }

    public void enterBuilding(int id) throws InterruptedException {
        final Condition condition = new Condition();
        AbstractWorldMapListener listener = new AbstractWorldMapListener() {
            @Override
            public void buildingEntered(int id) {
                condition.signal();
            }
        };
        worldMapService.addListener(listener);
        try {
            worldMapService.enterBuilding(id);
            condition.await();
        } finally {
            worldMapService.removeListener(listener);
        }
    }
}
