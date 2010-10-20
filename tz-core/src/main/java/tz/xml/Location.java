package tz.xml;

import tz.interceptor.game.Building;
import tz.xml.adaptor.LocationAdaptor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Location implements Serializable {
    private static final long serialVersionUID = 119294339154256310L;
   
    @XmlAttribute(name = "X")
    @XmlJavaTypeAdapter(LocationAdaptor.class)
    private Integer x;

    @XmlAttribute(name = "Y")
    @XmlJavaTypeAdapter(LocationAdaptor.class)
    private Integer y;

    @XmlAttribute(name = "m")
    private String content;

    @XmlAttribute(name = "t")
    private TerrainType type;

    @XmlAttribute(name = "danger")
    private int danger;

    @XmlElement(name = "B")
    private List<Building> buildings;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TerrainType getType() {
        return type;
    }

    public void setType(TerrainType type) {
        this.type = type;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
