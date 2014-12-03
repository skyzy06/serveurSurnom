package client;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import protocole.command.Add;
import protocole.command.Exit;
import protocole.command.GetNicknames;

/**
 * Classe contenant la création et l'interprétation des commandes clients
 *
 * @author Thomas Clop
 */
public class Client {

    String hostName;
    int portNumber;

    protected ObjectTCP TCPconnection;

    /**
     * Constructeur du client
     *
     * @param host adresse du serveur
     * @param portNumber port de communication du serveur
     */
    public Client(String host, int portNumber) {
        this.hostName = host;
        this.portNumber = portNumber;

        this.TCPconnection = new ObjectTCP(hostName, portNumber);
    }

    /**
     * Retourner le test de la connection au serveur
     *
     * @return true si la connexion c'est bien déroulée
     */
    public boolean connect() {
        return TCPconnection.connect();
    }
}
