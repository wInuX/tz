package tz.game.scenario.manager;

import com.google.inject.ImplementedBy;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(ArsenalManagerImpl.class)
public interface ArsenalManager {
    void collect(String name, int count) throws InterruptedException;
}
