package com.java.test;

import com.java.xml.AdherantXml;
import com.java.xml.OeuvreXml;
import com.java.xml.PretsXml;
import com.java.beans.Adherant;
import com.java.beans.Oeuvre;
import com.java.beans.Prets;
import com.java.test.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.util.ArrayList;
import java.util.List;


public class Test {

	
	private static void adherantsListe() {
		JFrame jFrame2 = new JFrame("Liste des Adherants");
		jFrame2.setSize(800, 600);
		
		// create a jTable
		JTable jTable = new JTable();
        jTable.setSize(200, 200);
		
		// import an xml file into jTable
		AdherantXml xml = new AdherantXml(Constants.ADHERANTS_XML_FILEPATH);
		DefaultTableModel model = xml.getTableModel();
		jTable.setModel(model);
	    
		
		JPanel panel = new JPanel(new BorderLayout());
		// add the table to the frame			
		panel.add(new JScrollPane(jTable), BorderLayout.CENTER);
		
		JButton addAdherant, searchAdherant, deleteAdherant;
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addAdherant = new JButton("Ajouter un Adherant");
		searchAdherant = new JButton("Rechercher un Adherant");
		deleteAdherant = new JButton("Supprimer un Adherant");

		deleteAdherant.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
            if(jTable.getSelectedRow() != -1) {
				DefaultTableModel m = (DefaultTableModel) jTable.getModel();
				long id = (long) m.getValueAt(jTable.getSelectedRow(), 0);
				xml.delete(id);
				m.removeRow(jTable.getSelectedRow());
				JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
            }
         }
      });

		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel searchLabel = new JLabel("Search: ");
		String[] columns = {"ID", "Nom", "Prenom", "Adresse", "Email"};
		JTextField searchTextField = new JTextField();
		JComboBox<String> columnsList = new JComboBox<>(columns); // create the JComboBox
		searchTextField.setColumns(20);
		searchPanel.add(searchLabel);
		searchPanel.add(columnsList);
		searchPanel.add(searchTextField);

		panel.add(searchPanel, BorderLayout.NORTH);

		buttonPanel.add(addAdherant);
		buttonPanel.add(searchAdherant);
		buttonPanel.add(deleteAdherant);

		// buttons event listeners
		searchAdherant.addActionListener(e -> {
			String search = searchTextField.getText();
			String col = (String) columnsList.getSelectedItem();
			TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable.getModel());
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + search,0, jTable.getColumnModel().getColumnIndex(col)));
			jTable.setRowSorter(rowSorter);
		});
		addAdherant.addActionListener(e -> {
			ajouterAdherant(xml, jTable);
		});
		panel.add(buttonPanel, BorderLayout.SOUTH);
		jFrame2.add(panel);
		jFrame2.pack();
		jFrame2.setVisible(true);

	}

	public static void ajouterAdherant(AdherantXml x, JTable table) {
		JFrame jFrame = new JFrame("Ajouter Adherant");
		jFrame.setSize(400, 400);

		JTextField t1,t2, t3, t4, t5;
		JButton ajouter;
		t1=new JTextField("ID");
		t1.setBounds(50,40, 200,30);
		t2=new JTextField("Nom");
		t2.setBounds(50,90, 200,30);
		t3=new JTextField("Prenom");
		t3.setBounds(50,140, 200,30);
		t4=new JTextField("Adresse");
		t4.setBounds(50,190, 200,30);
		t5=new JTextField("Email");
		t5.setBounds(50,240, 200,30);
		jFrame.add(t1);
		jFrame.add(t2);
		jFrame.add(t3);
		jFrame.add(t4);
		jFrame.add(t5);

		ajouter = new JButton("Ajouter");
		ajouter.setBounds(130,300,100,40);
		jFrame.add(ajouter);

		ajouter.addActionListener(e -> {
			Adherant ad = new Adherant(Long.parseLong(t1.getText()), t2.getText(), t3.getText(), t4.getText(), t5.getText());
			x.add(ad);
			AdherantXml xml = new AdherantXml(Constants.ADHERANTS_XML_FILEPATH);
			DefaultTableModel model = xml.getTableModel();
			table.setModel(model);
			model.fireTableDataChanged();
			JOptionPane.showMessageDialog(null, "Adherant added successfully");
		});

		jFrame.setLayout(null);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);


	}

	public static void ajouterOeuvre(OeuvreXml x, JTable table) {
		JFrame jFrame = new JFrame("Ajouter Oeuvre");
		jFrame.setSize(400, 500);

		JTextField t1,t2, t3, t4, t5, t6;
		JButton ajouter;
		t1=new JTextField("ID");
		t1.setBounds(50,40, 200,30);
		t2=new JTextField("Titre");
		t2.setBounds(50,90, 200,30);
		t3=new JTextField("Categorie");
		t3.setBounds(50,140, 200,30);
		t4=new JTextField("Auteur");
		t4.setBounds(50,190, 200,30);
		t5=new JTextField("Editeur");
		t5.setBounds(50,240, 200,30);
		t6=new JTextField("Annee");
		t6.setBounds(50,290, 200,30);
		String[] status = {"Disponible", "Pas Disponible"};
		JComboBox<String> t7 = new JComboBox<>(status); // create the JComboBox
		t7.setBounds(50, 340, 200, 30);



		jFrame.add(t1);
		jFrame.add(t2);
		jFrame.add(t3);
		jFrame.add(t4);
		jFrame.add(t5);
		jFrame.add(t6);
		jFrame.add(t7);


		ajouter = new JButton("Ajouter");
		ajouter.setBounds(130,390,100,40);
		jFrame.add(ajouter);

		ajouter.addActionListener(e -> {
			Oeuvre oe = new Oeuvre(Long.parseLong(t1.getText()), t2.getText(), t3.getText(), t4.getText(), t5.getText(), Integer.parseInt(t6.getText()), (String) t7.getSelectedItem() == "Disponible");
			x.add(oe);
			OeuvreXml xml = new OeuvreXml(Constants.OEUVRES_XML_FILEPATH);
			DefaultTableModel model = xml.getTableModel();
			table.setModel(model);
			model.fireTableDataChanged();
			JOptionPane.showMessageDialog(null, "Oeuvre added successfully");
		});


		jFrame.setLayout(null);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);

	}


	public static void OeuvresListe(){
		JFrame jFrame3 = new JFrame("Liste des Oeuvres");
		jFrame3.setSize(800, 600);
		
		// create a jTable
		JTable jTable = new JTable();
		jTable.setSize(200, 200);
		
		// import an xml file into jTable
		OeuvreXml xml = new OeuvreXml(Constants.OEUVRES_XML_FILEPATH);
		DefaultTableModel model = xml.getTableModel();
		jTable.setModel(model);
	    
		
		JPanel panel = new JPanel(new BorderLayout());
		// add the table to the frame			
		panel.add(new JScrollPane(jTable), BorderLayout.CENTER);
		
		JButton addOeuvre, searchOeuvre, deleteOeuvre;
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addOeuvre = new JButton("Ajouter une Oeuvre");
		searchOeuvre = new JButton("Rechercher une Oeuvre");
		deleteOeuvre = new JButton("Supprimer une Oeuvre");

		buttonPanel.add(addOeuvre);
		buttonPanel.add(searchOeuvre);
		buttonPanel.add(deleteOeuvre);

		// search
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel searchLabel = new JLabel("Search: ");
		String[] columns = {"ID", "Titre", "Categorie", "Auteur", "Editeur", "Annee", "Status"};
		JTextField searchTextField = new JTextField();
		JComboBox<String> columnsList = new JComboBox<>(columns); // create the JComboBox
		searchTextField.setColumns(20);
		searchPanel.add(searchLabel);
		searchPanel.add(columnsList);
		searchPanel.add(searchTextField);

		panel.add(searchPanel, BorderLayout.NORTH);

		searchOeuvre.addActionListener(e -> {
			String search = searchTextField.getText();
			String col = (String) columnsList.getSelectedItem();
			TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable.getModel());
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + search,0, jTable.getColumnModel().getColumnIndex(col)));
			jTable.setRowSorter(rowSorter);
		});

		addOeuvre.addActionListener(e -> {
			ajouterOeuvre(xml, jTable);
		});

		deleteOeuvre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(jTable.getSelectedRow() != -1) {
					DefaultTableModel m = (DefaultTableModel) jTable.getModel();
					System.out.println(jTable.getSelectedRow());
					long id = (long) m.getValueAt(jTable.getSelectedRow(), 0);
					System.out.println(jTable.getSelectedRow() + " | " + id);
					xml.delete(id);
					m.removeRow(jTable.getSelectedRow());
					JOptionPane.showMessageDialog(null, "Oeuvre deleted successfully");
				}
			}
		});
		panel.add(buttonPanel, BorderLayout.SOUTH);
		jFrame3.add(panel);
		jFrame3.pack();
		jFrame3.setVisible(true);

	}

	public static void PretsListe(){
		JFrame jFrame4 = new JFrame("Liste des Prets");
		jFrame4.setSize(800, 600);
		
		// create a jTable
		JTable jTable = new JTable();
		jTable.setSize(200, 200);
		
		// import an xml file into jTable
		PretsXml xml = new PretsXml(Constants.PRETS_XML_FILEPATH);
		DefaultTableModel model = xml.getTableModel();
		jTable.setModel(model);
		
		JPanel panel = new JPanel(new BorderLayout());
		// add the table to the frame			
		panel.add(new JScrollPane(jTable), BorderLayout.CENTER);
		
		JButton addPret, deletePret;
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addPret = new JButton("Ajouter un Pret");
		deletePret = new JButton("Supprimer un Pret");

		buttonPanel.add(addPret);
		buttonPanel.add(deletePret);

		addPret.addActionListener(e -> {
			ajouterPrets(xml, jTable);
		});

		deletePret.addActionListener(e-> {
				if(jTable.getSelectedRow() != -1) {
					DefaultTableModel m = (DefaultTableModel) jTable.getModel();
					System.out.println(jTable.getSelectedRow());
					long id = (long) m.getValueAt(jTable.getSelectedRow(), 0);
					System.out.println(jTable.getSelectedRow() + " | " + id);
					xml.delete(id);
					m.removeRow(jTable.getSelectedRow());
					JOptionPane.showMessageDialog(null, "Pret deleted successfully");
				}
		});
		panel.add(buttonPanel, BorderLayout.SOUTH);
		jFrame4.add(panel);
		jFrame4.pack();
		jFrame4.setVisible(true);

	}

	public static void ajouterPrets(PretsXml xml, JTable table){
		JFrame jFrame = new JFrame("Ajouter une Empreinte");
		jFrame.setSize(400, 300);
		JButton ajouter;
		JTextField emp, id;


		OeuvreXml oxml = new OeuvreXml(Constants.OEUVRES_XML_FILEPATH);
		AdherantXml axml = new AdherantXml(Constants.ADHERANTS_XML_FILEPATH);
		PretsXml pxml = new PretsXml(Constants.PRETS_XML_FILEPATH);
		List<Adherant> adherantsList = axml.getAll(); // get all Adherants from XML file
		String[] adherants = new String[adherantsList.size()]; // create a String array to hold names
		for (int i = 0; i < adherantsList.size(); i++) {
			adherants[i] = adherantsList.get(i).getNom() + " " + adherantsList.get(i).getPrenom(); // extract the name of each Adherant
		}
		List<Oeuvre> oeuvresList = oxml.getAll();
		String[] oeuvres = new String[oeuvresList.size()];
		for (int i = 0; i < oeuvresList.size(); i++) {
			oeuvres[i] = oeuvresList.get(i).getTitre(); // extract the name of each Adherant
		}

		JLabel idLabel = new JLabel("ID: ");
		idLabel.setBounds(50, 20, 200, 30);
		id=new JTextField();
		id.setBounds(160, 20, 160,30);


		JLabel adherantLabel = new JLabel("Adherant: ");
		adherantLabel.setBounds(50, 60, 200, 30);
		JComboBox<String> adh = new JComboBox<>(adherants); // create the JComboBox
		adh.setBounds(120, 60, 200, 30);

		JLabel oeuvreLabel = new JLabel("Oeuvre: ");
		oeuvreLabel.setBounds(50, 100, 200, 30);
		JComboBox<String> oeu = new JComboBox<>(oeuvres); // create the JComboBox
		oeu.setBounds(120, 100, 200, 30);

		JLabel empLabel = new JLabel("Date d'empreint: ");
		empLabel.setBounds(50, 140, 200, 30);
		emp=new JTextField();
		emp.setBounds(160, 140, 160,30);

		ajouter = new JButton("Ajouter");
		ajouter.setBounds(130,190,100,40);

		jFrame.add(ajouter);

		ajouter.addActionListener(e -> {
			Prets prt = new Prets(Long.parseLong(id.getText()), getAdFromName(axml.getAll(), (String) adh.getSelectedItem()), getOeFromName(oxml.getAll(),(String) oeu.getSelectedItem()), emp.getText());
			pxml.add(prt);
			DefaultTableModel model = xml.getTableModel();
			table.setModel(model);
			model.fireTableDataChanged();
			JOptionPane.showMessageDialog(null, "Oeuvre empreinted successfully");

		});

		jFrame.add(idLabel);
		jFrame.add(id);
		jFrame.add(adherantLabel);
		jFrame.add(adh);
		jFrame.add(oeuvreLabel);
		jFrame.add(oeu);
		jFrame.add(empLabel);
		jFrame.add(emp);


		jFrame.setLayout(null);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);

	}

	public static Adherant getAdFromName(List<Adherant> list, String requiredName) {
		for (Adherant obj : list) {
			String fullName = obj.getNom() + " " + obj.getPrenom();
			System.out.println(fullName + " VS " + requiredName);
			if (fullName.equals(requiredName)) {
				return obj;
			}
		}
		return null;
	}

	public static Oeuvre getOeFromName(List<Oeuvre> list, String requiredName) {
		for (Oeuvre obj : list) {
			String fullName = obj.getTitre();
			if (fullName.equals(requiredName)) {
				return obj;
			}
		}
		return null;
	}

    private static void createAndShowGUI() {
        JFrame jFrame = new JFrame("Gestion de Mediatheque");
        jFrame.setSize(800, 600);

        JTextField t1,t2, t3;  
        JButton adh, oeu, emp;
        
        adh = new JButton("Liste des Adherants");
        adh.setBounds(40,80,300,200);
		// open another jframe from clicking on adh button
		adh.addActionListener(e -> {
			adherantsListe();
		});

        oeu = new JButton("Liste des Oeuvres");
        oeu.setBounds(440,80,300,200);

		oeu.addActionListener(e -> {
			OeuvresListe();
		});

        emp = new JButton("Gérer les Empreintes");
        emp.setBounds(240,300,300,200);

		emp.addActionListener(e -> {
			PretsListe();
		});
        jFrame.add(adh);
        jFrame.add(oeu);
        jFrame.add(emp);

        jFrame.setLayout(null);
        jFrame.setVisible(true);
    }

	
	public static void main(String[] args) {
		createAndShowGUI();
	}

}
