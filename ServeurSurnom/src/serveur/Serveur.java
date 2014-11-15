/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import protocole.Command;

/**
 *
 * @author Administrateur
 */
public class Serveur {

	private ServerSocket socketServeur;
	private Socket clientSocket = null;
	private boolean listening = true; // écoute ou non
	private int portNumber;

	public Serveur(int portNumber) {
		this.portNumber = portNumber;
	}

	public void connect() throws IOException {
		//To avoid the "address already in use" exception
		socketServeur = new ServerSocket();
		socketServeur.setReuseAddress(true);
		socketServeur.bind(new InetSocketAddress(portNumber));
	}

	public void disconnect() throws IOException{
		socketServeur.close();
	}

	public void run() throws IOException {

		clientSocket = socketServeur.accept();
		listening =false;
		System.out.println("Un client connecté");
		try {
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

			System.out.println("okk");
			
			
			Object o = in.readObject();
			if(o instanceof Command){
				Command c = (Command)o;
				System.out.println("okok");
			}
			if(o instanceof Exception){

			}

		} catch (IOException | ClassNotFoundException e) {
		}

	}
}
