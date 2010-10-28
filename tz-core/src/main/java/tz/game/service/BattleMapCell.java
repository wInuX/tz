package tz.game.service;

/**
 * @author Dmitry Shyshkin
 */
public class BattleMapCell {
    private char ch;

    public BattleMapCell(char ch) {
        this.ch = ch;
    }

    public boolean isWalkable() {
        return ch >='A' && ch <= 'F';
    }
}
