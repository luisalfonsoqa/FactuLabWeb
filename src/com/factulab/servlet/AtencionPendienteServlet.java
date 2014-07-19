package com.factulab.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AtencionPendiente;
import com.factulab.dao.form.AtencionPendienteForm;
import com.factulab.service.AtencionService;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class AtencionPendienteServlet
 */
public class AtencionPendienteServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AtencionPendienteServlet.class);
	
	private static final long serialVersionUID = 1L;
	AtencionService atencionService = new AtencionService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AtencionPendienteServlet() {
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
		String txtError = null;
		AtencionPendienteForm atencion = new AtencionPendienteForm();
		List<AtencionPendiente> lAtencion = new ArrayList<AtencionPendiente>();
		Usuario usuarioLogin = null;
		try {
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			String accion = request.getParameter("accion");
			String txt_idInstitucion = request.getParameter("idInstitucion");
			Integer idInstitucion = null;
			try{ idInstitucion = Integer.parseInt(txt_idInstitucion);
			} catch(Exception e) { throw new FactulabException("IdInstitucion["+txt_idInstitucion+"] incorrecto."+e.getMessage()); }
			
			if(accion.equals("buscar")){
				DateFormat formato = new SimpleDateFormat(ServletConstante.WEB_FORMATO_FECHA);
				String txt_fechaIni = request.getParameter("fechaIni");
				String txt_fechaFin = request.getParameter("fechaFin");
				Date fechaIni = null;
				Date fechaFin = null;
				
				if(txt_fechaIni  != null && !txt_fechaIni.isEmpty()) {
					try{ fechaIni = formato.parse(txt_fechaIni);
					} catch(Exception e) { throw new FactulabException("FechaIni["+txt_fechaIni+"] incorrecto."+e.getMessage() + " Formato["+ServletConstante.WEB_FORMATO_FECHA+"]"); }
				
					if(txt_fechaFin  != null && !txt_fechaFin.isEmpty()) {
						try{ fechaFin = formato.parse(txt_fechaFin);
						} catch(Exception e) { throw new FactulabException("FechaFin["+txt_fechaFin+"] incorrecto."+e.getMessage() + " Formato["+ServletConstante.WEB_FORMATO_FECHA+"]"); }
						
						if(fechaFin.before(fechaIni)) {
							throw new FactulabException("La Fecha Fin debe ser mayor a la Fecha Inicio");
						}
					}
				}
				lAtencion = atencionService.obtenerAtencionesPendientes(idInstitucion,usuarioLogin.getIdSede(), fechaIni, fechaFin );
				miLog.info("Listando atenciones pendientes para la Institucion["+idInstitucion+"]. Número de atenciones pendientes encontradas. ["+lAtencion.size()+"]"+usuarioLogin);
			} else {
				throw new FactulabException("Accion no valida ["+accion+"]");
			}
			atencion.setlAtencion(lAtencion);
			atencion.setIdInstitucion(idInstitucion);
			request.getSession().setAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE, atencion);
		} catch (FactulabException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} catch (DAOException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch (Exception e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} finally{
			rd = request.getRequestDispatcher("atencionesPendientes.jsp");
			request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
			rd.forward(request, response);
		}
	}
}
