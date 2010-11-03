package tz.xml.def;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.ParserException;
import tz.service.Parser;
import tz.xml.transform.ClientOnly;
import tz.xml.transform.ServerOnly;
import tz.xml.transform.XmlComposite;
import tz.xml.transform.XmlPropertyMapping;
import tz.xml.transform.def.ElementDefinitionFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class InternalParserTest {
    @Test
    public void testSimpleDeserialize() throws ParserException {
        A a;

        a = (A) parseServer("<A></A>", A.class);
        Assert.assertNotNull(a);

        a = (A) parseServer("<A a=\"b\"></A>", A.class);
        Assert.assertNotNull(a);
        Assert.assertEquals(a.a, "b");
    }

    @Test
    public void testInnerDeserialize() throws ParserException {
        B b;

        b = (B)parseServer("<B><A/></B>", B.class);
        Assert.assertNotNull(b);
        Assert.assertNotNull(b.a);

        b = (B)parseServer("<B><A b=\"c\"/></B>", B.class);
        Assert.assertNotNull(b);
        Assert.assertEquals(b.a.b, "c");
    }

    @Test
    public void testCompositeDeserialize() throws ParserException {
        C c;

        c = (C)parseServer("<C a=\"a\" b=\"b\"></C>", C.class);
        Assert.assertNotNull(c);
        Assert.assertNotNull(c.a);
        Assert.assertEquals(c.a.a, "a");
        Assert.assertEquals(c.a.b, "b");
    }

    @Test
    public void testCompositeOverrideDeserialize() throws ParserException {
        M m;

        m = (M)parseServer("<M ma=\"a\" mb=\"b\"></M>", M.class);
        Assert.assertNotNull(m);
        Assert.assertNotNull(m.a);
        Assert.assertEquals(m.a.a, "a");
        Assert.assertEquals(m.a.b, "b");
    }


    @Test
    public void testPrimitiveConversionDeserialize() throws ParserException {
        D d;

        d = (D)parseServer("<D i=\"1\"></D>", D.class);
        Assert.assertNotNull(d);
        Assert.assertEquals(d.i, 1);
    }

    @Test
    public void testEnumDeserialize() throws ParserException {
        E e;

        e = (E)parseServer("<E e=\"a\"></E>", E.class);
        Assert.assertNotNull(e);
        Assert.assertEquals(e.e, EEnum.A);
    }

    @Test
    public void testAdapterDeserialize() throws ParserException {
        F f;

        f = (F)parseServer("<F sb=\"a\"></F>", F.class);
        Assert.assertNotNull(f);
        Assert.assertEquals(f.sb.toString(), "a");
    }

    @Test
    public void testListDeserialize() throws ParserException {
        L l;

        l = (L)parseServer("<L><A/><A/></L>", L.class);
        Assert.assertNotNull(l);
        Assert.assertNotNull(l.a);
        Assert.assertEquals(l.a.size(), 2);
    }


    @Test
    public void testSimpleSerialize() throws ParserException {
        A a;

        a = new A();
        Assert.assertEquals(serializeServer(a, A.class),"<A/>");
    }

    @Test
    public void testInnerSerialize() throws ParserException {
        B b;

        b = new B();
        b.a = new A();
        b.a.a = "a";
        Assert.assertEquals(serializeServer(b, B.class),"<B>\n <A a=\"a\"/>\n</B>");
    }

    @Test
    public void testCompositeSerialize() throws ParserException {
        C c;

        c = new C();
        c.a = new A();
        c.a.a = "a";
        c.a.b = "b";
        Assert.assertEquals(serializeServer(c, C.class),"<C a=\"a\" b=\"b\"/>");
    }

    @Test
    public void testCompositeOverrideSerialize() throws ParserException {
        M m;

        m = new M();
        m.a = new A();
        m.a.a = "a";
        m.a.b = "b";
        Assert.assertEquals(serializeServer(m, M.class),"<M ma=\"a\" mb=\"b\"/>");
    }

    @Test
    public void testPrimitiveConversionSerialize() throws ParserException {
        D d;

        d = new D();
        d.i = 1;
        d.l = -1;
        Assert.assertEquals(serializeServer(d, D.class),"<D i=\"1\" l=\"-1\"/>");

        d = new D();
        d.i = 1;
        d.l = -1;
        d.lw = -2L;
        d.iw = 2;
        Assert.assertEquals(serializeServer(d, D.class),"<D i=\"1\" l=\"-1\" iw=\"2\" lw=\"-2\"/>");
    }

    @Test
    public void testEnumSerialize() throws ParserException {
        E e;

        e = new E();
        e.e = EEnum.A;
        Assert.assertEquals(serializeServer(e, E.class),"<E e=\"a\"/>");
    }

    @Test
    public void testListSerialize() throws ParserException {
        L l;

        l = new L();
        l.a = new ArrayList<A>(Arrays.asList(new A(), new A()));
        Assert.assertEquals(serializeServer(l, L.class),"<L>\n <A/>\n <A/>\n</L>");
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

    @XmlRootElement(name = "A")
    private static class A {
        @XmlAttribute
        private String a;

        @XmlAttribute
        private String b;

    }

    @XmlRootElement(name = "B")
    private static class B {
        @XmlElement(name = "A")
        private A a;
    }

    @XmlRootElement(name = "C")
    private static class C {
        @XmlComposite
        private A a;
    }

    @XmlRootElement(name = "D")
    private static class D {
        @XmlAttribute
        private int i;

        @XmlAttribute
        private long l;

        @XmlAttribute
        private Integer iw;

        @XmlAttribute
        private Long lw;
    }

    @XmlRootElement(name = "E")
    private static class E {
        @XmlAttribute
        private EEnum e;
    }

    private enum EEnum {
        @XmlEnumValue("a")
        A,
        @XmlEnumValue("b")
        B,
        @XmlEnumValue("c")
        C
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

    @XmlRootElement(name = "L")
    private static class L {
        @XmlElement(name = "A")
        private List<A> a;
    }

    @XmlRootElement(name = "M")
    private static class M {
        @XmlComposite(override = {
                @XmlPropertyMapping(propety = "a", name = "ma"),
                @XmlPropertyMapping(propety = "b", name = "mb")
        })
        private A a;
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
