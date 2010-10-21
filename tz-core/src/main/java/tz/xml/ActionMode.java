package tz.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Dmitry Shyshkin
 */
@XmlEnum
public enum ActionMode {
    @XmlEnumValue("1")
    TURN,
    @XmlEnumValue("2")
    MOVE,
    @XmlEnumValue("3")
    RELOAD,
    @XmlEnumValue("4")
    UNKNOWN1,
    @XmlEnumValue("5")
    FIRE,
    @XmlEnumValue("6")
    POSITION,
    @XmlEnumValue("7")
    DIED,
    @XmlEnumValue("8")
    GET_ITEM,

    @XmlEnumValue("20")
    KILLED

}
