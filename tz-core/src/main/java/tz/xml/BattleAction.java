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
public abstract class BattleAction {

    public abstract <T, E extends Throwable> T  accept(BattleActionVisitor<T, E> visitor) throws E;

    @Override
    public String toString() {
        return Parser.marshall(this, "client");
    }
}
