package backend.academy.log.pipeline;

import backend.academy.log.pipeline.interfaces.Handler;
import backend.academy.log.settings.FileWriter;
import java.io.IOException;

public class FileWriteHandler implements Handler {
    @Override
    public void setNext(Handler next) {
        // Конец цепочки
    }

    @Override
    public void handle(Context context) {
        try {
            FileWriter.writeToFile("report." + context.format, context.reportContent, context.out);
        } catch (IOException e) {
            context.setError("Ошибка записи файла: " + e.getMessage());
        }
    }
}
