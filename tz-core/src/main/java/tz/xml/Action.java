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

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "count")
    private Integer count;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
