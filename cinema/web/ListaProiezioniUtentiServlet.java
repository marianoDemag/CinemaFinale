package com.azienda.cinema.web;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;
import com.sun.net.httpserver.HttpsServer;

@WebServlet("/ListaProiezioniUtentiServlet")
public class ListaProiezioniUtentiServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		Utente utente = (Utente) req.getSession().getAttribute(Costanti.LOGGATO);

		if (req.getParameter("data")==null || req.getParameter("data").equals("")) { 
			List<Proiezione> listaProiezioni=bl.getListaProiezioniGiornaliere(LocalDateTime.now());
			List<Integer> listaPostiDisponibili=new ArrayList<Integer>();
			if(listaProiezioni != null) {
				for (Proiezione p: listaProiezioni) {
					Integer posti= bl.getPostiDisponibili(p);
					listaPostiDisponibili.add(posti);
				}
			}
			req.setAttribute(Costanti.LISTA_PROIEZIONI_UTENTE, listaProiezioni);
			req.setAttribute(Costanti.LISTA_POSTI_DISPONIBILI, listaPostiDisponibili);
			req.getRequestDispatcher("/jsp/listaProiezioniUtenti.jsp").forward(req, resp);
		}else {
			String s= req.getParameter("data");
			LocalDate l= LocalDate.parse(s);
			LocalDateTime lt = LocalDateTime.of(l, LocalTime.of(0, 0));
			LocalDateTime data = LocalDateTime.parse(lt.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			List<Proiezione> listaProiezioni=bl.getListaProiezioniGiornaliere(data);
			List<Integer> listaPostiDisponibili=new ArrayList<Integer>();
			if(listaProiezioni != null) {
				for (Proiezione p: listaProiezioni) {
					Integer posti= bl.getPostiDisponibili(p);
					listaPostiDisponibili.add(posti);
				}
			}
			req.setAttribute(Costanti.LISTA_PROIEZIONI_UTENTE, listaProiezioni);
			req.setAttribute(Costanti.LISTA_POSTI_DISPONIBILI, listaPostiDisponibili);
			req.getRequestDispatcher("/jsp/listaProiezioniUtenti.jsp").forward(req, resp);	
		}
	}

}
