/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Administrateur
 */
public class ClientLauncher {

    public static void main(String[] args) throws IOException {
        /*if (args.length != 2) {
         System.err.println("La commande de lancement est de la forme java ClientLauncher <HOSTNAME> <PORTNUM>");
         System.exit(1);
         }*/

        String hostname = "127.0.0.1";//args[0];
        int portNumber = 1999;//Integer.parseInt(args[1]);

        Client clientSocket = new Client(hostname, portNumber);
        clientSocket.commandReader();
    }
}
