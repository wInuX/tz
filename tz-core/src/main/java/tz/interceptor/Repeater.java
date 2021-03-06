package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public class Repeater {
    private MessageListener listener = new MessageListener() {
        public void server(String content, Object message) {
            System.out.printf("[- %s \n", content);
            control.client(content);
        }

        public void client(String content, Object message) {
            System.out.printf("-> %s \n", content);
            control.server(content);
        }

        public void close() {

        }
    };

    private MessageControl control;

    private MessageLink link;

    public Repeater(MessageLink link) {
        this.link = link;
        this.control = link.getControl();
        link.setListener(listener);
    }

    public MessageListener getListener() {
        return listener;
    }

}
