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
    private Id ammoId;

    @XmlAttribute(name = "id2")
    private Id weaponId;

    @XmlAttribute(name = "n")
    private Integer n;

    public ActionReload() {
    }

    public ActionReload(Id weaponId, Id ammoId, Integer n) {
        this.weaponId = weaponId;
        this.ammoId = ammoId;
        this.n = n;
    }

    public Id getAmmoId() {
        return ammoId;
    }

    public void setAmmoId(Id ammoId) {
        this.ammoId = ammoId;
    }

    public Id getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(Id weaponId) {
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
