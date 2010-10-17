package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public class BlockLink {
    private LinkListener fromServer = new BlockListener() {
        @Override
        public void read(String block) {
            listener.server(block);
        }

        public void closed() {

        }
    };

    private LinkListener fromClient = new BlockListener() {
        @Override
        public void read(String block) {
            listener.client(block);
        }

        public void closed() {

        }
    };

    private BlockLinkControl control = new BlockLinkControl() {
        public void server(String content) {
            server.write(create(content), content.length() + 1);
        }

        public void client(String content) {
            client.write(create(content), content.length() + 1);
        }

        private char[] create(String content) {
            char[] chars = new char[content.length() + 1];
            System.arraycopy(content.toCharArray(), 0, chars, 0, content.length());
            return chars;
        }
    };

    private LinkControl client;
    private LinkControl server;
    private BlockLinkListener listener;
    private InterceptedConnection connection;

    public BlockLink(InterceptedConnection connection) {
        this.connection = connection;
        server = connection.getSlaveMasterControl();
        client = connection.getMasterSlaveControl();
    }

    public void start(BlockLinkListener listener) {
        this.listener = listener;
        connection.start(fromClient, fromServer);
    }

    public BlockLinkControl getControl() {
        return control;
    }

    private static abstract class BlockListener implements LinkListener {
        private StringBuilder sb = new StringBuilder();

        public void read(char[] buf, int length) {
            String content = new String(buf, 0, length);
            int index;
            while ((index = content.indexOf('\u0000')) >= 0) {
                sb.append(content.substring(0, index));
                content = content.substring(index + 1);
                read(sb.toString());
                sb = new StringBuilder();
            }
            sb.append(content);
        }

        public abstract void read(String block);
    }
}
