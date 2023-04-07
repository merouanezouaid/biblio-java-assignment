package com.java.xml;

import com.java.beans.Adherant;
import com.java.beans.Oeuvre;
import com.java.beans.Prets;
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
import java.util.List;

public class PretsXml {
	private String filePath;
	private Document doc;
	private Element root;
	
	public PretsXml(String filePath) {
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
		
	public int add(Prets l) {
		// use isEmprunter function here
		if(!isEmprunter(l.getAdherant())) {
			return -1;
		}

		Element e = new Element("Pret");
		Attribute a = new Attribute("id", "" + l.getId());
		e.setAttribute(a);
		
		Element e1 = new Element("adherant");
		e1.setAttribute(new Attribute("ad_id", "" + l.getAdherant().getId()));
		e.addContent(e1);

		Element e2 = new Element("oeuvre");
		e2.setAttribute(new Attribute("oe_id", "" + l.getOeuvre().getId()));
		e.addContent(e2);

		Element e3 = new Element("date_empreinte");
		e3.addContent(l.getDate_empreinte());
		e.addContent(e3);

		root.addContent(e);
		save();
		return 0;
	}

	// un adherant peut emprunter au max 3 oeuvres en meme temps

	public boolean isEmprunter(Adherant a) {
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			int count = 0;
			for (Element child : l) {
				long id = Long.parseLong(child.getChild("adherant").getAttributeValue("ad_id"));
				if(id == a.getId()) {
					count++;
				}
			}
			if(count < 3) {
				return true;
			}
		}
		return false;
	}

	public Prets get(long idPrets) {
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				long id = Long.parseLong(child.getAttributeValue("id"));
				if(id == idPrets) {
					String date_empreinte = child.getChildText("date_empreinte");
					long idAdherant = Long.parseLong(child.getChild("adherant").getAttributeValue("ad_id"));
					long idOeuvre = Long.parseLong(child.getChild("oeuvre").getAttributeValue("oe_id"));
					Mediator m = new Mediator();
					Adherant a = m.getAdherant(idAdherant);
					Oeuvre o = m.getOeuvre(idOeuvre);
					return new Prets(id, a, o, date_empreinte);
				}
			}
			
		}
		return null;
	}
	

	public void delete(long idPrets) {
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				long id = Long.parseLong(child.getAttributeValue("id"));
				if(id == idPrets) {
					root.removeContent(child);
					save();
					return;
				}
			}
		}
	}

	public void delete(long idATTR, String attribute) {
		List<Element> l = root.getChildren();
		if(l.size() > 0) {
			for (Element child : l) {
				long id_attr;
				if(attribute == "adherant"){
					id_attr = Long.parseLong(child.getChild("adherant").getAttributeValue("ad_id"));
				}
				else {
					id_attr = Long.parseLong(child.getChild("oeuvre").getAttributeValue("oe_id"));
				}

				if(id_attr == idATTR) {
					root.removeContent(child);
					save();
					return;
				}
			}
		}
	}

	public DefaultTableModel getTableModel() {
    try {
        // read the XML file into a Document object using JDOM
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new File(filePath));

        // create a table model with the headers "ID", "Name", "Address", "Phone Number"
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Adherant", "Oeuvre", "Date d'empreinte"}, 0);

        // get a list of all the "adherant" elements in the XML document
        List<Element> prets = document.getRootElement().getChildren("Pret");

        // loop through each adherant element and add its data to the table model
        for (Element p : prets) {
            long id = Long.parseLong(p.getAttributeValue("id"));

			String date_empreinte = p.getChildText("date_empreinte");
			long idAdherant = Long.parseLong(p.getChild("adherant").getAttributeValue("ad_id"));
			long idOeuvre = Long.parseLong(p.getChild("oeuvre").getAttributeValue("oe_id"));
			Mediator m = new Mediator();
			String a = m.getAdherant(idAdherant).getNom() + " " + m.getAdherant(idAdherant).getPrenom();
			String o = m.getOeuvre(idOeuvre).getTitre();
			model.addRow(new Object[]{id, a, o, date_empreinte});
        }

        return model;

    } catch (IOException | JDOMException ex) {
        ex.printStackTrace();
        return new DefaultTableModel();
    }
}
}
	
	
	
	
	
	
	
	
	
