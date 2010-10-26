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
}
