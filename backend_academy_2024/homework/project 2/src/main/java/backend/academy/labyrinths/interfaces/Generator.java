package backend.academy.labyrinths.interfaces;

import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;

public interface Generator {
    Maze generate(int height, int width, Coordinate start, Coordinate end);
}
