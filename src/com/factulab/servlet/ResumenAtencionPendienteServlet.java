package com.factulab.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AtencionPendienteForm;
import com.factulab.service.AtencionService;
import com.factulab.service.InstitucionService;
import com.factulab.service.TarifaService;
import com.factulab.service.TicketService;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceConstante;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class ResumenAtencionPendienteServlet
 */
public class ResumenAtencionPendienteServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(ResumenAtencionPendienteServlet.class);
	private static final long serialVersionUID = 1L;
	AtencionService atencionService= new AtencionService();
	InstitucionService institucionService = new InstitucionService(); 
	TarifaService tarifaService = new TarifaService();
	TicketService ticketService = new TicketService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResumenAtencionPendienteServlet() {
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
		Usuario usuarioLogin = null;
		try {
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			
			AtencionPendienteForm atencion = (AtencionPendienteForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE);
			String accion = request.getParameter("accion");
			if(accion.equals("detalle")){
				String[] txt_selected = request.getParameterValues("selected");
				if(txt_selected == null || txt_selected.length == 0){
					throw new FactulabException("Id no valido Selected["+txt_selected+"]");
				}
				String txt_lst = "";
				Integer id = null;
				try{
					for (String s : txt_selected) {
						id = Integer.parseInt(s);
						if(txt_lst.isEmpty()) txt_lst = Integer.toString(id);
						else txt_lst += "," +id;
					}
				} catch(Exception e){
					throw new FactulabException("Id no valido ActencionForm["+txt_lst+"]");
				}
				if(txt_lst.equals("")){
					throw new FactulabException("Lista ID["+txt_lst+"] vacia.");
				}
				atencion.setLstIds(txt_lst);
				atencion.setInstitucion(institucionService.obtenerInstitucionPorID(atencion.getIdInstitucion()));
				atencion.setTarifa(tarifaService.obtenerTarifaPorID(atencion.getInstitucion().getIdTarifa()));
				atencion.setlAtencionDetalle(atencionService.obtenerAtencionesPorID(atencion.getIdInstitucion(), txt_lst));
				if(atencion.getlAtencionDetalle() == null) throw new FactulabException("No se encontraron atenciones. ["+atencion+"]");
				atencionService.obtenerAtencionPendienteResumen(atencion);
				miLog.info("Listando las Atenciones Pendientes ["+atencion.getLstIds()+"] seleccionadas."+usuarioLogin.getLogUser());
				atencion.setlAtencion(null);
				request.getSession().setAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE, atencion);
				
				rd = request.getRequestDispatcher("resumenAtencionPendiente.jsp");
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd.forward(request, response);
			} else if(accion.equals("XLS")){
				miLog.info("Exportando XLS las Atenciones Pendientes ["+atencion.getLstIds()+"]."+usuarioLogin.getLogUser());
				
				HashMap parametros = new HashMap();
	            parametros.put("RUC", atencion.getInstitucion().getRuc());
	            parametros.put("INST", atencion.getInstitucion().getNombre());
	        	parametros.put("TOTAL", atencion.getTotalConDescuentoString());
	        	parametros.put("SEDE", usuarioLogin.getNombreSede());
		        parametros.put("USUARIO", usuarioLogin.getUsuario());
	            
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
		           	fis = new FileInputStream(reportLocation + "/reporte/reporteAntecionesPendientesDetalle.jasper");
		            bufferedInputStream = new BufferedInputStream(fis);
		            
		            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
//		            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros);
		            
		            JRBeanCollectionDataSource data = null;
		            data = new JRBeanCollectionDataSource(atencion.getlAtencionDetalle());
		            
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


		            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		            Date date = new Date();
		            String filename = "AnalisisPendientes_"+dateFormat.format(date)+".xls";
		            response.setHeader("Content-disposition", "attachment; filename="+filename); 
		            response.setContentLength(baos.size());
		            baos.writeTo(servletOutputStream);
		 
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
		        
				
			} else if(accion.equals("procesar")){
				String txt_tipoPago = request.getParameter("tipoPago");
				if(txt_tipoPago == null) throw new FactulabException("Tipo de Pago incorrecto ["+txt_tipoPago+"]");
				 /***************************************************
			     *                	TIPO DE PAGO  
			     ***************************************************/
				if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_CONTADO)) atencion.setTipoPago(ServiceConstante.TIPO_PAGO_CONTADO);
			    else if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_TARJETA)) atencion.setTipoPago(ServiceConstante.TIPO_PAGO_TARJETA);
			    else if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_CONTADOTARJETA)) atencion.setTipoPago(ServiceConstante.TIPO_PAGO_CONTADOTARJETA);
			    else if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_CREDITO)) atencion.setTipoPago(ServiceConstante.TIPO_PAGO_CREDITO);
			    else throw new FactulabException("Tipo de Pago incorrecto ["+txt_tipoPago+"]");
				
				 /***************************************************
			     *                		IGV
			     ***************************************************/
				String txt_conIGV = request.getParameter("igv");
				if(txt_conIGV == null)  throw new FactulabException("IGV incorrecto ["+txt_conIGV+"]");
				if(txt_conIGV.equals("1")) atencion.setConIGV(true);
				else if(txt_conIGV.equals("0")) atencion.setConIGV(false); 
				else throw new FactulabException("IGV incorrecto ["+txt_conIGV+"]");
				
				/***************************************************
			     *            	INSTITUCION / PACIENTE
			     ***************************************************/
				
				if(atencion.getConIGV()){
					String txt_ruc = request.getParameter("ruc");
					String txt_direccion = request.getParameter("direccion");
					boolean actualizarInstitucion = false;
					if(txt_ruc !=null && (atencion.getInstitucion().getRuc() == null || atencion.getInstitucion().getRuc().length() < 11)) {
						atencion.getInstitucion().setRuc(txt_ruc);
						actualizarInstitucion = true;
					}
					if(txt_direccion != null && (atencion.getInstitucion().getDireccion() == null || atencion.getInstitucion().getDireccion().trim().isEmpty())) {
						atencion.getInstitucion().setDireccion(txt_direccion);
						actualizarInstitucion = true;
					}
					if(actualizarInstitucion){
						institucionService.updateDatosFactura(atencion.getInstitucion().getIdInstitucion(),txt_ruc, txt_direccion);
						miLog.info("Se actualizó la Institucion["+atencion.getInstitucion().toString()+"]."+usuarioLogin.getLogUser());
					}
					
					if(atencion.getInstitucion().getRuc() == null || atencion.getInstitucion().getRuc().length() < 11) throw new FactulabException("RUC incorrecto Institucion["+atencion.getInstitucion()+"]");
					if(atencion.getInstitucion().getNombre() == null || atencion.getInstitucion().getNombre().isEmpty()) throw new FactulabException("Nombre incorrecto Institucion["+atencion.getInstitucion()+"]");
					if(atencion.getInstitucion().getDireccion() == null || atencion.getInstitucion().getDireccion().trim().isEmpty()) throw new FactulabException("Dirección incorrecto Institucion["+atencion.getInstitucion()+"]");
				} else {
//					if(atencion.getPaciente().getNombre() == null || atencion.getPaciente().getNombre().isEmpty()) throw new FactulabException("Nombre incorrecto Paciente["+atencion.getPaciente()+"]");
//					if(atencion.getPaciente().getApepat() == null || atencion.getPaciente().getApepat().isEmpty()) throw new FactulabException("Apellido Paterno incorrecto Paciente["+atencion.getPaciente()+"]");
//					if(atencion.getPaciente().getApemat() == null || atencion.getPaciente().getApemat().isEmpty()) throw new FactulabException("Apellido Materno incorrecto Paciente["+atencion.getPaciente()+"]");
				}
				
				/***************************************************
			     *            	PROCESANDO
			     ***************************************************/
				ticketService.crearTicketPendiente(atencion, usuarioLogin);
				atencionService.facturarAtencionesPendientes(atencion.getIdInstitucion(), atencion.getLstIds()); 
				miLog.info("Facturando las Atenciones Pendientes ["+atencion.getLstIds()+"] seleccionadas."+usuarioLogin.getLogUser());
				response.sendRedirect("confirmacionTicketPendiente.jsp");
			} else {
				throw new FactulabException("Accion no valida ["+accion+"]");
			}		
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
				rd = request.getRequestDispatcher("resumenAtencionPendiente.jsp");
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd.forward(request, response);
			}
		}
	}
}
