package Exception;

public class CannotProcessException extends RuntimeException {
    public CannotProcessException(String message) {
        super(message);
    }
}
