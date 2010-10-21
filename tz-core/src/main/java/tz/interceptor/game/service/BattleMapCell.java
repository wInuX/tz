package tz.interceptor.game.service;

/**
 * @author Dmitry Shyshkin
 */
public class BattleMapCell {
    public static final String WALK = "BCEP";
    private char ch;

    public BattleMapCell(char ch) {
        this.ch = ch;
    }

    public boolean isWalkable() {
        return ch >='A' && ch <= 'P';
    }
}
