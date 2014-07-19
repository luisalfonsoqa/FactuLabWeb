package com.factulab.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Institucion;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AtencionPendienteForm;
import com.factulab.service.InstitucionService;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class CargarFacturarPendientesFormServlet
 */
public class CargarFacturarPendientesFormServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(CargarFacturarPendientesFormServlet.class);
	
	private static final long serialVersionUID = 1L;
	InstitucionService institucionService = new InstitucionService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarFacturarPendientesFormServlet() {
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

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String txtError = null;
		List<Institucion> lInstitucion = new ArrayList<Institucion>();
		Usuario usuarioLogin = null;
		try {
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			
			lInstitucion = institucionService.obtenerInstitucionesConAntecionesPendientes(usuarioLogin, null, null );
			request.getSession().setAttribute(ServletConstante.SESSION_INSTITUCIONES_PENDIENTES, lInstitucion);
			request.getSession().setAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE, new AtencionPendienteForm());
			miLog.info("Nueva Factura Pendiente. Paso1. "+lInstitucion.size()+" Instituciones."+usuarioLogin.getLogUser());
		} catch(DAOException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} finally {
			rd = request.getRequestDispatcher("atencionesPendientes.jsp");
			request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
			rd.forward(request, response);
		}
	}
}
