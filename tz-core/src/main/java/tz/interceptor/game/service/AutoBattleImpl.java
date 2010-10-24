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
public class AutoBattleImpl extends AbstractService {
    @Inject
    private BattleService battleService;

    @Inject
    private ChatService chatService;

    @Inject
    private GameState gameState;

    @Inject
    private UserService userService;

    private boolean failed;

    private Integer expectedX;

    private Integer expectedY;

    private BattleListener battleListener = new AbstractBattleListener() {
        @Override
        public void battleStart() {
            expectedX = null;
            expectedY = null;
            failed = false;
        }

        @Override
        public void turnStarted(int turnNumber) {
            if (failed) {
                return;
            }
            if (expectedX == null && expectedY == null) {
                makeTurn();
            } else {
                User player = battleService.getPlayer();
                if (player.getX() != expectedX || player.getY() != expectedY) {
                    System.err.println("Failed Expected " + expectedX  + ", " + expectedY + " but found " + player.getX() + ", " + player.getY());
                    failed = true;
                } else  {
                    schedule(new Runnable() {
                        public void run() {
                            makeTurn();
                        }
                    }, 1500);
                }
            }
        }

        @Override
        public void battleEnd() {

        }
    };
    @Override
    public void initialize() {
        chatService.addCommand("bt", new CommandListener() {
            public void onCommand(String command, String[] parameters) {
                //battleService.addListener(battleListener);
                failed = false;
                expectedX = expectedY = null;
                makeTurn();
            }
        });
        battleService.addListener(battleListener);
    }

    private void makeTurn() {
        if (!battleService.isInBattle() || battleService.getUsers().size() == 0) {
            return;
        }
        boolean ratOnly = true;
        boolean hasColor = false;
        for (User user : battleService.getUsers()) {
            if (!user.getLogin().startsWith("$rat")) {
                ratOnly = false;
            }
            if (user.getColor() != null) {
                hasColor = true;
            }
        }
        if (!ratOnly) {
            System.err.println("Not only rats");
            failed = true;
            return;
        }
        if (hasColor) {
            System.err.println("Some users has colors");
            failed = true;
            return;
        }
        List<User> users = new ArrayList<User>(battleService.getUsers());
        int[][] m = buidMap(users);
        User player = battleService.getPlayer();
        int px = player.getX();
        int py = player.getY();
        Direction direction = player.getDirection();
        List<BattleAction> actions = new ArrayList<BattleAction>();
        int cmove = 0;
        // 2 6 12 20
        int[] moves = new int[]{2, 4, 6, 8, 10, 12, 14};
        int od = gameState.getOd();
        Position position = player.getPosition();

        aloop:
        while (od > 0 && m[px][py] > 3) {
            int nx = direction.moveX(px, py);
            int ny = direction.moveY(px, py);
            if (battleService.getMapCell(nx, ny) != null && m[px][py] == m[nx][ny] + 1) {
                if (position != Position.WALK) {
                    od -= 3;
                    if (od < 0) {
                        break aloop;
                    }
                    actions.add(new ActionPosition(Position.WALK));
                    position = Position.WALK;
                }

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
            failed = true;
            return;
        }
        Item weapon = null;
        ShotDefinition shot = null;
        wloop:
        for (Item item : gameState.getItems()) {
            if (item.getUsedSlot() == null) {
                continue;
            }
            if (!item.getUsedSlot().getSlots().contains(Slot.LEFT_HAND) && !item.getUsedSlot().getSlots().contains(Slot.RIGHT_HAND)) {
                continue;
            }
            if (item.getShotDefinitions() == null) {
                continue;
            }
            for (ShotDefinition shotDefinition : item.getShotDefinitions()) {
                if (shotDefinition.getType() == ShotType.AIMED) {
                    weapon = item;
                    shot = shotDefinition;
                    break wloop;
                }
            }
            for (ShotDefinition shotDefinition : item.getShotDefinitions()) {
                if (shotDefinition.getType() == ShotType.SINGLE) {
                    weapon = item;
                    shot = shotDefinition;
                    break wloop;
                }
            }
            for (ShotDefinition shotDefinition : item.getShotDefinitions()) {
                if (shotDefinition.getType() == ShotType.ENERGY_SHORT) {
                    weapon = item;
                    shot = shotDefinition;
                    break wloop;
                }
            }

        }
        if (weapon == null || shot == null) {
            System.err.println("no weapon");
            failed = true;
            return;
        }
        System.out.println("Found weapon: " + weapon.getText());
        if (weapon.getQuality() < 2) {
            System.err.println("weapon in bad condition");
            failed = true;
            return;
        }
        int loadedAmmo = 0;
        if (weapon.getInsertions() != null) {
            for (Item insertion : weapon.getInsertions()) {
                loadedAmmo = insertion.getCount();
            }
        }
        System.out.println("Loaded ammo: " + loadedAmmo);
        Item ammo = null;
        for (Item item : userService.getItems()) {
            if (item.getCalibre() == null || item.getText() == null) {
                continue;
            }
            if (item.getCalibre().equals(weapon.getCalibre()) && (item.getText().contains("ammo") || item.getText().contains("Energy cell"))) {
                ammo = item;
                break;
            }
        }
        if (ammo == null) {
            System.err.println("no ammo");
            failed = true;
            return;
        }
        if (ammo.getCount() < 20) {
            System.err.println("low ammo");
            failed = true;
            return;
        }
        System.out.println("Ammo: " + ammo.getCount());

        aloop:
        while (od > 0) {
            if (loadedAmmo == 0) {
                od -= weapon.getReloadOD();
                if (od < 0) {
                    break;
                }
                actions.add(new ActionReload(weapon.getId(), ammo.getId(), 1)); // what is 1???
                loadedAmmo = weapon.getAmmoMaxCount();
            }
            int mx = px;
            int my = py;
            while (m[mx][my] != 0) {
                for (Direction d: Direction.values()) {
                    int nx = d.moveX(mx, my);
                    int ny = d.moveY(mx, my);
                    if (m[mx][my] == m[nx][ny] + 1) {
                        mx = nx;
                        my = ny;
                    }
                }
            }
            User target = null;
            for (User user : users) {
                if (user.getX() == mx && user.getY() == my) {
                    target = user;
                    break;
                }
            }
            if (target == null) {
                System.err.println("No target");
                failed = true;
                return;
            }
            if (position != Position.SEAT) {
                od -= 3;
                if (od < 0) {
                    break aloop;
                }
                actions.add(new ActionPosition(Position.SEAT));
                position = Position.SEAT;
            }

            while (loadedAmmo > 0) {
                od -= shot.getOd();
                if (od < 0) {
                    break aloop;
                }
                actions.add(new ActionFire(weapon.getId(), target.getLogin(), shot.getType(), 2));
                --loadedAmmo;
            }
        }
        expectedX = px;
        expectedY = py;
        server(new BattleStart(battleService.getTurnNumber() + 1));
        for (BattleAction action : actions) {
            server(action);
        }
        server(new BattleEnd());
    }

    private int[][] buidMap(List<User> users) {
        int w = battleService.getMapWidth();
        int h = battleService.getMapHeight();
        int[][] m = new int[w][h];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                m[i][j] = w + h;
            }
        }
        for(User user: users) {
            boolean[][] b = new boolean[w][h];
            Queue<Point> queue = new LinkedList<Point>();
            queue.add(new Point(user.getX(), user.getY(), 0));
            b[user.getX()][user.getY()] = true;
            m[user.getX()][user.getY()] = 0;
            while (!queue.isEmpty()) {
                Point point = queue.remove();
                for (Direction d : Direction.values()) {
                    int nx = d.moveX(point.x, point.y);
                    int ny = d.moveY(point.x, point.y);
                    BattleMapCell cell = battleService.getMapCell(nx, ny);
                    if (cell == null || !cell.isWalkable()) {
                        continue;
                    }
                    if (battleService.getItem(nx, ny) != null) {
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
