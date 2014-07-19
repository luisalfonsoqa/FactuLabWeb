package com.factulab.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Medico;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.exception.FactulabError;
import com.factulab.service.MedicoService;
import com.factulab.servlet.util.ServletConstante;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AjaxMedicoServlet
 */
public class AjaxMedicoServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AjaxMedicoServlet.class);
	
	private static final long serialVersionUID = 1L;
	MedicoService medicoService = new MedicoService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxMedicoServlet() {
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
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String txtError = null;
		ObjectMapper mapper = new ObjectMapper();
		Usuario usuarioLogin = null;
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
			
			String accion = request.getParameter("accion");
			if(accion.equals("buscarID")){
				/***************************************************
			     *                	VALIDAR [BUSCAR POR ID]
			     ***************************************************/
				String txt_idMedico = request.getParameter("idMedico");
				Integer idMedico = null;
				try{
					idMedico = Integer.parseInt(txt_idMedico);
				}catch(Exception e){
					throw new FactulabException("IdMedico incorrecto. ["+txt_idMedico+"]");
				}
				
				/***************************************************
			     *                	INICIAR [BUSCAR POR ID]
			     ***************************************************/			
				Medico medico = medicoService.obtenerMedicoPorID(idMedico);
				mapper.writeValue(response.getOutputStream(), medico);
			} else if(accion.equals("buscar")){
				/***************************************************
			     *                	VALIDAR [BUSCAR]
			     ***************************************************/
				String criterio = request.getParameter("criterio");
				String texto = request.getParameter("texto");
				miLog.info("Buscar Medico [Ini] por "+criterio+"["+texto+"]"+usuarioLogin.getLogUser());
				List<Medico> lMedico = new ArrayList<>();
				if(!criterio.equals("nombre") && !criterio.equals("cmp") && !criterio.equals("apemat") && !criterio.equals("apepat")){
					throw new FactulabException("Criterio no valido ["+criterio+"]");
				}
				/***************************************************
			     *                	INICIAR [BUSCAR]
			     ***************************************************/	
				lMedico = medicoService.obtenerPorCriterio(criterio, texto);
				mapper.writeValue(response.getOutputStream(), lMedico);
				miLog.info("Buscar Medico [Fin] por "+criterio+"["+texto+"]"+usuarioLogin.getLogUser());
			} else if(accion.equals("nuevo")){
				/***************************************************
			     *                	VALIDAR [NUEVO]
			     ***************************************************/
				String nombre = request.getParameter("nombre");
				String apepat = request.getParameter("apepat");
				String apemat = request.getParameter("apemat");
				String cmp = request.getParameter("cmp");
				String txt_idEspecialidad = request.getParameter("idEspecialidad");
				String direccion = request.getParameter("direccion");
				
				Integer idEspecialidad = null;
				try{
					idEspecialidad = Integer.parseInt(txt_idEspecialidad);
				} catch (Exception e){
					throw new FactulabException("Ingresar nuevo Medico[idEspecialidad = "+txt_idEspecialidad+"] incorrecto. "+e.getMessage());
				}
				if(nombre == null || nombre.trim().isEmpty()) throw new FactulabException("Ingresar nuevo Medico[nombre = "+nombre+"] incorrecto. ");
				if(apepat == null || apepat.trim().isEmpty()) throw new FactulabException("Ingresar nuevo Medico[apepat = "+apepat+"] incorrecto. ");
				if(apemat == null || apemat.trim().isEmpty()) throw new FactulabException("Ingresar nuevo Medico[apemat = "+apemat+"] incorrecto. ");
				if(cmp == null || cmp.trim().isEmpty()) throw new FactulabException("Ingresar nuevo Medico[cmp = "+cmp+"] incorrecto. ");
				//if(direccion == null || direccion.trim().isEmpty()) throw new FactulabException("Ingresar nuevo Medico[direccion = "+direccion+"] incorrecto. ");
				
				/***************************************************
			     *                	INICIAR [NUEVO]
			     ***************************************************/	
				Medico m = new Medico();
				m.setNombre(nombre);
				m.setApepat(apepat);
				m.setApemat(apemat);
				m.setCmp(cmp);
				m.setDireccion(direccion == null ? "" : direccion);
				m.setIdEspecialidad(idEspecialidad);

				medicoService.create(m);
				mapper.writeValue(response.getOutputStream(), m);
				miLog.info("Inserto del nuevo Medico["+m.toString()+"]"+usuarioLogin.getLogUser());
			} else {
				throw new FactulabException("Accion no valida ["+accion+"]");
			}
		} catch(DAOException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} finally {
			if(txtError != null) mapper.writeValue(response.getOutputStream(), new FactulabError(txtError));
		}
	}
}