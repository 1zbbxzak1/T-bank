package backend.academy.log.pipeline;

import backend.academy.log.pipeline.interfaces.Handler;
import backend.academy.log.settings.ReportGenerator;

public class ReportGenerationHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(Context context) {
        context.reportContent =
            ReportGenerator.generateReport(context.format, context.path, context.fromDate, context.toDate,
                context.filterField, context.filterValue, context.out);
        if (context.reportContent == null) {
            context.setError("Ошибка: Отчет не был создан.");
            return;
        }
        if (next != null) {
            next.handle(context);
        }
    }
}
