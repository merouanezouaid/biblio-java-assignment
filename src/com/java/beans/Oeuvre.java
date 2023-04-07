package com.java.beans;

public class Oeuvre {
	private long id;
	private String titre;
	private String categorie;
	private String auteur;
	private String editeur;
	private int annee;
	private boolean status;

	public Oeuvre() {
	}

	public Oeuvre(long id, String titre, String categorie, String auteur, String editeur, int annee, boolean status) {
		this.id = id;
		this.titre = titre;
		this.categorie = categorie;
		this.auteur = auteur;
		this.editeur = editeur;
		this.annee = annee;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getEditeur() {
		return editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Oeuvre: " +
				"id=" + id +
				", titre=" + titre +
				", categorie=" + categorie +
				", auteur=" + auteur +
				", editeur=" + editeur +
				", annee=" + annee +
				", status=" + status +
				'}';
	}
}
