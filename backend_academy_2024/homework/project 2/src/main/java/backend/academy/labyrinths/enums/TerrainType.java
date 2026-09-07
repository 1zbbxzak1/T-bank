package backend.academy.labyrinths.enums;

public enum TerrainType {
    NORMAL(1),   // Обычная поверхность с базовым весом
    COIN(2),    // Монеты, хороший путь
    SAND(3),     // Песок, замедляет движение
    SWAMP(4);    // Болото, значительно замедляет движение

    private final int cost;

    TerrainType(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
