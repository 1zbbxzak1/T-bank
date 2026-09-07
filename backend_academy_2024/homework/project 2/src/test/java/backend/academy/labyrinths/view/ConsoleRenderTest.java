package backend.academy.labyrinths.view;

import backend.academy.labyrinths.enums.TerrainType;
import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleRenderTest {
    private Maze maze;
    private ConsoleRender consoleRender;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Создаем поток для захвата вывода
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        consoleRender = new ConsoleRender(printStream);

        // Создаем лабиринт 5x5
        maze = new Maze(5, 5);
        maze.setCell(0, 0, new Cell(0, 0, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(0, 1, new Cell(0, 1, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(0, 2, new Cell(0, 2, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(0, 3, new Cell(0, 3, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(0, 4, new Cell(0, 4, Type.PASSAGE, TerrainType.NORMAL));

        maze.setCell(1, 0, new Cell(1, 0, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(1, 1, new Cell(1, 1, Type.WALL, TerrainType.NORMAL));
        maze.setCell(1, 2, new Cell(1, 2, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(1, 3, new Cell(1, 3, Type.WALL, TerrainType.NORMAL));
        maze.setCell(1, 4, new Cell(1, 4, Type.PASSAGE, TerrainType.NORMAL));

        maze.setCell(2, 0, new Cell(2, 0, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(2, 1, new Cell(2, 1, Type.PASSAGE, TerrainType.SAND));
        maze.setCell(2, 2, new Cell(2, 2, Type.PASSAGE, TerrainType.SWAMP));
        maze.setCell(2, 3, new Cell(2, 3, Type.WALL, TerrainType.NORMAL));
        maze.setCell(2, 4, new Cell(2, 4, Type.PASSAGE, TerrainType.NORMAL));

        maze.setCell(3, 0, new Cell(3, 0, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(3, 1, new Cell(3, 1, Type.WALL, TerrainType.NORMAL));
        maze.setCell(3, 2, new Cell(3, 2, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(3, 3, new Cell(3, 3, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(3, 4, new Cell(3, 4, Type.PASSAGE, TerrainType.SWAMP));

        maze.setCell(4, 0, new Cell(4, 0, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(4, 1, new Cell(4, 1, Type.WALL, TerrainType.NORMAL));
        maze.setCell(4, 2, new Cell(4, 2, Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(4, 3, new Cell(4, 3, Type.WALL, TerrainType.NORMAL));
        maze.setCell(4, 4, new Cell(4, 4, Type.PASSAGE, TerrainType.NORMAL));
    }

    @Test
    void testRenderMaze() {
        // Тестируем метод render
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Обновляем ожидаемое значение, чтобы включить описание символов
        String expectedOutput = "️\uD83D\uDD34⬜⬜⬜⬜\n" +
            "⬜⬛⬜⬛⬜\n" +
            "⬜\uD83D\uDFE8\uD83D\uDFEB⬛⬜\n" +
            "⬜⬛⬜⬜\uD83D\uDFEB\n" +
            "⬜⬛⬜⬛\uD83D\uDFE2\n";

        String actualOutput = consoleRender.render(maze, start, end);

        System.out.println("Expected:\n" + expectedOutput);
        System.out.println("Actual:\n" + actualOutput);

        assertEquals(expectedOutput.strip(), actualOutput.strip());

    }

    @Test
    void testRenderPath() {
        // Тестируем метод renderPath
        List<Coordinate> path = List.of(
            new Coordinate(0, 0), // Старт
            new Coordinate(0, 1), // Проход
            new Coordinate(0, 2), // Проход
            new Coordinate(0, 3), // Проход
            new Coordinate(0, 4), // Проход
            new Coordinate(1, 4), // Проход
            new Coordinate(2, 4), // Проход
            new Coordinate(3, 4), // Проход
            new Coordinate(4, 4)  // Финиш
        );

        // Ожидаемое представление пути
        String expectedPathRepresentation =
            "️\uD83D\uDD34\uD83D\uDC38\uD83D\uDC38\uD83D\uDC38\uD83D\uDC38\n" + // Старт и путь
                "⬜⬛⬜⬛\uD83D\uDC38\n" + // Проход с монетой
                "⬜⬜⬜⬛\uD83D\uDC38\n" + // Проход
                "⬜⬛⬜⬜\uD83D\uDC38\n" + // Проход
                "⬜⬛⬜⬛\uD83D\uDFE2\n";   // Финиш

        // Отрисовка пути
        String actualOutput = consoleRender.renderPath(maze, path);

        System.out.println("Expected Path:\n" + expectedPathRepresentation);
        System.out.println("Actual Path:\n" + actualOutput);

        // Сравниваем ожидаемое и фактическое значения
        assertEquals(expectedPathRepresentation.strip(), actualOutput.strip());
    }

    @Test
    void testRenderPath_NoPath() {
        // Тестируем случай, когда путь отсутствует
        String expectedOutput = "Результат: Не найден путь от точки А до точки Б";

        String result = consoleRender.renderPath(maze, List.of());
        assertEquals(expectedOutput, result);
    }
}
