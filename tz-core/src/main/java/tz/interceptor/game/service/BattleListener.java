package tz.interceptor.game.service;

/**
 * @author Dmitry Shyshkin
 */
public interface BattleListener {
    void battleStart();

    void turnStarted(int turnNumber);

    void battleEnd();
}
