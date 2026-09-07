package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.interfaces.Generator;
import backend.academy.labyrinths.interfaces.Renderer;
import backend.academy.labyrinths.interfaces.Solver;
import backend.academy.labyrinths.models.Maze;
import backend.academy.labyrinths.records.Coordinate;
import backend.academy.labyrinths.view.ConsoleRender;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class MazeSettings {
    private final MazeDimensionInput dimensionInput;
    private final MazeCoordinateInput coordinateInput;
    private final GeneratorSelector generatorSelector;
    private final SolverSelector solverSelector;
    private final Renderer renderer;

    private final PrintStream output;

    public MazeSettings(PrintStream output, InputStream input) {
        this.output = output;
        this.dimensionInput = new MazeDimensionInput(output, input);
        this.coordinateInput = new MazeCoordinateInput(output, input);
        this.generatorSelector = new GeneratorSelector(output, input);
        this.solverSelector = new SolverSelector(output, input);
        this.renderer = new ConsoleRender(output);
    }

    public void start() {
        int height = dimensionInput.getMazeDimension("Введите высоту лабиринта: ");
        int width = dimensionInput.getMazeDimension("Введите ширину лабиринта: ");

        Coordinate start = coordinateInput.getCoordinate("начальную", height, width);
        Coordinate end = coordinateInput.getCoordinate("конечную", height, width);

        Generator generator = generatorSelector.selectMazeGenerator();
        Maze maze = generator.generate(height, width, start, end);

        Solver solver = solverSelector.selectPathSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        output.println();
        output.println(renderer.render(maze, start, end));
        output.println(renderer.renderPath(maze, path));
    }
}
