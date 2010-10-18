package tz.interceptor.game;

import tz.xml.MyParameters;

/**
 * @author Dmitry Shyshkin
 */
public class LocationController {
    private GameState state;

    @Intercept(InterceptionType.SERVER)
    boolean onMyParameters(String original, MyParameters message) {
        if (message.getX() != null && message.getY() != null) {
            state.setX(message.getX());
            state.setY(message.getY());
        }
        if (message.getLocationTime() != null) {
            state.setLocationTime(message.getLocationTime());
        }
        return false;
    }

    @Intercept(InterceptionType.CHAT_SERVER)
    boolean onChatMessage(String original, String message) {
        return false;
    }
}
