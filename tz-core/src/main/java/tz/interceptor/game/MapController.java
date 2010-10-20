package tz.interceptor.game;

import com.google.inject.Inject;
import tz.xml.Location;
import tz.xml.MiniMap;

/**
 * @author Dmitry Shyshkin
 */
public class MapController implements Controller {
    @Inject
    private WorldMap worldMap = new WorldMap();

    @Inject
    private GameModule game;

    public void attach() {
        game.registerInterceptors(this);
    }

    public void detach() {
        game.unregisterIntegerceptors(this);
    }

    @Intercept(value = InterceptionType.SERVER)
    boolean onMiniMap(String content, MiniMap map) {
        for (Location location: map.getLocations()) {
            MapCell cell = worldMap.getCell(location.getX(), location.getY());
            cell.setLocation(location);
        }
        return false;
    }
}
