package tz.interceptor;

import tz.interceptor.game.GameController;
import tz.xml.Key;
import tz.xml.Login;
import tz.xml.Message;

/**
 * @author Dmitry Shyshkin
 */
public class UnknownMessageLink {
    private MessageControl control;

    private static GameController gameController;
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
                gameController = new GameController();
                break;
            case GAME: {
                gameController.setGameControl(control);
                MessageListener listener = gameController.getGameListener();
                link.setListener(listener);
                listener.server(content, message);
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
