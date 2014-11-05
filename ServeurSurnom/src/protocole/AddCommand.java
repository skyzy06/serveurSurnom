package protocole;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AddCommand extends Command {

    // => parameter for the request to the server
    String name = "";
    List<String> nicknames = new LinkedList<String>();

    // <= eventual error message in the response of the server
    String errorMsg = "";

    /**
     *
     * @param succeed
     * @param date
     * @param name
     * @param nicknames
     */
    public AddCommand(boolean succeed, Calendar date, String name,
            List<String> nicknames) {
        super(succeed, date);
        this.name = name;
        this.nicknames = nicknames;
    }

    /**
     *
     * @return
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     *
     * @param errorMsg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public List<String> getNicknames() {
        return nicknames;
    }
}
