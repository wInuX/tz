package tz.xml;

import tz.xml.adaptor.BuildingTypeAdapter;
import tz.xml.adaptor.LocationAdaptor;
import tz.xml.transform.XmlComposite;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "MYPARAM")
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

    @XmlAttribute(name = "Z")
    private Integer buildingId;

    @XmlAttribute(name = "hz")
    @XmlJavaTypeAdapter(BuildingTypeAdapter.class)
    private BuildingType buildingType;

    @XmlComposite
    private Skills skills;

    @XmlComposite
    private IdRange idRange;

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

    public IdRange getIdRange() {
        return idRange;
    }

    public void setIdRange(IdRange idRange) {
        this.idRange = idRange;
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

    public void setOd(Integer od) {
        this.od = od;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}
