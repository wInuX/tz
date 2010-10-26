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
    private String id;

    @XmlAttribute(name = "slot")
    private SlotPosition usedSlot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SlotPosition getUsedSlot() {
        return usedSlot;
    }

    public void setUsedSlot(SlotPosition usedSlot) {
        this.usedSlot = usedSlot;
    }
}
