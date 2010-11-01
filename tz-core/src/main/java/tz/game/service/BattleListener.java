package tz.game.service;

/**
 * @author Dmitry Shyshkin
 */
public interface BattleListener {
    void battleStarted();

    void turnStarted(int turnNumber);

    void battleEnd();
}
