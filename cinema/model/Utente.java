package com.azienda.cinema.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id=null;
	@Column(unique=true, nullable = false)
	private String username=null;
	@Column(nullable = false)
	private String password=null;
	@Column(unique=true, nullable = false)
	private String mail=null;
	private String nome= null;
	private String cognome=null;
	
	@OneToMany(mappedBy = "utente")
	private List<Prenotazione> listaPrenotazioni=null;
	
	@ManyToOne
	@JoinColumn(name = "ruoloFk")
	private Ruolo ruolo;
	
	public Utente () {
		this(null,null,null,null,null);
	}
	
	public Utente(String username, String password, String mail) {
		this(null,null,username,password,mail);
	}
	
	public Utente(String nome, String cognome, String username, String password, String mail) {
		listaPrenotazioni= new LinkedList<Prenotazione>();
		setNome(nome);
		setCognome(cognome);
		setUsername(username);
		setPassword(password);
		setMail(mail);
	}
	
	

	public void assUtentePrenotazione (Prenotazione prenotazione) {
		//devo fare il controllo anche qui se ruolo== utente_finale? 
		this.getListaPrenotazioni().add(prenotazione);
		prenotazione.setUtente(this);
	}
	
	public List<Prenotazione> getListaPrenotazioni() {
		return listaPrenotazioni;
	}

	public void setListaPrenotazioni(List<Prenotazione> listaPrenotazioni) {
		this.listaPrenotazioni = listaPrenotazioni;
	}
	
	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getId() {
		return id;
	}
	
	

}
