package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
public class NestingParsingTest extends AbstractParserTest {
    @Test
    public void testNesting() throws ParserException {
        B b = (B) deserialize("<B><A/></B>", B.class);
        Assert.assertNotNull(b.a);

        Assert.assertEquals(serialize(b, B.class), "<B>\n <A/>\n</B>");
    }

    @Test
    public void testRecursive() throws ParserException {
        C c = (C)deserialize("<C><C><C/></C></C>", C.class);
        Assert.assertNotNull(c.c);
        Assert.assertNotNull(c.c.c);

        Assert.assertEquals(serialize(c, C.class), "<C>\n <C>\n  <C/>\n </C>\n</C>");
    }

    @Test
    public void testRecursive2() throws ParserException {
        D d = (D)deserialize("<D><B><C/></B></D>", D.class);
        Assert.assertNotNull(d.b);
        Assert.assertNotNull(d.b.c);

        Assert.assertEquals(serialize(d, D.class), "<D>\n <B>\n  <C/>\n </B>\n</D>");
    }


    @XmlRootElement
    private static class A {
        private String a;
    }

    @XmlRootElement
    private static class B {
        @XmlElement(name = "A")
        private A a;
    }

    @XmlRootElement
    private static class C {
        @XmlElement(name = "C")
        private C c;
    }

    @XmlRootElement
    private static class D {
        @XmlElement(name = "A")
        private C a;

        @XmlElement(name = "B")
        private C b;

    }
}
