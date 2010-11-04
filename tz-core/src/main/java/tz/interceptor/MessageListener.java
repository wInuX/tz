package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageListener {
    void server(String content, Object message);
    void client(String content, Object message);
    void close();
}
