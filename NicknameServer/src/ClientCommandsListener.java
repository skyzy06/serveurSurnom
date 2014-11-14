import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import protocole.Add;
import protocole.Command;
import protocole.Exit;
import protocole.GetNicknames;
import protocole.GetNicknamesException;
import protocole.NameAlreadyExistsException;
import protocole.NameDoesntExistException;

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
					if(cmd instanceof Exit) {
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

	public void exec(Command c) {
		/**
		 * Determine which command was send by client
		 */
		Object res = null;
		try {
	         res = this.getClass().getMethod("execCommand", c.getClass()).invoke(this, c.getClass().cast(c));
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
	        e.printStackTrace();
        }
		this.sendResponse((Command)res);
	}

	public void sendResponse(Command c) {
		try {
			out.writeObject(c);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	/* ***** SERVICES ***** */
	
	public Add execCommand(Add c) {
		System.out.println("Server : ADD");
		c.setSucceed(true);

		for(String n : Server.data.keySet()) {
			if(c.getName().equals(n)) {
				c.setSucceed(false);
				c.addError(new NameAlreadyExistsException(c.getName(), "Name already exists."));
			}

			for(String nk : Server.data.get(n)) {
				for(String nkCmd : c.getNicknames()) {
					if(nk.equals(nkCmd)) {
						c.setSucceed(false);
						c.addError(new NameAlreadyExistsException(nkCmd, "Nickname already exists."));
					}
				}
			}
		}

		if(c.isSucceed()) {
			Server.data.put(c.getName(), c.getNicknames());
		}

//		this.sendResponse(c);
		return c;
	}

	public GetNicknames execCommand(GetNicknames c) {
		System.out.println("Server : GET");
		c.setSucceed(true);

		for(String n : Server.data.keySet()) {
			if(c.getName().equals(n)) {
				c.setNicknames(Server.data.get(n));
//				this.sendResponse(c);
				return c;
			}
		}
		c.setSucceed(false);
		
		c.setError(new NameDoesntExistException(c.getName(), "Name doesn't exist."));
//		this.sendResponse(c);
		return c;
	}


}

