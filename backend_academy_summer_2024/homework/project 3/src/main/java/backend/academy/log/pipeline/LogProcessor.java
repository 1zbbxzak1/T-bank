package backend.academy.log.pipeline;

import backend.academy.log.pipeline.interfaces.Handler;
import backend.academy.log.settings.ArgumentsParser;
import backend.academy.log.settings.DateParser;
import backend.academy.log.settings.records.ArgumentValues;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogProcessor {
    public static void start(String[] args) {
        Context context = new Context();
        ArgumentValues values = ArgumentsParser.processArguments(args);

        if (values.mainCommand() == null || !values.mainCommand().equalsIgnoreCase("analyzer")) {
            context.out.println("Invalid main command. Use 'analyzer'.");
            System.exit(1);
        }

        context.path = values.path();
        context.fromDate = DateParser.parseDate(values.fromDate(), context.out);
        context.toDate = DateParser.parseDate(values.toDate(), context.out);
        context.format = values.format();
        context.filterField = values.filterField();
        context.filterValue = values.filterValue();

        Handler argumentHandler = new ArgumentHandler();
        Handler reportHandler = new ReportGenerationHandler();
        Handler fileHandler = new FileWriteHandler();

        argumentHandler.setNext(reportHandler);
        reportHandler.setNext(fileHandler);

        argumentHandler.handle(context);

        if (context.hasError) {
            context.out.println("Обработка завершена с ошибками.");
        } else {
            context.out.println("Обработка завершена успешно.");
        }
    }
}
