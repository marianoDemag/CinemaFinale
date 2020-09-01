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

@WebFilter("/jsp/private/*")
public class LoginFiltro implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsr= (HttpServletRequest) request;
		Utente utente= (Utente) hsr.getSession().getAttribute(Costanti.LOGGATO);
		if(utente!= null) {
			chain.doFilter(request, response);
		}
		else {
			request.getRequestDispatcher("/html/login.html").forward(request,response);
		}
		
	}
	
}
