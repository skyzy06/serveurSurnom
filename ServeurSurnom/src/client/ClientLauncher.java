package client;

import java.io.IOException;

public class ClientLauncher {
	
	public static void main(String[] args){

		Client client = new Client("localhost",1234);
		client.connect();
		client.sendTest();

		try {
			client.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
