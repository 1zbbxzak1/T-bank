package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.interfaces.Solver;
import backend.academy.labyrinths.solvers.AStarSolver;
import backend.academy.labyrinths.solvers.BFSSolver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class SolverSelectorTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    private SolverSelector createSolverSelector(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        PrintStream output = new PrintStream(outputStream);
        return new SolverSelector(output, inputStream);
    }

    @Test
    void testSelectAStarSolver() {
        SolverSelector selector = createSolverSelector("1\n");
        Solver solver = selector.selectPathSolver();

        assertInstanceOf(AStarSolver.class, solver, "Ожидался алгоритм A* (A-star).");
    }

    @Test
    void testSelectBFSSolver() {
        SolverSelector selector = createSolverSelector("2\n");
        Solver solver = selector.selectPathSolver();

        assertInstanceOf(BFSSolver.class, solver, "Ожидался алгоритм BFS.");
    }

    @Test
    void testInvalidInput_nonInteger() {
        SolverSelector selector = createSolverSelector("abc\n2\n");
        Solver solver = selector.selectPathSolver();

        String expectedOutput = "Выберите алгоритм поиска пути:" + System.lineSeparator() +
            "1. A* (A-star)" + System.lineSeparator() +
            "2. Поиск в ширину (BFS)" + System.lineSeparator() +
            "Введите число 1 или 2." + System.lineSeparator() +
            "Пожалуйста, выберите 1 или 2." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertInstanceOf(BFSSolver.class, solver, "Ожидался алгоритм BFS.");
    }

    @Test
    void testInvalidInput_outOfRange() {
        SolverSelector selector = createSolverSelector("3\n2\n");
        Solver solver = selector.selectPathSolver();

        String expectedOutput = "Выберите алгоритм поиска пути:" + System.lineSeparator() +
            "1. A* (A-star)" + System.lineSeparator() +
            "2. Поиск в ширину (BFS)" + System.lineSeparator() +
            "Пожалуйста, выберите 1 или 2." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertInstanceOf(BFSSolver.class, solver, "Ожидался алгоритм BFS.");
    }
}
