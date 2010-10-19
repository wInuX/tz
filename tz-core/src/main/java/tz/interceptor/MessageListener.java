package tz.interceptor;

import tz.xml.Message;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageListener {
    void server(String content, Message message);
    void client(String content, Message message);
}
