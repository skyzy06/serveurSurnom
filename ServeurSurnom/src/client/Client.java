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
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Administrateur
 */
public class Client {

    private Socket socketClient;
    private BufferedWriter out;
    private BufferedReader in;
    private Scanner sc = new Scanner(System.in);

    public Client(String hostName, int portNumber) {
        try {
            socketClient = new Socket(hostName, portNumber);
            out = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            System.out.println("connecté au serveur");
        } catch (UnknownHostException e) {
            System.err.println("Impossible de se connecter à " + hostName);
        } catch (IOException e) {
        }
    }

    /**
     * Méthode permettant d'interprété les commandes du client
     */
    public void commandReader() {
        System.out.println("Choississez la requète à exécuter ou \"quit\" pour sortir");
        System.out.println("ADD : pour ajouter un nom et un surnom");
        System.out.println("LIST : pour lister l'ensemble des surnoms");

        String choix;
        String requete = "";
        do {
            System.out.print("Votre choix : ");
            choix = sc.next();
            requete = commandSelection(choix);
        } while (!choix.equals("quit"));
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
}
