package com.factulab.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.service.UsuarioService;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class ObtenerServlet
 */
public class ObtenerImpresoraServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(ObtenerImpresoraServlet.class);
	
	private static final long serialVersionUID = 1L;
	UsuarioService usuarioService = new UsuarioService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerImpresoraServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		RequestDispatcher rd = null;
		String txtError = null;
		Usuario usuarioLogin = null;
		try {
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			
			usuarioService.obtenerImpresora(usuarioLogin, request.getParameter("device"));
			miLog.info("Carga de Impresora Completa UsuarioLogin["+usuarioLogin+"]");
		} catch (FactulabException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} catch (DAOException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} finally {
			if(txtError != null) {
				rd = request.getRequestDispatcher("index.jsp");
			} else{
				rd = request.getRequestDispatcher("principal.jsp");
			}
			request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
			rd.forward(request, response);
		}
	 }
}
