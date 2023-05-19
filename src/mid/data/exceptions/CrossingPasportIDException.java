package mid.data.exceptions;

import java.io.IOException;

public class CrossingPasportIDException extends IOException {
    public CrossingPasportIDException() {
        super("Паспортные данные не уникальны");
    }

    public CrossingPasportIDException(String message) {
        super(message);
    }

    public CrossingPasportIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
