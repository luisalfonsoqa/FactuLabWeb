package com.factulab.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.HojaTrabajoForm;
import com.factulab.service.AtencionService;
import com.factulab.service.exception.ServiceException;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class HojaTrabajoServlet
 */
public class HojaTrabajoServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(HojaTrabajoServlet.class);
	
	private static final long serialVersionUID = 1L;
	AtencionService atencionService = new AtencionService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HojaTrabajoServlet() {
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
		String txtError = null;
		RequestDispatcher rd = null;
		try {
			String txt_idAtencion = request.getParameter("idAtencion");
			Integer idAtencion = null;
			try{
				idAtencion = Integer.parseInt(txt_idAtencion);
			} catch(Exception e){
				throw new FactulabException("IdAtencion["+txt_idAtencion+"] incorrecto."+e.getMessage());
			}
			
			HojaTrabajoForm hojaTrabajo = atencionService.obtenerHojaTrabajo(idAtencion);
			hojaTrabajo.setlDetalle(atencionService.obtenerHojaTrabajoDetalle(idAtencion));
			
			HashMap parametros = new HashMap();
			parametros.put("PACIENTE", hojaTrabajo.getNombrePaciente());
			parametros.put("CODOMEGA", hojaTrabajo.getCodigoOmega());
			parametros.put("IDATENCION", hojaTrabajo.getIdAtencion());
			parametros.put("EDAD", hojaTrabajo.getEdad());

	        // set header as pdf
	        response.setContentType("application/pdf");
	 
	        // set input and output stream
	        ServletOutputStream servletOutputStream = response.getOutputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        FileInputStream fis;
	        BufferedInputStream bufferedInputStream;

	        try {
	            // get report location
	            ServletContext context = getServletContext();
	            String reportLocation = context.getRealPath("WEB-INF");
	 
	            // get report
	            fis = new FileInputStream(reportLocation + "/reporte/HojaTrabajo.jasper");
	            bufferedInputStream = new BufferedInputStream(fis);
	 
	            // fill it
	            JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(hojaTrabajo.getlDetalle());
	            
	            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jrbcds);
	 
	            // export to pdf
	            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
	 
	            response.setContentLength(baos.size());
	            baos.writeTo(servletOutputStream);
	            miLog.info("Imprimiendo Hoja de Trabajo ["+hojaTrabajo.toString()+"]");
	 
	            // close it
	            fis.close();
	            bufferedInputStream.close();
	 
	        } catch (Exception e) {
	        	throw new Exception(e.getMessage(),e);
	        } finally {
	            servletOutputStream.flush();
	            servletOutputStream.close();
	            baos.close();
	        }
		} catch(DAOException e){
			miLog.error(e.getMessage(),e);
        	txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage());
        	txtError = e.getMessage();
		} catch(ServiceException e){
			miLog.error(e.getMessage(),e);
        	txtError = e.getMessage();
		} catch(Exception e){
			miLog.error(e.getMessage(),e);
        	txtError = e.getMessage();
		} finally {
			if(txtError !=  null){
				rd = request.getRequestDispatcher("resumenAtencion.jsp");
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd.forward(request, response);
			}
		}
	}
}