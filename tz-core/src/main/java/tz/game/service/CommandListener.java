package tz.game.service;

/**
 * @author Dmitry Shyshkin
 */
public interface CommandListener {
    void onCommand(String command, String[] parameters);
}
