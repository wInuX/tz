package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class User implements Serializable {
    private static final long serialVersionUID = 6524299183582783310L;

    @XmlAttribute
    private String login;

    @XmlElement(name = "a")
    private List<Action> actions;

    @XmlElement(name = "O")
    private List<Item> items;

    @XmlAttribute(name = "bx")
    private int x;

    @XmlAttribute(name = "by")
    private int y;

    @XmlAttribute(name = "level")
    private int level;

    @XmlAttribute(name = "type")
    private String type;

    public String getLogin() {
        return login;
    }

    public List<Action> getActions() {
        return actions;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public UserType getUserType() {
        return UserType.getUserTypeByLogin(login);
    }
}
