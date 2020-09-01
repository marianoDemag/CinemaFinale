package com.azienda.cinema.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Prenotazione;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/PrenotaServlet")
public class PrenotaServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utente utente=(Utente)req.getSession().getAttribute(Costanti.LOGGATO);
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		String titolo= req.getParameter("titolo");
		String nomeSala= req.getParameter("sala");
		String d= req.getParameter("data");

		LocalDateTime data= LocalDateTime.parse(d, DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
		String posti= req.getParameter("postiPrenotati");
		Integer postiPrenotati= Integer.parseInt(posti);
		try {

			Proiezione proiezione = bl.getProiezione(titolo, nomeSala, data);
			Integer postiDisponibili= bl.getPostiDisponibili(proiezione);
			LocalDate dataProiezione = LocalDateTime.parse(d, DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")).toLocalDate();
			LocalDate data1 = LocalDate.now();
			if(dataProiezione.isAfter(data1) || dataProiezione.isEqual(data1)) {

				if(postiDisponibili >= postiPrenotati) {
					Prenotazione prenotazione= new Prenotazione(postiPrenotati);
					prenotazione.setProiezione(proiezione);
					prenotazione.setUtente(utente);
					bl.createPrenotazione(prenotazione);
					req.setAttribute(Costanti.SUCCESSO_PRENOTAZIONE, "La tua prenotazione è avvenuta con successo");
					req.getRequestDispatcher("/jsp/private/privataUtenteFinale/utenteFinalePrivata.jsp").forward(req, resp);
				} else {
					req.setAttribute(Costanti.ERRORE_PRENOTAZIONE, "Spiacenti, non ci sono i posti disponibili che hai richiesto");
					req.getRequestDispatcher("/jsp/private/privataUtenteFinale/utenteFinalePrivata.jsp").forward(req, resp);
				}
			} else {
				req.setAttribute(Costanti.ERRORE_PRENOTAZIONE, "Non puoi prenotare un film precedente alla data di oggi");
				req.getRequestDispatcher("/jsp/private/privataUtenteFinale/utenteFinalePrivata.jsp").forward(req, resp);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
