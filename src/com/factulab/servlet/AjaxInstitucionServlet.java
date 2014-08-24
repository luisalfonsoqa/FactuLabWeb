package com.factulab.servlet;

import java.io.IOException;
import java.net.URLDecoder;
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
				//1. Leer parametros 
				String txt_idTarifa = request.getParameter("idTarifa");
				String txt_idTipoFacturacion= request.getParameter("idTipoFacturacion");
				String nombre = request.getParameter("nombre");
				String ruc = request.getParameter("ruc");
				String direccion = request.getParameter("direccion");
				String email = request.getParameter("email");
				String contacto = request.getParameter("contacto");
				Integer idTarifa = null;
				Integer idTipoFacturacion = null;

				//2. Validacion de parametros no cadenas
				try{ idTarifa = Integer.parseInt(txt_idTarifa);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] Tarifa["+txt_idTarifa+"] incorrecto. "+e.getMessage());}
				try{ idTipoFacturacion = Integer.parseInt(txt_idTipoFacturacion);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] TipoFacturacion["+txt_idTipoFacturacion+"] incorrecto. "+e.getMessage());}
				
				//3. Validacion de parametros obligatorios
				if(nombre != null && !nombre.trim().isEmpty()) nombre = URLDecoder.decode(nombre, "UTF-8");
				else throw new FactulabException("Accion["+accion+"] Nombre["+nombre+"] incorrecto. ");
				if(ruc != null && !ruc.trim().isEmpty()) ruc = URLDecoder.decode(ruc, "UTF-8"); 
				else throw new FactulabException("Accion["+accion+"] Ruc["+ruc+"] incorrecto. ");
			
				//4. Validacion de parametros opcionales
				if(direccion != null && !direccion.trim().isEmpty()) direccion = URLDecoder.decode(direccion, "UTF-8"); 
				else direccion = "";
				if(email != null && !email.trim().isEmpty()) email = URLDecoder.decode(email, "UTF-8"); 
				else email = "";
				if(contacto != null && !contacto.trim().isEmpty()) contacto = URLDecoder.decode(contacto, "UTF-8"); 
				else contacto = "";
				
				//5. Proceso
				Institucion i = new Institucion();
				i.setFacMensual(idTipoFacturacion);
				i.setNombre(nombre);
				i.setIdTarifa(idTarifa);
				i.setDireccion(direccion);
				i.setEmailContacto(email);
				i.setNombreContacto(contacto);
				i.setRuc(ruc);
				institucionService.create(i);
				
				//6. Respuesta
				mapper.writeValue(response.getOutputStream(), i);
				miLog.info("Inserto nueva (ajax) Institución["+i.toString()+"]"+usuarioLogin.getLogUser());
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