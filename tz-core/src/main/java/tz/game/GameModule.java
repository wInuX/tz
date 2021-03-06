package tz.game;

import tz.interceptor.MessageControl;

/**
 * @author Dmitry Shyshkin
 */
public interface GameModule {
    MessageControl getGameControl();

    MessageControl getChatControl();

    void registerInterceptors(Object o);

    void unregisterIntegerceptors(Object o);

    void client(Object message);

    void server(Object message);

    void clientChat(Object message);

    void serverChat(Object message);

    void schedule(Runnable runnable, long delay);

    String getSessionId();

    Object getMonitor();

    void inject(Object v);
}
