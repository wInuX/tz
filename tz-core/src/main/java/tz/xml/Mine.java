package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Mine {
    @XmlAttribute(name = "map")
    private String map;

    @XmlAttribute(name = "map_size")
    private Integer mapSize;

    @XmlAttribute(name = "go")
    private MineDirection direction;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public MineDirection getDirection() {
        return direction;
    }

    public void setDirection(MineDirection direction) {
        this.direction = direction;
    }
}
