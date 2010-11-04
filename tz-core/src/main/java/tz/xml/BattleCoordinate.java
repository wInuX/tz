package tz.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
public class BattleCoordinate {
    @XmlAttribute
    private int x;

    @XmlAttribute
    private int y;

    public int getX() {
        return x - (25 - y) / 2;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x + (25 - y) / 2;
        this.y = y;
    }
}
