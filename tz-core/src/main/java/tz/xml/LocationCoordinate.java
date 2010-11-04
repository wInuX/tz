package tz.xml;

import tz.xml.adaptor.LocationAdaptor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class LocationCoordinate {
    @XmlAttribute
    @XmlJavaTypeAdapter(LocationAdaptor.class)
    private int x;

    @XmlAttribute
    @XmlJavaTypeAdapter(LocationAdaptor.class)
    private int y;

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
}
