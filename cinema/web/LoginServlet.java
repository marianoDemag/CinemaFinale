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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String user= (String) req.getParameter("username");
			String password= (String) req.getParameter("password");
			BusinessLogic bl= (BusinessLogic) req.getServletContext().getAttribute(Costanti.SC1);
			Utente utente = bl.login(user, password);
				if (utente == null) {
						req.setAttribute(Costanti.ERRORE_LOGIN, "L'username o la password sono errati, oppure l'admin non ha ancora accettato la tua richiesta ");
						req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
				}
				//.equals
				else if ( utente.getRuolo().getDescrizione().equals(Costanti.ADMIN)) {
						req.getSession().setAttribute(Costanti.LOGGATO, utente);
						req.getRequestDispatcher("/jsp/private/privataAdmin/adminPrivata.jsp").forward(req, resp);
				}
				else if( utente.getRuolo().getDescrizione().equals(Costanti.US)) {
						req.getSession().setAttribute(Costanti.LOGGATO, utente);
						req.getRequestDispatcher("/jsp/private/privataSTAFF/staffPrivata.jsp").forward(req, resp);
				}
				else {
						req.getSession().setAttribute(Costanti.LOGGATO, utente);
						req.getRequestDispatcher("/jsp/private/privataUtenteFinale/utenteFinalePrivata.jsp").forward(req, resp);
				}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
