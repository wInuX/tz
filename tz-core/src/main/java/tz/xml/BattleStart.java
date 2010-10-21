package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BattleStart {
    @XmlAttribute(name = "turn")
    private int turn;

    // <BSTART turn="2" />
    // <BGO to="5" />
    // <IN id1="53169863680.2" id2="53170180911.2" n="1" />
    // <FIRE id="53170180911.2" type="2" to="$rat87516" rg="2" />
    // <FIRE id="53170180911.2" type="2" to="$rat87516" rg="2" />
    // <BEND />


    public BattleStart() {
    }

    public BattleStart(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
