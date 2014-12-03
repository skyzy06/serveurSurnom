/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import protocole.command.Command;

/**
 *
 * @author Administrateur
 */
public class Serveur implements Runnable{

	private ServerSocket socketServeur;
	private Socket clientSocket = null;
	private boolean listening = true; // écoute ou non
	private int portNumber;
    public static HashMap<String, List<String>> data = new HashMap<String, List<String>>();
    private int nbClient = 0;

	public Serveur(int portNumber) {
		this.portNumber = portNumber;
	}

	public void connect() throws IOException {
		//To avoid the "address already in use" exception
		socketServeur = new ServerSocket();
		socketServeur.setReuseAddress(true);
		socketServeur.bind(new InetSocketAddress(portNumber));
	}


    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = socketServeur.accept();
                socket.setSoTimeout(60 * 1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server : Le client numéro " + nbClient + " est connecté !");
            nbClient++;
            /**
             * Create thread to execute request of this client without blocked
             * other incoming connections
             */
            Thread process = new Thread(new CommandListener(socket));
            process.start();
        }
    }
}
