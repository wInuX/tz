package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.Location;
import tz.xml.MiniMap;
import tz.xml.MyParameters;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class WorldMapServiceImpl extends AbstractService implements WorldMapService {
    @Inject
    private GameState state;

    private WorldMap worldMap = new WorldMap();

    @Intercept(value = InterceptionType.SERVER)
    boolean onMiniMap(MiniMap map) {
        for (Location location: map.getLocations()) {
            MapCell cell = worldMap.getCell(location.getX(), location.getY());
            cell.setLocation(location);
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onMyParameters(MyParameters message) {
        if (message.getX() != null && message.getY() != null) {
            state.setX(message.getX());
            state.setY(message.getY());
        }
        if (message.getLocationTime() != null) {
            state.setLocationTime(message.getLocationTime());
        }
        return false;
    }

    public void gotoLocation(int x, int y, Runnable callback) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void waitLocationTime(Runnable runnable) {
        
    }
}
