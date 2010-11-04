package tz.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "CHECK_BOX")
public class Checkbox {
    @XmlElement(name = "O")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
