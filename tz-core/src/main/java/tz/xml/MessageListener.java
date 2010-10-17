package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageListener {
    void goLocation(GoLocation location);

    void myParameters(MyParameters location);
}
