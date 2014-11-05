package protocole;

import java.util.Calendar;

public class Command {

    // => parameter for the request to the server
    Calendar date = Calendar.getInstance();

    // <= response to the server
    boolean isSucceed = false;

    public Command(boolean succeed, Calendar date) {
        this.isSucceed = succeed;
        this.date = date;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public Calendar getDate() {
        return date;
    }
}
