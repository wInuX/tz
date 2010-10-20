package tz.interceptor.game;

import com.google.inject.Inject;
import tz.xml.LoginOk;

/**
 * @author Dmitry Shyshkin
 */
public class UserController implements Controller {
    @Inject
    private GameState gameState;

    @Inject
    private GameModule game;

    public void attach() {
        game.registerInterceptors(this);
    }

    public void detach() {
        game.unregisterIntegerceptors(this);
    }

    @Intercept(InterceptionType.SERVER)
    boolean onLoginOk(String content, LoginOk loginOk) {
        gameState.setLogin(loginOk.getLogin());
        return false;
    }
}
