package protocole;

/**
 * Exception thrown when the nickname already exists in the server data.
 * The nicknames have to be unique.
 */
public class NicknameAlreadyExistsException extends AddException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * nickname of the parameter which cause the exception 
	 */
	private String nickname;
	
	public NicknameAlreadyExistsException(String msg, String nickname) {
	    super(msg);
	    this.nickname = nickname;
    }

	/**
	 * @return nickname of the parameter which cause the exception 
	 */
	public String getNickname() {
    	return nickname;
    }
  
}
