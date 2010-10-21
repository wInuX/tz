package tz.xml;


import tz.xml.adaptor.LocationDirectionAdaptor;

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
public class GoLocation {
    @XmlAttribute(name = "t2")
    private Long locationTime;

    @XmlAttribute(name = "t1")
    private Long currentTime;

    @XmlAttribute(name = "d")
    private String locationToLoad;

    @XmlAttribute(name = "n")
    @XmlJavaTypeAdapter(LocationDirectionAdaptor.class)
    private LocationDirection direction;

    @XmlElement(name = "L")
    private List<Location> locations;

    public Long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Long locationTime) {
        this.locationTime = locationTime;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public String getLocationToLoad() {
        return locationToLoad;
    }

    public void setLocationToLoad(String locationToLoad) {
        this.locationToLoad = locationToLoad;
    }

    public LocationDirection getDirection() {
        return direction;
    }

    public void setDirection(LocationDirection direction) {
        this.direction = direction;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
