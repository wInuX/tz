package tz.interceptor.game;

import tz.interceptor.MessageControl;
import tz.interceptor.MessageListener;
import tz.xml.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class GameController {
    private MessageListener chatListener = new MessageListener() {
        public void server(String content, Message message) {
            if (execute(InterceptionType.CHAT_SERVER, content, message.getValue())) {
                return;
            }
            chatControl.client(content);
        }

        public void client(String content, Message message) {
            // TODO:
            chatControl.server(content);
        }
    };

    private MessageListener gameListener = new MessageListener() {
        public void server(String content, Message message) {
//            System.out.printf("G <- %s\n", content);

            if (execute(InterceptionType.SERVER, content, message.getValue())) {
                return;
            }
            gameControl.client(content);

        }

        public void client(String content, Message message) {
            if (execute(InterceptionType.CLIENT, content, message.getValue())) {
                return;
            }
            gameControl.server(content);
        }
    };

    private MessageControl gameControl;

    private MessageControl chatControl;

    private List<IntercetorDefinition> interceptors = new ArrayList<IntercetorDefinition>();

    public MessageListener getChatListener() {
        return chatListener;
    }

    public MessageListener getGameListener() {
        return gameListener;
    }

    public void setGameControl(MessageControl gameControl) {
        this.gameControl = gameControl;
    }

    public void setChatControl(MessageControl chatControl) {
        this.chatControl = chatControl;
    }

    public void addInterceptor(InterceptionType type, Class<?> messageType, Interceptor interceptor) {
        interceptors.add(new IntercetorDefinition(type, messageType, interceptor));
    }

    private boolean execute(InterceptionType type, String original, Object message) {
        for (IntercetorDefinition definition : interceptors) {
            if (definition.getType() != type) {
                continue;
            }
            if (definition.getMessageType() != message.getClass()) {
                continue;
            }
            if (definition.getInterceptor().intercept(original, message)) {
                return true;
            }
        }
        return false;
    }

    private static class IntercetorDefinition {
        private InterceptionType type;
        private Class<?> messageType;
        private Interceptor interceptor;

        private IntercetorDefinition(InterceptionType type, Class<?> messageType, Interceptor interceptor) {
            this.type = type;
            this.messageType = messageType;
            this.interceptor = interceptor;
        }

        public InterceptionType getType() {
            return type;
        }

        public void setType(InterceptionType type) {
            this.type = type;
        }

        public Class<?> getMessageType() {
            return messageType;
        }

        public void setMessageType(Class<?> messageType) {
            this.messageType = messageType;
        }

        public Interceptor getInterceptor() {
            return interceptor;
        }

        public void setInterceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
        }
    }
}
