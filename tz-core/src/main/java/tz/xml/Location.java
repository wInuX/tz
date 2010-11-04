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
public class Location implements Serializable {
    private static final long serialVersionUID = 119294339154256310L;

    @XmlComposite(override = {
            @XmlPropertyMapping(propety = "x", name = "X"),
            @XmlPropertyMapping(propety = "y", name = "Y")
    })
    private LocationCoordinate coordinate;

    @XmlAttribute(name = "m")
    private String content;

    @XmlAttribute(name = "t")
    private TerrainType type;

    @XmlAttribute(name = "danger")
    private int danger;

    @XmlElement(name = "B")
    private List<Building> buildings;

    public Integer getX() {
        return coordinate != null ? coordinate.getX() : null;
    }

    public Integer getY() {
        return coordinate != null ? coordinate.getY() : null;
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
