package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.GameModule;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.Post;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class ChatServiceImpl extends AbstractService implements ChatService {
    @Inject
    private GameState state;

    private Map<String, CommandListener> commands = new HashMap<String, CommandListener>();

    public void addCommand(String name, CommandListener listener) {
        commands.put(name, listener);
    }

    @Intercept(InterceptionType.CHAT_CLIENT)
    boolean onClientChat(Post post) {
        if (post.isPrivate() && post.getLogin().equals(state.getLogin())) {
            String message = post.getMessage();
            String[] args = message.split(" ");
            String command = args[0];
            String[] parameters = new String[args.length - 1];
            System.arraycopy(args, 0, parameters, 0, parameters.length);
            CommandListener listener = commands.get(command);
            if (listener == null) {
                return false;
            }
            listener.onCommand(command, parameters);
            return true;
        }
        return false;
    }
}
