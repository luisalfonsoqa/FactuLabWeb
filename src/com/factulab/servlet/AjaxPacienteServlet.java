package com.factulab.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Paciente;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.exception.FactulabError;
import com.factulab.service.PacienteService;
import com.factulab.servlet.util.ServletConstante;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AjaxPacienteServlet
 */
public class AjaxPacienteServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AjaxPacienteServlet.class);
	
	private static final long serialVersionUID = 1L;
	PacienteService pacienteService = new PacienteService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxPacienteServlet() {
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
	/**
	 * AJAX - Agregar Paciente a la Atencion
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String txtError = null;
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
				String txt_idPaciente = request.getParameter("idPaciente");
				Integer idPaciente = null;
				try{
					idPaciente = Integer.parseInt(txt_idPaciente);
				}catch(Exception e){
					throw new FactulabException("IdPaciente incorrecto. ["+txt_idPaciente+"]");
				}
				
				/***************************************************
			     *                	INICIAR [BUSCAR POR ID]
			     ***************************************************/			
				Paciente paciente = pacienteService.obtenerPacientePorID(idPaciente);
				mapper.writeValue(response.getOutputStream(), paciente);
			} else if(accion.equals("buscar")){
				/***************************************************
			     *                	VALIDAR [BUSCAR]
			     ***************************************************/
				String criterio = request.getParameter("criterio");
				String texto = request.getParameter("texto");
				miLog.info("Buscar Paciente [Ini] por "+criterio+"["+texto+"]"+usuarioLogin.getLogUser());
				List<Paciente> lPaciente = new ArrayList<>();
				if(!criterio.equals("nombre") && !criterio.equals("dni") && !criterio.equals("apemat") && !criterio.equals("apepat")){
					throw new FactulabException("Criterio de busqueda no valido ["+criterio+"]");
				}
				
				/***************************************************
			     *                	INICIAR [BUSCAR]
			     ***************************************************/	
				lPaciente = pacienteService.obtenerPorCriterio(criterio, texto);
				mapper.writeValue(response.getOutputStream(), lPaciente);
				miLog.info("Buscar Paciente [Fin] por "+criterio+"["+texto+"]"+usuarioLogin.getLogUser());
			} else if(accion.equals("nuevo")){
				//1. Leer parametros 
				String txt_idInstitucion = request.getParameter("idInstitucion");
				String txt_idTipoPaciente = request.getParameter("idTipoPaciente");
				String txt_idDistrito = request.getParameter("idDistrito");
				String txt_fecnac = request.getParameter("fecnac");
				String txt_sexo = request.getParameter("sexo");
				
				String nombre = request.getParameter("nombre");
				String apepat = request.getParameter("apepat");
				String apemat = request.getParameter("apemat");
				String dni = request.getParameter("dni");
				String direccion =  request.getParameter("direccion");
				
				String telefono =  request.getParameter("telefono");
				String celular =  request.getParameter("celular");
				String histClinica =  request.getParameter("histClinica");
				String email =  request.getParameter("email");
				String fax =  request.getParameter("fax");

				Integer idInstitucion = null;
				Integer idTipoPaciente = null;
				Integer idDistrito = 0;
				Character sexo = null;
				Date fecnac;
				
				//2. Validacion de parametros no cadenas
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				try { idInstitucion = Integer.parseInt(txt_idInstitucion);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] ID Institucion ["+txt_idInstitucion+"] incorrecto. "+e.getMessage());}
				try { idDistrito = Integer.parseInt(txt_idDistrito);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] ID Distrito ["+txt_idDistrito+"] incorrecto. "+e.getMessage());}
				try { idTipoPaciente = Integer.parseInt(txt_idTipoPaciente);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] ID Tipo Paciente ["+txt_idTipoPaciente+"] incorrecto. "+e.getMessage());}
				try { sexo = txt_sexo.charAt(0);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] Sexo ["+txt_sexo+"] incorrecto. "+e.getMessage());}
				try{ fecnac = formatter.parse(txt_fecnac);
				} catch (Exception e){ throw new FactulabException("Accion["+accion+"] Fecha Nacimiento"+txt_fecnac+"] incorrecto. "+e.getMessage());}
				
				//3. Validacion de parametros obligatorios
				if(nombre != null && !nombre.trim().isEmpty()) nombre = URLDecoder.decode(nombre, "UTF-8");
				else throw new FactulabException("Accion["+accion+"] Nombre["+nombre+"] incorrecto. ");
				if(apepat != null && !apepat.trim().isEmpty()) apepat = URLDecoder.decode(apepat, "UTF-8"); 
				else throw new FactulabException("Accion["+accion+"] Apellido Paterno["+apepat+"] incorrecto. ");
				if(apemat != null && !apemat.trim().isEmpty()) apemat = URLDecoder.decode(apemat, "UTF-8"); 
				else throw new FactulabException("Accion["+accion+"] Apellido Materno["+apemat+"] incorrecto. ");
				//DNI no requiere 
				
				//4. Validacion de parametros opcionales
				if(direccion != null && !direccion.trim().isEmpty()) direccion = URLDecoder.decode(direccion, "UTF-8");
				else direccion = "";
				if(telefono != null && !telefono.trim().isEmpty()) telefono = URLDecoder.decode(telefono, "UTF-8");
				else telefono = "";
				if(celular != null && !celular.trim().isEmpty()) celular = URLDecoder.decode(celular, "UTF-8");
				else celular = "";
				if(histClinica != null && !histClinica.trim().isEmpty()) histClinica = URLDecoder.decode(histClinica, "UTF-8");
				else histClinica = "";
				if(email != null && !email.trim().isEmpty()) email = URLDecoder.decode(email, "UTF-8");
				else email = "";
				if(fax != null && !fax.trim().isEmpty()) fax = URLDecoder.decode(fax, "UTF-8");
				else fax = "";
	
				//5. Proceso
				Paciente p = new Paciente();
				p.setIdInstitucion(idInstitucion);
				p.setIdTipoPaciente(idTipoPaciente);
				p.setNombre(nombre);
				p.setApepat(apepat);
				p.setApemat(apemat);
				p.setDni(dni);
				p.setFecnac(fecnac);
				p.setSexo(sexo);
				p.setIdDistrito(idDistrito);
				p.setDireccion(direccion);
				p.setTelefono(telefono);
				p.setCelular(celular);
				p.setHistClinica(histClinica);
				p.setEmail(email);
				p.setFax(fax);
				pacienteService.create(p);
				Paciente paciente = pacienteService.obtenerPacientePorID(p.getIdPaciente());
				
				//6. Respuesta
				mapper.writeValue(response.getOutputStream(), paciente);
				miLog.info("Inserto del nuevo Paciente["+p.toString()+"]"+usuarioLogin.getLogUser());
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
