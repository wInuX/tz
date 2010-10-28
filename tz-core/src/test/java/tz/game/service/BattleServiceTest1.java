package tz.game.service;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Dmitry Shyshkin
 */
public class BattleServiceTest1 extends BattleServiceTest {
    @Override
    protected String getBattleName() {
        return "battle1.xml";
    }

    @Override
    protected String getUserName() {
        return "с т а л к е р";
    }

    @Test
    public void testLoad() {
        battleService.onBattle(null, battleView.getBattle());

        Assert.assertEquals(battleService.getUser("$rat11453").getReadablePosition(), "S6");
        Assert.assertEquals(battleService.getUser("$rat49677").getReadablePosition(), "P7");
        Assert.assertEquals(battleService.getUser("$stich74073").getReadablePosition(), "N36");
    }

    @Test
    public void testTurn4() {
        battleService.onBattle(null, battleView.getBattle());
        makeTurns(4);
    }

}
