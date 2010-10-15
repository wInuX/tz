package tz.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
@XmlEnum
public enum Direction {
    @XmlEnumValue("1")
    WEST,
    @XmlEnumValue("2")
    EAST,
    @XmlEnumValue("3")
    NORTH_EAST,
    @XmlEnumValue("4")
    NORTH_WEST,
    @XmlEnumValue("5")
    SOUTH_EAST,
    @XmlEnumValue("6")
    SOUTH_WEST
}
