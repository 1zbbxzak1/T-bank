package backend.academy.log.pipeline;

import backend.academy.log.pipeline.interfaces.Handler;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ArgumentHandlerTest {

    private ArgumentHandler argumentHandler;
    private Context context;
    private ByteArrayOutputStream outContent;

    @Mock
    private Handler nextHandler;

    @BeforeEach
    public void setUp() {
        argumentHandler = new ArgumentHandler();
        context = new Context();

        outContent = new ByteArrayOutputStream();
        context.out = new PrintStream(outContent);
    }

    @Test
    public void testHandlePathNotProvided() {
        argumentHandler.handle(context);

        assertTrue(context.hasError);

        String output = outContent.toString().trim();
        assertEquals("Ошибка: не указан путь к логам (--path).", output);
    }

    @Test
    public void testHandlePathProvided() {
        context.path = "path/to/logfile";

        argumentHandler.setNext(nextHandler);

        argumentHandler.handle(context);

        assertFalse(context.hasError);
        Mockito.verify(nextHandler).handle(context);
    }

    @Test
    public void testHandleErrorFlagSet() {
        context.path = "path/to/logfile";
        context.hasError = true;

        argumentHandler.setNext(nextHandler);

        argumentHandler.handle(context);

        Mockito.verify(nextHandler, Mockito.never()).handle(context);
    }
}
