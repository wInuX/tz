package tz.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.game.InterceptorPriority;
import tz.xml.ChatMessage;
import tz.xml.Post;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class ChatServiceImpl extends AbstractService implements ChatService {
    public static final Logger LOG = Logger.getLogger(ChatService.class);

    @Inject
    private UserService userService;

    private Map<String, CommandListener> commands = new HashMap<String, CommandListener>();

    public void addCommand(String name, CommandListener listener) {
        commands.put(name, listener);
    }

    public void display(String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom("nobody");
        StringBuilder sb = new StringBuilder();
        for (String line : message.split("\n")) {
            sb.append("<div>").append(line).append("</div>");
        }
        chatMessage.setText(sb.toString());
        chatMessage.setHtml(1);
        client(chatMessage);
    }

    @Intercept(value = InterceptionType.CHAT_CLIENT, priority = InterceptorPriority.EARLY)
    boolean onClientChat(Post post) {
        if (post.isPrivate() && post.getLogin().equals(userService.getLogin())) {
            String message = post.getMessage();
            executeCommand(message);
            return true;
        }
        if (post.getMessage() != null && post.getMessage().startsWith("!")) {
            executeCommand(post.getMessage().substring(1));
            return true;
        }
        return false;
    }

    private void executeCommand(String message) {
        String[] args = message.split(" ");
        String command = args[0];
        String[] parameters = new String[args.length - 1];
        System.arraycopy(args, 1, parameters, 0, parameters.length);
        CommandListener listener = commands.get(command);
        try {
            if (listener != null) {
            listener.onCommand(command, parameters);
        }
        } catch (Throwable t) {
            LOG.error("Error executing command: " + message, t);
        }
    }
}
