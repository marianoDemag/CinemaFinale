package com.azienda.cinema.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Film;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/GestisciFilmServlet")
public class GestisciFilmServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		try {
		Film film= bl.getFilm(req.getParameter("titolo"));
		if(film != null) {
		if(req.getParameter("azione").equals(Costanti.ELIMINA)) {
			bl.deleteFilm(film);
		}else {
			//modifica
		 }
		}
		
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
