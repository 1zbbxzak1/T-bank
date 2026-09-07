package backend.academy.labyrinths.generators;

import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KruskalMazeGeneratorTest {
    private final int height = 10;
    private final int width = 10;
    private KruskalMazeGenerator generator;
    private Coordinate start;
    private Coordinate end;

    @BeforeEach
    void setUp() {
        generator = new KruskalMazeGenerator();
        start = new Coordinate(0, 0);
        end = new Coordinate(height - 1, width - 1);
    }

    @Test
    void testMazeGenerationDimensions() {
        Maze maze = generator.generate(height, width, start, end);
        assertEquals(height, maze.getHeight(), "Высота лабиринта должна совпадать с заданной");
        assertEquals(width, maze.getWidth(), "Ширина лабиринта должна совпадать с заданной");
    }

    @Test
    void testStartAndEndArePassable() {
        Maze maze = generator.generate(height, width, start, end);
        assertTrue(maze.isPassable(start.row(), start.col()), "Начальная ячейка должна быть проходимой");
        assertTrue(maze.isPassable(end.row(), end.col()), "Конечная ячейка должна быть проходимой");
    }

    @Test
    void testMazeHasWallsAndPassages() {
        Maze maze = generator.generate(height, width, start, end);
        boolean hasWalls = false;
        boolean hasPassages = false;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.getType() == Type.WALL) {
                    hasWalls = true;
                } else if (cell.getType() == Type.PASSAGE) {
                    hasPassages = true;
                }
                if (hasWalls && hasPassages) {
                    break;
                }
            }
        }

        assertTrue(hasWalls, "Лабиринт должен содержать стены");
        assertTrue(hasPassages, "Лабиринт должен содержать проходы");
    }

    @Test
    void testExtraPathsCreated() {
        Maze maze = generator.generate(height, width, start, end);

        int passagesCount = 0;
        int wallsCount = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).getType() == Type.PASSAGE) {
                    passagesCount++;
                } else {
                    wallsCount++;
                }
            }
        }

        assertTrue(passagesCount > 1, "Лабиринт должен содержать несколько проходов");
        assertTrue(wallsCount > 0, "Лабиринт должен содержать стены для создания сложности");
    }

    @Test
    void testAllCellsInitialized() {
        Maze maze = generator.generate(height, width, start, end);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                assertNotNull(maze.getCell(row, col), "Все ячейки должны быть инициализированы");
            }
        }
    }
}
