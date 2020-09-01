package com.azienda.cinema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Prenotazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id=null;
	@Column(nullable = false)
	private Integer posti=null;
	@ManyToOne
	@JoinColumn(name = "utenteFk", nullable = false)
	private Utente utente=null;
	@ManyToOne
	@JoinColumn(name = "proiezioneFk",nullable = false)
	private Proiezione proiezione=null;
	
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Proiezione getProiezione() {
		return proiezione;
	}

	public void setProiezione(Proiezione proiezione) {
		this.proiezione = proiezione;
	}

	public Prenotazione(){
		this(null);
	}
	
	public Prenotazione(Integer posti) {
		setPosti(posti);
	}
	
	public Integer getPosti() {
		return posti;
	}
	public void setPosti(Integer posti) {
		this.posti = posti;
	}
	public Integer getId() {
		return id;
	}
	
	
	
}
