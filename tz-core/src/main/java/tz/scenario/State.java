package tz.scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class State {
    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef
    private List<Step> actions;

    public List<Step> getActions() {
        return actions;
    }

    public String getName() {
        return name;
    }
}
