package tz.xml;

import tz.xml.adaptor.LocationAdaptor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class MyParameters {

    @XmlAttribute(name = "time")
    private Long time;

    @XmlAttribute(name = "loc_time")
    private Long locationTime;

    @XmlJavaTypeAdapter(LocationAdaptor.class)
    @XmlAttribute(name = "X")
    private Integer x;

    @XmlJavaTypeAdapter(LocationAdaptor.class)
    @XmlAttribute(name = "Y")
    private Integer y;

    @XmlElement(name = "O")
    private List<Item> items;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Long locationTime) {
        this.locationTime = locationTime;
    }

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

    public List<Item> getItems() {
        return items;
    }
}
