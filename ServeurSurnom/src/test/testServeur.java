package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import serveur.Serveur;

public class testServeur {

	@Test
	public void testInit() {
		Serveur serveur = new Serveur(1234);
		try{
			serveur.connect();
		}catch(IOException ioe){
			fail();
		}
	}

}
