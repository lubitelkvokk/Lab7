package mid.data.exceptions;

import java.io.IOException;

public class CrossingIdException extends IOException {
    public CrossingIdException() {
        super("Пересечение ID групп");
    }

    public CrossingIdException(String msg) {
        super(msg);
    }

    public CrossingIdException(Throwable e, String msg) {
        super(msg, e);
    }
}
