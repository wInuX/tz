package tz.interceptor.game.service;

import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
public class WorldMap implements Serializable {
    private static final long serialVersionUID = -198488371739066564L;

    private MapCell[][] cells = new MapCell[360][360];

    public MapCell getCell(int x, int y) {
        if (x < 0 ) x = 360 + x;
        if (y < 0) y = 360 + y;
        if (cells[x][y] == null) {
            cells[x][y] = new MapCell();
        }
        return cells[x][y];
    }

    public void setCell(int x, int y, MapCell cell) {
        if (x < 0 ) x = 360 + x;
        if (y < 0) y = 360 + y;
        cells[x][y] = cell;
    }


}
