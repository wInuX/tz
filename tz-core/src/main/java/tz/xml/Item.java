package tz.xml;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
public class Item implements Serializable{
    private static final long serialVersionUID = -428335669527727169L;

    @XmlAttribute
    private String id;

    @XmlAttribute(name = "txt")
    private String text;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "bx")
    private int x;

    @XmlAttribute(name = "by")
    private int y;

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

    public int getX() {
        return x;
    }

    public int getY() {
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
}
