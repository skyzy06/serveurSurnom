package client;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import protocole.command.Add;
import protocole.command.Exit;
import protocole.command.GetNicknames;

/**
 *
 * @author Administrateur
 */
public class ClientByConsol extends Client {

    private final Scanner sc;

    public ClientByConsol(String host, int portNumber) {
        super(host, portNumber);
        sc = new Scanner(System.in);
    }

    /**
     * Permet d'avoir une invite de commande pour choisir la commande à envoyer
     *
     * @return si la commande est correcte ou non
     * @throws java.io.IOException
     */
    public boolean commandPrompt() throws IOException {
        System.out.println("Choississez la requete a executer ou \"quit\" pour sortir");
        System.out.println("ADD : pour ajouter un nom et un surnom");
        System.out.println("LIST : pour lister l'ensemble des surnoms");

        boolean correct = false;

        String userInput;
        //Demande, vérifie et envoie la commande
        do {
            System.out.print("Votre choix : ");
            userInput = sc.next();
            correct = commandSelection(userInput);
        } while (correct == true);

        if (!userInput.equals("quit")) {
            System.out.println(userInput.length());
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
    private boolean commandSelection(String choix) throws IOException {
        switch (choix) {

            case "ADD":
                String nom;
                List<String> surnom = new LinkedList<String>();
                System.out.print("A quel nom voulez-vous ajouter un surnom? : ");
                nom = sc.next();
                do {
                    System.out.print("Quel surnom voulez-vous lui attribuer? : ");
                    surnom.add(sc.next());
                    System.out.print("Voulez-vous en ajouter un autre? (no pour arrêter): ");
                } while (!sc.next().equals("no"));

                if (TCPconnection.send(new Add(nom, surnom))) {
                    return true;
                } else {
                    return false;
                }

            case "LIST":
                String name;
                System.out.print("A quel nom voulez-vous afficher les surnoms? : ");
                name = sc.next();

                if (TCPconnection.send(new GetNicknames(name))) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

}
