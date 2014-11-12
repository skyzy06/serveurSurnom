package protocole;

import java.util.LinkedList;
import java.util.List;

public class GetNicknamesCommand extends Command {

    private static final long serialVersionUID = 1L;

    // => parameter for the request to the server
    String name = "";

    // <= eventual error message in the response of the server
    String errorMsg = "";
    List<String> nicknames = new LinkedList<String>();

    public GetNicknamesCommand(String n) {
        super();
        this.name = n;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public String getName() {
        return name;
    }
}
