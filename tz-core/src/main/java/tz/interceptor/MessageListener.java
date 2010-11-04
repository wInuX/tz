package tz.interceptor;

import tz.xml.Message;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageListener {
    void server(String content, Object message);
    void client(String content, Object message);
    void close();
}
