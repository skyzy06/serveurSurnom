package protocole;

import java.util.LinkedList;
import java.util.List;


/**
 * Add command must be send by the client to the server 
 * in order to  : 
 * add a new name with nicknames, 
 * or add nicknames to the name given
 */
public class Add extends Command {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name to add
	 */
    String name = "";
    
	/**
	 * Nicknames to add
	 */
    List<String> nicknames = new LinkedList<String>();

    /**
	 * Exceptions thrown by the server if the command failed
	 */
    List<AddException> errors = new LinkedList<AddException>();

    /**
     * Make an Add command to add a new name with nicknames, or add nicknames to the name given
	 * @param name
	 * @param nicknames to add 
	 */
    public Add(String name,
            List<String> nicknames) {
        super();
        this.name = name;
        this.nicknames = nicknames;
    }

    /**
     * @return exceptions thrown by the server if the command failed
	 */
    public List<AddException> getErrors() {
    	return errors;
    }

    /**
     * Fonction reserved of the server
     * @param e exception set by the server for the result
	 */
	public void addError(AddException e) {
		this.errors.add(e);
    }

    /**
     * @return name parameter of the command
	 */
	public String getName() {
        return name;
    }

    /**
     * @return nicknames parameter of the command
	 */
    public List<String> getNicknames() {
        return nicknames;
    }
}