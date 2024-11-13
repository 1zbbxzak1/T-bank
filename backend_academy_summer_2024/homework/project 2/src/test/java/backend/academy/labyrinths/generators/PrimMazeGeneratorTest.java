package backend.academy.labyrinths.generators;

import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimMazeGeneratorTest {
    private final int height = 10;
    private final int width = 10;
    private PrimMazeGenerator generator;
    private Coordinate start;
    private Coordinate end;

    @BeforeEach
    void setUp() {
        generator = new PrimMazeGenerator();
        start = new Coordinate(0, 0); // начальная точка
        end = new Coordinate(height - 1, width - 1); // конечная точка
    }

    @Test
    void testStartAndEndCellsArePassage() {
        Maze maze = generator.generate(height, width, start, end);
        assertEquals(Type.PASSAGE, maze.getCell(start.row(), start.col()).getType(),
            "Начальная точка должна быть проходом");
        assertEquals(Type.PASSAGE, maze.getCell(end.row(), end.col()).getType(), "Конечная точка должна быть проходом");
    }

    @Test
    void testMazeStructure() {
        Maze maze = generator.generate(height, width, start, end);
        assertEquals(height, maze.getHeight(), "Высота лабиринта должна совпадать");
        assertEquals(width, maze.getWidth(), "Ширина лабиринта должна совпадать");

        // Проверяем, что каждая ячейка является либо стеной, либо проходом
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                assertTrue(
                    maze.getCell(row, col).getType() == Type.WALL || maze.getCell(row, col).getType() == Type.PASSAGE,
                    "Каждая ячейка должна быть либо стеной, либо проходом");
            }
        }
    }

    @Test
    void testDeadEndsAndSparseWalls() {
        Maze maze = generator.generate(height, width, start, end);

        int deadEndCount = 0;
        int wallCount = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).getType() == Type.WALL) {
                    wallCount++;
                }
                // Проверка тупиков, где только один сосед - проход
                else if (maze.getCell(row, col).getType() == Type.PASSAGE) {
                    int adjacentPassages = generator.countAdjacentPassages(maze, row, col, height, width);
                    if (adjacentPassages == 1) {
                        deadEndCount++;
                    }
                }
            }
        }

        assertTrue(deadEndCount > 0, "Должны присутствовать тупики");
        assertTrue(wallCount > (height * width) / 4, "Должны присутствовать разреженные стены");
    }
}
