package tz.game.scenario;

import com.google.inject.Inject;
import tz.game.GameModule;

/**
 * @author Dmitry Shyshkin
 */
public abstract class Scenario extends Thread {
    @Inject
    private GameModule module;

    public void load() {
        
    }

    public void unload() {
        interrupt();
    }

    public abstract void execute() throws InterruptedException;

    @Override
    public void run() {
        synchronized (module.getMonitor()) {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
