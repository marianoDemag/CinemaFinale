package com.azienda.cinema.businessLogic;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

public class UtenteDao implements DaoInterface<Utente>{

	private EntityManager em=null;
	
	public UtenteDao() {
		this(null);
	}
	
	public UtenteDao(EntityManager em) {
		setEm(em);
	}
	
	public List<Utente> findByUserPassRuolo (String user, String password) {
		return em.createQuery("select x from Utente x where x.username= :username and x.password= :password and x.ruolo IS NOT NULL", Utente.class).setParameter("username", user).setParameter("password", password).getResultList();
	}
	
	public List<Utente> findRuoloIsNull () {
		return em.createQuery("select x from Utente x where x.ruolo IS NULL", Utente.class).getResultList();
	}
	
	public List<Utente> findByRuoloNotNull () {
		return em.createQuery("select x from Utente x where x.ruolo IS NOT NULL", Utente.class).getResultList();
	}
	
	public List<Utente> findByRuoloFinale () {
		return em.createQuery("SELECT DISTINCT e FROM Utente e INNER JOIN e.ruolo t where t.descrizione= :descrizione", Utente.class).setParameter("descrizione", Costanti.UF).getResultList();
	}
	
	public List<Utente> findByUsername(String user) {
		return em.createQuery("select x from Utente x where x.username= :username", Utente.class).setParameter("username", user).getResultList();
	}
	
	public List<Utente> findByMail(String mail) {
		return em.createQuery("select x from Utente x where x.mail= :mail", Utente.class).setParameter("mail", mail).getResultList();
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void create(Utente object) {
		em.persist(object);
	}
	
	
	
	
	@Override
	public List<Utente> retrieve() {
		return em.createQuery("from Utente",Utente.class).getResultList();
	}

	@Override
	public void update(Utente object) {
		em.merge(object);
	}

	@Override
	public void delete(Utente object) {
		em.remove(object);
	}

}
