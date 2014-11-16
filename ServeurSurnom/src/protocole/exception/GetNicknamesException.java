package protocole.exception;

/**
 * Exception of the GetNicknames command
 */
public class GetNicknamesException extends Exception {
    private static final long serialVersionUID = 1L;

	public GetNicknamesException(String msg) {
		super(msg);
	}
}