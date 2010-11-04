package tz.xml;

import tz.xml.transform.XmlComposite;
import tz.xml.transform.XmlPropertyMapping;

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

    @XmlComposite(override = {
            @XmlPropertyMapping(propety = "x", name = "bx"),
            @XmlPropertyMapping(propety = "y", name = "by")
    })
    private BattleCoordinate coordinate;

    @XmlAttribute(name = "level")
    private int level;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "run")
    private Position position;

    @XmlAttribute(name = "direct")
    private Direction direction;

    @XmlAttribute(name = "color")
    private String color;

    @XmlAttribute(name = "battleid")
    private String battleId;

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
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public void setXY(int x, int y) {
        if (coordinate == null) {
            coordinate = new BattleCoordinate();
        }
        coordinate.setXY(x, y);
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getReadablePosition() {
        return String.valueOf((char)('A' + getY())) + (getX() + 1);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }
}
