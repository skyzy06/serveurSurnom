package serveur;

import java.io.IOException;

public class ServeurLauncher {

	public static void main(String[] args){
		
		Serveur serveur = new Serveur(1234);
		try {
			serveur.connect();
			serveur.run();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
