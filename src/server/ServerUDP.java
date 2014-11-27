package server;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerUDP implements IConnection {
	private DatagramSocket serverSoc= null;
	private ObjectOutputStream out;
	private ObjectInputStream ois;
	private int port;
	
	public ServerUDP() {
		this.port = 1234;
	}
	
	public ServerUDP(int port) {
		this.port = port;
	}
	
	public void launch() {
		try {
			serverSoc = new DatagramSocket(port);
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			out = new ObjectOutputStream(outputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server launched, listening...");
		this.listenRequests();
	}
	
	public void listenRequests() {
		Thread process = new Thread(new ClientCommandsListener(this));
		process.start();
	}	
	
	@Override
	public Object readClientRequest() throws ClassNotFoundException, IOException {
		byte[] receiveData = new byte[1024];
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		
		serverSoc.receive(packet);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(receiveData);
		ois = new ObjectInputStream(inputStream);
		
		Object o = ois.readObject();
		return o;
	}
	
	@Override
	public void writeResponse(Object o) throws IOException {
		out.writeObject(o); 
	}
	
	@Override
	public void exit() throws IOException {
		System.out.println("Client closed the established connection");
		out.close();
		ois.close();
	}

	public static void main(String[] args) {	
		ServerUDP s = new ServerUDP();
		s.launch();
	}
}





