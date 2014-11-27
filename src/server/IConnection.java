package server;

import java.io.IOException;

public interface IConnection {
	public Object readClientRequest() throws ClassNotFoundException, IOException;
	public void writeResponse(Object c) throws IOException;
	public void exit() throws IOException;
}
