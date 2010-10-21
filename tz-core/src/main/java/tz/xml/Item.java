package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Item implements Serializable{
    private static final long serialVersionUID = -428335669527727169L;

    @XmlAttribute
    private String id;

    @XmlAttribute(name = "txt")
    private String text;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "bx")
    private Integer x;

    @XmlAttribute(name = "by")
    private Integer y;

    @XmlAttribute
    private int count;

    @XmlAttribute(name = "massa")
    private double weight;

    @XmlAttribute(name = "drop")
    private String drop;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
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

    public int getCount() {
        return count;
    }

    public double getWeight() {
        return weight;
    }

    public String getDrop() {
        return drop;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
