package protocole;

/**
 * Exception of the AddException command
 */
public abstract class AddException extends Exception {
    private static final long serialVersionUID = 1L;

	public AddException(String msg) {
		super(msg);
	}
}
