package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;
import tz.xml.Id;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
public class PackageAdapterParserTest extends AbstractParserTest {
    @Test
    public void testId() throws ParserException {
        A a = (A) deserialize("<A id=\"1.2\"/>", A.class);
        Assert.assertEquals(a.id.getNumber(), 1);
        Assert.assertEquals(a.id.getServer(), 2);

        Assert.assertEquals(serialize(a, A.class), "<A id=\"1.2\"/>");
        
    }

    @XmlRootElement
    private static class A {
        @XmlAttribute
        private Id id;
    }
}
