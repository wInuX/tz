package tz.interceptor.game.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tz.BattleParserException;
import tz.interceptor.game.Game;
import tz.service.Normalizer;
import tz.service.Parser;
import tz.xml.BattleView;
import tz.xml.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * @author Dmitry Shyshkin
 */
public class BattleServiceTest {
    private BattleView battleView;

    private BattleServiceImpl battleService;

    @BeforeMethod
    public void setup() throws IOException, BattleParserException {
        StringBuilder sb = new StringBuilder();
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("battle0.xml"), "UTF-8");
        char[] buf = new char[1024];
        int read;
        while ((read = reader.read(buf)) > 0) {
            sb.append(buf, 0, read);
        }
        reader.close();
        Normalizer normalizer = new Normalizer(sb.toString());
        normalizer.normalize();
        battleView = Parser.parseBattle(normalizer.getNormalized());

        Game game = new Game();
        Injector injector = Guice.createInjector(game);
        game.start(injector);

        battleService = (BattleServiceImpl) injector.getInstance(BattleService.class);
        injector.getInstance(GameState.class).setLogin("green-rat");
    }

    @Test
    public void testLoad() {
        battleService.onBattle(battleView.getBattle());

        Assert.assertTrue(battleService.isInBattle());
        Assert.assertEquals(battleService.getUsers().size(), 1);
        Assert.assertEquals(battleService.getPlayer().getLogin(), "green-rat");

        User player = battleService.getPlayer();
        User rat = battleService.getUsers().get(0);

        Assert.assertEquals(player.getReadablePosition(), "J10");
    }

    @Test
    public void testTurn1() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(1);

        User player = battleService.getPlayer();
        User rat = battleService.getUsers().get(0);

        Assert.assertEquals(player.getReadablePosition(), "I14");
        Assert.assertEquals(rat.getReadablePosition(), "H37");
    }

    @Test
    public void testTurn2() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(2);

        User player = battleService.getPlayer();
        User rat = battleService.getUsers().get(0);

        Assert.assertEquals(player.getReadablePosition(), "I18");
        Assert.assertEquals(rat.getReadablePosition(), "J37");
    }

    @Test
    public void testTurn3() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(3);

        User player = battleService.getPlayer();
        User rat = battleService.getUsers().get(0);

        Assert.assertEquals(player.getReadablePosition(), "I22");
        Assert.assertEquals(rat.getReadablePosition(), "N35");
    }

    @Test
    public void testTurn4() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(4);

        User player = battleService.getPlayer();
        User rat = battleService.getUsers().get(0);

        Assert.assertEquals(player.getReadablePosition(), "I26");
        Assert.assertEquals(rat.getReadablePosition(), "Q34");
    }

    @Test
    public void testTurn17() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(17);

        User player = battleService.getPlayer();
        Assert.assertEquals(player.getReadablePosition(), "S13");
        Assert.assertEquals(battleService.getUsers().size(), 0);
    }

    @Test
    public void testTurn20() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(20);

        User player = battleService.getPlayer();
        Assert.assertEquals(player.getReadablePosition(), "P4");
        Assert.assertEquals(battleService.getItems().size(), 0);
    }

    private void makeTurns(int count) {
        for (int i = 0; i < count; ++i) {
            try {
                battleService.onTurn(battleView.getTurns().get(i));
            } catch (RuntimeException  e) {
                throw new IllegalStateException("Error processing step " + (i + 1), e);
            }
        }
    }
}
