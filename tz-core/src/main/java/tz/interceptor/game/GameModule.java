package tz.interceptor.game;

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

    void serverChar(Object message);

    void schedule(Runnable runnable, long delay);
}
