package com.azienda.cinema.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Film;
import com.azienda.cinema.model.Sala;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/ListaFilmESaleServlet")
public class ListaFilmESaleServlet	extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic)req.getServletContext().getAttribute(Costanti.SC1);
		List<Film> film= bl.getFilmDao().retrieve();
		List<Sala> sale= bl.getSalaDao().retrieve();
		req.setAttribute(Costanti.LISTA_FILM, film);
		req.setAttribute(Costanti.LISTA_SALE, sale);
		if(req.getParameter("listafilm").equals("Crea nuova proiezione") ) {
			req.getRequestDispatcher("/jsp/private/formInserimentoProiezione.jsp").forward(req, resp);
		}else {
			req.getRequestDispatcher("/jsp/private/listaFilm.jsp").forward(req, resp);
		}
		}
}
