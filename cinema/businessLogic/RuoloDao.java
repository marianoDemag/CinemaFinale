package com.azienda.cinema.businessLogic;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.cinema.model.Ruolo;
import com.azienda.cinema.utils.Costanti;

public class RuoloDao implements DaoInterface<Ruolo>{
	
	private EntityManager em=null;
	
	public RuoloDao() {
		this(null);
	}
	
	public RuoloDao(EntityManager em) {
		setEm(em);
	}
	

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void create(Ruolo object) {
		em.persist(object);
		
	}

	@Override
	public List<Ruolo> retrieve() {
		return em.createQuery("from Ruolo",Ruolo.class).getResultList();
	}
	
	
	public Ruolo setAdmin() {
		List<Ruolo> admin= em.createQuery("select d from Ruolo d where d.descrizione= :descrizione ",Ruolo.class).setParameter("descrizione",Costanti.ADMIN).getResultList();
		return admin.get(0);
	}
	
	public Ruolo setStaff() {
		List<Ruolo> admin= em.createQuery("select d from Ruolo d where d.descrizione= :descrizione ",Ruolo.class).setParameter("descrizione",Costanti.US).getResultList();
		return admin.get(0);
	}
	
	public Ruolo setFinale() {
		List<Ruolo> admin= em.createQuery("select d from Ruolo d where d.descrizione= :descrizione ",Ruolo.class).setParameter("descrizione",Costanti.UF).getResultList();
		return admin.get(0);
	}
	

	@Override
	public void update(Ruolo object) {
		em.persist(object);
		
	}

	@Override
	public void delete(Ruolo object) {
		em.remove(object);
	}

}
