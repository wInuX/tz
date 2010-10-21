package tz.interceptor.game.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.Assert;
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

/**
 * @author Dmitry Shyshkin
 */
public class BattleServiceTest0 extends BattleServiceTest {
    @Override
    protected String getBattleName() {
        return "battle0.xml";
    }

    @Override
    protected String getUserName() {
        return "green-rat";
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

        autoCollector.makeTurn();
    }

    @Test
    public void testTurn20() {
        battleService.onBattle(battleView.getBattle());
        makeTurns(20);

        User player = battleService.getPlayer();
        Assert.assertEquals(player.getReadablePosition(), "P4");
        Assert.assertEquals(battleService.getItems().size(), 0);
    }

}
