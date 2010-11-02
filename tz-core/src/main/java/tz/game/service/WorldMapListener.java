package tz.game.service;

import tz.remote.RemoteProxy;

/**
 * @author Dmitry Shyshkin
 */
@RemoteProxy
public interface WorldMapListener {
    void buildingExited();

    void buildingEntered(int id);

    void buildingNotEntered();

    void locationChanged(int x, int y);
}
