package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
public class Client {

	String hostName;
    int portNumber;

    private Socket socketClient;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner sc;
    

    public Client(String host, int portNumber) {
        this.hostName = host;
        this.portNumber = portNumber;
        sc = new Scanner(System.in);
    }

    public boolean connect() {
            
            try	{
            	socketClient = new Socket(hostName,portNumber);
            	
	            out = new ObjectOutputStream(socketClient.getOutputStream());
	            in = new ObjectInputStream(socketClient.getInputStream());
	            
	            return true;
            
            }catch(Exception e){
            	e.printStackTrace();
            	return false;
            }
            
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
            userInput =sc.next();
            correct = commandSelection(userInput);
        } while (correct == false);
        
        
        if(!userInput.equals("quit")){
        	//Attente de la réponse
            try {
            	System.out.println(userInput.length());
                while (true) {
                    Add cmd = (Add) in.readObject();
                    if (cmd != null) {
                        System.out.println("Result : " + cmd.isSucceed());
                        if (!cmd.isSucceed()) {
                            System.out.println("Result : " + cmd.isSucceed() + cmd.getErrors().toString());
                        }
                        break;
                    }
                }
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }else{
        	correct = false;
        }
        
        //Procédure de fin
        out.writeObject(new Exit());
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
                out.writeObject(new Add(nom, surnom));
                out.flush();
                return true;
                
            case "LIST":
                String name;
                System.out.print("A quel nom voulez-vous afficher les surnoms? : ");
                name = sc.next();
                out.writeObject(new GetNicknames(name));
                out.flush();
                return true;
                
            case "quit":
            	return true;
        }
        return false;
    }


    public boolean sendTest(){
    	List<String> l = new ArrayList<String>();
    	
    	Add addTest = new Add("string", l);
    	try {
			out.writeObject(addTest);
			out.flush();
			return true;
    	} catch (IOException e) {
			return false;
		}
 
    }
    
}
