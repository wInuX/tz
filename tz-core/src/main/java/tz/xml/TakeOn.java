package tz.xml;

import tz.xml.adaptor.SlotTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "TAKE_ON")
public class TakeOn {
    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "slot")
    @XmlJavaTypeAdapter(SlotTypeAdapter.class)
    private SlotPosition slot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SlotPosition getSlot() {
        return slot;
    }

    public void setSlot(SlotPosition slot) {
        this.slot = slot;
    }
}
