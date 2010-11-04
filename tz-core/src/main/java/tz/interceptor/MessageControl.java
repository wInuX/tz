package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageControl {
    void server(String content);

    void server(Object message);

    void client(String content);

    void client(Object message);
}