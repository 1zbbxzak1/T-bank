package backend.academy.labyrinths.models;

import backend.academy.labyrinths.enums.Type;

public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Cell cell) {
        grid[row][col] = cell;
    }

    // Метод для проверки, находится ли ячейка в пределах лабиринта и является ли она проходимой
    public boolean isPassable(int row, int col) {
        // Проверяем границы
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return false; // Ячейка вне границ
        }

        // Проверяем, является ли ячейка проходом
        Cell cell = getCell(row, col);
        return cell.getType() == Type.PASSAGE;
    }
}
