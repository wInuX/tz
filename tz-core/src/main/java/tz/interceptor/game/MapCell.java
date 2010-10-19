package tz.interceptor.game;

import tz.xml.TerrainType;

/**
 * @author Dmitry Shyshkin
 */
public class MapCell {
    private TerrainType terrainType;

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }
}
