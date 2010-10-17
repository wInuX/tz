package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public interface BlockLinkControl {
    void server(String content);
    void client(String content);
}