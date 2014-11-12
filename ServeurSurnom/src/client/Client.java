/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import protocole.*;

/**
 *
 * @author Administrateur
 */
public class Client {

    private Socket socketClient;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner sc = new Scanner(System.in);

    public Client(String hostName, int portNumber) {
        try {
            socketClient = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            System.out.println("connecté au serveur");
        } catch (UnknownHostException e) {
            System.err.println("Impossible de se connecter à " + hostName);
        } catch (IOException e) {
        }
    }

    /**
     * Méthode permettant d'interprété les commandes du client
     */
    public void commandReader() throws IOException {
        System.out.println("Choississez la requète à exécuter ou \"quit\" pour sortir");
        System.out.println("ADD : pour ajouter un nom et un surnom");
        System.out.println("LIST : pour lister l'ensemble des surnoms");

        String choix;
        String requete = "";
        do {
            System.out.print("Votre choix : ");
            choix = sc.next();
            requete = commandSelection(choix);
            ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());
            try {
                while (true) {
                    GetNicknamesCommand cmd = (GetNicknamesCommand) ois.readObject();
                    if (cmd != null) {
                        System.out.println("Result :" + cmd.getNicknames());
                        break;
                    }
                }
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        } while (!choix.equals("quit"));
        out.writeObject(new ExitCommand());
        in.close();
        out.close();
        socketClient.close();
    }

    /**
     * Méthode permettant d'exécuter la commande demandée
     *
     * @param choix la commande à exécuter
     * @return la réponse de la commande
     */
    private String commandSelection(String choix) throws IOException {
        switch (choix) {
            //TODO remplacer par les appels de méthodes
            case "ADD":
                String nom;
                List<String> surnom = new LinkedList<String>();
                System.out.print("A quel nom voulez-vous ajouter un surnom? : ");
                nom = sc.next();
                do {
                    System.out.print("Quel surnom voulez-vous lui attribuer? : ");
                    surnom.add(sc.next());
                    System.out.print("Voulez-vous en ajouter un autre? : ");
                } while (!sc.next().equals("no"));
                out.writeObject(new AddCommand(nom, surnom));
                return "ADD";
            case "LIST":
                return "LIST";
        }
        return "Default";
    }
}
