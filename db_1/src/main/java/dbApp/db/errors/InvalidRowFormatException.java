package dbApp.db.errors;

public class InvalidRowFormatException extends RuntimeException {

    public InvalidRowFormatException(String message) {
        super(message);
    }
}
