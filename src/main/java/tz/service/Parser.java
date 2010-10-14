package tz.service;

import tz.xml.BattleView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

/**
 * @author Dmitry Shyshkin
 */
public class Parser {
    private static JAXBContext context;

    public static BattleView parse(String content) {
        try {
            return (BattleView) context.createUnmarshaller().unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    static {
        try {
            context = JAXBContext.newInstance(BattleView.class);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
