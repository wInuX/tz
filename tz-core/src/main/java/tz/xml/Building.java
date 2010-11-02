package tz.xml;

import tz.xml.adaptor.BuildingTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 *
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Building implements Serializable {
    private static final long serialVersionUID = -3305122783686594826L;

    // <B X="653" Y="191" Z="1" maxHP="10000" HP="10000" name="17" txt="Северная шахта" N="0" layer="8"/>
    // <B X="360" Y="190" Z="1" name="65" txt="Training ground" N="0" layer="3"/>
    // "><B X="576" Y="304" Z="1" name="30" txt="Дом" N="0" layer="3"/><B X="666" Y="361" Z="2" name="29" txt="Дом" N="0" layer="3"/>
    @XmlAttribute(name = "X")
    private int x;

    @XmlAttribute(name = "Y")
    private int y;

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(BuildingTypeAdapter.class)
    private BuildingType type;

    @XmlAttribute(name = "txt")
    private String text;

    @XmlAttribute(name = "Z")
    private Integer id;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
