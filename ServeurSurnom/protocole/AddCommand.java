package protocole;

import java.util.LinkedList;
import java.util.List;

public class AddCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	// => parameter for the request to the server
    String name = "";
    List<String> nicknames = new LinkedList<String>();

    // <= eventual error message in the response of the server
    List<String> errorMsgs = new LinkedList<String>();

    public AddCommand(String name,
            List<String> nicknames) {
        super();
        this.name = name;
        this.nicknames = nicknames;
    }

	public List<String> getErrorMsgs() {
		return errorMsgs;
	}

	public void addErrorMsg(String errorMsg) {
		this.errorMsgs.add(errorMsg);
	}

    public String getName() {
        return name;
    }

    public List<String> getNicknames() {
        return nicknames;
    }
}