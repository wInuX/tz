package tz.interceptor.game.service;

import com.google.inject.Inject;
import tz.interceptor.game.GameModule;

/**
 * @author Dmitry Shyshkin
 */
public abstract class AbstractService implements GameService {
    @Inject
    private GameModule module;

    public void initialize() {

    }

    public void destroy() {

    }

    public void client(Object message) {
        module.client(message);
    }

    public void server(Object message) {
        module.server(message);
    }

    public void clientChat(Object message) {
        module.clientChat(message);
    }

    public void serverChar(Object message) {
        module.serverChar(message);
    }

    public void schedule(Runnable runnable, long delay) {
        module.schedule(runnable, delay);
    }
}
