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
import com.azienda.cinema.model.Sala;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/InserisciSalaServlet")
public class InserisciSalaServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		String nome = req.getParameter("nome");
		Integer posti =Integer.parseInt(req.getParameter("posti"));
		
		Sala sala= new Sala(posti,nome);
		try {
			bl.createSala(sala);
			req.getRequestDispatcher("/jsp/confermaInserimentoSala.jsp").forward(req, resp);
		}
		catch(PersistenceException e) {
			e.printStackTrace();
			req.setAttribute(Costanti.ERRORE_CAMPI, "Sala già inserita nel sistema");
			req.getRequestDispatcher("/jsp/private/formInserimentoSala.jsp").forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	}
	


