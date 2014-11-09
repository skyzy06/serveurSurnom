/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Administrateur
 */
public class BDGestion {

    private static HashMap<String, String> surnomMap;
    private static final String filename = "src/surnoms.xml";
    private static Element racine = new Element("surnoms");
    private static Document document = new Document(racine);
    private static final String SURNOM = "surnom";
    private static final String NOM = "nom";
    private static final String VALUE = "value";

    /**
     * Juste pour les tests
     * @param args 
     */
    public static void main(String[] args) {
        BDGestion bdsurnom = new BDGestion();
        bdsurnom.initSurnomMap();
        System.out.println(surnomMap);
        surnomMap.put("sopalin", "Chabert");
        System.out.println(surnomMap);
        bdsurnom.saveSurnomMap();
    }

    public BDGestion() {
        surnomMap = new HashMap<String, String>();
    }

    /**
     * Méthode permettant initialiser la HashMap au lancement du serveur
     */
    public void initSurnomMap() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(filename));
            racine = document.getRootElement();
        } catch (JDOMException | IOException ex) {
        }
        List<Element> surnoms = racine.getChildren(SURNOM);
        Iterator<Element> it = surnoms.iterator();

        while (it.hasNext()) {
            Element courant = it.next();
            surnomMap.put(courant.getAttributeValue(VALUE), courant.getAttribute(NOM).getValue());
        }
    }

    /**
     * Méthode permettant de sauvegarder la HashMap à l'arrêt du serveur
     */
    public void saveSurnomMap() {
        Set key = surnomMap.keySet();
        Iterator it = key.iterator();
        racine.removeContent();

        while (it.hasNext()) {
            Object surnom = it.next();
            Element newSurnom = new Element(SURNOM);
            newSurnom.setAttribute(VALUE, surnom.toString());
            newSurnom.setAttribute(NOM, surnomMap.get(surnom));
            racine.addContent(newSurnom);
        }
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(filename));
        } catch (Exception ex) {
        }
    }
}
