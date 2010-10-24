package tz.interceptor;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.*;

/**
 * @author Dmitry Shyshkin
 */
public class InterceptorDaemon {
    private static int lastPort = 10005;

    private final static Object monitor = new Object();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(5190, 0, InetAddress.getByName("127.0.0.1"));
        serverSocket.setReuseAddress(true);
        accept:
        while (true) {
            final Socket slave = serverSocket.accept();
            Socket master;
            System.out.println(slave.getRemoteSocketAddress() +  " -> " + slave.getLocalSocketAddress());
            do {
                lastPort++;
                if (lastPort >= 10100) {
                    lastPort = 10000;
                }
                master = new Socket();
                try {
                    master.bind(new InetSocketAddress((InetAddress) null, lastPort));
                } catch (IOException e) {
                    master.close();
                    continue;
                }
                InetSocketAddress address = new InetSocketAddress(slave.getLocalAddress(), 5190);
                try {
                    master.connect(address);
                } catch (IOException ioe) {
                    System.err.println(address.toString() + ": " + ioe.toString());
                    master.close();
                    slave.close();
                    continue accept;
                }
                break;
            } while (true);

            InterceptedSocket connection = new InterceptedSocket(monitor, slave, master);
            MessageLink messageLink = new MessageLink(connection);
            new UnknownMessageLink(messageLink, monitor);

            connection.start();
        }
    }

}
