package tz.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;

/**
 * @author Dmitry Shyshkin
 */
public class NormalizerTest {
    @Test
    public void testSimple() throws ParserException {
        check("<A/>", Normalizer.Status.OK, "<A/>");
        check(" <A/>", Normalizer.Status.OK, "<A/>");
        check("<A/> ", Normalizer.Status.OK, "<A/>");
        check("<A b=\"c\"/>", Normalizer.Status.OK, "<A b=\"c\"/>");
        check("<A b=\"c\" b=\"c\"/>", Normalizer.Status.OK, "<A b=\"c\"/>");
        check("<A b=\"c\"b=\"c\"/>", Normalizer.Status.OK, "<A b=\"c\"/>");
    }

    @Test
    public void testNeedMore() throws ParserException {
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
    public void testPartial() throws ParserException {
        check("<A/><B/>", Normalizer.Status.PARTIAL, "<A/>", "<B/>");
        check("<A></A><B/>", Normalizer.Status.PARTIAL, "<A>\n</A>", "<B/>");

    }

    @Test
    public void testFormating() throws ParserException {
        check("<A><B/><B/></A>", Normalizer.Status.OK, "<A>\n <B/>\n <B/>\n</A>");
        check("<A><B>\n\n\n<C/></B></A>", Normalizer.Status.OK, "<A>\n <B>\n  <C/>\n </B>\n</A>");
    }

    private void check(String input, Normalizer.Status status, String output) throws ParserException {
        Normalizer normalizer = new Normalizer(input);
        Assert.assertEquals(normalizer.normalize(), status);
        if (status != Normalizer.Status.NEEDMORE) {
            Assert.assertEquals(normalizer.getNormalized(), output);
        }
    }

    private void check(String input, Normalizer.Status status, String output, String rest) throws ParserException {
        Normalizer normalizer = new Normalizer(input);
        Assert.assertEquals(normalizer.normalize(), status);
        Assert.assertEquals(normalizer.getNormalized(), output);
        Assert.assertEquals(normalizer.getUnparsed(), rest);

    }

}
