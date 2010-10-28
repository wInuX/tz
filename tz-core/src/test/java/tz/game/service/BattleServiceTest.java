package tz.game.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.BeforeMethod;
import tz.BattleParserException;
import tz.game.Game;
import tz.service.Normalizer;
import tz.service.Parser;
import tz.xml.BattleView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Dmitry Shyshkin
 */
public abstract class BattleServiceTest {
    protected BattleView battleView;

    protected BattleServiceImpl battleService;

    protected AutoCollectorImpl autoCollector;

    @BeforeMethod
    public void setupBattle() throws IOException, BattleParserException {
        StringBuilder sb = new StringBuilder();
        Reader reader = new InputStreamReader(getClass().getResourceAsStream(getBattleName()), "UTF-8");
        char[] buf = new char[1024];
        int read;
        while ((read = reader.read(buf)) > 0) {
            sb.append(buf, 0, read);
        }
        reader.close();
        Normalizer normalizer = new Normalizer(sb.toString());
        normalizer.normalize();
        battleView = Parser.parseBattle(normalizer.getNormalized());

        Game game = new Game(new Object());
        Injector injector = Guice.createInjector(game);
        game.start(injector);

        battleService = (BattleServiceImpl) injector.getInstance(BattleService.class);
        autoCollector = injector.getInstance(AutoCollectorImpl.class);
        injector.getInstance(GameState.class).setLogin(getUserName());

    }

    protected void makeTurns(int count) {
        for (int i = 0; i < count; ++i) {
            try {
                battleService.onTurn(null, battleView.getTurns().get(i));
            } catch (RuntimeException  e) {
                throw new IllegalStateException("Error processing step " + (i + 1), e);
            }
        }
    }

    protected abstract String getBattleName();

    protected abstract String getUserName();
}
