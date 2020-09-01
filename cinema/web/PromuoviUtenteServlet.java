package com.azienda.cinema.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/PromuoviUtenteServlet")
public class PromuoviUtenteServlet extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
		try {
		Utente utente= bl.getUser(req.getParameter("username"));
		if(req.getParameter("azione").equals(Costanti.ELIMINA)) {
			bl.deleteUtente(utente);
			req.getRequestDispatcher("/ListaUtentiServlet").forward(req,resp);
			}else {
		bl.createStaff(utente);
		req.getRequestDispatcher("/ListaUtentiServlet").forward(req,resp);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
