package tz.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.BattleParserException;

/**
 * @author Dmitry Shyshkin
 */
public class NormalizerTest {
    @Test
    public void testSimple() throws BattleParserException {
        check("<A/>", Normalizer.Status.OK, "<A/>");
        check(" <A/>", Normalizer.Status.OK, "<A/>");
        check("<A/> ", Normalizer.Status.OK, "<A/>");
        check("<A b=\"c\"/>", Normalizer.Status.OK, "<A b=\"c\"/>");
        check("<A b=\"c\" b=\"c\"/>", Normalizer.Status.OK, "<A b=\"c\"/>");
        check("<A b=\"c\"b=\"c\"/>", Normalizer.Status.OK, "<A b=\"c\"/>");
    }

    @Test
    public void testNeedMore() throws BattleParserException {
        check("<", Normalizer.Status.NEEDMORE, null);
        check("<A", Normalizer.Status.NEEDMORE, null);
        check("<A a", Normalizer.Status.NEEDMORE, null);
        check("<A a=", Normalizer.Status.NEEDMORE, null);
        check("<A a=\"", Normalizer.Status.NEEDMORE, null);
        check("<A a=\"a", Normalizer.Status.NEEDMORE, null);
        check("<A a=\"a\"", Normalizer.Status.NEEDMORE, null);
        check("<A a=\"a\"/", Normalizer.Status.NEEDMORE, null);
        check("</", Normalizer.Status.NEEDMORE, null);
        check("</A", Normalizer.Status.NEEDMORE, null);
        check("</A ", Normalizer.Status.NEEDMORE, null);
    }

    @Test
    public void testPartial() throws BattleParserException {
        check("<A/><B/>", Normalizer.Status.PARTIAL, "<A/>", "<B/>");
        check("<A></A><B/>", Normalizer.Status.PARTIAL, "<A></A>", "<B/>");

    }

    private void check(String input, Normalizer.Status status, String output) throws BattleParserException {
        Normalizer normalizer = new Normalizer(input);
        Assert.assertEquals(normalizer.normalize(), status);
        if (status != Normalizer.Status.NEEDMORE) {
            Assert.assertEquals(normalizer.getNormalized(), output);
        }
    }

    private void check(String input, Normalizer.Status status, String output, String rest) throws BattleParserException {
        Normalizer normalizer = new Normalizer(input);
        Assert.assertEquals(normalizer.normalize(), status);
        Assert.assertEquals(normalizer.getNormalized(), output);
        Assert.assertEquals(normalizer.getUnparsed(), rest);

    }

}
