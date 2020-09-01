package com.azienda.cinema.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.azienda.cinema.model.Utente;
import com.azienda.cinema.utils.Costanti;

@WebFilter("/jsp/private/privataUtenteFinale/*")
public class FiltroUtenteFinale implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest hsr= (HttpServletRequest) request;
		Utente utente= (Utente) hsr.getSession().getAttribute(Costanti.LOGGATO);
		if(utente!= null && utente.getRuolo().getDescrizione().equals(Costanti.UF)) {
			chain.doFilter(request, response);
		}
		else {
			request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
		}


	}
	
	
}
