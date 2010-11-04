package tz.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
public class IdRange {
    @XmlAttribute(name = "id1")
    private Id start;

    @XmlAttribute(name = "id2")
    private Id end;

    @XmlAttribute(name = "i1")
    private Integer offset;

    public Id getStart() {
        return start;
    }

    public void setStart(Id start) {
        this.start = start;
    }

    public Id getEnd() {
        return end;
    }

    public void setEnd(Id end) {
        this.end = end;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
