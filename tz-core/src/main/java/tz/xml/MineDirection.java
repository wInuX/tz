package tz.xml;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Dmitry Shyshkin
 */
public enum MineDirection {
    @XmlEnumValue("1")
    LEFT(-1, 0),
    @XmlEnumValue("3")
    RIGHT(1, 0),
    @XmlEnumValue("2")
    UP(-1, 0),
    @XmlEnumValue("4")
    DOWN(1, 0);

    private int dx;
    private int dy;

    MineDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public static MineDirection getDirection(int dx, int dy) {
        for (MineDirection direction : values()) {
            if (direction.dx == dx && direction.dy == dy) {
                return direction;
            }
        }
        return null;
    }
}
