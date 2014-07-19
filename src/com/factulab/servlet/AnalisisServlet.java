package com.factulab.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Analisis;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;
import com.factulab.service.AnalisisService;
import com.factulab.service.InstitucionService;
import com.factulab.service.TarifaService;
import com.factulab.service.exception.ServiceException;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class AnalisisServlet
 */
public class AnalisisServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AnalisisServlet.class);
	
	private static final long serialVersionUID = 1L;
	AnalisisService analisisService = new AnalisisService();
	InstitucionService institucionService = new InstitucionService();
	TarifaService tarifaService = new TarifaService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalisisServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

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
			AtencionForm atencion = (AtencionForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION);
			
			String accion = request.getParameter("accion");
//			if(accion.equals("agregarInstitucion")){
//				/***************************************************
//			     *                	VALIDAR [NUEVA INSTITUCION]
//			     ***************************************************/
//				String txt_institucion = request.getParameter("idInstitucion");
//				Integer idInstitucion = null;
//				try{ idInstitucion = Integer.parseInt(txt_institucion);
//				}catch(Exception e){ throw new FactulabException("IdInstitucion incorrecto. ["+txt_institucion+"]"); }
//
//				/***************************************************
//			     *                	INICIAR [NUEVA INSTITUCION]
//			     ***************************************************/			
//				atencion.setInstitucion(institucionService.obtenerInstitucionPorID(idInstitucion));
//				atencion.setTarifa(tarifaService.obtenerTarifaPorID(atencion.getInstitucion().getIdTarifa()));
//				atencion.setlInstiAnali(institucionService.obtenerAnalisisConDescuento(atencion.getPaciente().getIdInstitucion()));
//				jsp = "insertarAnalisisForm.jsp";
//				//Limpia los analisis cargados
//				atencion.setlAnalisis(new ArrayList<AnalisisForm>());
//				atencion.setTotalConDescuento(BigDecimal.ZERO);
//				atencion.setTotalSinDescuento(BigDecimal.ZERO);
//			} else */
			if(accion.equals("agregar")){
				/***************************************************
			     *                	VALIDAR [AGREGAR ANALISIS]
			     ***************************************************/
				BigDecimal descuento = BigDecimal.ZERO;
				String txt_analisis = request.getParameter("idAnalisis");
				Integer idAnalisis = null;
				try{ idAnalisis = Integer.parseInt(txt_analisis);
				}catch(Exception e){ throw new FactulabException("IdAnalisis incorrecto. ["+txt_analisis+"]"); }
				
				String txt_descuento = request.getParameter("descuento");
				try{ descuento = new BigDecimal(txt_descuento);  
				} catch(Exception ex){ descuento = BigDecimal.ZERO; }
				
				/***************************************************
			     *                	INICIAR [AGREGAR ANALISIS]
			     ***************************************************/			
				Analisis analisis = analisisService.obtenerAnalisisPorID(idAnalisis);
				atencion.setPorcentajeDescuento(descuento);
				
				analisisService.agregarAlaAtencion(atencion, analisis);
				jsp = "insertarAnalisisForm.jsp";
				miLog.info(" Nueva Atencion. Paso2.5 - Add Analisis["+idAnalisis+"] Descuento["+descuento+"] " + usuarioLogin.getLogUser());
			} else if(accion.equals("eliminar")){
				/***************************************************
			     *                	VALIDAR [ELIMINAR ANALISIS]
			     ***************************************************/
				String txt_analisis = request.getParameter("idAnalisis");
				Integer idAnalisis = null;
				try{ idAnalisis = Integer.parseInt(txt_analisis);
				}catch(Exception e){ throw new FactulabException("IdAnalisis incorrecto. ["+txt_analisis+"]"); }
				
				/***************************************************
			     *                	INICIAR [ELIMINAR ANALISIS]
			     ***************************************************/			
				analisisService.eliminarDelaAtencion(atencion, idAnalisis);
				jsp = "insertarAnalisisForm.jsp";
				miLog.info(" Nueva Atencion. Paso2.5 - Del Analisis["+idAnalisis+"] " + usuarioLogin.getLogUser());
			} else if(accion.equals("limpiar")){
				/*************************************************************
			     *              INICIAR [LIMPIAR LISTADO DE ANALISIS]
			     *************************************************************/	
				atencion.setlAnalisis(new ArrayList<AnalisisForm>());
				atencion.setTotalConDescuento(BigDecimal.ZERO);
				atencion.setTotalSinDescuento(BigDecimal.ZERO);
				jsp = "insertarAnalisisForm.jsp";
				miLog.info(" Nueva Atencion. Paso2.5 - Dell All "+ usuarioLogin.getLogUser());
			} else if(accion.equals("actualizarDescuento")){
				/*************************************************************
			     *              VALIDAR [ACTUALIZAR DESCUENTO]
			     *************************************************************/	
				BigDecimal descuento = BigDecimal.ZERO;
				String txt_descuento = request.getParameter("descuento");
				try{ descuento = new BigDecimal(txt_descuento);  
				} catch(Exception ex){ descuento = BigDecimal.ZERO; }
				
				/*************************************************************
			     *              INICIAR [ACTUALIZAR DESCUENTO]
			     *************************************************************/	
				atencion.setPorcentajeDescuento(descuento);
				analisisService.actualizarDescuentoAtencion(atencion);
				jsp = "insertarAnalisisForm.jsp";
				miLog.info(" Nueva Atencion. Paso2.5 - Update Descuento["+descuento+"] " + usuarioLogin.getLogUser());
			} else {
				throw new FactulabException("Accion no valida ["+accion+"]");
			}
			request.getSession().setAttribute(ServletConstante.SESSION_ATENCION, atencion);
		} catch(DAOException e){
			jsp = "insertarAnalisisForm.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			jsp = "insertarAnalisisForm.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} catch(ServiceException e){
			jsp = "insertarAnalisisForm.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(Exception e){
			jsp = "insertarAnalisisForm.jsp";
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
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
