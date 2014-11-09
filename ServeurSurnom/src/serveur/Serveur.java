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
public class Serveur {

    private ServerSocket socketServeur;
    private boolean listen = true;

    public Serveur(int portNumber) {
        try {
            socketServeur = new ServerSocket(portNumber);
        } catch (IOException e) {
        }
    }
}
