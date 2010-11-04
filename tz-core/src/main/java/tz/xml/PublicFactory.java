package tz.xml;

import tz.xml.transform.ClientOnly;
import tz.xml.transform.ServerOnly;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "FP")
public class PublicFactory {
    @XmlAttribute(name = "full")
    @ClientOnly
    private Boolean full;

    @XmlAttribute(name = "full")
    @ServerOnly
    private Set<ShopCategory> categories;

    @XmlAttribute(name = "c")
    private NameCategory category;

    @XmlAttribute(name = "p")
    private Integer page;

    @XmlAttribute(name = "m")
    private Integer itemCount;

    @XmlElement(name = "O")
    private List<Item> items;

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public Set<ShopCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ShopCategory> categories) {
        this.categories = categories;
    }

    public NameCategory getCategory() {
        return category;
    }

    public void setCategory(NameCategory category) {
        this.category = category;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
