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
public class Sala {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id=null;
	@Column(nullable = false)
	private Integer postiTot=null;
	@Column(unique=true, nullable = false)
	private String nome;
	@OneToMany(mappedBy = "sala")
	private List<Proiezione> listaProiezioni=null;
	
	public Sala() {
		this(null,null);
	}
	
	public Sala(Integer postiTot, String nome) {
		listaProiezioni=new LinkedList<Proiezione>();
		setPostiTot(postiTot);
		setNome(nome);
	}
	
	public void assSalaProiezione(Proiezione proiezione) {
		this.getListaProiezioni().add(proiezione);
		proiezione.setSala(this);
	}

	public List<Proiezione> getListaProiezioni() {
		return listaProiezioni;
	}

	public void setListaProiezioni(List<Proiezione> listaProiezioni) {
		this.listaProiezioni = listaProiezioni;
	}

	public Integer getPostiTot() {
		return postiTot;
	}

	public void setPostiTot(Integer postiTot) {
		this.postiTot = postiTot;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}
	
}
