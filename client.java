package client;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException{
		if(args.length != 2){
			System.err.println("Usage:java Client <hostname> <portnumber>");
			System.exit(1);
		}
		
		String hostName = args[0];	//get the host name from the user's input 
		int portNumber = Integer.parseInt(args[1]);	//get the port number from the user's input
		
		try(
			Socket clientSocket = new Socket(hostName,portNumber);
			//write the info to the socket(to the server)
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			//read the info from the socket(from the server)
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		){
			//in order to get the inputstream from the user
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			String fromServer;
			String fromUser;
			
			while((fromServer = in.readLine()) != null)
			{
				System.out.println("Server:"+fromServer);
				if(fromServer.equals("Bye"))
					break;
				
				fromUser = stdIn.readLine();//get the order from the user
				if(fromUser != null)
				{
					System.out.println("Client:"+fromUser);
					out.println(fromUser);//write the user's order to the server
				}
			}
			
			clientSocket.close();
			
		}catch(UnknownHostException e){
			System.err.println("DOn't known about host:"+hostName);
			System.exit(1);
		}catch(IOException e){
			System.err.println("Coudn't get I/O for the connection to:"+hostName);
			System.exit(1);
		}
	}
}
