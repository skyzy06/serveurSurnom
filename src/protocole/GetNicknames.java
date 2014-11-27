package protocole;

import java.util.LinkedList;
import java.util.List;

/**
 * GetNicknames command must be send by the client to know the nicknames of a person
 */
public class GetNicknames extends Command {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of the person
	 */
    String name = "";

	/**
	 * List of nicknames result of the command, set by the server
	 */
    List<String> nicknames = new LinkedList<String>();
    
    /**
	 * Exceptions thrown by the server if the command failed
	 */
    GetNicknamesException error;

    /**
     * Make a GetNicknames command to know the nicknames of a person
     */
	public GetNicknames(String name) {
		super();
		this.name = name;
	}

    /**
     * @return exception thrown by the server if the command failed
	 */
	public GetNicknamesException getError() {
    	return error;
    }

	/**
	 * Fonction reserved to the server
     * @param e exception set by the server for the result
	 */
    public void setError(GetNicknamesException e) {
    	this.error = e;
    }

	/**
     * @return result of the command, ie the list of nicknames
	 */
	public List<String> getNicknames() {
		return nicknames;
	}

    /**
     * Fonction reserved to the server
     * @param nicknames result
	 */
	public void setNicknames(List<String> nicknames) {
		this.nicknames = nicknames;
	}

    /**
     * @return name parameter
	 */
	public String getName() {
		return name;
	}
}
