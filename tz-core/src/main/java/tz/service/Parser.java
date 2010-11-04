package tz.service;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tz.ParserException;
import tz.xml.*;
import tz.xml.Error;
import tz.xml.transform.ZDocumentFactory;
import tz.xml.transform.ZNode;
import tz.xml.transform.def.JAXMContext;

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
    private static JAXMContext jaxmContext = JAXMContext.createContext();

    public static BattleView parseBattle(String content) {
        try {
            return (BattleView) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            LOG.error("Error unmarshalling battle: [" + content + "]", e);
            throw new IllegalStateException(e);
        }
    }

    public static Object unmarshall(String message, String type) throws ParserException {
        ZDocumentFactory documentFactory = new ZDocumentFactory();
        try {
            saxParser.parse(new InputSource(new StringReader(message)), documentFactory);
        } catch (SAXException e) {
            throw new ParserException(e);
        } catch (IOException e) {
            throw new ParserException(e);
        }
        ZNode document = documentFactory.getDocument();
        return jaxmContext.unmarshall(document, type);
    }

    public static String marshall(Object message, String type) {
        return new ZDocumentFactory().toString(jaxmContext.marshall(message, type));
    }

    public static void setJaxmContext(JAXMContext jaxmContext) {
        Parser.jaxmContext = jaxmContext;
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

        jaxmContext.register(Alert.class);
        jaxmContext.register(GoLocation.class);
        jaxmContext.register(GoBuilding.class);
        jaxmContext.register(ActionFire.class);
        jaxmContext.register(ActionGo.class);
        jaxmContext.register(ActionPickup.class);
        jaxmContext.register(ActionPosition.class);
        jaxmContext.register(ActionReload.class);
        jaxmContext.register(AddOne.class);
        jaxmContext.register(ChangeOne.class);

        jaxmContext.register(BattleStart.class);
        jaxmContext.register(BattleEnd.class);
        jaxmContext.register(Turn.class);
        jaxmContext.register(Key.class);
        jaxmContext.register(Login.class);
        jaxmContext.register(LoginOk.class);
        jaxmContext.register(MyParameters.class);

        jaxmContext.register(Join.class);
        jaxmContext.register(Split.class);
        jaxmContext.register(Drop.class);
        jaxmContext.register(Drop.class);
        jaxmContext.register(MiniMap.class);
        jaxmContext.register(Post.class);
        jaxmContext.register(ListLogin.class);
        jaxmContext.register(Search.class);
        jaxmContext.register(Battle.class);
        jaxmContext.register(TakeOn.class);
        jaxmContext.register(TakeOff.class);
        jaxmContext.register(NewId.class);
        jaxmContext.register(Kickass.class);
        jaxmContext.register(ChatMessage.class);
        jaxmContext.register(Shop.class);
        jaxmContext.register(Mine.class);
        jaxmContext.register(GoError.class);
        jaxmContext.register(Error.class);
        jaxmContext.register(GetMe.class);


    }
}
