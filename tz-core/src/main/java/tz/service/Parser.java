package tz.service;

import org.apache.log4j.Logger;
import tz.BattleParserException;
import tz.xml.BattleView;
import tz.xml.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Dmitry Shyshkin
 */
public class Parser {
    private static final Logger LOG = Logger.getLogger(Parser.class);

    private static JAXBContext context;

    public static BattleView parseBattle(String content) {
        try {
            return (BattleView) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            LOG.error("Error unmarshalling battle: [" + content + "]", e);
            throw new IllegalStateException(e);
        }
    }

    public static Message parseMessage(String content) throws BattleParserException {
        try {
            return (Message) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            LOG.error("Error unmarshalling massage: [" + content + "]", e);
            throw new BattleParserException(e);
        }
    }

    public static String createMessage(Message message) {
        if (message.getDirect() != null) {
            return message.getDirect();
        }
        StringWriter writer = new StringWriter();
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(message, writer);
            String content = writer.toString();
            int index1 = content.indexOf("<MESSAGE>");
            int index2 = content.lastIndexOf("</MESSAGE>");
            return content.substring(index1 + "<MESSAGE>".length(), index2);
        } catch (JAXBException e) {
            LOG.error("Error marshalling massage: " + message + "]", e);
            throw new IllegalStateException(e);
        }
    }

    static {
        try {
            context = JAXBContext.newInstance(BattleView.class, Message.class);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
