package tz.game;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.BeforeMethod;
import tz.ParserException;
import tz.interceptor.MessageControl;
import tz.interceptor.MessageListener;
import tz.service.Parser;
import tz.xml.*;

/**
 * @author Dmitry Shyshkin
 */
public class AbstractGameTest {
    private Game game = new Game(new Object());

    private MessageListener gameListener;

    @BeforeMethod
    public void setupGame() {
        game.setGameControl(new MessageControl() {
            public void server(String content) {

            }

            public void client(String content) {

            }

        });
        gameListener = game.getGameListener();
        addServerInterceptor(Key.class, InterceptorPriority.LATE, new Interceptor<Key>() {
            public boolean intercept(String original, Key message) {
                Login login = new Login();
                login.setLogin("bot");
                fromClient(login);
                return true;
            }
        });
        addClientInterceptor(Login.class, InterceptorPriority.LATE, new Interceptor<Login>() {
            public boolean intercept(String original, Login message) {
                LoginOk ok = new LoginOk();
                ok.setLogin(message.getLogin());
                fromServer(ok);
                return true;
            }
        });
        addServerInterceptor(LoginOk.class, InterceptorPriority.LATE, new Interceptor<LoginOk>() {
            public boolean intercept(String original, LoginOk message) {
                fromClient(new GetMe());
                return true;
            }
        });
        addClientInterceptor(GetMe.class, InterceptorPriority.LATE, new Interceptor<GetMe>() {
            public boolean intercept(String original, GetMe message) {
                MyParameters myParameters = new MyParameters();
                myParameters.setLogin("bot");
                fromServer(myParameters);
                return true;
            }
        });
        Injector injector = Guice.createInjector(game);
        injector.injectMembers(this);
        game.start(injector);

        fromServer(new Key("yyaskqskjlxygduqsowvykjiklryfvme"));        
    }

    protected void addClientInterceptor(Class<?> type, InterceptorPriority priority, Interceptor<?> interceptor) {
        game.addInterceptor(InterceptionType.CLIENT, type, interceptor, priority);
    }

    protected void addServerInterceptor(Class<?> type, InterceptorPriority priority, Interceptor<?> interceptor) {
        game.addInterceptor(InterceptionType.SERVER, type, interceptor, priority);
    }

    protected void fromServer(Object message) {
        String content = Parser.marshall(message, "client");
        try {
            gameListener.server(content, Parser.unmarshall(content, "client"));
        } catch (ParserException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void fromClient(Object message) {
        String content = Parser.marshall(message, "server");
        try {
            gameListener.client(content, Parser.unmarshall(content, "server"));
        } catch (ParserException e) {
            throw new IllegalStateException(e);
        }
    }

}
