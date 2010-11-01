package tz.xml;

import javax.xml.bind.annotation.*;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "IN")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"n", "weaponId", "ammoId"})
public class ActionReload extends BattleAction {
    @XmlAttribute(name = "id1")
    private String ammoId;

    @XmlAttribute(name = "id2")
    private String weaponId;

    @XmlAttribute(name = "n")
    private Integer n;

    public ActionReload() {
    }

    public ActionReload(String weaponId, String ammoId, Integer n) {
        this.weaponId = weaponId;
        this.ammoId = ammoId;
        this.n = n;
    }

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

    @Override
    public <T, E extends Throwable> T accept(BattleActionVisitor<T, E> visitor) throws E {
        return visitor.visitReload(this);
    }
}
