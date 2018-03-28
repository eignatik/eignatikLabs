package space.eignatik.exceptions;

public class LabException extends RuntimeException {
    public LabException(Throwable throwable) {
        super(throwable);
    }

    public LabException(Throwable throwable, String message) {
        super(message, throwable);
    }

    public LabException(String message) {
        super(message);
    }

}
