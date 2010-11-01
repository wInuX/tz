package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "BGO")
@XmlAccessorType(XmlAccessType.NONE)
public class ActionGo extends BattleAction {
    @XmlAttribute(name = "to")
    private Direction direction;

    public ActionGo() {
    }

    public ActionGo(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public <T, E extends Throwable> T accept(BattleActionVisitor<T, E> visitor) throws E {
        return visitor.visitMove(this);
    }
}
