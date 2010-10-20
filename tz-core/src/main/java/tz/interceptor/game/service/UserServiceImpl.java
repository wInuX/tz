package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.LoginOk;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class UserServiceImpl {
    @Inject
    private GameState gameState;

    @Intercept(InterceptionType.SERVER)
    boolean onLoginOk(LoginOk loginOk) {
        gameState.setLogin(loginOk.getLogin());
        return false;
    }
}
