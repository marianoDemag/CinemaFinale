package com.azienda.cinema.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/RichiestaServlet")
public class RichiestaServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl=(BusinessLogic) getServletContext().getAttribute(Costanti.SC1);
		Utente utente= (Utente) req.getSession().getAttribute(Costanti.LOGGATO);
		String ruolo= utente.getRuolo().getDescrizione();
			if( utente != null && ruolo.equals(Costanti.ADMIN)  ){
 			List<Utente>listaRichieste= bl.getRichieste();
 			req.setAttribute(Costanti.LISTA_RICHIESTE, listaRichieste);
 			req.getRequestDispatcher("/jsp/private/privataAdmin/richieste.jsp").forward(req, resp);;
	}
	}

}
