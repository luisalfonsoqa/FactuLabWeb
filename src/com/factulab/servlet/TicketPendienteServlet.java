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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.TicketPendienteForm;
import com.factulab.service.AtencionService;
import com.factulab.service.TicketService;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceConstante;
import com.factulab.servlet.util.ServletConstante;
import com.factulab.servlet.util.ServletUtil;

/**
 * Servlet implementation class TicketPendienteServlet
 */
public class TicketPendienteServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(TicketPendienteServlet.class);
	TicketService ticketService = new TicketService();
	AtencionService atencionService = new AtencionService();

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketPendienteServlet() {
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
			String formato =  request.getParameter("formato");
			if(formato.equals("PDF")){
				Integer idTicket = null;
				try{
					idTicket = Integer.parseInt(txt_idTicket);
					if(idTicket == null)
						throw new FactulabException("IdTicket es NULO. txt_idTicket["+txt_idTicket+"]");
				} catch(Exception e){
					throw new FactulabException("IdTicket["+txt_idTicket+"] incorrecto."+e.getMessage());
				}
				
				TicketPendienteForm ticket = ticketService.obtenerTicketPendienteForm(idTicket);
				ticket.setlDetalle(ticketService.obtenerTicketPendinteDetalleForm(idTicket));
				ticket.setlAtencionResumen(atencionService.obtenerAtencionPendienteResumen(ticket));
				
				HashMap parametros = new HashMap();
				parametros.put("NOMBRE", ticket.getNombre());
			    parametros.put("DIRECCION", ticket.getDireccion());
			    parametros.put("SEDE", ticket.getNomsede());
	            parametros.put("NUMERO", ServletUtil.llenaCerosIzquierda(ticket.getNumTicket(), 7));
	            parametros.put("SERIE", ServletUtil.llenaCerosIzquierda(ticket.getNumSerie(), 3));
	            parametros.put("FECHA", ticket.getFecha());
	            parametros.put("IMPRESORA", ticket.getImpresora());
	            parametros.put("TOTAL", ticket.getTotal());
	            parametros.put("RESUMEN","Servicio de analisis de laboratorio, según hoja de detalle adjunta.");
	            
	            if (ticket.getIgv() != 0) {
	                parametros.put("RUC", ticket.getRuc());
	                parametros.put("SUBTOTAL", ticket.getSubtotal());
	                parametros.put("MONTOIGV", ticket.getIgv());
	                parametros.put("IGV", ServiceConstante.IGV.toString());
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
		            	fis = new FileInputStream(reportLocation + "/reporte/facturaPendiente.jasper");
		            else
		            	fis = new FileInputStream(reportLocation + "/reporte/boletaPendiente.jasper");
		            bufferedInputStream = new BufferedInputStream(fis);
		 
		            // fill it
		            //JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(null);
		            
		            JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(ticket.getlAtencionResumen());
		            
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
			} else if(formato.equals("XLS")){
				Integer idTicket = null;
				try{
					idTicket = Integer.parseInt(txt_idTicket);
					if(idTicket == null)
						throw new FactulabException("IdTicket es NULO. txt_idTicket["+txt_idTicket+"]");
				} catch(Exception e){
					miLog.error(e);
					throw new FactulabException("IdTicket["+txt_idTicket+"] incorrecto."+e.getMessage());
				}
				
				
				TicketPendienteForm ticket = ticketService.obtenerTicketPendienteForm(idTicket);
				ticket.setlDetalle(ticketService.obtenerTicketPendinteDetalleForm(idTicket));
				miLog.info("Imprimir Ticket["+ticket.toString()+"]");
				HashMap parametros = new HashMap();
				parametros.put("TOTAL", Double.toString(ticket.getTotal()));
	            parametros.put("TIPO", "TICKET:");
	            parametros.put("SEDE", ticket.getNomsede());
	            parametros.put("USUARIO", ticket.getNomusuario());
	            parametros.put("NUMERO", ServletUtil.llenaCerosIzquierda(ticket.getNumSerie(), 3) + " - " +  ServletUtil.llenaCerosIzquierda(ticket.getNumTicket(), 7));
	            parametros.put("RUC", ticket.getRuc());
	            parametros.put("INST", ticket.getNominstitucion());
	            
	            // set header as pdf
		        response.setContentType("application/vnd.ms-excel");
		 
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
		           	fis = new FileInputStream(reportLocation + "/reporte/reporteFacturaDetalle.jasper");
		            bufferedInputStream = new BufferedInputStream(fis);
		            
		            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
//		            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros);
		            
		            JRBeanCollectionDataSource data = null;
		            data = new JRBeanCollectionDataSource(ticket.getlDetalle());
		            
		            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, data);
		            JRXlsExporter exporterXLS = new JRXlsExporter();
		            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		            exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
		            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);

		            exporterXLS.exportReport();
		            // export to pdf
//		            JasperExportManager.exportreporttoe(jasperPrint, baos);
		            String filename = "FacturaPendiente_"+ticket.getNominstitucion()+".xls";
		            response.setHeader("Content-disposition", "attachment; filename="+filename); 
		            response.setContentLength(baos.size());
		            baos.writeTo(servletOutputStream);
		 
		            // close it
		            fis.close();
		            bufferedInputStream.close();
		            miLog.info("Exportar XLS Ticket["+idTicket+"]");
		        } catch (Exception e) {
		        	throw new Exception(e.getMessage(),e);
		        } finally {
		            servletOutputStream.flush();
		            servletOutputStream.close();
		            baos.close();
		        }
			} else {
				throw new FactulabException("Formato no valido["+formato+"]");
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
				rd = request.getRequestDispatcher("error.jsp");
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd.forward(request, response);
			}
		}
	}
}
