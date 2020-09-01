package com.azienda.cinema.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Ruolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id=null;
	@Column(unique=true, nullable = false)
	private String descrizione=null;
	@OneToMany(mappedBy = "ruolo")
	List<Utente> listaUtenti= null;
	
	public Ruolo () {
		this(null);
	}
	
	public Ruolo(String descrizione) {
		listaUtenti=new LinkedList<Utente>();
		setDescrizione(descrizione);
	}
	
	public void assRuoloUtente(Utente utente) {
		this.getListaUtenti().add(utente);
		utente.setRuolo(this);
	}
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Utente> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<Utente> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

	public Integer getId() {
		return id;
	}
	
	
	
}
