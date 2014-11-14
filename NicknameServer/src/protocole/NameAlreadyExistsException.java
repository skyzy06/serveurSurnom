package protocole;

/**
 * Exception thrown when the name to add already exists in the server data.
 * The names have to be unique.
 */
public class NameAlreadyExistsException extends AddException {
    private static final long serialVersionUID = 1L;
    
	/**
	 * name of the parameter which cause the exception 
	 */
    private String name;
    
	public NameAlreadyExistsException(String name, String msg) {
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
