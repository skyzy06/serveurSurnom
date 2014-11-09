import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import protocole.AddCommand;
import protocole.ExitCommand;

public class Client {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket soc = null; // le socket client
		
		try { 		
			/**
			 * Try to connect to the server and initialize stream
			 */
			soc = new Socket(InetAddress.getLocalHost(), 9999);
			ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());  
		   
	
			/**
			 * Construtct request to set nickname 'Ana' to 'Anais' and send it
			 */
			List<String> l = new LinkedList<String>();
			l.add("Ana");
		    out.writeObject(new AddCommand("Anaïs", l));
		    
		    /**
		     * Waiting server response
		     */
		    ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
			try {
				while(true) {
					AddCommand cmd = (AddCommand)ois.readObject();
					if(cmd != null) {
						System.out.println("Result : " + cmd.isSucceed());
						if(!cmd.isSucceed()) {
							System.out.println("Result : " + cmd.isSucceed() + cmd.getErrorMsgs().toString());
						}
						break;
					}
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
		    try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    /**
		     * 	OK now we closed the connection
		     */
		    out.writeObject(new ExitCommand());
		    		    
		    try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    ois.close();
		    out.close();
		    soc.close();
	        
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: hostname");
		}
	}
}
