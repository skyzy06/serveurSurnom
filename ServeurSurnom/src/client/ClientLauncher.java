package client;

import java.io.IOException;

public class ClientLauncher {
	
	public static void main(String[] args){

		Client client = new Client("localhost",1234);
		if(client.connect())
		try {
			do{
				client.connect();
			}while(client.commandPrompt());
			
			System.out.println("Exiting programm...");
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{		
		}
		
	}
}