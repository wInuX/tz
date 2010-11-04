package tz.xml;

import javax.xml.bind.annotation.*;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "JOIN")
public class Join {
    @XmlAttribute(name = "id1")
    private String sourceId;

    @XmlAttribute(name = "id2")
    private String destinationId;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}
