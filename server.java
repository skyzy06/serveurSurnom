package server;
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws IOException{
		if(args.length != 1){
			System.err.println("Usage:java Server <portnumber>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);//get the port number from the user's input
		
		try(
			//create a socket to listen for a connection from the client
			ServerSocket welcomingSocket = new ServerSocket(portNumber);
			//normal socket for transport the info with the client
			Socket serverSocket = welcomingSocket.accept();
			//to write the info to the serverSocket(to the client) 
			PrintWriter out = new PrintWriter(serverSocket.getOutputStream(),true);
			//to read the info from the serverSocket(from the client)
			BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		){
			String inputLine,outputLine;
			
			//initiate conversation with client
			Protocol p = new Protocol();
			outputLine = p.processInput(null);
			out.println(outputLine);
			
			while((inputLine = in.readLine()) != null){
				outputLine = p.processInput(inputLine);
				out.println(outputLine);
				if(outputLine.equals("Bye."))
					break;
			}
			out.flush();
			
			welcomingSocket.close();
			serverSocket.close();
		}catch(IOException e){
			System.err.println("Exception caught when try to listen on port"
					+ portNumber + "or listenint for a connection");
			System.out.println(e.getMessage());
		}
	}
}
