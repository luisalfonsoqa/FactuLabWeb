package com.factulab.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.ConstantesBD;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.DAOConstante;
import com.factulab.service.ConstantesService;
import com.factulab.service.UsuarioService;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(LoginServlet.class);
	
	private static final long serialVersionUID = 1L;
	UsuarioService usuarioService = new UsuarioService();
	ConstantesService constantesService = new ConstantesService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String txtError = null;
		Usuario usuarioLogin = new Usuario();
		String jsp = null;
		try{
			
			/***************************************************
		     *                	VALIDAR
		     ***************************************************/
			String txt_usuario = request.getParameter("usuario");
			String txt_clave = request.getParameter("clave");
			if(txt_usuario == null ||txt_usuario.trim().isEmpty() || txt_clave == null || txt_clave.trim().isEmpty())
				throw new FactulabException("Login incorrecto. Usuario["+txt_usuario+"] Clave["+txt_clave+"]");
			
			usuarioLogin = usuarioService.obtenerUsuario(txt_usuario, txt_clave);
			usuarioLogin.setId(UUID.randomUUID().toString());
//			if(!usuarioLogin.isImprimeTickets()) throw new FactulabException("Módulo disponible solo para los usuarios Cajeros.");
			
			if(usuarioLogin.getIdTipoUsuario() == DAOConstante.BD_TIPO_USUARIO_CAJA){
				String txt_device = request.getParameter("device");
				if(txt_device == null || txt_device.trim().isEmpty())
					throw new FactulabException("Login incorrecto. Device["+txt_device+"]");
					
				/***************************************************
				*                	INICIAR
				***************************************************/
				
				usuarioService.obtenerImpresora(usuarioLogin, txt_device);
				ConstantesBD constantes = constantesService.cargarConstantesBD();
				request.getSession().setAttribute(ServletConstante.SESSION_USUARIO, usuarioLogin);
				request.getSession().setAttribute(ServletConstante.SESSION_CONSTANTE, constantes);
				response.sendRedirect("principal.jsp");
				miLog.info("Cargando Modulo Cajero. Usuario["+usuarioLogin.toString()+"]."+usuarioLogin.getLogUser());
			} else {
				request.getSession().removeAttribute(ServletConstante.SESSION_USUARIO);
				request.getSession().removeAttribute(ServletConstante.SESSION_ATENCION);
				request.getSession().removeAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE);
				request.getSession().removeAttribute(ServletConstante.SESSION_CONSTANTE);
				request.getSession().removeAttribute(ServletConstante.SESSION_INSTITUCIONES_PENDIENTES);
				request.getSession().removeAttribute(ServletConstante.SESSION_VISTA);
				miLog.info("Cargando Modulo Mantenimiento. Usuario["+usuarioLogin.toString()+"]"+usuarioLogin.getLogUser());
				
				response.sendRedirect("../FactLabWeb/tiles/Login?usuario="+txt_usuario+"&clave="+txt_clave);
//				response.sendRedirect("../FactLabWeb/IngresarServlet?idApp="+ServletConstante.ID_APP_REP
//						+"&idSession="+usuarioLogin.getId()+"&usuario="+usuarioLogin.getUsuario()+"&idSede="+usuarioLogin.getIdSede());
			}
			
		} catch(DAOException e){
			jsp = "index.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			jsp = "index.jsp";
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
