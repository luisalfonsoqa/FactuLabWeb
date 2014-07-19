package com.factulab.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Usuario;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class LogoffServlet
 */
public class LogoffServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(LogoffServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoffServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;

		Usuario u = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
		if (request.getSession() !=null && request.getSession().getAttribute(ServletConstante.SESSION_USUARIO)!=null)
	    {
			request.getSession().removeAttribute(ServletConstante.SESSION_USUARIO);
			request.getSession().removeAttribute(ServletConstante.SESSION_ATENCION);
			request.getSession().removeAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE);
			request.getSession().removeAttribute(ServletConstante.SESSION_CONSTANTE);
			request.getSession().removeAttribute(ServletConstante.SESSION_INSTITUCIONES_PENDIENTES);
			request.getSession().removeAttribute(ServletConstante.SESSION_VISTA);
			miLog.info("Cerrando session Usuario["+u.toString()+"]"+u.getLogUser());
	    }
		rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}
}
