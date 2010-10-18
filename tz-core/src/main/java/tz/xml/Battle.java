package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Battle implements Serializable{
    private static final long serialVersionUID = 4437787869060823313L;
    @XmlAttribute
    private String note;

    @XmlElement(name = "USER")
    private List<User> users;

    @XmlElement(name = "O")
    private List<Item> items;

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

    public TerrainType getBattleType() {
        return TerrainType.getBattleTypeByCode(battleTypeCode);
    }
}
