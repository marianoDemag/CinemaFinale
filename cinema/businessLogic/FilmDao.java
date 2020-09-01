package com.azienda.cinema.businessLogic;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.cinema.model.Film;

public class FilmDao  implements DaoInterface<Film>{

	EntityManager em=null;
	
	public FilmDao() {
		this(null);
	}
	
	public FilmDao(EntityManager em) {
		setEm(em);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	

	@Override
	public void create(Film object) {
		em.persist(object);
		
	}

	@Override
	public List<Film> retrieve() {
		return em.createQuery("from Film", Film.class).getResultList();
	}

	@Override
	public void update(Film object) {
		em.persist(object);
	}

	@Override
	public void delete(Film object) {
		em.remove(object);
	}
	
	public List<Film> findByTitolo(String titolo){
		return em.createQuery("select x from Film x where x.titolo= :titolo",Film.class).setParameter("titolo",titolo).getResultList();
	}
	
	
}
