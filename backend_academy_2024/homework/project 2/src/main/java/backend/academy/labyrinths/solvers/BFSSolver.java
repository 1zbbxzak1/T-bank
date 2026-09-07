package backend.academy.labyrinths.solvers;

import backend.academy.labyrinths.interfaces.Solver;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

// Логика поиска пути методом BFS

public class BFSSolver implements Solver {
    private static final int[][] DIRECTIONS = {
        {-1, 0}, // вверх
        {1, 0},  // вниз
        {0, -1}, // влево
        {0, 1}   // вправо
    };

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (!maze.isPassable(start.row(), start.col()) || !maze.isPassable(end.row(), end.col())) {
            return Collections.emptyList();
        }

        // Очередь с приоритетом, где узлы с меньшей суммой веса пути будут обрабатываться раньше
        Queue<PathNode> queue = new PriorityQueue<>(Comparator.comparingInt(PathNode::getCost));
        queue.add(new PathNode(List.of(start), 0));

        Set<Coordinate> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            PathNode pathNode = queue.poll();
            List<Coordinate> path = pathNode.path;
            Coordinate current = path.get(path.size() - 1);

            if (current.equals(end)) {
                return path;
            }

            for (int[] direction : DIRECTIONS) {
                int newRow = current.row() + direction[0];
                int newCol = current.col() + direction[1];
                Coordinate neighbor = new Coordinate(newRow, newCol);

                if (maze.isPassable(newRow, newCol) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<Coordinate> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);

                    // Сумма текущего веса пути и стоимости новой клетки
                    int newCost = pathNode.cost + getTerrainCost(maze, neighbor);
                    queue.add(new PathNode(newPath, newCost));
                }
            }
        }

        return Collections.emptyList(); // Путь не найден
    }

    // Получение стоимости клетки в зависимости от типа поверхности
    private int getTerrainCost(Maze maze, Coordinate coord) {
        Cell cell = maze.getCell(coord.row(), coord.col());
        return cell.getTerrainType().getCost();
    }

    // Вспомогательный класс для хранения пути и его стоимости
    private static class PathNode {
        List<Coordinate> path;
        int cost;

        PathNode(List<Coordinate> path, int cost) {
            this.path = path;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }
}
