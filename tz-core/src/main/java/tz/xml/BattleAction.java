package tz.xml;

import tz.service.Parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso(value = {ActionFire.class})
public class BattleAction {
    @Override
    public String toString() {
        return Parser.createMessage(new Message(this));
    }
}
