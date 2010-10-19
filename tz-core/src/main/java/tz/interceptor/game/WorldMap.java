package tz.interceptor.game;

/**
 * @author Dmitry Shyshkin
 */
public class WorldMap {
    private MapCell[][] cells = new MapCell[360][360];

    public MapCell getCell(int x, int y) {
        if (x < 0 ) x = 360 + x;
        if (y < 0) y = 360 + y;
        return cells[x][y];
    }

    public void setCell(int x, int y, MapCell cell) {
        if (x < 0 ) x = 360 + x;
        if (y < 0) y = 360 + y;
        cells[x][y] = cell;
    }


}
