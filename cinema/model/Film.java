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
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id=null;
	@Column(unique=true, nullable = false)
	private String titolo=null;
	private String genere=null;
	private String trama=null;
	@Column(nullable = false)
	private Integer durata=null;
	@OneToMany(mappedBy = "film")
	private List<Proiezione> listaProiezioni=null;
	
	
	public Film() {
		this(null,null,null,null);
	}
	
	public Film(String titolo, Integer durata) {
		this(titolo,null,null,durata);
	}
	
	
	
	public Film (String titolo, String genere, String trama, Integer durata) {
		listaProiezioni= new LinkedList<Proiezione>();
		setTitolo(titolo);
		setGenere(genere);
		setTrama(trama);
		setDurata(durata);
	}
	
	public void assFilmProiezione(Proiezione proiezione) {
		this.getListaProiezioni().add(proiezione);
		proiezione.setFilm(this);
	}
	
	public List<Proiezione> getListaProiezioni() {
		return listaProiezioni;
	}

	public void setListaProiezioni(List<Proiezione> listaProiezioni) {
		this.listaProiezioni = listaProiezioni;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getTrama() {
		return trama;
	}

	public void setTrama(String trama) {
		this.trama = trama;
	}

	public Integer getDurata() {
		return durata;
	}

	public void setDurata(Integer durata) {
		this.durata = durata;
	}

	public Integer getId() {
		return id;
	}
	
}
