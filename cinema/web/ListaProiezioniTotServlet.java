package com.azienda.cinema.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/ListaProiezioniTotServlet")
public class ListaProiezioniTotServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		Utente utente= (Utente) req.getSession().getAttribute(Costanti.LOGGATO);
		List<Proiezione> listaProiezioni= bl.getListaProiezioni();
		req.setAttribute(Costanti.LISTA_PROIEZIONI, listaProiezioni);
		req.getRequestDispatcher("/jsp/private/formGestioneProiezioni.jsp").forward(req, resp);
	
		}
	}


