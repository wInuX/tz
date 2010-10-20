package tz.interceptor;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Dmitry Shyshkin
 */
public class InterceptorDaemon {
    private static int lastPort = 10005;

    private final static Object monitor = new Object();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(5190, 0, InetAddress.getByName("127.0.0.1"));
        serverSocket.setReuseAddress(true);
        while (true) {
            final Socket slave = serverSocket.accept();
            Socket master;
            System.out.println(slave.getRemoteSocketAddress() +  " -> " + slave.getLocalSocketAddress());
            do {
                lastPort++;
                if (lastPort >= 10100) {
                    lastPort = 10000;
                }
                try {
                    master = new Socket(slave.getLocalAddress(), 5190, null, lastPort);
                } catch (IOException e) {
                    System.err.println(e.toString());
                    continue;
                }
                break;
            } while (true);

            InterceptedConnection connection = new InterceptedConnection(monitor, slave, master);
            MessageLink messageLink = new MessageLink(connection);
            new UnknownMessageLink(messageLink);
            
            connection.start();

        }
    }

}
