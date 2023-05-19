package mid.data.exceptions;

import java.io.IOException;

public class IdException extends IOException {
    public IdException() {
        super("Id должен быть больше 0");
    }

    public IdException(String message) {
        super(message);
    }

    public IdException(String message, Throwable cause) {
        super(message, cause);
    }
}
