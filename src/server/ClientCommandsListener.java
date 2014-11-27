package server;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import protocole.Add;
import protocole.Command;
import protocole.Exit;
import protocole.GetNicknames;

public class ClientCommandsListener implements Runnable {
	private IConnection s;
	private NicknamesServices ns = new NicknamesServices();

	public ClientCommandsListener(IConnection s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			Command cmd = null;
			while ((cmd=(Command) s.readClientRequest() )!= null) {
					if(cmd instanceof Exit) {
						this.s.exit();
						return;
					}
					Command res = exec(cmd);
					this.s.writeResponse(res);
			}
		} catch (EOFException e) {
			System.out.println("Client interrupts connection");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Determine which command was send by client
	 */
	public Command exec(Command c) {
		Object res = null;
		try {
	         res = this.getClass().getMethod("execCommand", c.getClass()).invoke(this, c.getClass().cast(c));
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
	        e.printStackTrace();
        }
		return (Command)res;
	}
	
	/* ***** APPEL SERVICES ***** */
	
	public Add execCommand(Add c) {
		return this.ns.add(c);
	}

	public GetNicknames execCommand(GetNicknames c) {
		return this.ns.getNicknames(c);
	}
}

