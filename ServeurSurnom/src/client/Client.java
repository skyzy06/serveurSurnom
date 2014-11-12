/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;
import java.util.*;

import protocole.*;

/**
 *
 * @author Administrateur
 */
public class Client {

    String hostName;
    int portNumber;

    private Socket socketClient;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Scanner sc = new Scanner(System.in);

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void connect() throws UnknownHostException, IOException {
        socketClient = new Socket();
        socketClient.setReuseAddress(true);
        try {
            socketClient.bind(new InetSocketAddress(hostName, portNumber));
        } catch (BindException be) {
            System.out.println("erreur de bind");
            throw new IOException();
        }
        out = new ObjectOutputStream(socketClient.getOutputStream());
        in = new ObjectInputStream(socketClient.getInputStream());
    }

    public void disconnect() throws IOException {
        socketClient.close();
    }

    /**
     * Methode permettant d'interprete les commandes du client
     *
     * @return si la commande est correcte ou non
     * @throws java.io.IOException
     */
    public boolean commandPrompt() throws IOException {
        System.out.println("Choississez la requete a executer ou \"quit\" pour sortir");
        System.out.println("ADD : pour ajouter un nom et un surnom");
        System.out.println("LIST : pour lister l'ensemble des surnoms");

        boolean correct = false;

        do {
            System.out.print("Votre choix : ");
            correct = commandSelection(sc.next());
        } while (correct == false);
        ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());
        try {
            while (true) {
                AddCommand cmd = (AddCommand) ois.readObject();
                if (cmd != null) {
                    System.out.println("Result : " + cmd.isSucceed());
                    if (!cmd.isSucceed()) {
                        System.out.println("Result : " + cmd.isSucceed() + cmd.getErrorMsgs().toString());
                    }
                    break;
                }
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        out.writeObject(new ExitCommand());
        in.close();
        out.close();
        socketClient.close();
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
            //TODO remplacer par les appels de mÃ©thodes
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
                return true;
            case "LIST":
                return true;
        }
        return false;
    }

    public boolean sendTest() {
        try {
            out.writeUTF("test");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
