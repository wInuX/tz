package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageVisitor {
    void visitGoLocation(GoLocation message);

    void visitMyParameters(MyParameters message);
}
