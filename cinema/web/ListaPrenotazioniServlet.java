package com.azienda.cinema.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Prenotazione;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/ListaPrenotazioniServlet")
public class ListaPrenotazioniServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl = (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		Utente utente = (Utente) req.getSession().getAttribute(Costanti.LOGGATO);
		if(utente.getRuolo().getDescrizione().equals(Costanti.ADMIN)) {
			List<String> listaUtentiPrenotati= bl.getListaUtentiPrenotati();
			List<LocalDateTime> listaDatePrenotate=bl.getListaDatePrenotate();
			List<Prenotazione> listaPrenotazioni = bl.getListaPrenotazioniTot();
			req.setAttribute(Costanti.LISTA_PRENOTAZIONI, listaPrenotazioni);
			req.setAttribute(Costanti.LISTA_UTENTI_CON_PRENOTAZIONI, listaUtentiPrenotati);
			req.setAttribute(Costanti.LISTA_DATE_CON_PRENOTAZIONI, listaDatePrenotate);
			req.getRequestDispatcher("/jsp/private/listaPrenotazioni.jsp").forward(req, resp);	
		}else {
		List<Prenotazione> listaPrenotazioni = bl.getListaPrenotazioni(utente);
		req.setAttribute(Costanti.LISTA_PRENOTAZIONI, listaPrenotazioni);
		req.getRequestDispatcher("/jsp/private/listaPrenotazioni.jsp").forward(req, resp);
		}
		
		
	}

}
