package tz.game.scenario.manager;

import com.google.inject.ImplementedBy;
import tz.xml.MineDirection;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(MineManagerImpl.class)
public interface MineManager {
    void move(MineDirection direction) throws InterruptedException;

    void gotoRoom(int room) throws InterruptedException;

    void gotoDepth(int from, int to) throws InterruptedException;

    void gotoByPath(int[] path) throws InterruptedException;
}
