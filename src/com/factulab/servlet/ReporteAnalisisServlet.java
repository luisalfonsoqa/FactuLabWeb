package com.factulab.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.ReporteAnalisis;
import com.factulab.service.AtencionService;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceConstante;
import com.factulab.servlet.util.ServletConstante;
import com.factulab.servlet.util.ServletUtil;

/**
 * Servlet implementation class CargarReporteAnalisisServlet
 */
public class ReporteAnalisisServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(ReporteAnalisisServlet.class);
	AtencionService atencionService = new AtencionService();
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteAnalisisServlet() {
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
		
		String txt_fechaIni = request.getParameter("fechaIni");
		String txt_fechaFin = request.getParameter("fechaFin");
		String txt_turno = request.getParameter("turno");
		String txt_accion = request.getParameter("accion");
		String txt_formato = request.getParameter("formato");
		Date fechaIni = null;
		Date fechaFin = null;
		DateFormat formatDate = new SimpleDateFormat(ServletConstante.WEB_FORMATO_FECHA);
		String txtError = null;
		Usuario usuarioLogin = null;
		
		HashMap parametros = new HashMap();
		JRBeanCollectionDataSource data = null;
		String reporte = "";
		try{
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			/***************************************************
		     *                	VALIDAR
		     ***************************************************/
			try{ fechaIni = formatDate.parse(txt_fechaIni);
			} catch(Exception e) { throw new FactulabException("FechaIni["+txt_fechaIni+"] incorrecto."+e.getMessage() + " Formato["+ServletConstante.WEB_FORMATO_FECHA+"]"); }
			try{ fechaFin = formatDate.parse(txt_fechaFin);
			} catch(Exception e) { throw new FactulabException("FechaFin["+txt_fechaFin+"] incorrecto."+e.getMessage() + " Formato["+ServletConstante.WEB_FORMATO_FECHA+"]"); }
			if(fechaFin.before(fechaIni)) throw new FactulabException("La Fecha Fin debe ser mayor a la Fecha Inicio");
			if(!txt_turno.equals(ServiceConstante.TURNO_MAÑANA) && !txt_turno.equals(ServiceConstante.TURNO_TARDE) && !txt_turno.equals(ServiceConstante.TURNO_TODOS)){
				throw new FactulabException("El Id de Turno["+txt_turno+"] no valido.");
			}

			if(txt_accion.equals("repCajero")){
				ReporteAnalisis rep = atencionService.obtenerAtencionesTomadas(usuarioLogin.getIdUsuario(),fechaIni, fechaFin, txt_turno );
				String txt_nombre_turno = "Todos";
				if(rep.getTurno().equals(ServiceConstante.TURNO_TARDE)) txt_nombre_turno = "Tarde";
				if(rep.getTurno().equals(ServiceConstante.TURNO_MAÑANA)) txt_nombre_turno = "Mañana";
						
				parametros.put("FECFIN", formatDate.format(rep.getFechaInicio()));
	            parametros.put("FECINI", formatDate.format(rep.getFechaFin()));
	            parametros.put("TURNO", txt_nombre_turno);
	            
	            reporte = getServletContext().getRealPath("WEB-INF") + "/reporte/reporteCajerosXLS.jasper";
				data = new JRBeanCollectionDataSource(rep.getlDetalle());
				
			} else if(txt_accion.equals("repAnalisis")){
				ReporteAnalisis rep = atencionService.obtenerAtencionesTomadas(null,fechaIni, fechaFin, txt_turno );
				String txt_nombre_turno = "Todos";
				if(rep.getTurno().equals(ServiceConstante.TURNO_TARDE)) txt_nombre_turno = "Tarde";
				if(rep.getTurno().equals(ServiceConstante.TURNO_MAÑANA)) txt_nombre_turno = "Mañana";
						
				parametros.put("FECFIN", formatDate.format(rep.getFechaInicio()));
	            parametros.put("FECINI", formatDate.format(rep.getFechaFin()));
	            parametros.put("TURNO", txt_nombre_turno);
	            
	            reporte = getServletContext().getRealPath("WEB-INF") + "/reporte/reporteCajerosXLS.jasper";
				data = new JRBeanCollectionDataSource(rep.getlDetalle());
			} else { throw new FactulabException("Accion no valida ["+txt_accion+"]"); }
			
			if(txt_formato.equals("XLS")){
				ServletUtil.getReporteXLS(response, parametros, "AnalisisTomados_"+usuarioLogin.getUsuario()+".xls", reporte, data);
				miLog.info("Reporte Excel Analisis Tomados realizado. User["+usuarioLogin.getIdUsuario()+"] Fecha["+fechaIni +" - "+ fechaFin+"] Turno["+txt_turno+"]"+usuarioLogin.getLogUser());
			} else if(txt_formato.equals("PDF")){
				ServletUtil.getReportePDF(response, parametros, "AnalisisTomados_"+usuarioLogin.getUsuario()+".pdf", reporte, data);
				miLog.info("Reporte PDF Analisis Tomados realizado. ULogin["+usuarioLogin.getIdUsuario()+"] Fecha["+fechaIni +" - "+ fechaFin+"] Turno["+txt_turno+"]"+usuarioLogin.getLogUser());
			} else { throw new FactulabException("Fromato no valido ["+txt_formato+"]"); }
		} catch(DAOException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
        	txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
        	txtError = e.getMessage();
		} catch(ServiceException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
        	txtError = e.getMessage();
		} catch(Exception e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
        	txtError = e.getMessage();
		} finally {
			if(txtError !=  null){
				rd = request.getRequestDispatcher("atencionesPendientes.jsp");
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd.forward(request, response);
			}
		}
	}
}