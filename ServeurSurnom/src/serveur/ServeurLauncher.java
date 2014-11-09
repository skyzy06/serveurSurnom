/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Administrateur
 */
public class ServeurLauncher {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("La commande de lancement est de la forme java ServeurLauncher <PORTNUM>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        System.out.println("Démarrage du serveur de surnom");
        try {
            Serveur serviceSurnom = new Serveur(portNumber);
        } catch (IOException e) {
        }
    }
}
