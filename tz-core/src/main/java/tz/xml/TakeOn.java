package tz.xml;

import tz.xml.adaptor.SlotTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "TAKE_ON")
public class TakeOn {
    @XmlAttribute(name = "id")
    private Id id;

    @XmlAttribute(name = "slot")
    @XmlJavaTypeAdapter(SlotTypeAdapter.class)
    private SlotPosition slot;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public SlotPosition getSlot() {
        return slot;
    }

    public void setSlot(SlotPosition slot) {
        this.slot = slot;
    }
}
