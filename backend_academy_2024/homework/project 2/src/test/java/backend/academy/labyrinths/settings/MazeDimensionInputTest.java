package backend.academy.labyrinths.settings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeDimensionInputTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    private MazeDimensionInput createMazeDimensionInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        PrintStream output = new PrintStream(outputStream);

        return new MazeDimensionInput(output, inputStream);
    }

    @Test
    void testValidInput() {
        MazeDimensionInput mazeDimensionInput = createMazeDimensionInput("10\n");
        int result = mazeDimensionInput.getMazeDimension("Введите размер лабиринта:");

        assertEquals(10, result);
    }

    @Test
    void testInvalidInput_nonInteger() {
        MazeDimensionInput mazeDimensionInput = createMazeDimensionInput("abc\n2\n");
        int result = mazeDimensionInput.getMazeDimension("Введите размер лабиринта:");

        String expectedOutput = "Введите размер лабиринта:" + System.lineSeparator() +
            "Пожалуйста, введите корректное целое число." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(2, result);
    }

    @Test
    void testInvalidInput_negativeNumber() {
        MazeDimensionInput mazeDimensionInput = createMazeDimensionInput("-5\n8\n");
        int result = mazeDimensionInput.getMazeDimension("Введите размер лабиринта:");

        String expectedOutput = "Введите размер лабиринта:" + System.lineSeparator() +
            "Пожалуйста, введите положительное целое число." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(8, result);
    }

    @Test
    void testInvalidInput_multipleNumbers() {
        MazeDimensionInput mazeDimensionInput = createMazeDimensionInput("5 10\n5\n");
        int result = mazeDimensionInput.getMazeDimension("Введите размер лабиринта:");

        String expectedOutput = "Введите размер лабиринта:" + System.lineSeparator() +
            "Пожалуйста, введите ровно одно целое число." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertEquals(5, result);
    }
}
