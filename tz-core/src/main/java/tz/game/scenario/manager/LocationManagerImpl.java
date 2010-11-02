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
        long waitTime = worldMapService.getWaitTime();
        while (waitTime > 0) {
            Thread.sleep(waitTime * 1000);
            waitTime = worldMapService.getWaitTime();
        }

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

    public void move(int targetX, int targetY) throws InterruptedException {
        int x = worldMapService.getLocationX();
        int y = worldMapService.getLocationY();
        while (x != targetX && y != targetY) {
            int dx = sign(targetX - x);
            int dy = sign(targetY - y);
            for (LocationDirection direction: LocationDirection.values()) {
                if (direction.getDx() == dx && direction.getDy() == dy) {
                    move(direction);
                    break;
                }
            }
        }
    }

    private static int sign(int v) {
        if (v < 0) {
            return -1;
        } else if (v > 0) {
            return 1;
        } else {
            return 0;
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

            @Override
            public void buildingNotEntered() {
                throw new IllegalStateException("Can't enter building");
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
