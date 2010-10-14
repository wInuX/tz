package tz;

/**
 * @author Dmitry Shyshkin
 */
public class BattleParserException extends Exception {
    public BattleParserException() {
    }

    public BattleParserException(String s) {
        super(s);
    }

    public BattleParserException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BattleParserException(Throwable throwable) {
        super(throwable);
    }
}
