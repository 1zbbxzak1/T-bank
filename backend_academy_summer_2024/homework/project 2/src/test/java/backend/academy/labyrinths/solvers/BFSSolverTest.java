package backend.academy.labyrinths.solvers;

import backend.academy.labyrinths.enums.TerrainType;
import backend.academy.labyrinths.enums.Type;
import backend.academy.labyrinths.models.Cell;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BFSSolverTest {

    @Test
    void testSimplePath() {
        Maze maze = createSimpleMaze();
        BFSSolver solver = new BFSSolver();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        List<Coordinate> path = solver.solve(maze, start, end);
        assertEquals(5, path.size());
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(path.size() - 1));
    }

    @Test
    void testNoPath() {
        Maze maze = createMazeWithWalls();
        BFSSolver solver = new BFSSolver();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        List<Coordinate> path = solver.solve(maze, start, end);
        assertTrue(path.isEmpty());
    }

    private Maze createSimpleMaze() {
        Maze maze = new Maze(3, 3);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                maze.setCell(row, col, new Cell(row, col, Type.PASSAGE, TerrainType.NORMAL));
            }
        }
        maze.setCell(1, 0, new Cell(1, 0, Type.WALL, TerrainType.NORMAL));
        maze.setCell(1, 1, new Cell(1, 1, Type.PASSAGE, TerrainType.COIN));

        return maze;
    }

    private Maze createMazeWithWalls() {
        Maze maze = new Maze(3, 3);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                maze.setCell(row, col, new Cell(row, col, Type.PASSAGE, TerrainType.NORMAL));
            }
        }
        maze.setCell(1, 0, new Cell(1, 0, Type.WALL, TerrainType.NORMAL));
        maze.setCell(1, 1, new Cell(1, 1, Type.WALL, TerrainType.NORMAL));
        maze.setCell(1, 2, new Cell(1, 2, Type.WALL, TerrainType.NORMAL));
        return maze;
    }
}
