package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "JOIN")
public class Join {
    @XmlAttribute(name = "id1")
    private Id sourceId;

    @XmlAttribute(name = "id2")
    private Id destinationId;

    public Id getSourceId() {
        return sourceId;
    }

    public void setSourceId(Id sourceId) {
        this.sourceId = sourceId;
    }

    public Id getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Id destinationId) {
        this.destinationId = destinationId;
    }
}
