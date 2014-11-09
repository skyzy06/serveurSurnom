import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import protocole.AddCommand;
import protocole.Command;
import protocole.ExitCommand;
import protocole.GetNicknamesCommand;

public class ClientCommandsListener implements Runnable {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream out;

	public ClientCommandsListener(Socket socket) {
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			/**
			 * Listen client commands and try to execute them
			 */
			while (ois != null) {
				Command cmd = (Command)ois.readObject();
				if (cmd != null) {
					// Case client disconnect
					if(cmd instanceof ExitCommand) {
						System.out.println("Client closed the established connection");
						ois.close();
						out.close();
						socket.close();
						return;
					}
					exec(cmd);
				}
			}
		} catch (EOFException e) {
			System.out.println("Client interrupts connection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

	public void exec(Command cmd) {
		/**
		 * Determine which command was send by client
		 */
		if(cmd instanceof AddCommand) {
			this.execCommand((AddCommand)cmd);
		} else if (cmd instanceof GetNicknamesCommand) {
			this.execCommand((GetNicknamesCommand)cmd);
		}
	}

	public void execCommand(AddCommand c) {
		System.out.println("Server : ADD");
		c.setSucceed(true);

		for(String n : Server.data.keySet()) {
			if(c.getName().equals(n)) {
				c.setSucceed(false);
				c.addErrorMsg(c.getName().toString() + " name already exists.");
			}

			for(String nk : Server.data.get(n)) {
				for(String nkCmd : c.getNicknames()) {
					if(nk.equals(nkCmd)) {
						c.setSucceed(false);
						c.addErrorMsg(nkCmd + " nickname already exists.");
					}
				}
			}
		}

		if(c.isSucceed()) {
			Server.data.put(c.getName(), c.getNicknames());
		}

		this.sendResponse(c);
	}

	public void execCommand(GetNicknamesCommand c) {
		System.out.println("Server : GET");
		c.setSucceed(true);

		for(String n : Server.data.keySet()) {
			if(c.getName().equals(n)) {
				c.setNicknames(Server.data.get(n));
				this.sendResponse(c);
				return;
			}
		}
		c.setSucceed(false);
		c.setErrorMsg(c.getName().toString() + " name doesn't exist.");
		this.sendResponse(c);
	}

	public void sendResponse(Command c) {
		try {
			out.writeObject(c);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}

