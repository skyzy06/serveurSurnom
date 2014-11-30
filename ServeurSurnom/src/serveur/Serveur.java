package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import protocole.command.Command;

/**
 * Classe contenant la création et le lancement du serveur et l'écoute des
 * commandes du client
 *
 * @author Administrateur
 */
public class Serveur {

    private ServerSocket socketServeur;
    private Socket clientSocket = null;
    private boolean listening = true; // écoute ou non
    private int portNumber;
    public static HashMap<String, List<String>> data = new HashMap<String, List<String>>();
    private int nbClient = 0;

    /**
     * Constructeur du serveur
     *
     * @param portNumber port d'écoute du serveur
     */
    public Serveur(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        //To avoid the "address already in use" exception
        socketServeur = new ServerSocket();
        socketServeur.setReuseAddress(true);
        socketServeur.bind(new InetSocketAddress(portNumber));
    }

    /**
     * Méthode de lancement du serveur
     */
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
