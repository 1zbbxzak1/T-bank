package backend.academy.labyrinths.generators;

import backend.academy.labyrinths.constants.ConfigConstants;
import backend.academy.labyrinths.enums.TerrainType;
import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.interfaces.Generator;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Логика генерации лабиринта с использованием алгоритма Прима

public class PrimMazeGenerator implements Generator {
    private final Random random = new SecureRandom();

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        Maze maze = new Maze(height, width);
        ObstaclesGenerator obstaclesGenerator = new ObstaclesGenerator();
        List<Coordinate> toCheck = new ArrayList<>();

        // Инициализация всех ячеек как стены
        initializeMazeAsWalls(maze, height, width);

        // Устанавливаем начальную точку как проход
        setPassageCell(maze, start);
        addValidNeighbors(maze, toCheck, start.row(), start.col());

        // Основной алгоритм генерации лабиринта
        while (!toCheck.isEmpty()) {
            Coordinate cell = toCheck.remove(random.nextInt(toCheck.size()));

            if (maze.getCell(cell.row(), cell.col()).getType() == Type.WALL) {
                // Устанавливаем текущую ячейку как проход
                setPassageCell(maze, cell);
                connectWithAdjacentPassage(maze, cell.row(), cell.col());
                addValidNeighbors(maze, toCheck, cell.row(), cell.col());
            }
        }

        // Генерация тупиков и закоулков
        createDeadEnds(maze, height, width);

        // Устанавливаем конечную точку как проход
        setPassageCell(maze, end);

        // Генерация препятствий и монеток
        obstaclesGenerator.generateObstaclesAndCoins(maze, height, width, random);

        // Убираем случайные стены, чтобы сделать лабиринт неидеальным
        removeRandomWalls(maze, height, width, (height * width) / ConfigConstants.MIN_PERCENT_VALUE);

        return maze;
    }

    public int countAdjacentPassages(Maze maze, int row, int col, int height, int width) {
        int count = 0;
        if (row > 0 && maze.getCell(row - ConfigConstants.ONE_VALUE, col).getType() == Type.PASSAGE) {
            count++;
        }
        if (row < height - ConfigConstants.ONE_VALUE
            && maze.getCell(row + ConfigConstants.ONE_VALUE, col).getType() == Type.PASSAGE) {
            count++;
        }
        if (col > 0 && maze.getCell(row, col - ConfigConstants.ONE_VALUE).getType() == Type.PASSAGE) {
            count++;
        }
        if (col < width - ConfigConstants.ONE_VALUE
            && maze.getCell(row, col + ConfigConstants.ONE_VALUE).getType() == Type.PASSAGE) {
            count++;
        }
        return count;
    }

    private void initializeMazeAsWalls(Maze maze, int height, int width) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze.setCell(row, col, new Cell(row, col, Type.WALL, TerrainType.NORMAL));
            }
        }
    }

    private void createDeadEnds(Maze maze, int height, int width) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).getType() == Type.PASSAGE) {
                    int neighborCount = countAdjacentPassages(maze, row, col, height, width);
                    if (neighborCount == 1 && random.nextBoolean()) {
                        maze.setCell(row, col, new Cell(row, col, Type.WALL, TerrainType.NORMAL));
                    }
                }
            }
        }
    }

    private void removeRandomWalls(Maze maze, int height, int width, int wallRemoveCount) {
        int removed = 0;
        while (removed < wallRemoveCount) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            if (maze.getCell(row, col).getType() == Type.WALL
                && countAdjacentPassages(maze, row, col, height, width) <= ConfigConstants.TWO_VALUE) {
                setPassageCell(maze, new Coordinate(row, col));
                removed++;
            }
        }
    }

    private void setPassageCell(Maze maze, Coordinate coordinate) {
        maze.setCell(coordinate.row(), coordinate.col(),
            new Cell(coordinate.row(), coordinate.col(), Type.PASSAGE, TerrainType.NORMAL));
    }

    // Добавление допустимых соседних ячеек в массив toCheck
    private void addValidNeighbors(Maze maze, List<Coordinate> toCheck, int x, int y) {
        int[][] directions = {
            {ConfigConstants.INVERT_TWO_VALUE, ConfigConstants.ZERO_VALUE},
            {ConfigConstants.TWO_VALUE, ConfigConstants.ZERO_VALUE},
            {ConfigConstants.ZERO_VALUE, ConfigConstants.INVERT_TWO_VALUE},
            {ConfigConstants.ZERO_VALUE, ConfigConstants.TWO_VALUE}
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (isWithinBounds(newX, newY, maze.getHeight(), maze.getWidth())
                && maze.getCell(newX, newY).getType() == Type.WALL
                && !toCheck.contains(new Coordinate(newX, newY))) {
                toCheck.add(new Coordinate(newX, newY));
            }
        }
    }

    // Создание соединения с соседним проходом
    private void connectWithAdjacentPassage(Maze maze, int x, int y) {
        List<int[]> directions =
            List.of(new int[] {ConfigConstants.INVERT_TWO_VALUE, ConfigConstants.ZERO_VALUE},
                new int[] {ConfigConstants.TWO_VALUE, ConfigConstants.ZERO_VALUE},
                new int[] {ConfigConstants.ZERO_VALUE, ConfigConstants.INVERT_TWO_VALUE},
                new int[] {ConfigConstants.ZERO_VALUE, ConfigConstants.TWO_VALUE});

        for (int[] direction : shuffleDirections(directions)) {
            int adjX = x + direction[0];
            int adjY = y + direction[1];
            if (isWithinBounds(adjX, adjY, maze.getHeight(), maze.getWidth())
                && maze.getCell(adjX, adjY).getType() == Type.PASSAGE) {
                setPassageCell(maze,
                    new Coordinate((x + adjX) / ConfigConstants.TWO_VALUE, (y + adjY) / ConfigConstants.TWO_VALUE));
                break;
            }
        }
    }

    private List<int[]> shuffleDirections(List<int[]> directions) {
        List<int[]> shuffled = new ArrayList<>(directions);
        for (int i = 0; i < shuffled.size(); i++) {
            int j = random.nextInt(shuffled.size());
            int[] temp = shuffled.get(i);
            shuffled.set(i, shuffled.get(j));
            shuffled.set(j, temp);
        }
        return shuffled;
    }

    private boolean isWithinBounds(int x, int y, int height, int width) {
        return x >= ConfigConstants.ZERO_VALUE && x < height && y >= ConfigConstants.ZERO_VALUE && y < width;
    }
}
