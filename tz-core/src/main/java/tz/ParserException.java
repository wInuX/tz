package tz;

/**
 * @author Dmitry Shyshkin
 */
public class ParserException extends Exception {
    public ParserException() {
    }

    public ParserException(String s) {
        super(s);
    }

    public ParserException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ParserException(Throwable throwable) {
        super(throwable);
    }
}
