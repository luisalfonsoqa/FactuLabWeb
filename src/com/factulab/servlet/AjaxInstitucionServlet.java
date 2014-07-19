package com.factulab.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Institucion;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.DAOConstante;
import com.factulab.exception.FactulabError;
import com.factulab.service.InstitucionService;
import com.factulab.servlet.util.ServletConstante;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AjaxInstitucionServlet
 */
public class AjaxInstitucionServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AjaxInstitucionServlet.class);
	private static final long serialVersionUID = 1L;
	InstitucionService institucionService = new InstitucionService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxInstitucionServlet() {
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
		ObjectMapper mapper = new ObjectMapper();
		RequestDispatcher rd = null;
		String txtError = null;
		Usuario usuarioLogin = null;
		String jsp = null;
		String accion = "";
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
			
			accion = request.getParameter("accion");
			if(accion.equals("nuevo")){
				//jsp = "CargarInsertarAtencionFormServlet";
				/***************************************************
			     *                	VALIDAR [NUEVO]
			     ***************************************************/
				String txt_idTarifa = request.getParameter("idTarifa");
				String txt_idTipoFacturacion= request.getParameter("idTipoFacturacion");
				String nombre = request.getParameter("nombre");
				String ruc = request.getParameter("ruc");
				String direccion = request.getParameter("direccion");
				String email = request.getParameter("email");
				String contacto = request.getParameter("contacto");
				
				Integer idTarifa = null;
				Integer idTipoFacturacion = null;
				try{
					idTarifa = Integer.parseInt(txt_idTarifa);
					idTipoFacturacion = Integer.parseInt(txt_idTipoFacturacion);
					if(idTipoFacturacion != null && idTipoFacturacion != DAOConstante.BD_FACTURACION_INMEDIATA && idTipoFacturacion != DAOConstante.BD_FACTURACION_MENSUAL){
						throw new FactulabException("Institucion con Tipo de Facturacion incorrecta["+idTipoFacturacion+"]");
					}
				} catch (Exception e){
					throw new FactulabException("Ingresar nueva Institucion["+idTarifa+", "+idTipoFacturacion+"] incorrecto. "+e.getMessage());
				}
				
				/***************************************************
			     *                	INICIAR [NUEVO]
			     ***************************************************/	
				Institucion i = new Institucion();
				i.setFacMensual(idTipoFacturacion);
				i.setNombre(nombre);
				i.setIdTarifa(idTarifa);
				i.setDireccion(direccion ==null ? "" : direccion);
				i.setEmailContacto(email ==null ? "" : email);
				i.setNombreContacto(contacto ==null ? "" : contacto);
				i.setRuc(ruc ==null ? "" : ruc);
				institucionService.create(i);
				
//				String tipo = request.getParameter("tipo");
//				if(tipo != null && tipo.equals("ajax")){
					jsp = null;
					mapper.writeValue(response.getOutputStream(), i);
					miLog.info("Inserto nueva (ajax) Institución["+i.toString()+"]"+usuarioLogin.getLogUser());
//				} else {
//					miLog.info("Inserto nueva Institución["+i.toString()+"]"+usuarioLogin.getLogUser());
//				}
			} else if(accion.equals("buscar")){
				String tipo = request.getParameter("tipo");
				boolean all = false;
				all = (tipo != null && tipo.equals("all"));
				
				/***************************************************
			     *              VALIDAR [BUSCAR POR CRITERIO]
			     ***************************************************/
				String criterio = request.getParameter("criterio");
				String texto = request.getParameter("texto");
//				miLog.info("Buscar Institucion [Ini] por "+criterio+"["+texto+"]"+usuarioLogin.getLogUser());
				List<Institucion> lInstitucion = new ArrayList<>();
				if(!criterio.equals("nombre") && !criterio.equals("ruc")){
					throw new FactulabException("Criterio no valido ["+criterio+"]");
				}
				/***************************************************
			     *             INICIAR [BUSCAR POR CRITERIO]
			     ***************************************************/	
				lInstitucion = institucionService.obtenerPorCriterio(criterio, texto, all);
				mapper.writeValue(response.getOutputStream(), lInstitucion);
//				miLog.info("Buscar Institucion [Fin] por "+criterio+"["+texto+"]"+usuarioLogin.getLogUser());
			} else {
				throw new FactulabException("Accion no valida ["+accion+"]");
			}
		}  catch(DAOException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} finally {
			if(jsp == null) {
				if(txtError != null) mapper.writeValue(response.getOutputStream(), new FactulabError(txtError));
			} else {
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd = request.getRequestDispatcher(jsp);
				rd.forward(request, response);
			}
		}
	}
}