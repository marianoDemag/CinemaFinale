package com.azienda.cinema.web;

import java.io.IOException;


import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/ProvaServlet")
public class ProvaServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String nome= (String) req.getParameter("nome");
			String cognome= (String) req.getParameter("cognome");
			String username= (String) req.getParameter("username");
			String password= (String) req.getParameter("password");
			String mail= (String) req.getParameter("mail");
			BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
			
			String password1= password.toLowerCase();
			if(password.length()>=8 && !(password.equals(password1)) && username.length()>=3 ) {
			//if (bl.getUser(username) == null && bl.getMail(mail) == null) { 
			Utente utente= new Utente(nome, cognome, username, password, mail);
			bl.createUtente(utente);
			req.getRequestDispatcher("/jsp/confermaRegistrazione.jsp").forward(req, resp);
			} else {
				req.setAttribute(Costanti.ERRORE_CAMPI, "La password deve contenere almeno un carattere maiuscolo ed essere formata da almeno otto caratteri; l'username deve avere almeno tre caratteri.");
				req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
			}
			
			//}
			//else {
			//	req.setAttribute(Costanti.ERRORE_CAMPI, "Username o Email già inseriti, inseriscine altri");
			//	req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
			//}
			
		}
		catch(PersistenceException e) {
			e.printStackTrace();
			req.setAttribute(Costanti.ERRORE_CAMPI, "Username o Email già inseriti");
			req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
