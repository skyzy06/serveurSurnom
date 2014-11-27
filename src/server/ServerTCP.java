package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP implements IConnection {
	private ServerSocket serverSoc;
	private Socket soc;
	private static int nbrClient = 1;
	private ObjectOutputStream out;
	private ObjectInputStream ois;
	private int port;
	
	public ServerTCP() {
		this.port = 1234;
	}
	
	public ServerTCP(int port) {
		this.port = port;
	}
	
	public void launch() {
		try {
			serverSoc = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server launched, listening...");
		this.listenConnexions();
	}
	
	public void listenConnexions() {
		while (true) {
			try {
				soc = serverSoc.accept();
				soc.setSoTimeout(60 * 1000);
				out = new ObjectOutputStream(soc.getOutputStream());
				ois = new ObjectInputStream(soc.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Server : Client " + nbrClient
			        + " is connected !");
			nbrClient++;

			/**
			 * Create thread to execute request of this client without blocked
			 * other incoming connections
			 */
			Thread process = new Thread(new ClientCommandsListener(this));
			process.start();
		}
	}	
	
	@Override
	public Object readClientRequest() throws ClassNotFoundException, IOException {
		Object o = ois.readObject();
		return o;
	}
	
	@Override
	public void writeResponse(Object c) throws IOException {
		out.writeObject(c); 
	}
	
	@Override
	public void exit() throws IOException {
		System.out.println("Client closed the established connection");
		out.close();
		ois.close();
		soc.close();
	}

	public static void main(String[] args) {	
		ServerTCP s = new ServerTCP();
		s.launch();
	}
}
