package tz.game.scenario;

import com.google.inject.Inject;
import tz.game.GameModule;
import tz.game.service.Notificator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public abstract class Scenario extends Thread {
    @Inject
    private GameModule module;

    private List<ScenarioListener> listeners = new ArrayList<ScenarioListener>();

    private ScenarioListener notificator = Notificator.createNotificator(ScenarioListener.class, listeners);

    public void load() {

    }

    public void unload() {

    }

    public abstract void execute() throws InterruptedException;

    @Override
    public void run() {
        synchronized (module.getMonitor()) {
            load();
            notificator.scenarioLoaded(this);
            try {
                execute();
            } catch (InterruptedException e) {
                //ignore
            } finally {
                unload();
                notificator.scenarioUnloaded(this);
            }
        }
    }

    public void addListsner(ScenarioListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ScenarioListener listener) {
        listeners.remove(listener);
    }
}
