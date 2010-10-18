package tz.xml;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
public enum LocationDirection {
    @XmlEnumValue("5")
    NONE,

    @XmlEnumValue("2")
    N,
    @XmlEnumValue("8")
    S,
    @XmlEnumValue("4")
    W,
    @XmlEnumValue("6")
    E,
    

    @XmlEnumValue("7")
    SW,
    @XmlEnumValue("9")
    SE,


    @XmlEnumValue("1")
    NW,
    @XmlEnumValue("3")
    NE
}
