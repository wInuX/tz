package tz.xml;

import javax.xml.bind.annotation.*;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "PICKUP")
@XmlType(propOrder = {"section", "id"})
public class ActionPickup extends BattleAction {
    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "s")
    private Integer section;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }
}
