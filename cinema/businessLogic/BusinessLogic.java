package com.azienda.cinema.businessLogic;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.swing.text.DateFormatter;

import org.hibernate.exception.ConstraintViolationException;

import com.azienda.cinema.model.Film;
import com.azienda.cinema.model.Prenotazione;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.model.Ruolo;
import com.azienda.cinema.model.Sala;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;



public class BusinessLogic {


	private EntityManager em=null;
	private FilmDao filmDao=null;
	private PrenotazioneDao prenotazioneDao=null;
	private ProiezioneDao proiezioneDao=null;
	private RuoloDao ruoloDao=null;
	private SalaDao salaDao=null;
	private UtenteDao utenteDao=null;


	public BusinessLogic(EntityManager em, FilmDao filmDao, PrenotazioneDao prenotazioneDao, ProiezioneDao proiezioneDao, RuoloDao ruoloDao, SalaDao salaDao, UtenteDao utenteDao) {
		setEm(em);
		setFilmDao(filmDao);
		setProiezioneDao(proiezioneDao);
		setPrenotazioneDao(prenotazioneDao);
		setRuoloDao(ruoloDao);
		setSalaDao(salaDao);
		setUtenteDao(utenteDao);

	}

	public boolean createProiezione(String titoloFilm, String nomeSala, LocalDateTime dataProiezione) {
		em.getTransaction().begin();
		try {

			List<Proiezione> listaProiezioni=proiezioneDao.findByGiornoSala(nomeSala, dataProiezione);
			Film film= filmDao.findByTitolo(titoloFilm).get(0);
			Sala sala= salaDao.findByNome(nomeSala).get(0);
			Proiezione proiezione= new Proiezione(dataProiezione);
			proiezione.setFilm(film);
			proiezione.setSala(sala);
			proiezione.setOrarioFineProiezione();
			boolean daMettere=true;
			if(listaProiezioni==null) {		
				proiezioneDao.create(proiezione);
				em.getTransaction().commit();
			} else {
				for(Proiezione p: listaProiezioni) {
					LocalTime timeEsistente= p.getData().toLocalTime();
					LocalTime timeDaMettere= dataProiezione.toLocalTime();
					Integer compare= timeEsistente.compareTo(timeDaMettere);
					if (compare <= 0) {
						//orario inizio film da inserire > orario fine film esistente

						if (timeDaMettere.compareTo(p.getOrarioFineProiezione().toLocalTime()) < 0) {
							daMettere=false;
							break;
						}
					} else {
						//orario inizio film esistente > orario fine film da mettere
						if(timeEsistente.compareTo(proiezione.getOrarioFineProiezione().toLocalTime()) <0){
							daMettere=false;
							break;
						}
					}


				}

				if(daMettere) {
					proiezioneDao.create(proiezione);
					em.getTransaction().commit();
				}else {
					em.getTransaction().rollback();	
				}
			}
			return daMettere;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public Proiezione getProiezione (String titolo, String nomeSala, LocalDateTime data) throws Exception{
		List<Proiezione> listaProiezioni= proiezioneDao.findByTitoloSalaData(titolo, nomeSala, data);
		if(listaProiezioni.size()>1) {
			throw new Exception("non è possibile che ci siano piu proiezioni per lo stesso giorno sala e ora");
		} else if (listaProiezioni.size()==0) {
			return null;
		} else {
			return listaProiezioni.get(0);
		}
	}
	
	public Proiezione getProiezioneId(Integer id) {
		List<Proiezione> listaProiezioni=proiezioneDao.findById(id);
		if(listaProiezioni.size()==1) {
			return listaProiezioni.get(0);
		} else {
			return null;
		}
	}

	public List<Proiezione> getListaProiezioni (){
		return proiezioneDao.retrieve();
	}

	public List<Proiezione> getListaProiezioniGiornaliere(LocalDateTime dataProiezione){
		return proiezioneDao.findByGiorno(dataProiezione);
	}

	public void deleteProiezione (Integer id)  throws SQLIntegrityConstraintViolationException {
		em.getTransaction().begin();
		try {
			List <Proiezione> listaPr=proiezioneDao.findById(id);
			if(listaPr.size()==1) {
				Proiezione proiezione= listaPr.get(0);
				proiezioneDao.delete(proiezione);
			}else {
				throw new Exception("Non è possibile che abbia trovato due proiezioni con lo stesso id");
			}
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public void deleteUtente (Utente utente)   {
		em.getTransaction().begin();
		try {
			utenteDao.delete(utente);
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}


	public void createUtente (Utente utente)   {
		em.getTransaction().begin();
		try {
			utenteDao.create(utente);
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public void createFilm (Film film)   {
		em.getTransaction().begin();
		try {
			filmDao.create(film);
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new PersistenceException();
		}
	}
	
	public void createSala (Sala sala)   {
		em.getTransaction().begin();
		try {
			salaDao.create(sala);
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new PersistenceException();
		}
	}

	public Utente login(String user, String password) throws Exception {
		List<Utente> utente1=new ArrayList<Utente>();		
		utente1= utenteDao.findByUserPassRuolo(user, password);
		if(utente1.size()>1) throw new Exception("Non ci possono essere due utenti Con lo stesso user e password");
		else if(utente1.size()==0) {
			return null;
		} else {
			return utente1.get(0);		
		}		
	}

	public List<Utente> getRichieste (){
		return utenteDao.findRuoloIsNull();
	}

	public Utente getMail(String mail) throws Exception {
		List<Utente> utente1=new ArrayList<Utente>();		
		utente1= utenteDao.findByMail(mail);
		if(utente1.size()>1) throw new Exception("Non ci possono essere due utenti Con lo stesso user e password");
		else if(utente1.size()==0) {
			return null;
		} else {
			return utente1.get(0);		
		}		
	}

	public List<Utente> getListaUtentiAdmin(){
		List<Utente> utente1=new ArrayList<Utente>();
		utente1= utenteDao.findByRuoloNotNull();
		if(utente1.size()==0) {
			return null;
		} else {
			return utente1;
		}
	}

	public List<Utente> getListaUtentiFinale(){
		List<Utente> utente1=new ArrayList<Utente>();
		utente1= utenteDao.findByRuoloFinale();
		if(utente1.size()==0) {
			return null;
		} else {
			return utente1;
		}
	}

	public Utente getUser(String user) throws Exception {
		List<Utente> utente1=new ArrayList<Utente>();		
		utente1= utenteDao.findByUsername(user);
		if(utente1.size()>1) throw new Exception("Non ci possono essere due utenti Con lo stesso user e password");
		else if(utente1.size()==0) {
			return null;
		} else {
			return utente1.get(0);		
		}		
	}

	public Film getFilm(String film) throws Exception {
		List<Film> listaFilm=new ArrayList<Film>();		
		listaFilm= filmDao.findByTitolo(film);
		if(listaFilm.size()>1) throw new Exception("errore");
		else if(listaFilm.size()==0) {
			return null;
		} else {
			return listaFilm.get(0);		
		}		
	}

	public void deleteFilm(Film film) {
		em.getTransaction().begin();
		try {
			filmDao.delete(film);
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();

		}
	}

	public Integer getPostiDisponibili(Proiezione proiezione) {
		//mi serve conoscere tutte le prenotazioni associate a quella proiezione
		Integer id= proiezione.getId();
		List<Prenotazione> listaPrenotazioni= prenotazioneDao.retrieveByProiezione(id);
		Integer postiTot=0;
		if (listaPrenotazioni.size()>0) {
			for(Prenotazione p: listaPrenotazioni) {
				postiTot+= p.getPosti();
			}
			return postiTot=proiezione.getSala().getPostiTot()-postiTot;
		}else {
			return postiTot=proiezione.getSala().getPostiTot();
		}
	}

	public List<Prenotazione> getListaPrenotazioni(Utente utente){
		Integer id = utente.getId();
		return prenotazioneDao.findByUtente(id);
	}

	public List<Prenotazione> getListaPrenotazioniTot(){
		return prenotazioneDao.retrieve();
	}

	public List<LocalDateTime>getListaDatePrenotate(){
		return prenotazioneDao.retrieveByData();
	}

	public List<Prenotazione>getListaPrenotazioniData(LocalDateTime data){
		return prenotazioneDao.findByData(data);
	}

	public List<String>getListaUtentiPrenotati(){
		return prenotazioneDao.retrieveByUtente();
	}

	public void createRuolo(Ruolo ruolo) {
		em.getTransaction().begin();
		try {
			ruoloDao.create(ruolo);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void createAdmin(Utente utente) {
		em.getTransaction().begin();
		try {
			utente.setRuolo(ruoloDao.setAdmin());
			utenteDao.create(utente);
			em.getTransaction().commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void deletePrenotazione(Integer id) {
		em.getTransaction().begin();
		try {
			Prenotazione prenotazione = prenotazioneDao.findById(id);
			String mail= prenotazione.getUtente().getMail();
			String msg="La sua prenotazione numero "+prenotazione.getId() +" per il film: "+prenotazione.getProiezione().getFilm().getTitolo()+" in data: "+DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm").format(prenotazione.getProiezione().getData())+" è stata declinata.";
			String subject="Declinazione prenotazione";
			prenotazioneDao.delete(prenotazione);
			this.mandaMail(mail, msg, subject);
			em.getTransaction().commit();
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	
	public void mandaMail(String mail, String msgg, String subj) {
		String to = mail;
	    String subject = subj;
	    String msg =msgg;
	    String from ="cinemaFinalee@gmail.com";
	    String password ="CinemaFinalee94";


	    Properties props = new Properties();  
	    props.setProperty("mail.transport.protocol", "smtp");     
	    props.setProperty("mail.host", "smtp.gmail.com");  
	    props.put("mail.smtp.auth", "true");  
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.debug", "true");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    props.put("mail.smtp.socketFactory.fallback", "false");  
	    Session session = Session.getDefaultInstance(props,  
	    new javax.mail.Authenticator() {
	       protected PasswordAuthentication getPasswordAuthentication() {  
	       return new PasswordAuthentication(from,password);  
	   }  
	   });  

	   //session.setDebug(true);  
	   try {
		   Transport transport = session.getTransport();  
		   InternetAddress addressFrom = new InternetAddress(from);  

		   MimeMessage message = new MimeMessage(session);  
		   message.setSender(addressFrom);  
		   message.setSubject(subject);  
		   message.setContent(msg, "text/plain");  
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  

		   transport.connect();  
		   Transport.send(message);  
		   transport.close();
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	}
	
	public void createFinale(Utente utente) {
		em.getTransaction().begin();
		try {
			utente.setRuolo(ruoloDao.setFinale());
			utenteDao.update(utente);
			em.getTransaction().commit();
			String mail= utente.getMail();
			String msg="Gentile "+utente.getUsername()+" la sua registrazione è stata confermata. \nA presto!";
			String subject="Conferma registrazione";
			this.mandaMail(mail, msg, subject);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void createStaff(Utente utente) {
		em.getTransaction().begin();
		try {
			utente.setRuolo(ruoloDao.setStaff());
			utenteDao.update(utente);
			em.getTransaction().commit();
			String mail= utente.getMail();
			String msg="Gentile "+utente.getUsername()+" la sua registrazione è stata confermata. \n Benvenuto nel nostro STAFF.";
			String subject="Conferma registrazione";
			this.mandaMail(mail, msg, subject);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void createPrenotazione(Prenotazione prenotazione) {
		em.getTransaction().begin();
		try {
			prenotazioneDao.create(prenotazione);
			em.getTransaction().commit();
			String mail= prenotazione.getUtente().getMail();
			String msg="Gentile "+prenotazione.getUtente().getUsername()+" la informiamo che la sua prenotazione è andata a buon fine.\nIl numero prenotazione è "+prenotazione.getId()+" per lo spettacolo: "+prenotazione.getProiezione().getFilm().getTitolo()+" il giorno "+DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm").format(prenotazione.getProiezione().getData())+".\nHa prenototato "+prenotazione.getPosti()+" posti. Ci auguriamo che lo spettacolo sia di vostro gradimento!";
			String subject="Conferma Prenotazione";
			this.mandaMail(mail, msg, subject);
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}




	public EntityManager getEm() {
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	public FilmDao getFilmDao() {
		return filmDao;
	}
	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}
	public PrenotazioneDao getPrenotazioneDao() {
		return prenotazioneDao;
	}
	public void setPrenotazioneDao(PrenotazioneDao prenotazioneDao) {
		this.prenotazioneDao = prenotazioneDao;
	}
	public ProiezioneDao getProiezioneDao() {
		return proiezioneDao;
	}
	public void setProiezioneDao(ProiezioneDao proiezioneDao) {
		this.proiezioneDao = proiezioneDao;
	}
	public RuoloDao getRuoloDao() {
		return ruoloDao;
	}
	public void setRuoloDao(RuoloDao ruoloDao) {
		this.ruoloDao = ruoloDao;
	}
	public SalaDao getSalaDao() {
		return salaDao;
	}
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}
	public UtenteDao getUtenteDao() {
		return utenteDao;
	}
	public void setUtenteDao(UtenteDao utenteDao) {
		this.utenteDao = utenteDao;
	}


}
