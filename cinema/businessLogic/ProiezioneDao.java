package com.azienda.cinema.businessLogic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.cinema.model.Proiezione;

public class ProiezioneDao implements DaoInterface<Proiezione>{
	
	EntityManager em=null;

	public ProiezioneDao() {
		this(null);
	}
	
	public ProiezioneDao(EntityManager em) {
		setEm(em);
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void create(Proiezione object) {
		em.persist(object);
	}

	@Override
	public List<Proiezione> retrieve() {
		return em.createQuery("SELECT a FROM Proiezione a ORDER BY a.data ASC ",Proiezione.class).getResultList();
	}
	
	public List<Proiezione> findBySala(String nomeSala) {
		return em.createQuery("SELECT a FROM Proiezione a INNER JOIN a.sala s where s.nome= :nome ORDER BY a.data ASC ",Proiezione.class).setParameter("nome", nomeSala).getResultList();
	}
	
	public List<Proiezione> findByTitoloSalaData(String titolo, String nomeSala, LocalDateTime data){
		return em.createQuery("SELECT a FROM Proiezione a INNER JOIN a.sala s INNER JOIN a.film f where s.nome=: nome AND f.titolo= :titolo AND a.data= :data",Proiezione.class).setParameter("nome", nomeSala).setParameter("titolo", titolo).setParameter("data", data).getResultList();
	}
	
	public List<Proiezione> findById(Integer id){
		return em.createQuery("SELECT a FROM Proiezione a where a.id= :id",Proiezione.class).setParameter("id", id).getResultList();
	}
	
	
	
	
	public List<Proiezione> findByGiornoSala(String nomeSala, LocalDateTime dataProiezione) {
		//da scrivere
		//return em.createQuery("SELECT p FROM Proiezione p INNER JOIN p.sala s where s.nome= :nome AND p.data = :data ORDER BY p.data",Proiezione.class).setParameter("nome", nomeSala).getResultList();
		List<Proiezione> pr=this.findBySala(nomeSala);
		List<Proiezione>proiezioni=new ArrayList<Proiezione>();
		LocalDate data2= dataProiezione.toLocalDate();
		for (Proiezione p: pr) {
			LocalDate data1=p.getData().toLocalDate();
			if (data1.equals(data2)){
				proiezioni.add(p);
			}
		}
		if(proiezioni.size()>0) {
		return proiezioni;
		} else {
			return null;
		}
			
	}
	
	
	
	public List<Proiezione> findByGiorno(LocalDateTime dataProiezione){
		List<Proiezione> pr=this.retrieve();
		List<Proiezione>proiezioni=new ArrayList<Proiezione>();
		LocalDate data2= dataProiezione.toLocalDate();
		for (Proiezione p: pr) {
			LocalDate data1=p.getData().toLocalDate();
			if (data1.equals(data2)){
				proiezioni.add(p);
			}
		}
		if(proiezioni.size()>0) {
		return proiezioni;
		} else {
			return null;
		}
	}

	@Override
	public void update(Proiezione object) {
		em.persist(object);
		
	}

	@Override
	public void delete(Proiezione object) {
		em.remove(object);
	}
	
	
}
