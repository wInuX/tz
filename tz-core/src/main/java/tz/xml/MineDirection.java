package tz.xml;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
public enum MineDirection {
    @XmlEnumValue("1")
    LEFT,
    @XmlEnumValue("3")
    RIGHT,
    @XmlEnumValue("2")
    UP,
    @XmlEnumValue("4")
    DOWN
}
