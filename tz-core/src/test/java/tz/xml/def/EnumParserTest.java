package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
public class EnumParserTest extends AbstractParserTest {
    @Test
    public void testEnum1() throws ParserException {
        E e = (E)deserialize("<E e1=\"a\"></E>", E.class);
        Assert.assertEquals(e.e1, E1.A);
        Assert.assertEquals(serialize(e, E.class),"<E e1=\"a\"/>");
    }

    @Test
    public void testEnum2() throws ParserException {
        E e = (E)deserialize("<E e2=\"A\"></E>", E.class);
        Assert.assertEquals(e.e2, E2.A);
        Assert.assertEquals(serialize(e, E.class),"<E e2=\"A\"/>");
    }

    @Test
    public void testUnknownValue() throws ParserException {
        E e = (E)deserialize("<E e1=\"N\"/>", E.class);
        Assert.assertNull(e.e1);
    }

    @XmlRootElement(name = "E")
    private static class E {
        @XmlAttribute
        private E1 e1;

        @XmlAttribute
        private E2 e2;
    }

    private enum E1 {
        @XmlEnumValue("a")
        A,
        @XmlEnumValue("b")
        B,
        @XmlEnumValue("c")
        C
    }

    private enum E2 {
        A,
        B,
        C
    }
}
