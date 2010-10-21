package tz.xml;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
public enum Position {
    @XmlEnumValue("1")
    WALK,
    @XmlEnumValue("2")
    RUN,
    @XmlEnumValue("3")
    SEAT,
    @XmlEnumValue("4")
    HIDE
}
