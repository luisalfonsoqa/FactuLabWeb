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
import com.factulab.dao.form.TicketForm;
import com.factulab.service.TicketService;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceConstante;
import com.factulab.servlet.util.ServletConstante;
import com.factulab.servlet.util.ServletUtil;

/**
 * Servlet implementation class TicketServlet
 */
public class TicketServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(TicketServlet.class);
	
	private static final long serialVersionUID = 1L;
	TicketService ticketService = new TicketService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketServlet() {
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
			String txt_idTicket = request.getParameter("idTicket");
			Integer idTicket = null;
			try{
				idTicket = Integer.parseInt(txt_idTicket);
			} catch(Exception e){
				throw new FactulabException("IdTicket["+txt_idTicket+"] incorrecto."+e.getMessage());
			}
			
			TicketForm ticket = ticketService.obtenerTicketForm(idTicket);
			ticket.setlDetalle(ticketService.obtenerTicketDetalleForm(idTicket));
			
			HashMap parametros = new HashMap();
			parametros.put("PACIENTE", ticket.getNombrePaciente().toUpperCase());
			parametros.put("NUMERO", ServletUtil.llenaCerosIzquierda(ticket.getNumTicket(), 7));
			parametros.put("SERIE", ServletUtil.llenaCerosIzquierda(ticket.getNumSerie(), 3));
			parametros.put("USUARIO", ticket.getNomusuario());
			parametros.put("SEDE", ticket.getNomsede());
			parametros.put("FECHA", ticket.getFecha());
			parametros.put("CODOMEGA", ticket.getCodigoOmega());
			parametros.put("TOTAL", ticket.getTotal());
			parametros.put("NOMBRE", ticket.getNombre());
			parametros.put("DIRECCION", ticket.getDireccion());
			parametros.put("IMPRESORA", ticket.getImpresora());

			if (ticket.getIgv() != 0) {
				parametros.put("RUC", ticket.getRuc());
				parametros.put("SUBTOTAL", ticket.getSubtotal());
				parametros.put("MONTOIGV", ticket.getIgv());
				parametros.put("IGV", ServiceConstante.IGV.toString());
			} else {
				parametros.put("DNI", ticket.getDniPaciente());
			}
			parametros.put("TIPOPAGO", ServletUtil.obtenerDescripcionTipoPago(ticket.getTipopago()));

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
	            if (ticket.getIgv() != 0) 
	            	fis = new FileInputStream(reportLocation + "/reporte/facturaInmediata.jasper");
	            else
	            	fis = new FileInputStream(reportLocation + "/reporte/boletaInmediata.jasper");
	            bufferedInputStream = new BufferedInputStream(fis);
	 
	            // fill it
	            JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(ticket.getlDetalle());
	            
	            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jrbcds);
	 
	            // export to pdf
	            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
	 
	            response.setContentLength(baos.size());
	            baos.writeTo(servletOutputStream);
	 
	            // close it
	            fis.close();
	            bufferedInputStream.close();
	            miLog.info("Imprimir Ticket["+idTicket+"]");
	 
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
			miLog.error(e.getMessage(),e);
			txtError = e.getMessage();
		} catch(ServiceException e){
			miLog.error(e.getMessage(),e);
			txtError = e.getMessage();
		} catch(Exception e){
			miLog.error(e.getMessage(),e);
			txtError = e.getMessage();
		} finally {
			if(txtError != null) {
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd = request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}
		}
	}
}
