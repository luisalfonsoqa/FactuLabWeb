package com.factulab.servlet;

import java.io.IOException;
import java.math.BigDecimal;

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
import com.factulab.dao.form.AtencionForm;
import com.factulab.service.AnalisisService;
import com.factulab.service.AtencionService;
import com.factulab.service.InstitucionService;
import com.factulab.service.TicketService;
import com.factulab.service.exception.ServiceException;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class ResumenAtencionServlet
 */
public class ResumenAtencionServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(ResumenAtencionServlet.class);
	
	private static final long serialVersionUID = 1L;
	AnalisisService analisisService = new AnalisisService();
	AtencionService atencionService = new AtencionService();
	TicketService ticketService = new TicketService();
	InstitucionService institucionService = new InstitucionService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResumenAtencionServlet() {
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
	/***
	 * Atencion con Tipo de FActura - INMEDIATA
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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
			
			AtencionForm atencionForm = (AtencionForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION);
			ConstantesBD constantesBD = (ConstantesBD) request.getSession().getAttribute(ServletConstante.SESSION_CONSTANTE);
			String accion = request.getParameter("accion");
			
			if(accion.equals("resumen")){
				BigDecimal descuento = BigDecimal.ZERO;
				String txt_descuento = request.getParameter("descuento");
				try{
					descuento = new BigDecimal(txt_descuento); 
				} catch(Exception ex){
					descuento = BigDecimal.ZERO;
				}
				atencionForm.setPorcentajeDescuento(descuento);
				analisisService.actualizarDescuentoAtencion(atencionForm);
				//atencionService.cargarResumenAtencion(atencionForm);
				
				rd = request.getRequestDispatcher("resumenAtencion.jsp");
				miLog.info("Atencion["+atencionForm.toString()+"] resumen."+usuarioLogin.getLogUser());
				rd.forward(request, response);
				
			} /* else if(accion.equals("procesar")  && atencionForm.getInstitucion().getFacMensual() == 0 ){
				String txt_tipoPago = request.getParameter("tipoPago");
				if(txt_tipoPago == null) throw new FactulabException("Tipo de Pago incorrecto ["+txt_tipoPago+"]");
				 /***************************************************
			     *                	TIPO DE PAGO  
			     *************************************************** /
				if (txt_tipoPago.equals(Constante.TIPO_PAGO_CONTADO)) atencionForm.setTipoPago(Constante.TIPO_PAGO_CONTADO);
			    else if (txt_tipoPago.equals(Constante.TIPO_PAGO_TARJETA)) atencionForm.setTipoPago(Constante.TIPO_PAGO_TARJETA);
			    else if (txt_tipoPago.equals(Constante.TIPO_PAGO_CONTADOTARJETA)) atencionForm.setTipoPago(Constante.TIPO_PAGO_CONTADOTARJETA);
			    else if (txt_tipoPago.equals(Constante.TIPO_PAGO_CREDITO)) atencionForm.setTipoPago(Constante.TIPO_PAGO_CREDITO);
			    else throw new FactulabException("Tipo de Pago incorrecto ["+txt_tipoPago+"]");
				
				 /***************************************************
			     *                		IGV
			     *************************************************** /
				/* String txt_conIGV = request.getParameter("igv");
				if(txt_conIGV == null)  throw new FactulabException("IGV incorrecto ["+txt_conIGV+"]");
				if(txt_conIGV.equals("1")) atencionForm.setConIGV(true);
				else if(txt_conIGV.equals("0")) atencionForm.setConIGV(false); 
				else throw new FactulabException("IGV incorrecto ["+txt_conIGV+"]");
				* /
				/***************************************************
			     *            	INSTITUCION / PACIENTE
			     *************************************************** /

				if(atencionForm.getConIGV()){
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
						miLog.info("Se actualizó la Institucion["+atencionForm.getInstitucion().toString()+"]."+usuarioLogin.getLogUser());
					}
					
					if(atencionForm.getInstitucion().getRuc() == null || atencionForm.getInstitucion().getRuc().length() < 11) throw new FactulabException("RUC incorrecto Institucion["+atencionForm.getInstitucion()+"]");
					if(atencionForm.getInstitucion().getNombre() == null || atencionForm.getInstitucion().getNombre().isEmpty()) throw new FactulabException("Nombre incorrecto Institucion["+atencionForm.getInstitucion()+"]");
					if(atencionForm.getInstitucion().getDireccion() == null || atencionForm.getInstitucion().getDireccion().trim().isEmpty()) throw new FactulabException("Dirección incorrecto Institucion["+atencionForm.getInstitucion()+"]");
				} else {
					if(atencionForm.getPaciente().getNombre() == null || atencionForm.getPaciente().getNombre().isEmpty()) throw new FactulabException("Nombre incorrecto Paciente["+atencionForm.getPaciente()+"]");
					if(atencionForm.getPaciente().getApepat() == null || atencionForm.getPaciente().getApepat().isEmpty()) throw new FactulabException("Apellido Paterno incorrecto Paciente["+atencionForm.getPaciente()+"]");
					if(atencionForm.getPaciente().getApemat() == null || atencionForm.getPaciente().getApemat().isEmpty()) throw new FactulabException("Apellido Materno incorrecto Paciente["+atencionForm.getPaciente()+"]");
				}
				
				/***************************************************
			     *            	PROCESANDO
			     *************************************************** /
				atencionService.crearAtencion(atencionForm, usuarioLogin, constantesBD);
				ticketService.crearTicket(atencionForm, usuarioLogin);
				atencionService.facturarAtencion(atencionForm);
				miLog.info("Atencion["+atencionForm.toString()+"] procesada."+usuarioLogin.getLogUser());
				response.sendRedirect("confirmacionTicket.jsp"); 
			} */ else if(accion.equals("procesar")  && atencionForm.getInstitucion().getFacMensual() == 1 ){
				/***************************************************
			     *            	PROCESANDO
			     ***************************************************/
				atencionService.crearAtencion(atencionForm, usuarioLogin, constantesBD);
				response.sendRedirect("confirmacionTicket.jsp");
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
				rd = request.getRequestDispatcher("confirmacionTicket.jsp");
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd.forward(request, response);
			}
		}
	}
}
