package tz.interceptor;

import java.io.*;
import java.net.Socket;

/**
 * @author Dmitry Shyshkin
 */
public class InterceptedConnection {
    private Socket slave, master;

    // slave -> master
    private boolean smActive = true;
    // master -> slave
    private boolean msActive = true;

    private final LinkThread smLink;
    private final LinkThread msLink;

    private final Object monitor;

    private boolean isBinary = false;

    public InterceptedConnection(Object monitor, Socket slave, Socket master) throws IOException {
        this.monitor = monitor;
        this.slave = slave;
        this.master = master;

        smLink = new LinkThread(LinkType.SLAVE_MASTER, slave.getInputStream(), master.getOutputStream());
        msLink = new LinkThread(LinkType.MASTER_SLAVE, master.getInputStream(), slave.getOutputStream());
    }

    public void start() {
        smLink.start();
        msLink.start();
    }

    public boolean isBinary() {
        return isBinary;
    }

    public void setBinary(boolean binary) {
        isBinary = binary;
    }

    public LinkControl getSlaveMasterControl() {
        return smLink;
    }

    public LinkThread getMasterSlaveControl() {
        return msLink;
    }

    public void setSlaveMasterListener(LinkListener listener) {
        synchronized (monitor) {
            smLink.setListener(listener);
        }
    }

    public void setMasterSlaveListener(LinkListener listener) {
        synchronized (monitor) {
            msLink.setListener(listener);
        }
    }

    public void closeAll() {
        try {
            slave.close();
            master.close();
        } catch (IOException e) {
            //
        }
    }

    public void closeLink(LinkType type) {
        switch (type) {
            case SLAVE_MASTER:
                smActive = false;
                break;
            case MASTER_SLAVE:
                msActive = false;
                break;
        }
        if (!smActive && !msActive) {
            terminateLink(LinkType.SLAVE_MASTER);
            terminateLink(LinkType.MASTER_SLAVE);
        }
    }

    public void terminateLink(LinkType type) {
        closeAll();
    }

    private class LinkThread extends Thread implements LinkControl {
        private Reader reader;
        private Writer writer;
        private LinkListener listener;
        private LinkType type;
        private InputStream is;
        private OutputStream os;

        private LinkThread(LinkType type,  InputStream is, OutputStream os) throws IOException {
            this.type = type;
            this.is = is;
            this.os = os;
            reader = new InputStreamReader(is, "UTF-8");
            writer = new OutputStreamWriter(os, "UTF-8");
        }

        public LinkListener getListener() {
            return listener;
        }

        public void setListener(LinkListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            try {
                if (!isBinary) {
                    char[] buf = new char[0x400];
                    int read;
                    while ((read = reader.read(buf)) >= 0) {
                        synchronized (monitor) {
                            listener.read(buf, read);
                        }
                    }
                } else {
                    byte[] buf = new byte[0x400];
                    int read;
                    while ((read = is.read(buf)) >= 0) {
                        synchronized (monitor) {
                            char[] buf2 = new char[read];
                            for (int i = 0; i < read; ++i) {
                                buf2[i] = (char) buf[i];
                            }
                            listener.read(buf2, read);
                        }
                    }
                }
                closeLink(type);
            } catch (IOException e) {
                e.printStackTrace();
                terminateLink(type);
            }
            synchronized (monitor) {
                listener.closed();
            }
        }

        public void write(char[] buf, int length) {
            try {
                if (!isBinary) {
                    writer.write(buf, 0, length);
                } else {
                    byte[] buf2 = new byte[length];
                    for (int i = 0; i < length; ++i) {
                        buf2[i] = (byte) buf[i];
                    }
                    os.write(buf2, 0, length);
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                terminateLink(type);
            }
        }

        public void close() {
            closeLink(type);
        }
    }

    private enum LinkType {
        SLAVE_MASTER, MASTER_SLAVE
    }

}
