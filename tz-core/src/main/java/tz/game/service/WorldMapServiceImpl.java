package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.game.InterceptorPriority;
import tz.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class WorldMapServiceImpl extends AbstractService implements WorldMapService {
    @Inject
    private UserService userService;

    private WorldMap worldMap = new WorldMap();

    private List<WorldMapListener> listeners = new ArrayList<WorldMapListener>();

    private WorldMapListener notificator = Notificator.createNotificator(WorldMapListener.class, listeners);

    private Building building;

    private int locationX;

    private int locationY;

    private long locationTime;

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onMiniMap(MiniMap map) {
        for (Location location: map.getLocations()) {
            MapCell cell = worldMap.getCell(location.getX(), location.getY());
            cell.setLocation(location);
        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onMyParameters(MyParameters message) {
        if (message.getX() != null && message.getY() != null) {
            locationX = message.getX();
            locationY = message.getY();
            notificator.locationChanged(message.getX(), message.getY());
        }
        if (message.getLocationTime() != null) {
            locationTime = message.getLocationTime();
        }
        if (message.getBuildingId() != null) {
            if (message.getBuildingId() == 0) {
                building = null;
            } else {
                building = new Building();
                building.setId(message.getBuildingId());
                building.setType(message.getBuildingType());
            }
        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onGoLocation(GoLocation goLocation) {
        LocationDirection direction = goLocation.getDirection();
        int nx = locationX + direction.getDx();
        int ny = locationY + direction.getDy();
        if (nx != locationX || ny != locationY) {
            locationX = nx;
            locationY = ny;
            notificator.locationChanged(nx, ny);
        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onGoBuilding(GoBuilding goBuilding) {
        if (goBuilding.getId() != null) {
            if (goBuilding.getId() == 0) {
                building = null;
                notificator.buildingExited();
            } else {
                building = new Building();
                building.setType(goBuilding.getType());
                building.setId(goBuilding.getId());
                notificator.buildingEntered(goBuilding.getId());
            }

        }
        return false;
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    boolean onGoError(GoError error) {
        notificator.buildingNotEntered();
        return false;
    }

    public long getWaitTime() {
        long currentTime = userService.currentTime();
        return locationTime > currentTime ? locationTime - currentTime : 0;
    }

    public void move(LocationDirection direction) {
        if (building != null) {
            throw new IllegalStateException("Inside building");
        }
        if (getWaitTime() > 0) {
            throw new IllegalStateException("Too early for location change");
        }
        GoLocation goLocation = new GoLocation();
        goLocation.setDirection(direction);
        goLocation.setCurrentTime(userService.currentTime());
        goLocation.setLocationTime(locationTime);
        server(goLocation);
    }

    public void enterBuilding(int id) {
        if (building != null) {
            throw new IllegalStateException("Inside building");
        }
        GoBuilding messsage = new GoBuilding();
        messsage.setId(id);
        server(messsage);
    }

    public void exitBuilding() {
        if (building == null) {
            throw new IllegalStateException("Not inside building");
        }
        GoBuilding messsage = new GoBuilding();
        messsage.setId(0);
        server(messsage);
    }

    public Building getBuilding() {
        return building;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void addListener(WorldMapListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WorldMapListener listener) {
        listeners.remove(listener);
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }
}
