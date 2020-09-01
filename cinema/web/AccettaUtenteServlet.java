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

@WebServlet("/AccettaUtenteServlet")
public class AccettaUtenteServlet  extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Utente utente= new Utente();
//		if (req.getAttribute("nome")!= null) {
//			String nome = (String) req.getParameter("nome");
//			utente.setNome(nome);
//		}
//		//se è stringa vuota req.getParameter("cognome").equals("") ? null : req.getParameter("cognome")
//		if (req.getAttribute("cognome")!= null) {
//			String cognome = (String) req.getParameter("nome");
//			utente.setCognome(cognome);
//		}
//		String username = (String) req.getParameter("username");
//		utente.setUsername(username);
//		//levo il cast a string 
//		String mail= (String) req.getParameter("mail");
//		utente.setMail(mail);
//		String password= req.getParameter("password");
//		utente.setPassword(password);
//		
//		String ruolo= (String) req.getParameter("ruolo");
		BusinessLogic bl= (BusinessLogic)req.getServletContext().getAttribute(Costanti.SC1);
		
		try {
		utente= bl.getUser(req.getParameter("username"));
		if (req.getParameter("ruolo")!= null) {
			if (req.getParameter("ruolo").equals(Costanti.ELIMINA)) {
				bl.deleteUtente(utente);
				req.getRequestDispatcher("/RichiestaServlet").forward(req,resp);
			} else if(req.getParameter("ruolo").equals(Costanti.STAFF)){
				bl.createStaff(utente);
				req.getRequestDispatcher("/RichiestaServlet").forward(req,resp);
				}
				else {
					bl.createFinale(utente);
					req.getRequestDispatcher("/RichiestaServlet").forward(req,resp);
				}
		} else throw new RuntimeException("Non è possibile che il ruolo sia null");
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
