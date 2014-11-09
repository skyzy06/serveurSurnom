import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
	/**  
	 * Map<Name, Nickname List>
	 * ConcurrentHashMap used cause of thread context
	 */
	public static Map<String, List<String>> data = new ConcurrentHashMap<String, List<String>>();

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		/**
		 * Init. socket
		 */
		ServerSocket socketserver = null;
		try {
			socketserver = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server launched, listening...");		
		/**
		 * Listen incoming connections
		 */
		int nbrClient = 0;
		while(true){
			Socket socket = null;
			try {
				 socket = socketserver.accept();
				 socket.setSoTimeout(60*1000);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			System.out.println("Server : Le client numéro " + nbrClient + " est connecté !");
			nbrClient++;

			/**
			 * Create thread to execute request of this client
			 * without blocked other incoming connections
			 */
			Thread process = new Thread(new ClientCommandsListener(socket));
			process.start();
		}
	}
}
