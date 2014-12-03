package client;

import java.io.IOException;

public class ClientLauncher {
	
	public static void main(String[] args){

		Client client = new Client("localhost",1234);
		
		try {
			
			while(client.connect() && client.commandPrompt());
			
			System.out.println("Exiting programm...");
		} catch (IOException e1) {
		}
		
	}
}