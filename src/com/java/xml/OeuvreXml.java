package com.java.xml;

import com.java.beans.Oeuvre;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OeuvreXml {
	private String filePath;
	private Document doc;
	private Element root;
	
	public OeuvreXml(String filePath) {
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
		
	public void add(Oeuvre oeuvre) {
		Element e = new Element("oeuvre");
		Attribute a = new Attribute("id", "" + oeuvre.getId());
		e.setAttribute(a);
		
		Element e1 = new Element("titre");
		e1.addContent(oeuvre.getTitre());
		e.addContent(e1);
		
		Element e2 = new Element("categorie");
		e2.addContent(oeuvre.getCategorie());
		e.addContent(e2);

		Element e3 = new Element("auteur");
		e3.addContent(oeuvre.getAuteur());
		e.addContent(e3);

		Element e4 = new Element("editeur");
		e4.addContent(oeuvre.getEditeur());
		e.addContent(e4);

		Element e5 = new Element("annee");
		e5.addContent(Integer.toString(oeuvre.getAnnee()));
		e.addContent(e5);

		Element e6 = new Element("status");
		e6.addContent(Boolean.toString(oeuvre.isStatus()));
		e.addContent(e6);
		
		root.addContent(e);
		save();
	}
	
	public Oeuvre get(long idOeuvre) {
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				long id = Long.parseLong(child.getAttributeValue("id"));
				if (id == idOeuvre) {
					String titre = child.getChildText("titre");
					String categorie = child.getChildText("categorie");
					String auteur = child.getChildText("auteur");
					String editeur = child.getChildText("editeur");
					int annee = Integer.parseInt(child.getChildText("annee"));
					boolean status = Boolean.parseBoolean(child.getChildText("status"));
					return new Oeuvre(id, titre, categorie, auteur, editeur, annee, status);
				}
			}
		}
		return null;
	}

	public void delete(long idOeuvre) {
        List<Element> l = root.getChildren();
        if(l.size() > 0) {
            for (Element child : l) {
                long id = Long.parseLong(child.getAttributeValue("id"));
                if (id == idOeuvre) {
					Mediator m = new Mediator();
					m.checkPrets(idOeuvre, "oeuvre");
                    child.getParent().removeContent(child);
                    save();
                    return;
                }
            }
        }
    }   

	// give me a search method for oeuvre

	public List<Oeuvre> search(String titre) {
		List<Oeuvre> oeuvres = new ArrayList<Oeuvre>();
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				String titreOeuvre = child.getChildText("titre");
				if (titreOeuvre.contains(titre)) {
					long id = Long.parseLong(child.getAttributeValue("id"));
					String categorie = child.getChildText("categorie");
					String auteur = child.getChildText("auteur");
					String editeur = child.getChildText("editeur");
					int annee = Integer.parseInt(child.getChildText("annee"));
					boolean status = Boolean.parseBoolean(child.getChildText("status"));
					oeuvres.add(new Oeuvre(id, titreOeuvre, categorie, auteur, editeur, annee, status));
				}
			}
		}
		return oeuvres;
	}

	// afficher tous les objets de la collection de la médiatheque

	public List<Oeuvre> getAll() {
		List<Oeuvre> oeuvres = new ArrayList<Oeuvre>();
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				long id = Long.parseLong(child.getAttributeValue("id"));
				String titre = child.getChildText("titre");
				String categorie = child.getChildText("categorie");
				String auteur = child.getChildText("auteur");
				String editeur = child.getChildText("editeur");
				int annee = Integer.parseInt(child.getChildText("annee"));
				boolean status = Boolean.parseBoolean(child.getChildText("status"));
				oeuvres.add(new Oeuvre(id, titre, categorie, auteur, editeur, annee, status));
			}
		}
		return oeuvres;
	}

	//afficher tous les objets d'une méme catégorie

	public List<Oeuvre> getByCategorie(String cat) {
		List<Oeuvre> oeuvres = new ArrayList<Oeuvre>();
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				String categorie = child.getChildText("categorie");
				if (categorie == cat) {
					long id = Long.parseLong(child.getAttributeValue("id"));
					String titre = child.getChildText("titre");
					String auteur = child.getChildText("auteur");
					String editeur = child.getChildText("editeur");
					int annee = Integer.parseInt(child.getChildText("annee"));
					boolean status = Boolean.parseBoolean(child.getChildText("status"));
					oeuvres.add(new Oeuvre(id, titre, categorie, auteur, editeur, annee, status));
				}
			}
		}
		return oeuvres;
	}

	
    public DefaultTableModel getTableModel() {
    try {
        // read the XML file into a Document object using JDOM
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new File(filePath));

        // create a table model with the headers "ID", "Name", "Address", "Phone Number"
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Titre", "Categorie", "Auteur", "Editeur", "Annee", "Status"}, 0);

        // get a list of all the "adherant" elements in the XML document
        List<Element> oeuvres = document.getRootElement().getChildren("oeuvre");

        // loop through each adherant element and add its data to the table model
        for (Element oeuvre : oeuvres) {
            long id = Long.parseLong(oeuvre.getAttributeValue("id"));
            // String nom = oeuvre.getChildText("nom");
            // String prenom = oeuvre.getChildText("prenom");
            // String adresse = oeuvre.getChildText("adresse");
            // String email = oeuvre.getChildText("email");
            // model.addRow(new Object[]{id, nom, prenom, adresse, email});

			String titre = oeuvre.getChildText("titre");
			String categorie = oeuvre.getChildText("categorie");
			String auteur = oeuvre.getChildText("auteur");
			String editeur = oeuvre.getChildText("editeur");
			int annee = Integer.parseInt(oeuvre.getChildText("annee"));
			String status = Boolean.parseBoolean(oeuvre.getChildText("status")) ? "Disponible" : "Pas Disponible";
			model.addRow(new Object[]{id, titre, categorie, auteur, editeur, annee, status});
        }

        return model;

    } catch (IOException | JDOMException ex) {
        ex.printStackTrace();
        return new DefaultTableModel();
    }
}

}
