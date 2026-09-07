package backend.academy.fractal.settings;

import backend.academy.fractal.enums.Screen;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetScreenTest {
    @Test
    public void testGetScreenValidInput() {
        String input = "2\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetScreen getScreen = new GetScreen(out, scanner);
        Screen result = getScreen.getScreen();

        assertEquals(Screen.values()[1], result);
    }

    @Test
    public void testGetScreenInvalidInput() {
        String input = "5\n2\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetScreen getScreen = new GetScreen(out, scanner);
        Screen result = getScreen.getScreen();

        assertEquals(Screen.values()[4], result);
    }

    @Test
    public void testGetScreenEdgeCase() {
        String input = "1\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetScreen getScreen = new GetScreen(out, scanner);
        Screen result = getScreen.getScreen();

        assertEquals(Screen.values()[0], result);
    }

    @Test
    public void testGetScreenNonNumericInput() {
        String input = "abc\n2\n";
        Scanner scanner = new Scanner(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        GetScreen getScreen = new GetScreen(out, scanner);
        Screen result = getScreen.getScreen();

        assertEquals(Screen.values()[1], result);
    }
}
