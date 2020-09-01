package com.azienda.cinema.businessLogic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.cinema.model.Prenotazione;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.model.Utente;

public class PrenotazioneDao implements DaoInterface<Prenotazione>{

	EntityManager em=null;

	public PrenotazioneDao() {
		this(null);
	}

	public PrenotazioneDao(EntityManager em) {
		setEm(em);
	}

	public List<Prenotazione> retrieveByProiezione(Integer id){
		return em.createQuery("SELECT p from Prenotazione p INNER JOIN p.proiezione pr where pr.id= :id",Prenotazione.class).setParameter("id", id).getResultList();
	}

	public List<Prenotazione> findByUtente (Integer id){
		return em.createQuery("SELECT p FROM Prenotazione p INNER JOIN p.utente u where u.id = :id ",Prenotazione.class).setParameter("id", id).getResultList();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void create(Prenotazione object) {
		em.persist(object);

	}

	public List<Prenotazione> findByData(LocalDateTime data){
		List<Prenotazione> listaPr= this.retrieve();
		LocalDate data1= data.toLocalDate();
		List<Prenotazione> nuovaLista=new ArrayList<Prenotazione>();
		for (Prenotazione l: listaPr) {
			LocalDate data2= l.getProiezione().getData().toLocalDate();
			if(data1.isEqual(data2)) {
				nuovaLista.add(l);
			}
		}
		if(nuovaLista.size()>0) {
			return nuovaLista;
		}else {
			return null;
		}
	}


	public List<LocalDateTime> retrieveByDat(){
		return em.createQuery("SELECT DISTINCT pr.data FROM Prenotazione p INNER JOIN p.proiezione pr GROUP BY pr.data ORDER BY pr.data",LocalDateTime.class).getResultList();
	}



	public List<LocalDateTime> retrieveByData(){
		List<LocalDateTime> listaPr= this.retrieveByDat();
		List<LocalDateTime> listaPrenotazioni= new ArrayList<LocalDateTime>();
		Integer i=0;
		for(LocalDateTime d : listaPr) {
			if(listaPrenotazioni.size()>0) {
				LocalDate data1=listaPrenotazioni.get(i).toLocalDate();
				LocalDate data=d.toLocalDate();
				if (!data.isEqual(data1)) {
					listaPrenotazioni.add(d);
					i++;
				}

			}else {
				listaPrenotazioni.add(d);
			}


		}
		return listaPrenotazioni;
	}

	public Prenotazione findById (Integer id) {
		List<Prenotazione> listaPrenotazioni = em.createQuery("SELECT p FROM Prenotazione p WHERE p.id = :id",Prenotazione.class).setParameter("id", id).getResultList();
		if(listaPrenotazioni.size()==1) {
			return listaPrenotazioni.get(0);
		} else {
			return null;
		}
	}

	public List<String> retrieveByUtente(){
		return em.createQuery("SELECT u.username FROM Prenotazione p INNER JOIN p.utente u GROUP BY u.username ",String.class).getResultList();
	}

	@Override
	public List<Prenotazione> retrieve() {
		return em.createQuery("from Prenotazione", Prenotazione.class).getResultList();
	}

	@Override
	public void update(Prenotazione object) {
		em.persist(object);

	}

	@Override
	public void delete(Prenotazione object) {
		em.remove(object);
	}



}
