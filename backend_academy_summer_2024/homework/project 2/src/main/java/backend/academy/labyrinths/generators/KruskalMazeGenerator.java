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
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Логика генерации лабиринта с использованием алгоритма Краскала

public class KruskalMazeGenerator implements Generator {
    private final Random random = new SecureRandom();
    private int[] parent;
    private int[] rank;

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        Maze maze = new Maze(height, width);
        ObstaclesGenerator obstaclesGenerator = new ObstaclesGenerator();
        initializeMaze(maze, height, width);
        initializeDisjointSet(height, width);

        List<Edge> edges = generateEdges(height, width);

        // Перемешиваем рёбра для случайности
        Collections.shuffle(edges, random);

        for (Edge edge : edges) {
            int cell1 = toIndex(edge.cell1.row(), edge.cell1.col(), width);
            int cell2 = toIndex(edge.cell2.row(), edge.cell2.col(), width);

            // Если клетки находятся в разных компонентах, соединяем их
            if (find(cell1) != find(cell2)) {
                union(cell1, cell2);

                // Открываем путь между соединёнными клетками
                maze.setCell(edge.cell1.row(), edge.cell1.col(),
                    new Cell(edge.cell1.row(), edge.cell1.col(), Type.PASSAGE, TerrainType.NORMAL));
                maze.setCell(edge.cell2.row(), edge.cell2.col(),
                    new Cell(edge.cell2.row(), edge.cell2.col(), Type.PASSAGE, TerrainType.NORMAL));

                // Также открываем проход между ними
                int midRow = (edge.cell1.row() + edge.cell2.row()) / ConfigConstants.TWO_VALUE;
                int midCol = (edge.cell1.col() + edge.cell2.col()) / ConfigConstants.TWO_VALUE;
                maze.setCell(midRow, midCol, new Cell(midRow, midCol, Type.PASSAGE, TerrainType.NORMAL));

                // Случайно открываем дополнительные проходы для создания альтернативных путей
                if (random.nextBoolean()) {
                    createExtraPath(maze, edge.cell1, edge.cell2);
                }
            }
        }

        // Устанавливаем стартовую и конечную точки
        maze.setCell(start.row(), start.col(), new Cell(start.row(), start.col(), Type.PASSAGE, TerrainType.NORMAL));
        maze.setCell(end.row(), end.col(), new Cell(end.row(), end.col(), Type.PASSAGE, TerrainType.NORMAL));

        // Генерация препятствий и монеток
        obstaclesGenerator.generateObstaclesAndCoins(maze, height, width, random);

        return maze;
    }

    // Инициализация всех ячеек как стен
    private void initializeMaze(Maze maze, int height, int width) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze.setCell(row, col, new Cell(row, col, Type.WALL, TerrainType.NORMAL));
            }
        }
    }

    // Создание структуры для объединения-по-рангу и поиска
    private void initializeDisjointSet(int height, int width) {
        int size = height * width;
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    // Генерация списка рёбер между соседними клетками
    private List<Edge> generateEdges(int height, int width) {
        List<Edge> edges = new ArrayList<>();

        for (int row = 0; row < height; row += ConfigConstants.TWO_VALUE) {
            for (int col = 0; col < width; col += ConfigConstants.TWO_VALUE) {
                Coordinate cell = new Coordinate(row, col);

                // Горизонтальные и вертикальные соседи
                if (row + ConfigConstants.TWO_VALUE < height) {
                    edges.add(new Edge(cell, new Coordinate(row + ConfigConstants.TWO_VALUE, col)));
                }
                if (col + ConfigConstants.TWO_VALUE < width) {
                    edges.add(new Edge(cell, new Coordinate(row, col + ConfigConstants.TWO_VALUE)));
                }
            }
        }
        return edges;
    }

    // Преобразование координат в индекс для объединения-по-рангу
    private int toIndex(int row, int col, int width) {
        return row * width + col;
    }

    private void createExtraPath(Maze maze, Coordinate cell1, Coordinate cell2) {
        int midRow = (cell1.row() + cell2.row()) / ConfigConstants.TWO_VALUE;
        int midCol = (cell1.col() + cell2.col()) / ConfigConstants.TWO_VALUE;

        // Проверка и установка дополнительных проходов с ограничением
        // 50% вероятность открывать дополнительный проход
        if (random.nextDouble() < ConfigConstants.AVERAGE_PERCENT_VALUE) {
            if (midRow - 1 >= 0) {
                maze.setCell(midRow - 1, midCol, new Cell(midRow - 1, midCol, Type.PASSAGE, TerrainType.NORMAL));
            }
        }
        if (random.nextDouble() < ConfigConstants.AVERAGE_PERCENT_VALUE) {
            if (midRow + 1 < maze.getHeight()) {
                maze.setCell(midRow + 1, midCol, new Cell(midRow + 1, midCol, Type.PASSAGE, TerrainType.NORMAL));
            }
        }
        if (random.nextDouble() < ConfigConstants.AVERAGE_PERCENT_VALUE) {
            if (midCol - 1 >= 0) {
                maze.setCell(midRow, midCol - 1, new Cell(midRow, midCol - 1, Type.PASSAGE, TerrainType.NORMAL));
            }
        }
        if (random.nextDouble() < ConfigConstants.AVERAGE_PERCENT_VALUE) {
            if (midCol + 1 < maze.getWidth()) {
                maze.setCell(midRow, midCol + 1, new Cell(midRow, midCol + 1, Type.PASSAGE, TerrainType.NORMAL));
            }
        }
    }

    // Класс, представляющий рёбра между клетками
    private static class Edge {
        Coordinate cell1;
        Coordinate cell2;

        Edge(Coordinate cell1, Coordinate cell2) {
            this.cell1 = cell1;
            this.cell2 = cell2;
        }
    }
}
