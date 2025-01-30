package backend.academy.log.pipeline;

import backend.academy.log.settings.FileWriter;
import backend.academy.log.settings.enums.ReportFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class FileWriterHandlerTest {

    private FileWriteHandler fileWriteHandler;
    private Context context;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        fileWriteHandler = new FileWriteHandler();
        context = new Context();

        outContent = new ByteArrayOutputStream();
        context.out = new PrintStream(outContent);
    }

    @Test
    public void testHandleFileWrittenSuccessfully() {
        context.format = ReportFormat.MARKDOWN;
        context.reportContent = "Sample Report Content";

        try (MockedStatic<FileWriter> mockedFileWriter = mockStatic(FileWriter.class)) {
            fileWriteHandler.handle(context);

            assertFalse(context.hasError);
            mockedFileWriter.verify(() -> FileWriter.writeToFile(
                "report.MARKDOWN", "Sample Report Content", context.out));
        }
    }

    @Test
    public void testHandleIOException() throws IOException {
        context.format = ReportFormat.MARKDOWN;
        context.reportContent = "Sample Report Content";

        try (MockedStatic<FileWriter> mockedFileWriter = mockStatic(FileWriter.class)) {
            mockedFileWriter.when(() -> FileWriter.writeToFile(anyString(), anyString(), any(PrintStream.class)))
                .thenThrow(new IOException("Test IOException"));

            fileWriteHandler.handle(context);

            assertTrue(context.hasError);
            String output = outContent.toString().trim();
            assertTrue(output.contains("Ошибка записи файла: Test IOException"));
        }
    }
}
