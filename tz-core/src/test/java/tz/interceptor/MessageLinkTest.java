package tz.interceptor;

import org.testng.Assert;
import org.testng.annotations.Test;
import tz.xml.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class MessageLinkTest {
    private LinkListener listener;
    private List<String> blocks;

    @Test
    public void testSimple() {
        check("<A/>\u0000", "<A/>");
        check("<A/>\u0000<B/>\u0000", "<A/>", "<B/>");
        check("A\u0000", "A");
    }

    @Test
    public void testMultiple() {
        check("<A/><B/>\u0000", "<A/>", "<B/>");
    }

    @Test
    public void testPartial() {
        check("<A/\u0000>\u0000", "<A/>");
        check("<\u0000A/\u0000>\u0000", "<A/>");
    }

    @Test
    public void testPartialAndMultiple() {
        check("<\u0000A/\u0000><B/>\u0000", "<A/>", "<B/>");
    }

    @Test
    public void testError() {
        check("<>\u0000", "<");
    }

    public void setup() {
        blocks = new ArrayList<String>();
        MessageLink link = new MessageLink(new InterceptedConnection() {

            public LinkControl getSlaveMasterControl() {
                return new NullLinkControl();
            }

            public LinkControl getMasterSlaveControl() {
                return new NullLinkControl();
            }

            public void setSlaveMasterListener(LinkListener listener) {
                MessageLinkTest.this.listener = listener;
            }

            public void setMasterSlaveListener(LinkListener listener) {

            }
        });
        link.setListener(new MessageListener() {
            public void server(String content, Object message) {

            }

            public void client(String content, Object message) {
                blocks.add(content);
            }

            public void close() {

            }
        });
    }



    private void check(String input, String... expected) {
        setup();
        listener.read(input.toCharArray(), input.length());
        Assert.assertEquals(blocks.toArray(new Object[blocks.size()]), expected);
    }

    private static class NullLinkControl implements LinkControl {
        public void write(char[] buf, int length) {

        }

        public void close() {

        }
    }
}
