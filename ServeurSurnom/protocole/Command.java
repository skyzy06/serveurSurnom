package protocole;

import java.io.Serializable;

public abstract class Command implements Serializable {

	private static final long serialVersionUID = 1L;

    // <= response to the server
    boolean isSucceed = false;

    public Command() {
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
}