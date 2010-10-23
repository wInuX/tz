package tz.xml;

import tz.xml.adaptor.ActionModeAdaptor;
import tz.xml.adaptor.ShotTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ActionGo.class, ActionFire.class, ActionPickup.class, ActionPosition.class, ActionReload.class})
public class Action implements Serializable {
    private static final long serialVersionUID = 6307026121370694011L;

    @XmlAttribute(name = "sf")
    private int time;

    @XmlAttribute(name = "t")
    @XmlJavaTypeAdapter(ActionModeAdaptor.class)
    private ActionMode mode;

    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(ShotTypeAdapter.class)
    private ShotType type;

    @XmlAttribute(name = "run")
    private Position position;

    @XmlAttribute(name = "login")
    private String login;

    @XmlAttribute(name = "direct")
    private Direction direction;

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "count")
    private Integer count;

    @XmlAttribute(name = "bx")
    private Integer x;

    @XmlAttribute(name = "by")
    private Integer y;

    public int getTime() {
        return time;
    }

    public ActionMode getMode() {
        return mode;
    }

    public ShotType getType() {
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getX() {
        if (x == null) {
            return null;
        }
        return x - (25 - y) / 2;
    }

    public Integer getY() {
        if (y == null) {
            return null;
        }
        return y;
    }

}
