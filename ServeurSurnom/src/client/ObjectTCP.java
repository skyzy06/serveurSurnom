package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Classe TCP
 * hérite de Communication
 * envoie les données sous formes d'objet
 * 
 * @author achabert
 *
 */
public class ObjectTCP extends Communication{

	String hostname;
	int portnumber;
	
	//Socket TCP
	private Socket socket;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	/**
	 * 
	 * @param hostname la string représentant l'adresse IP cible
	 * @param portNumber le port cible
	 * 
	 * Note : hostname est de la forme "A.B.C.D"
	 */
	public ObjectTCP(String hostname, int portNumber){
		this.hostname = hostname;
		this.portnumber = portNumber;
	}
	
	/**
	 * Connection de la socket
	 * 
	 * @return true si la connection s'est bien déroulée, false sinon
	 */
	public boolean connect(){
		try{
			socket = new Socket(hostname,portnumber);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois= new ObjectInputStream(socket.getInputStream());
			return true;
		}catch(IOException ioe){
			return false;
		}
	}
	
	/**
	 * Envoi l'objet sur le flux de sortie
	 * 
	 * @param o Objet à envoyer
	 * @return true si l'envoi s'est bien déroulé, false sinon
	 */
	public boolean send(Object o){
		if(socket.isConnected()){
			try {
				oos.writeObject(o);
				oos.flush();
				return true;
			} catch (IOException e) {
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * Lis l'objet sur le flux d'entrée
	 * 
	 * @return l'objet en question, ou null si exception
	 */
	public Object read(){
		try {
			return ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}
	
	/**
	 * Déconnecte la socket
	 * @return true si déconnection réussi, false sinon
	 */
	public boolean disconnect(){
		try {
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
