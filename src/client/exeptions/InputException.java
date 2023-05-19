package client.exeptions;

import mid.logger.ColorLogger;

import java.io.IOException;

public class InputException extends IOException {
    public InputException() {
    }

    public InputException(String message) {
        super(ColorLogger.logErr(message));
    }

    public InputException(String message, Throwable cause) {
        super(message, cause);
    }
}
