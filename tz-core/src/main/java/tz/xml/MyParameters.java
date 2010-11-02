package tz.xml;

import tz.xml.adaptor.BuildingTypeAdapter;
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
    @XmlAttribute(name = "login")
    private String login;

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

    @XmlAttribute(name = "OD")
    private Integer od;

    @XmlAttribute(name = "id1")
    private String idRangeStart;

    @XmlAttribute(name = "id2")
    private String idRangeEnd;

    @XmlAttribute(name = "i1")
    private Integer idOffset;

    @XmlAttribute(name = "Z")
    private Integer buildingId;

    @XmlAttribute(name = "hz")
    @XmlJavaTypeAdapter(BuildingTypeAdapter.class)
    private BuildingType buildingType;

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

    public Integer getOd() {
        return od;
    }

    public String getIdRangeStart() {
        return idRangeStart;
    }

    public void setIdRangeStart(String idRangeStart) {
        this.idRangeStart = idRangeStart;
    }

    public String getIdRangeEnd() {
        return idRangeEnd;
    }

    public void setIdRangeEnd(String idRangeEnd) {
        this.idRangeEnd = idRangeEnd;
    }

    public Integer getIdOffset() {
        return idOffset;
    }

    public void setIdOffset(Integer idOffset) {
        this.idOffset = idOffset;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
