package backend.academy.log.pipeline.interfaces;

import backend.academy.log.pipeline.Context;

public interface Handler {
    void setNext(Handler next);

    void handle(Context context);
}
