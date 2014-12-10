package client;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import protocole.command.Add;
import protocole.command.Exit;
import protocole.command.GetNicknames;

/**
 *
 * @author Administrateur
 */
public class ClientByParam extends Client {

    public ClientByParam(String host, int portNumber) {
        super(host, portNumber);
    }

    /**
     * Permet d'avoir une invite de commande pour choisir la commande à envoyer
     *
     * @return si la commande est correcte ou non
     * @throws java.io.IOException
     */
    public boolean commandPrompt(String com, String... param) throws IOException {
        //Demande, vérifie et envoie la commande
        boolean correct = commandSelection(com, param);

        if (!com.equals("quit")) {
            while (true) {
                Add cmd = (Add) TCPconnection.read();
                if (cmd != null) {
                    System.out.println("Result : " + cmd.isSucceed());
                    if (!cmd.isSucceed()) {
                        System.out.println("Result : " + cmd.isSucceed() + cmd.getErrors().toString());
                    }
                    break;
                }
            }
        } else {
            correct = false;
        }

        //Procédure de fin
        if (TCPconnection.send(new Exit())) {
            if (TCPconnection.disconnect()) {
                System.out.println("Déconnection OK");
            }
        }

        return correct;
    }

    /**
     * Methode permettant d'executer la commande demandee
     *
     * @param choix la commande a executer
     * @return la reponse de la commande
     */
    private boolean commandSelection(String com, String... param) throws IOException {
        switch (com) {
            case "ADD":
                if (param.length < 2) {
                    System.out.println("Nombre de paramètre insuffisant");
                    return false;
                }
                List<String> surnom = new LinkedList<String>();
                for (int i = 1; i < param.length; i++) {
                    surnom.add(param[i]);
                }
                return TCPconnection.send(new Add(param[0], surnom));
            case "LIST":
                if (param.length == 0) {
                    System.out.println("Nombre de paramètre insuffisant");
                    return false;
                }
                return TCPconnection.send(new GetNicknames(param[0]));
            default:
                return false;
        }
    }

}
