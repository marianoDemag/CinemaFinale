package com.azienda.cinema.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Prenotazione;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/EliminaPrenotazioneServlet")
public class EliminaPrenotazioneServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl = (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		
		if(req.getParameter("hidden")!= null) {
			
			if(req.getParameter("hidden").equals("singola")) {
				Integer id = Integer.parseInt(req.getParameter("idPrenotazione"));
				bl.deletePrenotazione(id);
				req.getRequestDispatcher("/ListaPrenotazioniServlet").forward(req, resp);
				
			} else if(req.getParameter("hidden").equals("perData")) {
				String s= req.getParameter("data");
				LocalDateTime data= LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
				
				List<Prenotazione> listaPr= bl.getListaPrenotazioniData(data);
				if(listaPr!=null) {
					for(Prenotazione pr: listaPr) {
						Integer id=pr.getId();
						bl.deletePrenotazione(id);
					}
				}
				
				req.getRequestDispatcher("/ListaPrenotazioniServlet").forward(req, resp);

			} else { //per proiezione
				Integer id= Integer.parseInt(req.getParameter("idProiezione"));
				Proiezione pro= bl.getProiezioneId(id);
				List<Prenotazione> pr= pro.getListaPrenotazioni();
				if(pr!=null) {
				for (Prenotazione p : pr) {
					Integer id1=p.getId();
					bl.deletePrenotazione(id1);
				}
				}
				req.getRequestDispatcher("/ListaPrenotazioniServlet").forward(req, resp);

			}
		}
	}


}
