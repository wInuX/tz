package tz.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.BattleParserException;
import tz.xml.*;

/**
 * @author Dmitry Shyshkin
 */
public class ParserTest {
    @Test
    public void testUnmarshalling() throws BattleParserException {
        GoLocation goLocation = (GoLocation) unmarshall("<GOLOC/>");
        Assert.assertNotNull(goLocation);

    }

    @Test
    public void testUnmarshallingComposite() throws BattleParserException {
        BattleActions battleActions = (BattleActions) unmarshall("<BSTART/><BEND/>");
        Assert.assertNotNull(battleActions);
        Assert.assertNotNull(battleActions.getBattleStart());
        Assert.assertNotNull(battleActions.getBattleEnd());

    }

    @Test
    public void testMarshalling() throws BattleParserException {
        Assert.assertEquals(marshall(new GoLocation()), "<GOLOC/>");
    }

    @Test
    public void testMarshallingComposite() throws BattleParserException {
        BattleActions battleActions = new BattleActions();
        battleActions.setBattleStart(new BattleStart());
        battleActions.setBattleEnd(new BattleEnd());
        Assert.assertEquals(marshall(battleActions), "<BSTART/><BEND/>");
    }

    @Test
    public void testAttributeOrder() throws BattleParserException {
        Search search = new Search();
        search.setId("id");
        search.setCount(1);
        search.setS(0);
        Assert.assertEquals(marshall(search), "<AR a=\"id\" c=\"1\" s=\"0\"/>");
    }

    private Object unmarshall(String content) throws BattleParserException {
        return Parser.parseMessage("<MESSAGE>" + content + "</MESSAGE>").getValue();
    }

    private String marshall(Object value) throws BattleParserException {
        Message message = new Message();
        message.setValue(value);
        return Parser.createMessage(message);
    }

}
