package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.xml.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class AutoCollectorImpl extends AbstractService {
    @Inject
    private BattleService battleService;

    @Inject
    private ChatService chatService;

    @Inject
    private GameState gameState = new GameState();
    private BattleListener battleListener = new AbstractBattleListener() {
        @Override
        public void turnStarted(int turnNumber) {
            schedule(new Runnable() {
                public void run() {
                    makeTurn();
                }
            }, 2000);
        }

        @Override
        public void battleEnd() {
            battleService.removeListener(this);
        }
    };

    @Override
    public void initialize() {
        chatService.addCommand("cl", new CommandListener() {
            public void onCommand(String command, String[] parameters) {
                makeTurn();
                battleService.addListener(battleListener);
            }
        });
    }

    protected void makeTurn() {
        if (!battleService.isInBattle() || battleService.getUsers().size() != 0 || battleService.getItems().size() == 0) {
            battleService.removeListener(battleListener);
            return;
        }
        List<Item> items = new ArrayList<Item>(battleService.getItems());

        int[][] m = buidMap(items);
        User player = battleService.getPlayer();
        int px = player.getX();
        int py = player.getY();
        Direction direction = player.getDirection();
        List<BattleAction> actions = new ArrayList<BattleAction>();
        int cmove = 0;
        // 2 6 12 20
        int[] moves = new int[]{2, 4, 6, 8, 10, 12};
        int od = gameState.getOd();
        if (player.getPosition() != Position.WALK) {
            od -= 3;
            actions.add(new ActionPosition(Position.WALK));
        }
        aloop:
        while (items.size() > 0) {
            int current = m[px][py];
            if (current == 1) {
                for (Item item: new ArrayList<Item>(items)) {
                    boolean match = false;
                    for (Direction d: Direction.values()) {
                        int dx = d.moveX(px, py);
                        int dy = d.moveY(px, py);
                        if (item.getX() == dx && item.getY() == dy) {
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        continue;
                    }
                    od -= 2;
                    if (od < 0) {
                        break;
                    }
                    ActionPickup pickup = new ActionPickup();
                    pickup.setSection(0);
                    pickup.setId(item.getId());
                    actions.add(pickup);
                    items.remove(item);
                }
                m = buidMap(items);
                continue;
            }
            int nx = direction.moveX(px, py);
            int ny = direction.moveY(px, py);
            if (battleService.getMapCell(nx, ny) != null && m[px][py] == m[nx][ny] + 1) {
                // make step
                od -= moves[cmove++];
                if (od < 0) {
                    break;
                }
                actions.add(new ActionGo(direction));
                px = nx;
                py = ny;
                continue;
            }
            for (Direction d : Direction.values()) {
                nx = d.moveX(px, py);
                ny = d.moveY(px, py);
                if (battleService.getMapCell(nx, ny) != null && m[px][py] == m[nx][ny] + 1) {
                    // rotate
                    direction = d;
                    continue aloop;
                }
            }
            System.err.println("can't do");
            return;
        }
        server(new BattleStart(battleService.getTurnNumber() + 1));
        for (BattleAction action : actions) {
            server(action);
        }
        server(new BattleEnd());
    }

    private int[][] buidMap(List<Item> items) {
        int w = battleService.getMapWidth();
        int h = battleService.getMapHeight();
        int[][] m = new int[w][h];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                m[i][j] = w + h;
            }
        }
        for(Item item: items) {
            boolean[][] b = new boolean[w][h];
            Queue<Point> queue = new LinkedList<Point>();
            queue.add(new Point(item.getX(), item.getY(), 0));
            b[item.getX()][item.getY()] = true;
            while (!queue.isEmpty()) {
                Point point = queue.remove();
                for (Direction d : Direction.values()) {
                    int nx = d.moveX(point.x, point.y);
                    int ny = d.moveY(point.x, point.y);
                    BattleMapCell cell = battleService.getMapCell(nx, ny);
                    if (cell == null || !cell.isWalkable()) {
                        continue;
                    }
                    if (!b[nx][ny] && m[nx][ny] > point.depth + 1) {
                        b[nx][ny] = true;
                        m[nx][ny] = point.depth + 1;
                        queue.add(new Point(nx, ny, point.depth + 1));
                    }
                }
            }
        }
        System.out.println();
        for (int j = 0; j < h; ++j) {
            for (int i = 0; i < w; ++i) {
                char ch;
                if (!battleService.getMapCell(i, j).isWalkable()) {
                    ch = 'x';
                } else if (m[i][j] < 10) {
                    ch = (char) ('0' + m[i][j]);
                } else if (m[i][j] == w + h){
                    ch = 'N';
                } else {
                    ch = '+';
                }
                System.out.print(ch);
            }
            System.out.println();
        }
        return m;
    }

    private static class Point {
        private int x;
        private int y;
        private int depth;


        private Point(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }
}
