package exceptions_custom;

public class BadTimerConstructorException extends Exception {
    public BadTimerConstructorException(String message) {
        super(message);
    }
}