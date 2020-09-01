package com.azienda.cinema.web;

import java.io.IOException;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Film;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/InserisciFilmServlet")
public class InserisciFilmServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		String titolo= req.getParameter("titolo");
		Integer durata=Integer.parseInt(req.getParameter("durata"));
		String trama= req.getParameter("trama");
		String genere= req.getParameter("genere");
		Film film= new Film(titolo,genere,trama,durata);
		try {
			bl.createFilm(film);
			req.getRequestDispatcher("/jsp/confermaInserimentoFilm.jsp").forward(req, resp);
		}
		catch(PersistenceException e) {
			e.printStackTrace();
			req.setAttribute(Costanti.ERRORE_CAMPI, "Film già inserito nel sistema");
			req.getRequestDispatcher("/jsp/private/formInserimentoFilm.jsp").forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
