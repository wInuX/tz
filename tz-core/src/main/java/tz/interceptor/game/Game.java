package tz.interceptor.game;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import tz.interceptor.MessageControl;
import tz.interceptor.MessageListener;
import tz.interceptor.game.service.*;
import tz.xml.Message;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dmitry Shyshkin
 */
public class Game extends AbstractModule implements GameModule {
    private MessageListener chatListener = new MessageListener() {
        public void server(String content, Message message) {
            if (!content.startsWith("\u0002") && !content.startsWith("\u0005") && !content.startsWith("\u0004")) {
                debug("C [- ", content);
            }
            if (execute(InterceptionType.CHAT_SERVER, content, message.getValue())) {
                return;
            }
            chatControl.client(content);
        }

        public void client(String content, Message message) {
            debug("C -> ", content);
            if (execute(InterceptionType.CHAT_CLIENT, content, message.getValue())) {
                return;
            }
            chatControl.server(content);
        }
    };

    private MessageListener gameListener = new MessageListener() {
        public void server(String content, Message message) {
            debug("G [- ", content);

            if (execute(InterceptionType.SERVER, content, message.getValue())) {
                return;
            }
            gameControl.client(content);

        }

        public void client(String content, Message message) {
            debug("G -> ", content);

            if (execute(InterceptionType.CLIENT, content, message.getValue())) {
                return;
            }
            gameControl.server(content);
        }
    };

    private MessageControl gameControl;

    private MessageControl chatControl;

    private List<IntercetorDefinition> interceptors = new ArrayList<IntercetorDefinition>();
    private Injector injector;
    private final Object monitor;

    public Game(Object monitor) {
        this.monitor = monitor;
    }

    private void debug(String prefix, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (char ch : content.toCharArray()) {
            if (ch < 0x20) {
                sb.append(String.format("\\u%04x", (int)ch));
            } else {
                sb.append(ch);
            }
        }
        System.out.println(sb.toString());
    }

    public MessageListener getChatListener() {
        return chatListener;
    }

    public MessageListener getGameListener() {
        return gameListener;
    }

    public MessageControl getGameControl() {
        return gameControl;
    }

    public MessageControl getChatControl() {
        return chatControl;
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
        if (message == null) {
            return false;
        }
        for (IntercetorDefinition definition : interceptors) {
            if (definition.getType() != type) {
                continue;
            }
            if (definition.getMessageType() != message.getClass()) {
                continue;
            }
            try {
                if (definition.getInterceptor().intercept(original, message)) {
                    return true;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void registerInterceptors(Object o) {
        Class type = o.getClass();
        while (type != Object.class) {
            for (Method method : type.getDeclaredMethods()) {
                Intercept intercept = method.getAnnotation(Intercept.class);
                if (intercept == null) {
                    continue;
                }
                Class<?> messageType = method.getParameterTypes().length > 1 ? method.getParameterTypes()[1] : method.getParameterTypes()[0];
                interceptors.add(new IntercetorDefinition(intercept.value(), messageType, new MethodBasedInterceptor(method, o)));
            }
            type = type.getSuperclass();
        }
    }

    public void unregisterIntegerceptors(Object o) {
        // TODO:
    }

    public void client(Object message) {
        getGameControl().client(new Message(message));
    }

    public void server(Object message) {
        getGameControl().server(new Message(message));
    }

    public void clientChat(Object message) {
        getChatControl().client(new Message(message));
    }

    public void serverChar(Object message) {
        getChatControl().server(new Message(message));
    }

    public void schedule(final Runnable runnable, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (monitor) {
                    runnable.run();
                }
            }
        }, delay);
    }

    public void start(Injector injector) {
        this.injector = injector;
        registerService(ChatService.class);
        registerService(WorldMapService.class);
        registerService(ArsenalServiceImpl.class);
        registerService(UserServiceImpl.class);
        registerService(BattleService.class);
        registerService(AutoCollectorImpl.class);
    }

    @SuppressWarnings({"unchecked"})
    private void registerService(Class type) {
        AbstractService service = (AbstractService) injector.getInstance(type);
        registerInterceptors(service);
        service.initialize();
    }

    @Override
    protected void configure() {
        bind(GameModule.class).toInstance(this);
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
