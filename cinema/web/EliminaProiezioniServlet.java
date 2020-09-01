package com.azienda.cinema.web;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.cinema.businessLogic.BusinessLogic;
import com.azienda.cinema.model.Proiezione;
import com.azienda.cinema.utils.Costanti;

@WebServlet("/EliminaProiezioniServlet")
public class EliminaProiezioniServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BusinessLogic bl= (BusinessLogic)req.getServletContext().getAttribute(Costanti.SC1);
		try {
			Integer id= Integer.parseInt(req.getParameter("idProiezione"));
			bl.deleteProiezione(id);
			req.getRequestDispatcher("/ListaProiezioniTotServlet").forward(req, resp);
		}catch(PersistenceException ex) {
				req.setAttribute(Costanti.ELIMINA_PROIEZIONE, "Elimina prima tutte le prenotazione associate a questa proiezione, poi potrai eliminarla");
				ex.printStackTrace();
				req.getRequestDispatcher("/ListaProiezioniTotServlet").forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}

}
