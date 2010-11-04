package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;
import tz.service.Parser;
import tz.xml.transform.ClientOnly;
import tz.xml.transform.ServerOnly;
import tz.xml.transform.def.ElementDefinitionFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class InternalParserTest {

    @Test
    public void testAdapterDeserialize() throws ParserException {
        F f;

        f = (F)parseServer("<F sb=\"a\"></F>", F.class);
        Assert.assertNotNull(f);
        Assert.assertEquals(f.sb.toString(), "a");
    }

    @Test
    public void testContextSerialize() throws ParserException {
        N n;

        n = new N();
        n.a = 1L;
        n.b = 2;
        Assert.assertEquals(serializeServer(n, N.class),"<N a=\"1\"/>");

        n = new N();
        n.b = 2;
        Assert.assertEquals(serializeServer(n, N.class),"<N/>");
    }


    private Object parseServer(String xml, Class<?>... types) throws ParserException {
        ElementDefinitionFactory factory = ElementDefinitionFactory.createFactory();
        for (Class<?> type : types) {
            factory.register(type);
        }
        Parser.setElementDefinitionFactory(factory);
        return Parser.parse2(xml, "server");
    }

    private String serializeServer(Object object, Class<?>... types) {
        ElementDefinitionFactory factory = ElementDefinitionFactory.createFactory();
        for (Class<?> type : types) {
            factory.register(type);
        }
        Parser.setElementDefinitionFactory(factory);
        return Parser.create2(object, "server");
    }

    @XmlRootElement(name = "F")
    private static class F {
        @XmlAttribute
        @XmlJavaTypeAdapter(value = StringBuilderAdapter.class)
        private StringBuilder sb;
    }

    private static class StringBuilderAdapter extends XmlAdapter<String, StringBuilder> {
        @Override
        public StringBuilder unmarshal(String v) throws Exception {
            return new StringBuilder(v);
        }

        @Override
        public String marshal(StringBuilder v) throws Exception {
            return v.toString();
        }
    }

    @XmlRootElement(name = "N")
    private class N {
        @XmlAttribute(name = "a")
        @ServerOnly
        private Long a;

        @XmlAttribute(name = "a")
        @ClientOnly
        private Integer b;
    }
}
