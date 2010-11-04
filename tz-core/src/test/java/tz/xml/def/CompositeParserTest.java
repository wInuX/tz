package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;
import tz.xml.transform.XmlComposite;
import tz.xml.transform.XmlPropertyMapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
public class CompositeParserTest extends AbstractParserTest {
    @Test
    public void testComposite() throws ParserException {
        C c = (C)deserialize("<C a=\"a\" b=\"b\"></C>", C.class);
        Assert.assertNotNull(c);
        Assert.assertNotNull(c.a);
        Assert.assertEquals(c.a.a, "a");
        Assert.assertEquals(c.a.b, "b");

        Assert.assertEquals(serialize(c, C.class), "<C a=\"a\" b=\"b\"/>");
    }

    @Test
    public void testCompositeOverride() throws ParserException {
        D d = (D)deserialize("<D na=\"a\" nb=\"b\"></D>", D.class);
        Assert.assertNotNull(d);
        Assert.assertNotNull(d.a);
        Assert.assertEquals(d.a.a, "a");
        Assert.assertEquals(d.a.b, "b");

        Assert.assertEquals(serialize(d, D.class), "<D na=\"a\" nb=\"b\"/>");
    }

    @Test
    public void testCompositeOverride2() throws ParserException {
        E e = (E)deserialize("<E aa=\"a\" ab=\"b\" ba=\"c\" bb=\"d\"/>", E.class);
        Assert.assertNotNull(e.a);
        Assert.assertNotNull(e.b);
        Assert.assertEquals(e.a.a, "a");
        Assert.assertEquals(e.a.b, "b");
        Assert.assertEquals(e.b.a, "c");
        Assert.assertEquals(e.b.b, "d");

        Assert.assertEquals(serialize(e, E.class), "<E aa=\"a\" ab=\"b\" ba=\"c\" bb=\"d\"/>");
    }

    @XmlRootElement(name = "A")
    private static class A {
        @XmlAttribute
        private String a;

        @XmlAttribute
        private String b;

    }

    @XmlRootElement(name = "C")
    private static class C {
        @XmlComposite
        private A a;
    }

    @XmlRootElement
    private static class D {
        @XmlComposite(override = {
                @XmlPropertyMapping(propety = "a", name = "na"),
                @XmlPropertyMapping(propety = "b", name = "nb")
        })
        private A a;
    }

    @XmlRootElement
    private static class E {
        @XmlComposite(override = {
                @XmlPropertyMapping(propety = "a", name = "aa"),
                @XmlPropertyMapping(propety = "b", name = "ab")
        })
        private A a;

        @XmlComposite(override = {
                @XmlPropertyMapping(propety = "a", name = "ba"),
                @XmlPropertyMapping(propety = "b", name = "bb")
        })
        private A b;

    }

}
