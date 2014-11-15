package client;

import java.io.IOException;

public class ClientLauncher {
	
	public static void main(String[] args){

		Client client = new Client("localhost",1234);
		if(client.connect())
		try {
			while(client.commandPrompt());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{		
		}
		
	}
}