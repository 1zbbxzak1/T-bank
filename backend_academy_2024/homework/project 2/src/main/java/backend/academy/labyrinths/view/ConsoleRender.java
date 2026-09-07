package backend.academy.labyrinths.view;

import backend.academy.labyrinths.enums.TerrainType;
import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.interfaces.Renderer;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.io.PrintStream;
import java.util.List;

public class ConsoleRender implements Renderer {
    private static final String START_SYMBOL = "️\uD83D\uDD34"; // Старт
    private static final String END_SYMBOL = "\uD83D\uDFE2";   // Конец

    private static final String WALL_SYMBOL = "⬛";  // Блок
    private static final String PASSAGE_SYMBOL = "⬜"; // Проход
    private static final String SAND_SYMBOL = "\uD83D\uDFE8";    // Песок
    private static final String SWAMP_SYMBOL = "\uD83D\uDFEB"; // Болото
    private static final String COIN_SYMBOL = "\uD83E\uDD6E"; // Монета
    private static final String PATH_SYMBOL = "\uD83D\uDC38";
    private final PrintStream output;

    public ConsoleRender(PrintStream output) {
        this.output = output;
    }

    @Override
    public String render(Maze maze, Coordinate start, Coordinate end) {
        StringBuilder mazeRepresentation = new StringBuilder();

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.getType() == Type.WALL) {
                    mazeRepresentation.append(WALL_SYMBOL);
                } else if (new Coordinate(row, col).equals(start)) {
                    mazeRepresentation.append(START_SYMBOL);
                } else if (new Coordinate(row, col).equals(end)) {
                    mazeRepresentation.append(END_SYMBOL);
                } else if (cell.getTerrainType() == TerrainType.SAND) {
                    mazeRepresentation.append(SAND_SYMBOL);
                } else if (cell.getTerrainType() == TerrainType.SWAMP) {
                    mazeRepresentation.append(SWAMP_SYMBOL);
                } else if (cell.getTerrainType() == TerrainType.COIN) {
                    mazeRepresentation.append(COIN_SYMBOL);
                } else {
                    mazeRepresentation.append(PASSAGE_SYMBOL);
                }
            }
            mazeRepresentation.append("\n");
        }

        output.println("Старт: " + START_SYMBOL);
        output.println("Финиш: " + END_SYMBOL);
        output.println("Стена: " + WALL_SYMBOL);
        output.println("Проход: " + PASSAGE_SYMBOL);
        output.println("Песок: " + SAND_SYMBOL);
        output.println("Болото: " + SWAMP_SYMBOL);
        output.println("Монета: " + COIN_SYMBOL);
        output.println();
        output.println("Путь: " + PATH_SYMBOL);
        output.println();

        return mazeRepresentation.toString();
    }

    @Override
    public String renderPath(Maze maze, List<Coordinate> path) {
        if (path == null || path.isEmpty()) {
            return "Результат: Не найден путь от точки А до точки Б"; // Сообщение, если путь не найден
        }

        output.println("Результат:");

        StringBuilder result = new StringBuilder();

        // Создаем временный лабиринт для отображения
        String[][] displayGrid = new String[maze.getHeight()][maze.getWidth()];

        // Заполняем временный лабиринт символами по умолчанию
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.getType() == Type.WALL) {
                    displayGrid[row][col] = WALL_SYMBOL; // Стена
                } else if (cell.getType() == Type.PASSAGE) {
                    displayGrid[row][col] = PASSAGE_SYMBOL; // Проход
                } else if (cell.getTerrainType() == TerrainType.SAND) {
                    displayGrid[row][col] = SAND_SYMBOL; // Песок
                } else if (cell.getTerrainType() == TerrainType.SWAMP) {
                    displayGrid[row][col] = SWAMP_SYMBOL; // Болото
                } else if (cell.getTerrainType() == TerrainType.COIN) {
                    displayGrid[row][col] = COIN_SYMBOL; // Монета
                }
            }
        }

        // Устанавливаем символы старта и конца
        Coordinate start = path.get(0);
        Coordinate end = path.get(path.size() - 1);
        displayGrid[start.row()][start.col()] = START_SYMBOL; // Старт
        displayGrid[end.row()][end.col()] = END_SYMBOL; // Конец

        // Отображаем путь, избегая перезаписи старта и конца
        for (int i = 1; i < path.size() - 1; i++) {
            Coordinate coord = path.get(i);
            // Убедимся, что ячейки старта и конца не перезаписываются
            if (!coord.equals(start) && !coord.equals(end)) {
                displayGrid[coord.row()][coord.col()] = PATH_SYMBOL; // Путь
            }
        }

        // Сборка результата
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                result.append(displayGrid[row][col]);
            }
            result.append("\n");
        }

        return result.toString();
    }
}
