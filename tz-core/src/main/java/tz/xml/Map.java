package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Map implements Serializable {
    private static final long serialVersionUID = -1420698306214067527L;

    @XmlAttribute(name = "v")
    private String content;

    public String getContent() {
        return content;
    }
}
