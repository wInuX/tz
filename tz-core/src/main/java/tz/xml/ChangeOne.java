package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ChangeOne {
    @XmlAttribute(name = "id")
    private Id id;

    @XmlAttribute(name = "slot")
    private SlotPosition usedSlot;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public SlotPosition getUsedSlot() {
        return usedSlot;
    }

    public void setUsedSlot(SlotPosition usedSlot) {
        this.usedSlot = usedSlot;
    }
}
