package tz.xml;

import tz.xml.adaptor.ShotTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    @XmlJavaTypeAdapter(ShotTypeAdapter.class)
    private ShotType type;

    @XmlAttribute(name = "rg")
    private Integer rg;

    public ActionFire() {
    }

    public ActionFire(String weaponId, String to, ShotType type, Integer rg) {
        this.weaponId = weaponId;
        this.to = to;
        this.type = type;
        this.rg = rg;
    }

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

    public ShotType getType() {
        return type;
    }

    public void setType(ShotType type) {
        this.type = type;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }

    @Override
    public <T, E extends Throwable> T accept(BattleActionVisitor<T, E> visitor) throws E {
        return visitor.visitFire(this);
    }
}
