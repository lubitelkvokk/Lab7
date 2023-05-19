package mid.data.exceptions;

import java.io.IOException;

public class WorldBorderException extends IOException {
    public WorldBorderException() {
        super("Координаты превышают заданные границы");
    }

    public WorldBorderException(String message) {
        super(message);
    }

    public WorldBorderException(String message, Throwable cause) {
        super(message, cause);
    }
}
