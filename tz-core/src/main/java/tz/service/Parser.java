package tz.service;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tz.ParserException;
import tz.xml.*;
import tz.xml.transform.ZDocumentFactory;
import tz.xml.transform.ZNode;
import tz.xml.transform.def.Context;
import tz.xml.transform.def.ElementDef;
import tz.xml.transform.def.ElementDefinitionFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author Dmitry Shyshkin
 */
public class Parser {
    private static final Logger LOG = Logger.getLogger(Parser.class);

    private static JAXBContext context;
    private static SAXParser saxParser;
    private static ElementDefinitionFactory elementDefinitionFactory = ElementDefinitionFactory.createFactory();

    public static BattleView parseBattle(String content) {
        try {
            return (BattleView) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            LOG.error("Error unmarshalling battle: [" + content + "]", e);
            throw new IllegalStateException(e);
        }
    }

    public static Object parse2(String message, String type) throws ParserException {
        ZDocumentFactory documentFactory = new ZDocumentFactory();
        try {
            saxParser.parse(new InputSource(new StringReader(message)), documentFactory);
        } catch (SAXException e) {
            throw new ParserException(e);
        } catch (IOException e) {
            throw new ParserException(e);
        }
        ZNode document = documentFactory.getDocument();
        Context context = new Context(type, elementDefinitionFactory.getCache());
        ElementDef def = elementDefinitionFactory.getByName(document.getName(), context);
        if (def == null) {
            return null;
        }
        return def.fromNode(document, context);
    }

    public static String create2(Object message, String type) {
        Context context = new Context(type, elementDefinitionFactory.getCache());
        ElementDef def = elementDefinitionFactory.getByClass(message.getClass(), context);
        ZNode document = def.toNode(message, context);
        return new ZDocumentFactory().toString(document);
    }

    public static void setElementDefinitionFactory(ElementDefinitionFactory elementDefinitionFactory) {
        Parser.elementDefinitionFactory = elementDefinitionFactory;
    }

    static {
        try {
            context = JAXBContext.newInstance(BattleView.class);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
        try {
            saxParser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException();
        } catch (SAXException e) {
            throw new IllegalStateException();
        }

        elementDefinitionFactory.register(Alert.class);
        elementDefinitionFactory.register(GoLocation.class);
        elementDefinitionFactory.register(GoBuilding.class);
        elementDefinitionFactory.register(ActionFire.class);
        elementDefinitionFactory.register(ActionGo.class);
        elementDefinitionFactory.register(ActionPickup.class);
        elementDefinitionFactory.register(ActionPosition.class);
        elementDefinitionFactory.register(ActionReload.class);
        elementDefinitionFactory.register(AddOne.class);

    }
}
