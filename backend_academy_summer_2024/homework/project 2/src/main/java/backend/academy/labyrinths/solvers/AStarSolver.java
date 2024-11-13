package backend.academy.labyrinths.solvers;

import backend.academy.labyrinths.enums.Type;
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
import java.util.Set;

// Логика поиска пути методом A*

public class AStarSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::fCost));
        Set<Coordinate> closedSet = new HashSet<>();

        Node startNode = new Node(start, null, 0, heuristic(start, end));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Если достигнута конечная точка, восстанавливаем путь
            if (current.coord.equals(end)) {
                return reconstructPath(current);
            }

            closedSet.add(current.coord);

            for (Coordinate neighbor : getNeighbors(current.coord, maze)) {
                if (closedSet.contains(neighbor) || !isPassable(maze, neighbor)) {
                    continue; // Пропустить уже проверенные ячейки и непроходимые клетки
                }

                int terrainCost = getTerrainCost(maze, neighbor);
                int tentativeGCost = current.gCost + terrainCost; // Общая стоимость пути до текущей клетки

                Node neighborNode = new Node(neighbor, current, tentativeGCost, heuristic(neighbor, end));

                /// Добавляем в открытое множество, если это не улучшает путь или путь короче
                if (!openSet.contains(neighborNode) || tentativeGCost < neighborNode.gCost) {
                    openSet.add(neighborNode);
                }
            }
        }

        return Collections.emptyList(); // Путь не найден
    }

    // Эвристическая функция: Манхэттенское расстояние
    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    // Стоимость клетки в зависимости от типа поверхности
    private int getTerrainCost(Maze maze, Coordinate coord) {
        Cell cell = maze.getCell(coord.row(), coord.col());
        return cell.getTerrainType().getCost();
    }

    // Получение проходимых соседей
    private List<Coordinate> getNeighbors(Coordinate coord, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = coord.row();
        int col = coord.col();

        // Возможные направления: вверх, вниз, влево, вправо
        if (row > 0) {
            neighbors.add(new Coordinate(row - 1, col)); // Вверх
        }
        if (row < maze.getHeight() - 1) {
            neighbors.add(new Coordinate(row + 1, col)); // Вниз
        }
        if (col > 0) {
            neighbors.add(new Coordinate(row, col - 1)); // Влево
        }
        if (col < maze.getWidth() - 1) {
            neighbors.add(new Coordinate(row, col + 1)); // Вправо
        }

        return neighbors;
    }

    // Проверка, является ли клетка проходимой
    private boolean isPassable(Maze maze, Coordinate coord) {
        if (coord.row() < 0 || coord.row() >= maze.getHeight()
            || coord.col() < 0 || coord.col() >= maze.getWidth()) {
            return false; // Вне границ
        }
        return maze.getCell(coord.row(), coord.col()).getType() != Type.WALL; // Не стена
    }

    // Восстановление пути от конечной точки к начальной
    private List<Coordinate> reconstructPath(Node node) {
        List<Coordinate> path = new ArrayList<>();
        Node currentNode = node;

        while (currentNode != null) {
            path.add(currentNode.coord);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path); // Обратный порядок для получения пути от начала до конца
        return path;
    }

    private static class Node {
        Coordinate coord;
        Node parent;
        int gCost; // Стоимость пути от начальной точки до этой клетки
        int hCost; // Ожидаемая стоимость от этой клетки до цели

        Node(Coordinate coord, Node parent, int gCost, int hCost) {
            this.coord = coord;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
        }

        int fCost() {
            return gCost + hCost;
        }
    }
}
