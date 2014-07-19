package com.factulab.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.ConstantesBD;
import com.factulab.dao.bean.Tarifa;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AtencionForm;
import com.factulab.service.AnalisisService;
import com.factulab.service.AtencionService;
import com.factulab.service.InstitucionService;
import com.factulab.service.TarifaService;
import com.factulab.service.TicketService;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceConstante;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class AtencionServlet
 */
public class ProcesarAtencionServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(ProcesarAtencionServlet.class);
	private static final long serialVersionUID = 1L;
	AnalisisService analisisService = new AnalisisService();
	InstitucionService institucionService = new InstitucionService();
	AtencionService atencionService = new AtencionService();
	TicketService ticketService = new TicketService();
	TarifaService tarifaService = new TarifaService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcesarAtencionServlet() {
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
/**
 * RESUMEN Y PROCESO DE ATENCION
 * 
 * SOLO PROCESA LA ATENCION SI EL TIPO DE PAGO ES EN CREDITO
 * PROCESA Y FACTURA LA ATENCION PARA LOS DEMAS TIPOS DE PAGO
 *  
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String txtError = null;
		boolean redirect = true;
		String jsp = null;
		Usuario usuarioLogin = null;
		try {
			 /***************************************************
		     *                	VALIDAR
		     ***************************************************/
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			
			AtencionForm atencionForm = (AtencionForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION);
			ConstantesBD constantesBD = (ConstantesBD) request.getSession().getAttribute(ServletConstante.SESSION_CONSTANTE);
			String accion = request.getParameter("accion");
			
			if(accion.equals("resumen")){
				/***************************************************
			     *                	VALIDAR (VER RESUMEN)
			     ***************************************************/
				BigDecimal descuento = BigDecimal.ZERO;
				String txt_descuento = request.getParameter("descuento");
				try{
					descuento = new BigDecimal(txt_descuento); 
				} catch(Exception ex){
					descuento = BigDecimal.ZERO;
				}
				/***************************************************
			     *                	INICIO (VER RESUMEN)
			     ***************************************************/
				atencionForm.setPorcentajeDescuento(descuento);
				//atencionForm.setConIGV(false);
				
				analisisService.actualizarDescuentoAtencion(atencionForm);
				miLog.info("Nueva Atencion. Paso3. Atencion["+atencionForm.toString()+"]"+usuarioLogin.getLogUser());
				
				jsp = "resumenAtencion.jsp";
				redirect = false;
				List<Tarifa> tarifas = tarifaService.listarTarifasHead();
				request.setAttribute("tarifas", tarifas);
			} else if(accion.equals("procesar")){
				/***************************************************
			     *          VALIDAR [PROCESAR - TIPO PAGO]
			     ***************************************************/
				String txt_tipoPago = request.getParameter("tipoPago");
				if(txt_tipoPago == null) throw new FactulabException("Tipo de Pago incorrecto ["+txt_tipoPago+"]");
				if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_CONTADO)) atencionForm.setTipoPago(ServiceConstante.TIPO_PAGO_CONTADO);
			    else if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_TARJETA)) atencionForm.setTipoPago(ServiceConstante.TIPO_PAGO_TARJETA);
			    else if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_CONTADOTARJETA)) atencionForm.setTipoPago(ServiceConstante.TIPO_PAGO_CONTADOTARJETA);
			    else if (txt_tipoPago.equals(ServiceConstante.TIPO_PAGO_CREDITO)) atencionForm.setTipoPago(ServiceConstante.TIPO_PAGO_CREDITO);
			    else throw new FactulabException("Tipo de Pago incorrecto ["+txt_tipoPago+"]");
				
				/*
				String txt_idInstitucion = request.getParameter("idInstitucion");
				Integer idInstitucion = null;
				try{ idInstitucion = Integer.parseInt(txt_idInstitucion);
				}catch(Exception e){ throw new FactulabException("IdInstitucion incorrecto. ["+txt_idInstitucion+"]"); }
				atencionForm.setInstitucion(institucionService.obtenerInstitucionPorID(idInstitucion));
				 */
				
				if(atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_CREDITO)) {
					/***************************************************
					 *           	
					 *           FACTURACION PENDIENTE
					 *                
				     ***************************************************/
					
					/***************************************************
				     *               PROCESANDO ATENCION
				     ***************************************************/
					atencionService.crearAtencion(atencionForm, usuarioLogin, constantesBD);
					miLog.info("Nueva Atencion. Paso4. Atencion["+atencionForm.toString()+"] con Facturacion pendiente procesada."+usuarioLogin.getLogUser());
					jsp = "confirmacionTicket.jsp";
				} else {
					/***************************************************
					 *           
					 *           FACTURACION INMEDIATA
					 *           
				     ***************************************************/
					
					/***************************************************
				     *          VALIDAR [PROCESAR - IGV]
				     ***************************************************/
					/*
					String txt_conIGV = request.getParameter("igv");
					if(txt_conIGV == null)  throw new FactulabException("IGV incorrecto ["+txt_conIGV+"]");
					if(txt_conIGV.equals("1")) atencionForm.setConIGV(true);
					else if(txt_conIGV.equals("0")) atencionForm.setConIGV(false); 
					else throw new FactulabException("IGV incorrecto ["+txt_conIGV+"]");
					*/
					if(atencionForm.getConIGV()){
						/***************************************************
					     *          VALIDAR [INSTITUCION - CON IGV]
					     ***************************************************/
						String txt_ruc = request.getParameter("ruc");
						String txt_direccion = request.getParameter("direccion");
						boolean actualizarInstitucion = false;
						
						if(txt_ruc !=null && (atencionForm.getInstitucion().getRuc() == null || atencionForm.getInstitucion().getRuc().length() < 11)) {
							atencionForm.getInstitucion().setRuc(txt_ruc);
							actualizarInstitucion = true;
						}
						if(txt_direccion != null && (atencionForm.getInstitucion().getDireccion() == null || atencionForm.getInstitucion().getDireccion().trim().isEmpty())) {
							atencionForm.getInstitucion().setDireccion(txt_direccion);
							actualizarInstitucion = true;
						}
						if(actualizarInstitucion){
							institucionService.updateDatosFactura(atencionForm.getInstitucion().getIdInstitucion(),txt_ruc, txt_direccion);
							miLog.info("Institucion Actualizada["+atencionForm.getInstitucion().toString()+"]."+usuarioLogin.getLogUser());
						}
						
						if(atencionForm.getInstitucion().getRuc() == null || atencionForm.getInstitucion().getRuc().length() < 11) throw new FactulabException("RUC incorrecto Institucion["+atencionForm.getInstitucion()+"]");
						if(atencionForm.getInstitucion().getNombre() == null || atencionForm.getInstitucion().getNombre().isEmpty()) throw new FactulabException("Nombre incorrecto Institucion["+atencionForm.getInstitucion()+"]");
						if(atencionForm.getInstitucion().getDireccion() == null || atencionForm.getInstitucion().getDireccion().trim().isEmpty()) throw new FactulabException("Dirección incorrecto Institucion["+atencionForm.getInstitucion()+"]");
					} else {
						/***************************************************
					     *          VALIDAR [PACIENTE - SIN IGV]
					     ***************************************************/
						if(atencionForm.getPaciente().getNombre() == null || atencionForm.getPaciente().getNombre().isEmpty()) throw new FactulabException("Nombre incorrecto Paciente["+atencionForm.getPaciente()+"]");
						if(atencionForm.getPaciente().getApepat() == null || atencionForm.getPaciente().getApepat().isEmpty()) throw new FactulabException("Apellido Paterno incorrecto Paciente["+atencionForm.getPaciente()+"]");
						if(atencionForm.getPaciente().getApemat() == null || atencionForm.getPaciente().getApemat().isEmpty()) throw new FactulabException("Apellido Materno incorrecto Paciente["+atencionForm.getPaciente()+"]");
					}
				
					/***************************************************
				     *            	PROCESANDO ATENCION
				     ***************************************************/
					atencionService.crearAtencion(atencionForm, usuarioLogin, constantesBD);
					/***************************************************
				     *            	FACTURANDO
				     ***************************************************/
					ticketService.crearTicket(atencionForm, usuarioLogin);
					atencionService.facturarAtencion(atencionForm);
					miLog.info("Nueva Atencion. Paso4. Atencion["+atencionForm.toString()+"] con Facturacion inmediata procesada."+usuarioLogin.getLogUser());
					jsp = "confirmacionTicket.jsp";
				}
			}
		} catch(DAOException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
			jsp = "confirmacionTicket.jsp";
		} catch (ServiceException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
			jsp = "confirmacionTicket.jsp";
		} catch(FactulabException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
			jsp = "confirmacionTicket.jsp";
		} finally {
			if(jsp != null) {
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				if(txtError != null){
					rd = request.getRequestDispatcher(jsp);
					rd.forward(request, response);
				}
				else if(redirect){
					response.sendRedirect(jsp);
				} else {
					rd = request.getRequestDispatcher(jsp);
					rd.forward(request, response);
				}
			}
		}
	}
}
