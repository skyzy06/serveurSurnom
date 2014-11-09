/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import com.sun.xml.internal.ws.api.pipe.Fiber;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Administrateur
 */
public class BDGestion {

    private HashMap<String, String> surnomMap;
    private final String filename = "BD/surnoms.xml";
    private static Document document;

    public BDGestion() {
        surnomMap = new HashMap<String, String>();
    }

    public void initSurnomMap() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(filename));
        } catch (JDOMException | IOException e) {
        }
        
        Element root = document.getRootElement();
        List<Element> surnoms = root.getChildren("surnom");
        Iterator<Element> it = surnoms.iterator();
        
        while(it.hasNext()){
            Element courant = it.next();
            Element nom = courant.getChild("nom");
            surnomMap.put(courant.getText(), nom.getText());
        }
    }
    
    public void saveSurnomMap(){
        
    }
}
