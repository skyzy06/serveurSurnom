package protocole.exception;

/**
 * Exception thrown when the name given doesn't exist in the server data
 */
public class NameDoesntExistException extends GetNicknamesException {
    private static final long serialVersionUID = 1L;
	/**
	 * name of the parameter which cause the exception 
	 */
    private String name;
    
	public NameDoesntExistException(String msg, String name) {
	    super(msg);
	    this.name = name;
    }

	/**
	 * @return name of the parameter which cause the exception 
	 */
	public String getName() {
    	return name;
    }
}