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
    @XmlEnumValue("6")
    POSITION,

    @XmlEnumValue("20")
    KILLED

}
