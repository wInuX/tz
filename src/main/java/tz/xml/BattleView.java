package tz.xml;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "VIEW")
@XmlAccessorType(XmlAccessType.NONE)
public class BattleView implements Serializable {
    private static final long serialVersionUID = -4123598141591782501L;

    @XmlAttribute(name = "id")
    private long id;

    @XmlElement(name = "BATTLE")
    private Battle battle;

    @XmlElement(name = "TURN")
    private List<Turn> turns;

    public Battle getBattle() {
        return battle;
    }

    public List<Turn> getTurns() {
        return turns != null ? turns : Collections.<Turn>emptyList();
    }

    public long getId() {
        return id;
    }
}
