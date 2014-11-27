package client;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import protocole.Add;

public class ClientUDP {

  final static int port = 1234;
  static byte buffer[] = new byte[1024];

  public static void main(String argv[]) throws Exception {
    try {
    	
    	/**
		 * Construtct request to set nickname 'Ana' to 'Anais' and send it
		 */
		List<String> l = new LinkedList<String>();
		l.add("Ana");
		Add a = new Add("Anaïs", l);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(outputStream);
		out.writeObject(new Add("Anaïs", l));
		byte[] data = outputStream.toByteArray();
    	
     // String s = "HELLO\n";
      InetAddress serveur = InetAddress.getLocalHost();
      int length = data.length;
           
      DatagramSocket socket = new DatagramSocket();
      DatagramPacket donneesEmises = new DatagramPacket(data, length, serveur, port);
//      DatagramPacket donneesRecues = new DatagramPacket(new byte[1024], 1024);

      socket.setSoTimeout(30000);
      socket.send(donneesEmises);
//      socket.receive(donneesRecues);
//
//      System.out.println("Message : " + new String(donneesRecues.getData(), 
//        0, donneesRecues.getLength()));
//      System.out.println("de : " + donneesRecues.getAddress() + ":" + 
//        donneesRecues.getPort());
    } catch (SocketTimeoutException ste) {
      System.out.println("Le delai pour la reponse a expire");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}