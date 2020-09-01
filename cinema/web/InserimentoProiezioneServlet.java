package com.azienda.cinema.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/InserimentoProiezioneServlet")
public class InserimentoProiezioneServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String titoloFilm = req.getParameter("film");
		String nomeSala = req.getParameter("sala");
		String dataPrString= req.getParameter("dataProiezione");
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		LocalDateTime dataProiezione = LocalDateTime.parse(dataPrString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		try {
			
			LocalDateTime data1 = LocalDateTime.now();
			 

			if(dataProiezione.isAfter(data1)|| (dataProiezione.isEqual(data1))) {

				boolean inserito= bl.createProiezione(titoloFilm, nomeSala, dataProiezione);
				if(!inserito) {
					req.setAttribute(Costanti.ERRORE_PROIEZIONE, "Non è stato possibile inserire la proiezione");
					Utente utente=(Utente)req.getSession().getAttribute(Costanti.LOGGATO);
					if(utente.getRuolo().getDescrizione().equals(Costanti.ADMIN)) {
						req.getRequestDispatcher("/jsp/private/privataAdmin/adminPrivata.jsp").forward(req, resp);
					} else {
						req.getRequestDispatcher("/jsp/private/privataSTAFF/staffPrivata.jsp").forward(req, resp);
					}

				}else {
					req.getRequestDispatcher("/jsp/private/confermaProiezione.jsp").forward(req, resp);
				}
			} else {
				
				req.setAttribute(Costanti.ERRORE_PROIEZIONE, "Non puoi inserire la proiezione in un giorno precedente ad oggi");
				Utente utente=(Utente)req.getSession().getAttribute(Costanti.LOGGATO);
				if(utente.getRuolo().getDescrizione().equals(Costanti.ADMIN)) {
					req.getRequestDispatcher("/jsp/private/privataAdmin/adminPrivata.jsp").forward(req, resp);
				} else {
					req.getRequestDispatcher("/jsp/private/privataSTAFF/staffPrivata.jsp").forward(req, resp);
				}
				
			}
		}
		catch(PersistenceException e) {
			e.printStackTrace();
			req.setAttribute(Costanti.ERRORE_PROIEZIONE, "Proiezione già inserita");
			req.getRequestDispatcher("/ListaFilmESaleServlet").forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}


}
