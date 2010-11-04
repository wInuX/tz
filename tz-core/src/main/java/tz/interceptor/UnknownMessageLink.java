package tz.interceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import tz.game.Game;
import tz.xml.Key;
import tz.xml.ListLogin;

/**
 * @author Dmitry Shyshkin
 */
public class UnknownMessageLink {
    private MessageControl control;

    private static Game gameController;
    private MessageLink link;
    private Object monitor;

    public UnknownMessageLink(MessageLink link, Object monitor) {
        this.link = link;
        this.monitor = monitor;
        control = link.getControl();
        link.setListener(new MessageListener() {
            public void server(String content, Object message) {
                System.out.printf("? [- %s\n", content);
                if (message instanceof Key) {
                    assign(LinkType.GAME, content, message);
                } else {
                    assign(LinkType.CHAT, content, message);
                }
            }

            public void client(String content, Object message) {
                System.out.printf("? -> %s\n", content);
                if (message instanceof ListLogin) {
                    assign(LinkType.LOGIN, content, message);
                } else {
                    assign(LinkType.CHAT, content, message);
                }

            }

            public void close() {

            }
        });
    }

    private void assign(LinkType type, String content, Object message) {
        switch (type) {
            case LOGIN:
                gameController = null;
                new Repeater(link).getListener().client(content, message);
                break;
            case GAME: {
                gameController = new Game(monitor);
                gameController.setGameControl(control);
                MessageListener listener = gameController.getGameListener();
                link.setListener(listener);
                link.setDecode(true);
                listener.server(content, message);

                Injector injector = Guice.createInjector(gameController);
                gameController.start(injector);
                break;
            }
            case CHAT: {
                gameController.setChatControl(control);
                MessageListener listener = gameController.getChatListener();
                link.setListener(listener);
                listener.server(content, message);
                break;
            }
        }

    }
}
