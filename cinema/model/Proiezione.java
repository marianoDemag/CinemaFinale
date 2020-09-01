package com.azienda.cinema.model;

import java.time.LocalDateTime;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"data", "filmFk","salaFk"})) 
public class Proiezione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id=null;
	@Column(nullable = false)
	private LocalDateTime data=null;
	@ManyToOne
	@JoinColumn(name = "filmFk")
	private Film film=null;
	@ManyToOne
	@JoinColumn(name = "salaFk")
	private Sala sala=null;
	
	@OneToMany(mappedBy = "proiezione")
	private List<Prenotazione> listaPrenotazioni=null;
	
	private LocalDateTime orarioFineProiezione=null;
	
	
	public Proiezione() {
		this(null);
	}
	
	public Proiezione(LocalDateTime data) {
		listaPrenotazioni= new LinkedList<Prenotazione>();
		setData(data);
	}

	public void assProiezionePrenotazione(Prenotazione prenotazione) {
		this.getListaPrenotazioni().add(prenotazione);
		prenotazione.setProiezione(this);
	}
	
	
	
	public LocalDateTime getOrarioFineProiezione() {
		return orarioFineProiezione;
	}

	public void setOrarioFineProiezione() {
		if (this.data != null && this.film!= null) {
			orarioFineProiezione= data.plusMinutes(film.getDurata()+30);
		}
	}

	public List<Prenotazione> getListaPrenotazioni() {
		return listaPrenotazioni;
	}

	public void setListaPrenotazioni(List<Prenotazione> listaPrenotazioni) {
		this.listaPrenotazioni = listaPrenotazioni;
	}
	
	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}
	
	

}
