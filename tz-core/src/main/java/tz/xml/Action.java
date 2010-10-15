package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Action implements Serializable {
    private static final long serialVersionUID = 6307026121370694011L;

    @XmlAttribute(name = "sf")
    private int time;

    @XmlAttribute(name = "t")
    private ActionMode mode;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "login")
    private String login;

    @XmlAttribute(name = "direct")
    private Direction direction;

    public int getTime() {
        return time;
    }

    public ActionMode getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }

    public Direction getDirection() {
        return direction;
    }
}
