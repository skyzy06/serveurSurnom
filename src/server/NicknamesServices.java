package server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import protocole.Add;
import protocole.GetNicknames;
import protocole.NameAlreadyExistsException;
import protocole.NameDoesntExistException;

public class NicknamesServices {
	/**
	 * Map<Name, Nickname List> ConcurrentHashMap used cause of thread context
	 */
	private static Map<String, List<String>> data = new ConcurrentHashMap<String, List<String>>();
	
	
	public Add add(Add c) {
		System.out.println("Server : ADD");
		c.setSucceed(true);

		for(String n : NicknamesServices.data.keySet()) {
			if(c.getName().equals(n)) {
				c.setSucceed(false);
				c.addError(new NameAlreadyExistsException(c.getName(), "Name already exists."));
			}

			for(String nk : NicknamesServices.data.get(n)) {
				for(String nkCmd : c.getNicknames()) {
					if(nk.equals(nkCmd)) {
						c.setSucceed(false);
						c.addError(new NameAlreadyExistsException(nkCmd, "Nickname already exists."));
					}
				}
			}
		}

		if(c.isSucceed()) {
			NicknamesServices.data.put(c.getName(), c.getNicknames());
		}

		return c;
	}

	public GetNicknames getNicknames(GetNicknames c) {
		System.out.println("Server : GET");
		c.setSucceed(true);

		for(String n : NicknamesServices.data.keySet()) {
			if(c.getName().equals(n)) {
				c.setNicknames(NicknamesServices.data.get(n));
				
				System.out.println("2" + NicknamesServices.data);
				return c;
			}
		}
		c.setSucceed(false);
		
		c.setError(new NameDoesntExistException(c.getName(), "Name doesn't exist."));
		
		return c;
	}

	@Override
    public String toString() {
	    return "NicknamesServices [data=" + data + "]";
    }
}
