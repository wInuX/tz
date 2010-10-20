package tz.interceptor.game.service;

import com.google.inject.ImplementedBy;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(WorldMapServiceImpl.class)
public interface WorldMapService {
    void gotoLocation(int x, int y, Runnable callback);
}
