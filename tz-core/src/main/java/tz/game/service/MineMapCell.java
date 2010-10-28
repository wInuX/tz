package tz.game.service;

/**
 * @author Dmitry Shyshkin
 */
public class MineMapCell {
    private char ch;

    private int number;

    public MineMapCell(char ch, int number) {
        this.ch = ch;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
