package tz.xml.def;

import tz.ParserException;
import tz.service.Parser;
import tz.xml.transform.def.JAXMContext;

/**
 * @author Dmitry Shyshkin
 */
public class AbstractParserTest {
    protected Object deserialize(String xml, Class<?>... types) throws ParserException {
        return deserialize(xml, "server", types);
    }

    protected Object deserialize(String xml, String context, Class<?>... types) throws ParserException {
        JAXMContext factory = JAXMContext.createContext();
        for (Class<?> type : types) {
            factory.register(type);
        }
        Parser.setJaxmContext(factory);
        return Parser.unmarshall(xml, context);
    }

    protected String serialize(Object object, Class<?>... types) {
        return serialize(object, "server", types);
    }

    protected String serialize(Object object, String context, Class<?>... types) {
        JAXMContext factory = JAXMContext.createContext();
        for (Class<?> type : types) {
            factory.register(type);
        }
        Parser.setJaxmContext(factory);
        return Parser.marshall(object, context);
    }
}
