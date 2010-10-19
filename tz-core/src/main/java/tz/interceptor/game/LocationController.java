package tz.interceptor.game;

import com.google.inject.Inject;
import tz.xml.MyParameters;

/**
 * @author Dmitry Shyshkin
 */
public class LocationController implements Controller {
    @Inject
    private GameState state;

    @Inject
    private GameModule game;

    public void attach() {
        game.registerInterceptors(this);
    }

    public void detach() {
        game.unregisterIntegerceptors(this);
    }

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
