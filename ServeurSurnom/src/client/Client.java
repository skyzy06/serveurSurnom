/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Administrateur
 */
public class Client {

    private Socket socketClient;

    public Client(String hostName, int portNumber) {
        try {
            socketClient = new Socket(hostName, portNumber);
        } catch (IOException e) {
        }
    }
}
