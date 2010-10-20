package tz.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "count", "s", "items"})
public class Search {
    @XmlElement(name = "O")
    private List<Item> items;

    @XmlAttribute(name = "a")
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
