package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "RUN")
public class ActionPosition extends BattleAction {
    @XmlAttribute(name = "type")
    private Position position;

    public ActionPosition() {
    }

    public ActionPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public <T, E extends Throwable> T accept(BattleActionVisitor<T, E> visitor) throws E {
        return visitor.visitPosition(this);
    }
}
