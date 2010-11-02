package tz.xml;

import tz.xml.adaptor.BuildingTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class GoBuilding {
    @XmlAttribute(name = "n")
    private Integer id;

    @XmlAttribute
    private Integer owner;

    @XmlAttribute(name = "tz")
    @XmlJavaTypeAdapter(BuildingTypeAdapter.class)
    private BuildingType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public BuildingType getType() {
        return type;
    }
}
