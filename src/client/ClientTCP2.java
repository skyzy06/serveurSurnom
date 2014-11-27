package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import protocole.Exit;
import protocole.GetNicknames;

public class ClientTCP2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket soc = null; // le socket client
		
		try { 		
			/**
			 * Try to connect to the server and initialize stream
			 */
			soc = new Socket(InetAddress.getLocalHost(), 1234);
			ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());  
	
			/**
			 * Construtct request to set nickname 'Ana' to 'Anais' and send it
			 */
		    out.writeObject(new GetNicknames("Anaïs"));
		    
		    /**
		     * Waiting server response
		     */
		    ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
			try {
				while(true) {
					GetNicknames cmd = (GetNicknames)ois.readObject();
					if(cmd != null) {
						System.out.println("Result :" + cmd.getNicknames());
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
		    out.writeObject(new Exit());
		    		    
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