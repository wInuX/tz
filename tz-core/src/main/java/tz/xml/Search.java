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
    private Id takeId;

    @XmlAttribute(name = "d")
    private Id dropId;

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

    public Id getTakeId() {
        return takeId;
    }

    public void setTakeId(Id takeId) {
        this.takeId = takeId;
    }

    public Id getDropId() {
        return dropId;
    }

    public void setDropId(Id dropId) {
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
