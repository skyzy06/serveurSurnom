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
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrateur
 */
public class Serveur extends Thread {

    private ServerSocket socketServeur;
    private Socket clientSocket = null;
    private boolean listening = true; // écoute ou non

    public Serveur(int portNumber) throws IOException {
        socketServeur = new ServerSocket(portNumber);
        clientSocket = socketServeur.accept();
    }

    public void run() {
        System.out.println("Un client connecté");
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            {
                String inputLine;
                while (listening && (inputLine = in.readLine()) != null) {
                    //TODO  command response
                }
            }
        } catch (IOException e) {
        }

    }
}
