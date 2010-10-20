package tz.interceptor.game.service;

import com.google.inject.ImplementedBy;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(ChatServiceImpl.class)
public interface ChatService {
    void addCommand(String name, CommandListener listener);
}
