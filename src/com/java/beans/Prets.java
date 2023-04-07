package com.java.beans;

	public class Prets {
	private long id;
	private Adherant adherant;
	private Oeuvre oeuvre;
	private String date_empreinte;

	public Prets() {
	}

	public Prets(long id, Adherant a, Oeuvre o, String date_empreinte) {
		this.id = id;
		this.adherant = a;
		this.oeuvre = o;
		this.date_empreinte = date_empreinte;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Adherant getAdherant() {
		return adherant;
	}

	public void setAdherant(Adherant a) {
		this.adherant = a;
	}

	public Oeuvre getOeuvre() {
		return oeuvre;
	}

	public void setOeuvre(Oeuvre o) {
		this.oeuvre = o;
	}

	public String getDate_empreinte() {
		return date_empreinte;
	}

	public void setDate_empreinte(String date_empreinte) {
		this.date_empreinte = date_empreinte;
	}

	
	@Override
	public String toString() {
		return "Prets{" +
				"id=" + id +
				", adherant=" + adherant +
				", oeuvre=" + oeuvre +
				", date_empreinte='" + date_empreinte + '\'' +
				'}';
	}
	}

	


