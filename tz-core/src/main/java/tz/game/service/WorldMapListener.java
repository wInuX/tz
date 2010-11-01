package tz.game.service;

/**
 * @author Dmitry Shyshkin
 */
public interface WorldMapListener {
    void buildingExited();

    void buildingEntered(int id);

    void locationChanged(int x, int y);
}
