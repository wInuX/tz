package tz.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;
import tz.xml.*;

/**
 * @author Dmitry Shyshkin
 */
public class ParserTest {
    @Test
    public void testUnmarshalling() throws ParserException {
        GoLocation goLocation = (GoLocation) unmarshall("<GOLOC/>");
        Assert.assertNotNull(goLocation);

    }

    @Test
    public void testUnmarshallingComposite() throws ParserException {
        BattleActions battleActions = (BattleActions) unmarshall("<BSTART/><BEND/>");
        Assert.assertNotNull(battleActions);
        Assert.assertNotNull(battleActions.getBattleStart());
        Assert.assertNotNull(battleActions.getBattleEnd());

    }

    @Test
    public void testMarshalling() throws ParserException {
        Assert.assertEquals(marshall(new GoLocation()), "<GOLOC/>");
    }

    @Test
    public void testMarshalling2() throws ParserException {
        Assert.assertEquals(marshall(new ActionGo(Direction.EAST)), "<BGO to=\"4\"/>");
    }

    @Test
    public void testMarshallingComposite() throws ParserException {
        BattleActions battleActions = new BattleActions();
        battleActions.setBattleStart(new BattleStart());
        battleActions.setBattleEnd(new BattleEnd());
        Assert.assertEquals(marshall(battleActions), "<BSTART/><BEND/>");
    }

    @Test
    public void testAttributeOrder() throws ParserException {
        Search search = new Search();
        search.setTakeId(new Id(1, 1));
        search.setCount(1);
        search.setS(0);
        Assert.assertEquals(marshall(search), "<AR a=\"1.1\" c=\"1\" s=\"0\"/>");
    }

    private Object unmarshall(String content) throws ParserException {
        return Parser.unmarshall(content, "");
    }

    private String marshall(Object value) throws ParserException {
        return Parser.marshall(value, "");
    }

}
