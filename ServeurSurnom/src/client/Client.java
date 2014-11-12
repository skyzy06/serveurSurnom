/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
     * Méthode permettant d'interprété les commandes du client
     */
    public boolean commandPrompt() {
        System.out.println("Choississez la requète à exécuter ou \"quit\" pour sortir");
        System.out.println("ADD : pour ajouter un nom et un surnom");
        System.out.println("LIST : pour lister l'ensemble des surnoms");

        String choix;
        String requete = "";
        boolean correct = false;

        do {
            System.out.print("Votre choix : ");
            choix = sc.next();
            requete = commandSelection(choix);
        } while (correct == false || choix == "quit");

        return correct;
    }

    /**
     * Méthode permettant d'exécuter la commande demandée
     *
     * @param choix la commande à exécuter
     * @return la réponse de la commande
     */
    private String commandSelection(String choix) {
        switch (choix) {
            //TODO remplacer par les appels de méthodes
            case "ADD":
                return "ADD";
            case "LIST":
                return "LIST";
        }
        return "Default";
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
