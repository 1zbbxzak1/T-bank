package backend.academy.labyrinths.interfaces;

import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import java.util.List;

public interface Renderer {
    String render(Maze maze, Coordinate start, Coordinate end);

    String renderPath(Maze maze, List<Coordinate> path);
}
