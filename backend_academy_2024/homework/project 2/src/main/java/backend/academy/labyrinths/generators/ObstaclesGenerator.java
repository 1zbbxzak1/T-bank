package backend.academy.labyrinths.generators;

import backend.academy.labyrinths.constants.ConfigConstants;
import backend.academy.labyrinths.enums.TerrainType;
import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import java.util.Random;

public class ObstaclesGenerator {
    // Генерация препятствий и монеток
    public void generateObstaclesAndCoins(Maze maze, int height, int width, Random random) {
        int obstacleCount = (height * width) / ConfigConstants.MIN_PERCENT_VALUE; // 10% от общего количества ячеек
        int coinCount = (height * width) / ConfigConstants.MAX_PERCENT_VALUE; // 5% от общего количества ячеек

        int totalCount = obstacleCount + coinCount;

        for (int i = 0; i < totalCount; i++) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            // Проверяем, что ячейка является проходом
            if (maze.getCell(row, col).getType() != Type.PASSAGE) {
                continue; // Пропускаем итерацию, если ячейка не является проходом
            }

            // Проверяем, что ячейка является проходом
            if (maze.getCell(row, col).getType() == Type.PASSAGE) {
                // Генерируем случайное число для выбора типа (0 - песок, 1 - болото, 2 - монета)
                int elementType = random.nextInt(ConfigConstants.MAX_RANDOM_VALUE); // 0, 1 или 2

                if (elementType == 0) {
                    // Устанавливаем ячейку как песок
                    maze.setCell(row, col, new Cell(row, col, Type.PASSAGE, TerrainType.SAND));
                } else if (elementType == 1) {
                    // Устанавливаем ячейку как болото
                    maze.setCell(row, col, new Cell(row, col, Type.PASSAGE, TerrainType.SWAMP));
                } else {
                    // Устанавливаем ячейку как монету
                    maze.setCell(row, col, new Cell(row, col, Type.PASSAGE, TerrainType.COIN));
                }
            }
        }
    }
}
