package com.factulab.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Especialidad;
import com.factulab.dao.bean.Institucion;
import com.factulab.dao.bean.Tarifa;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.service.EspecialidadService;
import com.factulab.service.InstitucionService;
import com.factulab.service.MedicoService;
import com.factulab.service.PacienteService;
import com.factulab.service.TarifaService;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class CargarInsertarAnalisisFormServlet
 */
public class CargarInsertarAtencionFormServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(CargarInsertarAtencionFormServlet.class);
	
	private static final long serialVersionUID = 1L;
	PacienteService pacienteService = new PacienteService();
	MedicoService medicoService = new MedicoService();
	TarifaService tarifaService = new TarifaService();
	InstitucionService institucionService = new InstitucionService();
	EspecialidadService especialidadService = new EspecialidadService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarInsertarAtencionFormServlet() {
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
	/**
	 * Cargar pantalla de (Ingreso de una Nueva Atencion)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		RequestDispatcher rd = null;
		String txtError = null;
		Usuario usuarioLogin = null;
		String jsp = null;
		
		try {
			/***************************************************
		     *                	VALIDAR
		     ***************************************************/
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
				return;
			}
			String error = (String) request.getAttribute(ServletConstante.REQUEST_ERROR);
			if(error != null){
				jsp = "insertarAtencionForm.jsp";
				txtError = error;
			}
			
			/***************************************************
		     *                	INICIAR
		     ***************************************************/
			List<Institucion> instituciones = institucionService.listarInstitucionesHead();
			List<Tarifa> tarifas = tarifaService.listarTarifasHead();
			List <Especialidad> especialidades = especialidadService.listarEspecialidades();
			request.setAttribute("tarifas", tarifas);
			request.setAttribute("instituciones", instituciones);
			request.setAttribute("especialidades", especialidades);
			jsp = "insertarAtencionForm.jsp";
			
			miLog.info("Nueva Atencion. Paso1. "+usuarioLogin.getLogUser());
		} catch(DAOException e){
			jsp = "insertarAtencionForm.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			jsp = "insertarAtencionForm.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} finally {
			if(jsp != null) {
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd = request.getRequestDispatcher(jsp);
				rd.forward(request, response);
			}
		}
	}
}
