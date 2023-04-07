package com.java.xml;

import com.java.beans.Adherant;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdherantXml {
    private String filePath;
    private Document doc;
    private Element root;

    public AdherantXml(String filePath) {
        this.filePath = filePath;
        load();
    }

    private void load() {
        try {
            SAXBuilder sax = new SAXBuilder();
            doc = sax.build(new File(filePath));
            root = doc.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(doc, new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Adherant adherant) {
        Element e = new Element("adherant");
        Attribute a = new Attribute("id", "" + adherant.getId());
        e.setAttribute(a);

        Element e1 = new Element("nom");
        e1.addContent(adherant.getNom());
        e.addContent(e1);

        Element e2 = new Element("prenom");
        e2.addContent(adherant.getPrenom());
        e.addContent(e2);

        Element e3 = new Element("adresse");
        e3.addContent(adherant.getAdresse());
        e.addContent(e3);

        Element e4 = new Element("email");
        e4.addContent(adherant.getEmail());
        e.addContent(e4);

        root.addContent(e);
        save();
    }

    public Adherant get(long idAdherant) {
        List<Element> l = root.getChildren();
        if(l.size() > 0) {
            for (Element child : l) {
                long id = Long.parseLong(child.getAttributeValue("id"));
                if (id == idAdherant) {
                    String nom = child.getChildText("nom");
                    String prenom = child.getChildText("prenom");
                    String adresse = child.getChildText("adresse");
                    String email = child.getChildText("email");
                    return new Adherant(id, nom, prenom, adresse, email);
                }
            }
        }
        return null;
    }

    public List<Adherant> getAll() {
		List<Adherant> adherants = new ArrayList<Adherant>();
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				long id = Long.parseLong(child.getAttributeValue("id"));
                String nom = child.getChildText("nom");
                String prenom = child.getChildText("prenom");
                String adresse = child.getChildText("adresse");
                String email = child.getChildText("email");
				adherants.add(new Adherant(id, nom, prenom, adresse, email));
			}
		}
		return adherants;
	}

    public void delete(long idAdherant) {
        List<Element> l = root.getChildren();
        if(l.size() > 0) {
            for (Element child : l) {
                long id = Long.parseLong(child.getAttributeValue("id"));
                if (id == idAdherant) {
                    // delete first in prets so it won't cause problems
                    Mediator m = new Mediator();
                    m.checkPrets(idAdherant, "adherant");
                    child.getParent().removeContent(child);
                    save();
                    return;
                }
            }
        }
    }

    // create the getTableModel function

    public DefaultTableModel getTableModel() {
    try {
        // read the XML file into a Document object using JDOM
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new File(filePath));

        // create a table model with the headers "ID", "Name", "Address", "Phone Number"
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nom", "Prenom", "Adresse", "Email"}, 0);

        // get a list of all the "adherant" elements in the XML document
        List<Element> adherants = document.getRootElement().getChildren("adherant");

        // loop through each adherant element and add its data to the table model
        for (Element adherant : adherants) {
            long id = Long.parseLong(adherant.getAttributeValue("id"));
            String nom = adherant.getChildText("nom");
            String prenom = adherant.getChildText("prenom");
            String adresse = adherant.getChildText("adresse");
            String email = adherant.getChildText("email");
            model.addRow(new Object[]{id, nom, prenom, adresse, email});
        }

        return model;

    } catch (IOException | JDOMException ex) {
        ex.printStackTrace();
        return new DefaultTableModel();
    }
}
}
