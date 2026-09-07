package backend.academy.fractal.settings;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetIterationsTest {
    @Test
    public void testGetIterationsValidInput() {
        String input = "500\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetIterations getIterations = new GetIterations(out, scanner);
        int result = getIterations.getIterations();

        assertEquals(500, result);
    }

    @Test
    public void testGetIterationsBelowMinInput() {
        String input = "50\n150\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetIterations getIterations = new GetIterations(out, scanner);
        int result = getIterations.getIterations();

        assertEquals(50, result);
    }

    @Test
    public void testGetIterationsInvalidInput() {
        String input = "abc\n200\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetIterations getIterations = new GetIterations(out, scanner);
        int result = getIterations.getIterations();

        assertEquals(200, result);
    }

    @Test
    public void testGetIterationsEdgeCase() {
        String input = "1000\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetIterations getIterations = new GetIterations(out, scanner);
        int result = getIterations.getIterations();

        assertEquals(1000, result);
    }
}
