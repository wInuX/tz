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
}
