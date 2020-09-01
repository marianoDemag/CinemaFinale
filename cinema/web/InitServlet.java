package com.azienda.cinema.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.businessLogic.FilmDao;
import com.azienda.cinema.businessLogic.PrenotazioneDao;
import com.azienda.cinema.businessLogic.ProiezioneDao;
import com.azienda.cinema.businessLogic.RuoloDao;
import com.azienda.cinema.businessLogic.SalaDao;
import com.azienda.cinema.businessLogic.UtenteDao;
import com.azienda.cinema.model.Film;
import com.azienda.cinema.model.Ruolo;
import com.azienda.cinema.model.Sala;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet(value="/iS",loadOnStartup = 1)
public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf=null;
	@Override
	public void init() throws ServletException {
		emf=Persistence.createEntityManagerFactory("CinemaFinale");
		EntityManager em=emf.createEntityManager();
		FilmDao filmDao= new FilmDao(em);
		PrenotazioneDao prenotazioneDao= new PrenotazioneDao(em);
		ProiezioneDao proiezioneDao= new ProiezioneDao(em);
		RuoloDao ruoloDao= new RuoloDao(em);
		SalaDao salaDao= new SalaDao(em);
		UtenteDao utenteDao= new UtenteDao(em);
		BusinessLogic bl= new BusinessLogic(em, filmDao, prenotazioneDao, proiezioneDao, ruoloDao, salaDao, utenteDao);
		Utente utente= new Utente();
		
		
		getServletContext().setAttribute(Costanti.SC1, bl);
		//carico sul db la prima volta che va in create i ruoli e l'admin
		//ruoliEadmin(bl);
		
	
		
		
	}
	
	public void saleEfilm(BusinessLogic bl) {
		List<Film> listaFilm= new ArrayList<Film>();
		Film natale = new Film("natale in india", "comico", "aaa", 120);
		Film natale2 = new Film("natale sul nilo", "thriller", "bbb", 100);
		listaFilm.add(natale);
		listaFilm.add(natale2);
		List<Sala> listaSale= new ArrayList<Sala>();
		Sala sala= new Sala(100, "sala1");
		Sala sala2= new Sala(110, "sala2");
		listaSale.add(sala);
		listaSale.add(sala2);
		for (Sala s : listaSale) {
			bl.getSalaDao().create(s);
		}
		for (Film f : listaFilm) {
			bl.getFilmDao().create(f);
		}
	
	}
	
	public void ruoliEadmin(BusinessLogic bl) {
		List<Ruolo> listaRuoli= new ArrayList<Ruolo>();
		Ruolo finale = new Ruolo(Costanti.UF);
		Ruolo staff = new Ruolo(Costanti.US);
		Ruolo admin = new Ruolo(Costanti.ADMIN);
		listaRuoli.add(finale);
		listaRuoli.add(staff);
		listaRuoli.add(admin);
		for (Ruolo r : listaRuoli) {
			bl.getRuoloDao().create(r);
		}
		Utente scotti= new Utente("Gerry", "Scotti", "admin", "admin", "drscotti70@gmail.com");
		bl.createAdmin(scotti);
	}
	
	@Override
	public void destroy() {
		emf.close();
	}
}
