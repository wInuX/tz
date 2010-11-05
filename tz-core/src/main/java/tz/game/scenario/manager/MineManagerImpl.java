package tz.game.scenario.manager;

import com.google.inject.Inject;
import tz.game.scenario.Condition;
import tz.game.service.*;
import tz.xml.MineDirection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Dmitry Shyshkin
 */
public class MineManagerImpl implements MineManager {
    @Inject
    private MineService mineService;
    @Inject
    private RandomService randomService;

    public void move(MineDirection direction) throws InterruptedException {
        final Condition condition = new Condition();
        MineListener listener = new AbstractMineListener() {
            @Override
            public void roomEntered(MineMapCell cell) {
                condition.signal();
            }
        };
        mineService.addListener(listener);
        try {
            mineService.move(direction);
            condition.await();
        } finally {
            mineService.removeListener(listener);
        }
    }

    public void gotoByPath(int[] path) throws InterruptedException {
        MineMapCell current = mineService.getCurrentRoom();
        for (int room : path) {
            MineMapCell next = mineService.getRoom(room);
            int dx = next.getX() - current.getX();
            int dy = next.getY() - current.getY();
            MineDirection direction = MineDirection.getDirection(dx, dy);
            move(direction);
            current = next;
        }
    }

    public void gotoRoom(int room) throws InterruptedException {
        int[][] v = buildMap();
        MineMapCell[][] map = mineService.getMap();
        MineMapCell target = mineService.getCurrentRoom();
        MineMapCell current = mineService.getRoom(room);
        int[] path = new int[v[current.getX()][current.getY()]];
        int p = path.length - 1;
        while (current != target) {
            List<MineDirection> directions = new ArrayList<MineDirection>();
            for (MineDirection direction : MineDirection.values()) {
                int nx = current.getX() + direction.getDx();
                int ny = current.getY() + direction.getDy();
                if (nx < 0 || nx >= v.length || ny < 0 || ny >= v.length) {
                    continue;
                }
                if (v[current.getX()][current.getY()] == v[nx][ny] + 1) {
                    directions.add(direction);
                }
            }
            if (directions.isEmpty()) {
                throw new IllegalStateException();
            }
            MineDirection direction = randomService.randomElement(directions);
            MineMapCell prev = map[current.getX() + direction.getDx()][current.getY() + direction.getDy()];
            path[p--] = prev.getNumber();
            current = prev;
        }
        gotoByPath(path);
    }

    public void gotoDepth(int from, int to) throws InterruptedException {
        int min = Integer.MAX_VALUE;
        List<Integer> rooms = new ArrayList<Integer>();
        MineMapCell[][] map = mineService.getMap();
        int[][] v = buildMap();
        for (int i = 0; i < v.length; ++i) {
            for (int j = 0; j < v.length; ++j) {
                int depth = Math.abs(i - v.length / 2) + Math.abs(j - v.length / 2);
                if (depth < from || depth > to) {
                    continue;
                }
                if (v[i][j] < min) {
                    min = v[i][j];
                    rooms.clear();
                }
                if (v[i][j] == min) {
                    rooms.add(map[i][j].getNumber());
                }
            }
        }
        if (rooms.size() == 0) {
            throw new IllegalStateException();
        }
        gotoRoom(randomService.randomElement(rooms));
    }

    private int[][] buildMap() {
        MineMapCell[][] map = mineService.getMap();
        boolean[][] visited = new boolean[map.length][map.length];
        int[][] v = new int[map.length][map.length];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map.length; ++j) {
                v[i][j] = Integer.MAX_VALUE;
            }
        }
        MineMapCell cell = mineService.getCurrentRoom();
        int sx = cell.getX();
        int sy = cell.getY();
        Queue<MineMapCell> queue = new LinkedList<MineMapCell>();
        queue.add(map[sx][sy]);
        visited[sx][sy] = true;
        v[sx][sy] = 0;
        while(!queue.isEmpty()) {
            MineMapCell current = queue.remove();
            int x = current.getX();
            int y = current.getY();
            for (MineDirection direction : MineDirection.values()) {
                int nx = x + direction.getDx();
                int ny = y + direction.getDy();
                if (nx < 0 || nx >= map.length || ny < 0 || ny >= map.length) {
                    continue;
                }
                if (map[nx][ny].walkable() && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(map[nx][ny]);
                    v[nx][ny] = v[x][y] + 1;
                }
            }
        }
        return v;
    }
}
