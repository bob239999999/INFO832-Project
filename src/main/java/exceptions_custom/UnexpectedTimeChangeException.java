package exceptions_custom;

public class UnexpectedTimeChangeException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnexpectedTimeChangeException(String message) {
        super(message);
    }
}
