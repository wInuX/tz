package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Turn implements Serializable {
    private static final long serialVersionUID = -3616230849313332002L;

    @XmlAttribute(name = "turn")
    private Integer turn;

    @XmlElement(name = "USER")
    private List<User> users;

    @XmlElement(name = "O")
    private List<Item> items;

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items != null ? items : Collections.<Item>emptyList();
    }

    public Integer getTurn() {
        return turn;
    }
}
