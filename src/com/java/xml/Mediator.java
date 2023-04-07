package com.java.xml;

import com.java.beans.Oeuvre;
import com.java.beans.Adherant;
import com.java.test.Constants;
import com.java.beans.Prets;

public class Mediator {
	
	public Mediator() {
	}

	public Oeuvre getOeuvre(long id) {
		OeuvreXml xml = new OeuvreXml(Constants.OEUVRES_XML_FILEPATH);
		return xml.get(id);
	}

	public Adherant getAdherant(long id) {
		AdherantXml xml = new AdherantXml(Constants.ADHERANTS_XML_FILEPATH);
		return xml.get(id);
	}

	public void checkPrets(long id, String attribute) {
		PretsXml xml = new PretsXml(Constants.PRETS_XML_FILEPATH);
		System.out.println(getAdherant(id));
		if(attribute == "adherant"){
			if(getAdherant(id) != null){
				xml.delete(id, attribute);
			}
		}
		else if(attribute == "oeuvre"){
			if(getOeuvre(id) != null){
				xml.delete(id, attribute);
			}
		}

	}



}
