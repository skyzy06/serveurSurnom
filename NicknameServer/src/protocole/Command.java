package protocole;

import java.io.Serializable;

/**
 * Objects exchanged between the client and the server extends this Command class
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 
	 * Indicate if the execution of the Command succeed or not
	 * */ 
    private boolean isSucceed = false;

	/** 
	 * @return test if the command succeed or not
	 * */    
    public boolean isSucceed() {
        return isSucceed;
    }

    /**
     * Fonction reserved to the server
     * @param isSucceed set by the server
	 */
    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
}