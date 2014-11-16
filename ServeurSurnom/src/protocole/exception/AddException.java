package protocole.exception;

/**
 * Exception of the AddException command
 */
public class AddException extends Exception {
    private static final long serialVersionUID = 1L;

	public AddException(String msg) {
		super(msg);
	}
}