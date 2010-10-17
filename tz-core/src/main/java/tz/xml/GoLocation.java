package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class GoLocation {
    @XmlAttribute(name = "t2")
    private Long locationTime;

    @XmlAttribute(name = "t1")
    private Long currentTime;

    private LocationDirection direction;
}
