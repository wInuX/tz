package tz.interceptor;

import tz.BattleParserException;
import tz.interceptor.game.GameController;
import tz.service.Normalizer;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class InterceptorDaemon {
    private static int lastPort = 10005;
    private static GameController gameController;
    private static boolean doLogin = true;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(5190, 0, InetAddress.getByName("127.0.0.1"));
        serverSocket.setReuseAddress(true);
        while (true) {
            final Socket slave = serverSocket.accept();
            System.out.println(slave.getRemoteSocketAddress() +  " -> " + slave.getLocalSocketAddress());
            Socket master;
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
            
            InterceptedConnection connection = new InterceptedConnection(slave, master);
            BlockLink blockLink = new BlockLink(connection);
            LinkType linkType = LinkType.getLinkType(slave.getLocalAddress().getHostAddress());
            switch (linkType) {
                case LOGIN_CHAT: {
                    if (doLogin) {
                        Repeater repeater = new Repeater(blockLink);
                        repeater.start();
                        gameController = new GameController();
                        doLogin = false;
                        break;
                    } else {
                        gameController.setChatControl(blockLink);
                        doLogin = true;
                    }
                    break;
                }
                case GAME: {
                    gameController.start(blockLink);
                    break;
                }
                default: {
                    System.err.println("Unknown link type " + slave.getLocalAddress().getHostAddress());
                    Repeater repeater = new Repeater(blockLink);
                    repeater.start();
                    break;
                }
            }
        }
    }

    private enum LinkType {
        LOGIN_CHAT, GAME;
        private static Map<String, LinkType> map = new HashMap<String, LinkType>();

        static {
            map.put("188.93.63.180", LOGIN_CHAT);
            map.put("188.93.63.175", LOGIN_CHAT);
            map.put("188.93.63.168", GAME);
            //map.put("188.93.63.180", CHAT);
        }

        public static LinkType getLinkType(String ip) {
            return  map.get(ip);
        }
    }
}
