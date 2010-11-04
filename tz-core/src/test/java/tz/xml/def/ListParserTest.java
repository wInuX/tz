package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class ListParserTest extends AbstractParserTest {
    @Test
    public void testListDeserialize() throws ParserException {
        L l = (L)deserialize("<L><A/><A/></L>", L.class);
        Assert.assertNotNull(l);
        Assert.assertNotNull(l.a);
        Assert.assertEquals(l.a.size(), 2);

        Assert.assertEquals(serialize(l, L.class),"<L>\n <A/>\n <A/>\n</L>");
    }

    @XmlRootElement(name = "L")
    private static class L {
        @XmlElement(name = "A")
        private List<A> a;
    }

    @XmlRootElement(name = "A")
    private static class A {
        @XmlAttribute
        private String a;

        @XmlAttribute
        private String b;

    }
}
