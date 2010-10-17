package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public class Repeater {
    private BlockLinkListener listener = new BlockLinkListener() {
        public void server(String content) {
            System.out.printf("<- %s \n", content);
            control.client(content);
        }

        public void client(String content) {
            System.out.printf("-> %s \n", content);
            control.server(content);
        }
    };

    private BlockLinkControl control;
    private BlockLink link;

    public Repeater(BlockLink link) {
        this.link = link;
        this.control = link.getControl();
    }

    public void start() {
        link.start(listener);
    }
}
