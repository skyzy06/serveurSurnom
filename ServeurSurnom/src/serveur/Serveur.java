/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class Serveur {

    public static HashMap<String, List<String>> data = new HashMap<String, List<String>>();
    private ServerSocket socketServeur = null;
    private Socket clientSocket = null;
    private boolean listening = true; // ecoute ou non
    private int nbClient = 0;

    public Serveur(int portNumber) throws IOException {
        // To avoid the "address already in use" exception
        socketServeur = new ServerSocket(portNumber);
        System.out.println("Server launched, listening...");
        /*
         * socketServeur = new ServerSocket();
         * socketServeur.setReuseAddress(true); try{ socketServeur.bind(new
         * InetSocketAddress(portNumber)); clientSocket =
         * socketServeur.accept(); }catch(java.net.BindException b){
         * 
         * }
         */

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
