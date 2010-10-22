package tz.xml;

import javax.xml.bind.annotation.*;

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
}
