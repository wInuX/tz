package tz.game.service;

/**
 * @author Dmitry Shyshkin
 */
public class MineMapCell {
    private char ch;

    private int number;

    private int x;

    private int y;

    public MineMapCell(char ch, int number, int x, int y) {
        this.ch = ch;
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean walkable() {
        return ch != '0';
    }
}
