package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.records.Coordinate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeCoordinateInputTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    private MazeCoordinateInput createMazeCoordinateInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        PrintStream output = new PrintStream(outputStream);
        return new MazeCoordinateInput(output, inputStream);
    }

    @Test
    void testValidInput() {
        MazeCoordinateInput mazeCoordinateInput = createMazeCoordinateInput("3 4\n");
        Coordinate result = mazeCoordinateInput.getCoordinate("начальную", 10, 10);

        assertEquals(new Coordinate(3, 4), result);
    }

    @Test
    void testInvalidInput_nonInteger() {
        MazeCoordinateInput mazeCoordinateInput = createMazeCoordinateInput("abc def\n3 4\n");
        Coordinate result = mazeCoordinateInput.getCoordinate("начальную", 10, 10);

        String expectedOutput = "Введите начальную точку (строка и столбец через пробел): " +
            "Пожалуйста, введите корректные целые числа." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(new Coordinate(3, 4), result);
    }

    @Test
    void testInvalidInput_outOfBounds() {
        MazeCoordinateInput mazeCoordinateInput = createMazeCoordinateInput("10 15\n3 4\n");
        Coordinate result = mazeCoordinateInput.getCoordinate("начальную", 10, 10);

        String expectedOutput = "Введите начальную точку (строка и столбец через пробел): " +
            "Координаты должны быть в пределах [0, 10) x [0, 10). Попробуйте ещё раз: ";
        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(new Coordinate(3, 4), result);
    }

    @Test
    void testInvalidInput_incorrectFormat() {
        MazeCoordinateInput mazeCoordinateInput = createMazeCoordinateInput("3 4 5\n3 4\n");
        Coordinate result = mazeCoordinateInput.getCoordinate("начальную", 10, 10);

        String expectedOutput = "Введите начальную точку (строка и столбец через пробел): " +
            "Пожалуйста, введите ровно два целых числа." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(new Coordinate(3, 4), result);
    }
}
