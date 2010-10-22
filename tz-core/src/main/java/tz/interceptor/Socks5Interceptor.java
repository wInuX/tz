package tz.interceptor;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Dmitry Shyshkin
 */
public class Socks5Interceptor {
    private static final Object monitor = new Object();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 1080), 0);
        
        while (true) {
            final Socket slave = serverSocket.accept();
            DataInputStream in = new DataInputStream(slave.getInputStream());
            DataOutputStream out = new DataOutputStream(slave.getOutputStream());

            // version
            in.readByte();
            // methods
            in.skipBytes(in.readByte());

            out.writeByte(0x05);
            out.writeByte(0x00);

            in.readByte();
            int cmd = in.readByte();
            if (cmd != 1) {
                throw new IllegalStateException();
            }
            in.readByte();
            int addressType = in.readByte();
            String hostname;
            if (addressType == 0x01) {
                byte[] bytes = new byte[4];
                in.readFully(bytes);
                hostname = InetAddress.getByAddress(bytes).getHostAddress();
            } else if (addressType == 0x03) {
                int length = in.readByte();
                byte[] bytes = new byte[length];
                in.readFully(bytes);
                hostname = new String(bytes, "ISO-8859-1");
            } else {
                throw new IllegalStateException();
            }
            int port = in.readShort() & 0xffff;
            Socket master = new Socket(hostname, port);

            out.writeByte(0x05);
            out.writeByte(0x00);
            out.writeByte(0x00);
            out.writeByte(0x01);
            out.writeInt(0x7f000001);
            out.writeShort(master.getLocalPort());
            out.flush();

            System.out.println(slave.getLocalSocketAddress() +  " -> " + master.getRemoteSocketAddress());

            InterceptedSocket connection;
            if (port == 5190) {
                connection = new InterceptedSocket(monitor, slave, master);
                MessageLink messageLink = new MessageLink(connection);
                new UnknownMessageLink(messageLink, monitor);
            } else {
                connection = new InterceptedSocket(new Object(), slave, master);
                connection.setBinary(true);
                new RepeaterLink(connection);
            }
            connection.start();
        }
    }
}
