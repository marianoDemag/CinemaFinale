package com.azienda.cinema.businessLogic;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.cinema.model.Film;
import com.azienda.cinema.model.Sala;

public class SalaDao implements DaoInterface<Sala>{
	
	EntityManager em=null;
	
	
	public SalaDao() {
		this(null);
	}
	
	public SalaDao(EntityManager em) {
		setEm(em);
	}
	
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public List<Sala> findByNome(String nome){
		return em.createQuery("select x from Sala x where x.nome= :nome",Sala.class).setParameter("nome",nome).getResultList();
	}

	@Override
	public void create(Sala object) {
		em.persist(object);
		
	}

	@Override
	public List<Sala> retrieve() {
		return em.createQuery("from Sala", Sala.class).getResultList();
	}

	@Override
	public void update(Sala object) {
		em.persist(object);
		
	}

	@Override
	public void delete(Sala object) {
		em.remove(object);
	}

		
}
