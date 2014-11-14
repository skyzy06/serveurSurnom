import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import protocole.Add;
import protocole.GetNicknames;


public class ServerTest {

	@Ignore
	@Test
	public void execAddTest() throws IOException {
		List<String> l = new LinkedList<String>();
		l.add("Ana");
		l.add("Nana");
		l.add("Ananas");
		Add a = new Add("Anaïs", l);

		ClientCommandsListener s = new ClientCommandsListener(null);
		assertEquals(Server.data.toString(), "{}");
		
		s.execCommand(a);
		assertEquals(Server.data.toString(), "{Anaïs=[Ana, Nana, Ananas]}");
		assertEquals(a.getErrors().toString(), "[]");
		assertEquals(a.isSucceed(), true);
		
		s.execCommand(a);
		assertEquals(Server.data.toString(), "{Anaïs=[Ana, Nana, Ananas]}");
		assertEquals(a.getErrors().toString(), "[Anaïs name already exists., Ana nickname already exists., Nana nickname already exists., Ananas nickname already exists.]");
		assertEquals(a.isSucceed(), false);
	}
	
	@Ignore
	@Test
	public void execGetNicknamesTest() throws IOException {
		GetNicknames g = new GetNicknames("Anaïs");
		GetNicknames g2 = new GetNicknames("Alice");
		
		List<String> l = new LinkedList<String>();
		l.add("Ana");
		l.add("Nana");
		l.add("Ananas");
		Add a = new Add("Anaïs", l);
		
		ClientCommandsListener s = new ClientCommandsListener(null);
		s.execCommand(a);
		
		s.execCommand(g);
		assertEquals(Server.data.toString(), "{Anaïs=[Ana, Nana, Ananas]}");
		assertEquals(g.getErrorMsg(), "");
		assertEquals(g.isSucceed(), true);
		assertEquals(g.getNicknames().toString(), "[Ana, Nana, Ananas]");
		
		s.execCommand(g2);
		assertEquals(Server.data.toString(), "{Anaïs=[Ana, Nana, Ananas]}");
		assertEquals(g2.getErrorMsg(), "Alice name doesn't exist.");
		assertEquals(g2.isSucceed(), false);
		assertEquals(g2.getNicknames().toString(), "[]");
	}
}


