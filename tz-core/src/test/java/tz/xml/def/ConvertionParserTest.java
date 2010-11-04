package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
public class ConvertionParserTest extends AbstractParserTest {

    @Test
    public void testPrimitiveConversion() throws ParserException {
        A a = (A)deserialize("<A i=\"1\"></A>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.i, 1);

        Assert.assertEquals(serialize(a, A.class), "<A i=\"1\" l=\"0\"/>");
    }

    @Test
    public void testPrimitiveConversion2() throws ParserException {
        A a = (A)deserialize("<A l=\"1\"></A>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.l, 1);

        Assert.assertEquals(serialize(a, A.class), "<A i=\"0\" l=\"1\"/>");
    }

    @Test
    public void testWrapperConversion() throws ParserException {
        A a = (A)deserialize("<A lw=\"1\"></A>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.lw, Long.valueOf(1L));

        Assert.assertEquals(serialize(a, A.class), "<A i=\"0\" l=\"0\" lw=\"1\"/>");
    }

    @Test
    public void testWrapperConversion2() throws ParserException {
        A a = (A)deserialize("<A lw=\"1\" iw=\"2\"></A>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.lw, Long.valueOf(1L));
        Assert.assertEquals(a.iw, Integer.valueOf(2));

        Assert.assertEquals(serialize(a, A.class), "<A i=\"0\" l=\"0\" iw=\"2\" lw=\"1\"/>");
    }

    @Test
    public void testDoubleConversion() throws ParserException {
        A a = (A)deserialize("<A dw=\"1.12\" />", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.dw, 1.12d);
        Assert.assertEquals(serialize(a, A.class), "<A i=\"0\" l=\"0\" dw=\"1.12\"/>");
    }


    @XmlRootElement(name = "A")
    private static class A {
        @XmlAttribute
        private int i;

        @XmlAttribute
        private long l;

        @XmlAttribute
        private Integer iw;

        @XmlAttribute
        private Long lw;

        @XmlAttribute
        private Double dw;
    }

}
