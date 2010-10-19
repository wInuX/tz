package tz.interceptor;

import tz.xml.Message;

/**
 * @author Dmitry Shyshkin
 */
public interface MessageControl {
    void server(String content);

    void server(Message message);

    void client(String content);

    void client(Message message);
}