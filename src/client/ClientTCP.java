package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import protocole.Add;
import protocole.AddException;
import protocole.Command;
import protocole.Exit;
import protocole.NameAlreadyExistsException;

public class ClientTCP {
	private Socket soc;
	private ObjectOutputStream out;
	private ObjectInputStream ois;

	public ClientTCP() {
		try {
			soc = new Socket(InetAddress.getLocalHost(), 1234);
			out = new ObjectOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientTCP(InetAddress ip, int port) {
		try {
			soc = new Socket(ip, port);
			out = new ObjectOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO use reflexivity to read all type of command
	public void readResponseAdd() {
		try {
			ois = new ObjectInputStream(soc.getInputStream());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			while (true) {
				Add cmd = null;
				try {
					cmd = (Add) ois.readObject();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (cmd != null) {
					System.out.println("Result : " + cmd.isSucceed());
					if (!cmd.isSucceed()) {
						for (AddException e : cmd.getErrors()) {
							System.out.println(((NameAlreadyExistsException) e)
							        .getName());
							System.out.println(((NameAlreadyExistsException) e)
							        .getMessage());
						}
					}
					break;
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void exit() {
		try {
			ois.close();
			out.close();
			soc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeRequest(Command c) {
		try {
			out.writeObject(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientTCP c = new ClientTCP();
		List<String> l = new LinkedList<String>();
		l.add("Ana");
		
		c.writeRequest(new Add("Anaïs", l));
		c.readResponseAdd();
		
		c.writeRequest(new Exit());
		c.exit();
	}
}
