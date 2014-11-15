package client;

import java.io.IOException;

public class ClientLauncher {
	
	public static void main(String[] args){

		Client client = new Client("localhost",1234);
		client.connect();
		try {
			client.commandPrompt();
			client.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}