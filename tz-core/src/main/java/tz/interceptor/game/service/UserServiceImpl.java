package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.LoginOk;
import tz.xml.MyParameters;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class UserServiceImpl extends AbstractService {
    @Inject
    private GameState gameState;

    @Intercept(InterceptionType.SERVER)
    boolean onLoginOk(LoginOk loginOk) {
        gameState.setLogin(loginOk.getLogin());
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onMyParam(MyParameters myParameters) {
        if (myParameters.getItems() != null && myParameters.getItems().size() > 0) {
            gameState.setItems(myParameters.getItems());
        }
        if (myParameters.getOd() != null) {
            gameState.setOd(myParameters.getOd());
        }
        return false;
    }
}
