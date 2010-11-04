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
    private Id id;

    @XmlAttribute(name = "s")
    private Integer section;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    @Override
    public <T, E extends Throwable> T accept(BattleActionVisitor<T, E> visitor) throws E {
        return visitor.visitPickup(this);
    }
}
