package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public class RepeaterLink {
    private LinkControl masterSlaveControl;

    private LinkControl slaveMasterControl;

    public RepeaterLink(InterceptedConnection connection) {
        masterSlaveControl = connection.getMasterSlaveControl();
        slaveMasterControl = connection.getSlaveMasterControl();

        connection.setMasterSlaveListener(new LinkListener() {
            public void read(char[] buf, int length) {
                masterSlaveControl.write(buf, length);
            }

            public void closed() {
                masterSlaveControl.close();
            }
        });
        connection.setSlaveMasterListener(new LinkListener() {
            public void read(char[] buf, int length) {
                slaveMasterControl.write(buf, length);
            }

            public void closed() {
                slaveMasterControl.close();
            }
        });
    }
}
