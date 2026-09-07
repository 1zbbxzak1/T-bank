package backend.academy.labyrinths.models;

import backend.academy.labyrinths.enums.TerrainType;
import backend.academy.labyrinths.enums.Type;

public class Cell {
    private final int row;
    private final int col;
    private final Type type;
    private final TerrainType terrainType;

    public Cell(int row, int col, Type type, TerrainType terrainType) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.terrainType = terrainType;
    }

    public Type getType() {
        return type;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public int getTraversalCost() {
        return terrainType.getCost();
    }
}
