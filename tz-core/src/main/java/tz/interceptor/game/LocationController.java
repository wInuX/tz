package tz.interceptor.game;

import com.google.inject.Inject;
import tz.xml.*;

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

    @Intercept(InterceptionType.CHAT_CLIENT)
    boolean onChatMessage(String original, Post post) {
        if (post.isPrivate() && post.getLogin().equals(state.getLogin())) {
//            GoLocation goLocation = new GoLocation();
//            goLocation.setDirection(LocationDirection.N);
//            game.getGameControl().server(new Message(goLocation));
            return false;
        }
        return false;
    }
}
