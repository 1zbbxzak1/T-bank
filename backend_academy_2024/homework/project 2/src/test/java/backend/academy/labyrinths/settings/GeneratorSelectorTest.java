package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.generators.KruskalMazeGenerator;
import backend.academy.labyrinths.generators.PrimMazeGenerator;
import backend.academy.labyrinths.interfaces.Generator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class GeneratorSelectorTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    private GeneratorSelector createGeneratorSelector(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        PrintStream output = new PrintStream(outputStream);
        return new GeneratorSelector(output, inputStream);
    }

    @Test
    void testSelectPrimMazeGenerator() {
        GeneratorSelector selector = createGeneratorSelector("1\n");
        Generator generator = selector.selectMazeGenerator();

        assertInstanceOf(PrimMazeGenerator.class, generator, "Ожидался генератор Прима.");
    }

    @Test
    void testSelectKruskalMazeGenerator() {
        GeneratorSelector selector = createGeneratorSelector("2\n");
        Generator generator = selector.selectMazeGenerator();

        assertInstanceOf(KruskalMazeGenerator.class, generator, "Ожидался генератор Краскала.");
    }

    @Test
    void testInvalidInput_nonInteger() {
        GeneratorSelector selector = createGeneratorSelector("abc\n2\n");
        Generator generator = selector.selectMazeGenerator();

        String expectedOutput = "Выберите алгоритм генерации лабиринта:" + System.lineSeparator() +
            "1. Алгоритм Прима" + System.lineSeparator() +
            "2. Алгоритм Краскала" + System.lineSeparator() +
            "Введите число 1 или 2." + System.lineSeparator() +
            "Пожалуйста, выберите 1 или 2." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertInstanceOf(KruskalMazeGenerator.class, generator, "Ожидался генератор Краскала.");
    }

    @Test
    void testInvalidInput_outOfRange() {
        GeneratorSelector selector = createGeneratorSelector("3\n2\n");
        Generator generator = selector.selectMazeGenerator();

        String expectedOutput = "Выберите алгоритм генерации лабиринта:" + System.lineSeparator() +
            "1. Алгоритм Прима" + System.lineSeparator() +
            "2. Алгоритм Краскала" + System.lineSeparator() +
            "Пожалуйста, выберите 1 или 2." + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
        assertInstanceOf(KruskalMazeGenerator.class, generator, "Ожидался генератор Краскала.");
    }

}
