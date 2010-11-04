package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "NOP")
public class Nop {
    @XmlAttribute(name = "id1")
    private String idRangeStart;

    @XmlAttribute(name = "id2")
    private String idRangeEnd;

    @XmlAttribute(name = "i1")
    private Integer idOffset;

    public String getIdRangeStart() {
        return idRangeStart;
    }

    public void setIdRangeStart(String idRangeStart) {
        this.idRangeStart = idRangeStart;
    }

    public String getIdRangeEnd() {
        return idRangeEnd;
    }

    public void setIdRangeEnd(String idRangeEnd) {
        this.idRangeEnd = idRangeEnd;
    }

    public Integer getIdOffset() {
        return idOffset;
    }

    public void setIdOffset(Integer idOffset) {
        this.idOffset = idOffset;
    }
}
