package tz.xml;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
public enum LocationDirection {
    @XmlEnumValue("1")
    SW,
    @XmlEnumValue("2")
    S,
    @XmlEnumValue("3")
    SE,
    @XmlEnumValue("4")
    W,
    @XmlEnumValue("5")
    E,
    @XmlEnumValue("6")
    NW,
    @XmlEnumValue("7")
    N,
    @XmlEnumValue("8")
    NE
}
