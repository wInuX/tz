package tz.interceptor;

import tz.service.Parser;

/**
 * @author Dmitry Shyshkin
 */
public abstract class MessageControl {
    public abstract void server(String content);

    public abstract void client(String content);

    public void server(Object message) {
        server(Parser.marshall(message, "client"));
    }

    public void client(Object message) {
        client(Parser.marshall(message, "server"));
    }
}