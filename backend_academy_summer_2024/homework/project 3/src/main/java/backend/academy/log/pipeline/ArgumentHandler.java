package backend.academy.log.pipeline;

import backend.academy.log.pipeline.interfaces.Handler;

public class ArgumentHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(Context context) {
        if (context.path == null) {
            context.setError("Ошибка: не указан путь к логам (--path).");
            return;
        }
        if (!context.hasError && next != null) {
            next.handle(context);
        }
    }
}
