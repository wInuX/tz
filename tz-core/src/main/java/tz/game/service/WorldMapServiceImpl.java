package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class WorldMapServiceImpl extends AbstractService implements WorldMapService {
    @Inject
    private GameState state;

    private WorldMap worldMap = new WorldMap();

    private List<WorldMapListener> listeners = new ArrayList<WorldMapListener>();

    private WorldMapListener notificator = Notificator.createNotificator(WorldMapListener.class, listeners);

    @Intercept(value = InterceptionType.SERVER)
    boolean onMiniMap(MiniMap map) {
//        for (Location location: map.getLocations()) {
//            MapCell cell = worldMap.getCell(location.getX(), location.getY());
//            cell.setLocation(location);
//        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onMyParameters(MyParameters message) {
        if (message.getX() != null && message.getY() != null) {
            state.setX(message.getX());
            state.setY(message.getY());
            notificator.locationChanged(message.getX(), message.getY());
        }
        if (message.getLocationTime() != null) {
            state.setLocationTime(message.getLocationTime());
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onGoBuilding(GoBuilding goBuilding) {
        if (goBuilding.getN() != null) {
            if (goBuilding.getN() == 0) {
                notificator.buildingExited();
            } else {
                notificator.buildingEntered(goBuilding.getN());
            }

        }
        return false;
    }

    public void gotoLocation(int x, int y, Runnable callback) {
        //TODO:
        throw new IllegalStateException(); 
    }

    public void move(LocationDirection direction) {
        GoLocation goLocation = new GoLocation();
        goLocation.setDirection(direction);
        server(goLocation);
    }

    public void enterBuilding(int id) {
        GoBuilding messsage = new GoBuilding();
        messsage.setN(id);
        server(messsage);
    }

    public void exitBuilding() {
        GoBuilding messsage = new GoBuilding();
        messsage.setN(0);
        server(messsage);
    }

    private void waitLocationTime(Runnable runnable) {
        
    }

    public void addListener(WorldMapListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WorldMapListener listener) {
        listeners.remove(listener);
    }
}
