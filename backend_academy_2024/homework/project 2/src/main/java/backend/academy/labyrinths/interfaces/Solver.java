package backend.academy.labyrinths.interfaces;

import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
