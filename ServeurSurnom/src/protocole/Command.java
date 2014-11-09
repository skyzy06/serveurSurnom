package protocole;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author Administrateur
 */
public class Command implements Serializable {

    // => parameter for the request to the server
    Calendar date = Calendar.getInstance();

    // <= response to the server
    boolean isSucceed = false;

    /**
     *
     * @param succeed
     * @param date
     */
    public Command(boolean succeed, Calendar date) {
        this.isSucceed = succeed;
        this.date = date;
    }

    /**
     *
     * @return
     */
    public boolean isSucceed() {
        return isSucceed;
    }

    /**
     *
     * @param isSucceed
     */
    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    /**
     *
     * @return
     */
    public Calendar getDate() {
        return date;
    }
}
