package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public interface BlockLinkListener {
    void server(String content);
    void client(String content);
}
