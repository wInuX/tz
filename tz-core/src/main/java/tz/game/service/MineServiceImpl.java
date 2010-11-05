package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.game.InterceptorPriority;
import tz.xml.Building;
import tz.xml.BuildingCategory;
import tz.xml.Mine;
import tz.xml.MineDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class MineServiceImpl extends AbstractService implements MineService {
    private MineMapCell[][] map;

    private int mapSize;

    private List<MineListener> listeners = new ArrayList<MineListener>();

    private MineListener notificator = Notificator.createNotificator(MineListener.class, listeners);

    @Inject
    private WorldMapService worldMapService;

    private Building mine;

    private int roomNumber;

    @Override
    public void initialize() {
        worldMapService.addListener(new AbstractWorldMapListener() {
            @Override
            public void buildingEntered(int id) {
                Building building = worldMapService.getBuilding();
                if (building.getType().getCategory() == BuildingCategory.MINE) {
                    mine = building;
                }
            }

            @Override
            public void buildingExited() {
                mine = null;
                map = null;
            }
        });
    }

    @Intercept(value = InterceptionType.SERVER, priority = InterceptorPriority.EARLY)
    void onMine(Mine mine) {
        int size = mine.getMapSize();
        mapSize = (size - 1) /2;
        if (map != null) {
            map = new MineMapCell[size][size];
            String[] lines = mine.getMap().split("\n");
            for (int i = 0; i < size; ++size) {
                for (int j = 0; j < size; ++size) {
                    map[i][j] = new MineMapCell(lines[j].charAt(i), getRoomNumber(i, j), i, j);
                }
            }
        }
        roomNumber = mine.getRoom();
        notificator.roomEntered(getCurrentRoom());
    }

    private int getRoomNumber(int x, int y) {
        x -= mapSize;
        y -= mapSize;
        int dx  = x >= 0 ? 2 * x : (-2 * x) - 1;
        int dy =  y >= 0 ? 64 * y : (-64 * y) - 32;
        return dx + dy;
    }

    public MineMapCell[][] getMap() {
        if (mine == null) {
            throw new IllegalStateException("not in mine");
        }
        return map;
    }

    public void move(MineDirection direction) {
        if (mine == null) {
            throw new IllegalStateException("not in mine");
        }
        Mine mine = new Mine();
        mine.setDirection(direction);
        server(mine);
    }

    public MineMapCell getCurrentRoom() {
        return getRoom(roomNumber);
    }

    public MineMapCell getRoom(int roomNumber) {
        return map[getX(roomNumber)][getY(roomNumber)];
    }

    public int getX(int roomNumber) {
        int dx = roomNumber & 31;
        int x = (dx & 1) > 0 ? (dx + 1) / -2 : dx / 2;
        return x + mapSize;
    }

    public int getY(int roomNumber) {
        int dy = roomNumber & ~31;
        int y = (dy & 32) > 0 ? (dy + 32) / -64 : dy / 64;
        return y + 1;
    }

    public void addListener(MineListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MineListener listener) {
        listeners.remove(listener);
    }
}
