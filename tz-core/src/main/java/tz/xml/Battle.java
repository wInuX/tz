package tz.xml;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "BATTLE")
public class Battle implements Serializable{
    private static final long serialVersionUID = 4437787869060823313L;
    @XmlAttribute
    private String note;

    @XmlElement(name = "USER")
    private List<User> users;

    @XmlElement(name = "O")
    private List<Item> items;

    @XmlElement(name = "MAP")
    private List<MapLine> map;

    @XmlAttribute(name = "f")
    private String battleTypeCode;

    public List<User> getUsers() {
        return users != null ? users : Collections.<User>emptyList();
    }

    public List<Item> getItems() {
        return items != null ? items : Collections.<Item>emptyList();
    }

    public int getLocationX() {
        int v = Integer.parseInt(note.split(",")[0]);
        return v > 180 ? v - 360 : v;
    }

    public int getLocationY() {
        int v = Integer.parseInt(note.split(",")[1]);
        return v > 180 ? v - 360 : v;
    }

    public Date getDate() {
        return new Date(Long.parseLong(note.split(",")[2]) * 1000);
    }

    public List<MapLine> getMap() {
        return map;
    }

    public TerrainType getBattleType() {
        return TerrainType.getBattleTypeByCode(battleTypeCode);
    }
}
