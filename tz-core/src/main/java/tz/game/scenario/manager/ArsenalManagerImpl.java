package tz.game.scenario.manager;

import com.google.inject.Inject;
import tz.game.scenario.Condition;
import tz.game.service.*;
import tz.xml.Building;
import tz.xml.BuildingCategory;
import tz.xml.Item;
import tz.xml.LocationDirection;

/**
 * @author Dmitry Shyshkin
 */
public class ArsenalManagerImpl implements ArsenalManager {
    @Inject
    private WorldMapService worldMapService;

    @Inject
    private ArsenalService arsenalService;

    @Inject
    private LocationManager locationManager;

    public void collect(String name, int count) throws InterruptedException {
        gotoArsenal();
        Building arsenal = worldMapService.getBuilding();

        while (true) {
            waitForItems();
            Item collectItem = null;
            for (Item item : arsenalService.getItems()) {
                if (item.getName().equals(name)) {
                    collectItem = item;
                    break;
                }
            }
            if (collectItem == null) {
                throw new IllegalStateException("No item in arsenal");
            }
            int c = collectItem.getCount() != 0 ? Math.min(collectItem.getCount(), count) : 1;
            arsenalService.takeItem(collectItem.getId(), c);
            if (count == 0) {
                break;
            }
            worldMapService.exitBuilding();
            worldMapService.enterBuilding(arsenal.getId());
        }
    }

    private void waitForItems() throws InterruptedException {
        if (arsenalService.getItems() == null) {
            final Condition condition = new Condition();
            ArsenalListener listener = new AbstractArsenalListener() {
                @Override
                public void itemsLoaded() {
                    condition.signal();
                }
            };
            arsenalService.addListener(listener);
            try {
                condition.await();
            } finally {
                arsenalService.removeListener(listener);
            }
        }
    }

    private void gotoArsenal() throws InterruptedException {
        while (worldMapService.getBuilding() == null || worldMapService.getBuilding().getType().getCategory() != BuildingCategory.ARSENAL) {
            if (worldMapService.getBuilding() != null) {
                locationManager.exitBuilding();
            }
            if (worldMapService.getBuilding() == null) {
                WorldMap map = worldMapService.getWorldMap();
                int targetX = 0;
                int targetY = 0;
                Building targetBuilding = null;
                aloop:
                for (int d = 0; d < 3; ++d) {
                    for (LocationDirection direction : LocationDirection.values()) {
                        if (direction.getDx() == 0 && direction.getDy() == 0) {
                            continue;
                        }
                        int nx = worldMapService.getLocationX() + d * direction.getDx();
                        int ny = worldMapService.getLocationX() + d * direction.getDy();
                        MapCell cell = map.getCell(nx, ny);
                        if (cell.getLocation() != null && cell.getLocation().getBuildings() != null) {
                            for (Building building : cell.getLocation().getBuildings()) {
                                if (building.getType().getCategory() == BuildingCategory.ARSENAL) {
                                    targetX = nx;
                                    targetY = ny;
                                    targetBuilding = building;
                                    break aloop;
                                }
                            }
                        }
                    }
                }
                if (targetBuilding == null) {
                    throw new IllegalStateException("Can't find arsenal");
                }
                locationManager.move(targetX, targetY);
                locationManager.enterBuilding(targetBuilding.getId());
            }
        }
    }
}
