package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
}
