package tz.xml;

import javax.xml.bind.annotation.*;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "FIRE")
@XmlType(propOrder = {"rg", "type", "to", "weaponId"})
public class ActionFire extends BattleAction {
    @XmlAttribute(name = "id")
    private String weaponId;

    @XmlAttribute(name = "to")
    private String to;

    @XmlAttribute(name = "type")
    private Integer type;

    @XmlAttribute(name = "rg")
    private Integer rg;

    public String getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(String weaponId) {
        this.weaponId = weaponId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }
}
