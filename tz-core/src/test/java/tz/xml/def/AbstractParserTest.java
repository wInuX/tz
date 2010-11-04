package tz.xml.def;

import tz.ParserException;
import tz.service.Parser;
import tz.xml.transform.def.ElementDefinitionFactory;

/**
 * @author Dmitry Shyshkin
 */
public class AbstractParserTest {
    protected Object deserialize(String xml, Class<?>... types) throws ParserException {
        return deserialize(xml, "server", types);
    }

    protected Object deserialize(String xml, String context, Class<?>... types) throws ParserException {
        ElementDefinitionFactory factory = ElementDefinitionFactory.createFactory();
        for (Class<?> type : types) {
            factory.register(type);
        }
        Parser.setElementDefinitionFactory(factory);
        return Parser.parse2(xml, context);
    }

    protected String serialize(Object object, Class<?>... types) {
        return serialize(object, "server", types);
    }

    protected String serialize(Object object, String context, Class<?>... types) {
        ElementDefinitionFactory factory = ElementDefinitionFactory.createFactory();
        for (Class<?> type : types) {
            factory.register(type);
        }
        Parser.setElementDefinitionFactory(factory);
        return Parser.create2(object, context);
    }
}
