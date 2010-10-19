package tz.interceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import tz.interceptor.game.Game;
import tz.interceptor.game.LocationController;
import tz.xml.Key;
import tz.xml.Login;
import tz.xml.Message;

/**
 * @author Dmitry Shyshkin
 */
public class UnknownMessageLink {
    private MessageControl control;

    private static Game gameController;
    private MessageLink link;

    public UnknownMessageLink(MessageLink link) {
        this.link = link;
        control = link.getControl();
        link.setListener(new MessageListener() {
            public void server(String content, Message message) {
                if (message.getValue() instanceof Key) {
                    assign(LinkType.GAME, content, message);
                } else {
                    assign(LinkType.CHAT, content, message);
                }
            }

            public void client(String content, Message message) {
                if (message.getValue() instanceof Login) {
                    assign(LinkType.LOGIN, content, message);
                } else {
                    assign(LinkType.CHAT, content, message);
                }

            }
        });
    }

    private void assign(LinkType type, String content, Message message) {
        switch (type) {
            case LOGIN:
                gameController = null;
                break;
            case GAME: {
                gameController.setGameControl(control);
                MessageListener listener = gameController.getGameListener();
                link.setListener(listener);
                listener.server(content, message);

                Injector injector = Guice.createInjector(gameController);
                injector.getInstance(LocationController.class);
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
