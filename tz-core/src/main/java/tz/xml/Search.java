package tz.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"s", "count", "takeId", "dropId", "items"})
@XmlRootElement(name = "AR")
public class Search {
    @XmlElement(name = "O")
    private List<Item> items;

    @XmlAttribute(name = "a")
    private String takeId;

    @XmlAttribute(name = "d")
    private String dropId;

    @XmlAttribute(name = "c")
    private Integer count;

    @XmlAttribute(name = "s")
    private Integer s;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getTakeId() {
        return takeId;
    }

    public void setTakeId(String takeId) {
        this.takeId = takeId;
    }

    public String getDropId() {
        return dropId;
    }

    public void setDropId(String dropId) {
        this.dropId = dropId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }
}
