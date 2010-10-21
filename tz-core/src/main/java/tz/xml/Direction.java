package tz.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
@XmlEnum
public enum Direction {
    @XmlEnumValue("1")
    WEST(-1, 0),
    @XmlEnumValue("4")
    EAST(1, 0),
    @XmlEnumValue("3")
    NORTH_EAST(1, -1),
    @XmlEnumValue("2")
    NORTH_WEST(-1, -1),
    @XmlEnumValue("5")
    SOUTH_EAST(1, 1),
    @XmlEnumValue("6")
    SOUTH_WEST(-1, 1);

    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int moveX(int x, int y) {
        if (dy == 0) {
            return x + dx;
        }
        if (dx == -1) {
            --x;
        }
        if ((y & 1) > 0) {
            return x + 1;
        }
        return x;
    }

    public int moveY(int x, int y) {
        return y + dy;
    }
}
