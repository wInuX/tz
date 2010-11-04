package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
public class SimpleParserTest extends AbstractParserTest {
    @Test
    public void testSimple() throws ParserException {
        A a = (A) deserialize("<A></A>", A.class);
        Assert.assertNotNull(a);

        Assert.assertEquals(serialize(a, A.class), "<A/>");
    }

    @Test
    public void testAttributes() throws ParserException {
        A a = (A) deserialize("<A a=\"b\"></A>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.a, "b");

        Assert.assertEquals(serialize(a, A.class), "<A a=\"b\"/>");
    }

    @Test
    public void testAttributes2() throws ParserException {
        A a = (A) deserialize("<A a=\"b\" b=\"c\"/>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.a, "b");
        Assert.assertEquals(a.b, "c");

        Assert.assertEquals(serialize(a, A.class), "<A a=\"b\" b=\"c\"/>");
    }

    @XmlRootElement(name = "A")
    private static class A {
        @XmlAttribute
        private String a;

        @XmlAttribute
        private String b;

    }
}
