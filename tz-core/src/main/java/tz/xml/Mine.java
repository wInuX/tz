package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "HD")
public class Mine {
    @XmlAttribute(name = "map")
    private String map;

    @XmlAttribute(name = "map_size")
    private Integer mapSize;

    @XmlAttribute(name = "go")
    private MineDirection direction;

    @XmlAttribute(name = "r")
    private Integer room;

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

    public Integer getMapSize() {
        return mapSize;
    }

    public void setMapSize(Integer mapSize) {
        this.mapSize = mapSize;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }
}
