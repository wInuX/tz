package tz.service;

import tz.BattleParserException;
import tz.xml.BattleView;
import tz.xml.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

/**
 * @author Dmitry Shyshkin
 */
public class Parser {
    private static JAXBContext context;

    public static BattleView parseBattle(String content) {
        try {
            return (BattleView) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Message parseMessage(String content) throws BattleParserException {
        try {
            return (Message) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            throw new BattleParserException(e);
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
