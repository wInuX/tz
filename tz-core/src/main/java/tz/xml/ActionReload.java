package tz.xml;

import javax.xml.bind.annotation.*;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "IN")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"n", "weaponId", "ammoId"})
public class ActionReload extends BattleAction {
    @XmlAttribute(name = "id")
    private String ammoId;

    @XmlAttribute(name = "id2")
    private String weaponId;

    @XmlAttribute(name = "n")
    private Integer n;

    public String getAmmoId() {
        return ammoId;
    }

    public void setAmmoId(String ammoId) {
        this.ammoId = ammoId;
    }

    public String getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(String weaponId) {
        this.weaponId = weaponId;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }
}
