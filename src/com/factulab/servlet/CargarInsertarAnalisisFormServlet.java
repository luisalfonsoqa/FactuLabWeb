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

import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;
import com.factulab.service.AnalisisService;
import com.factulab.service.InstitucionService;
import com.factulab.service.MedicoService;
import com.factulab.service.PacienteService;
import com.factulab.service.TarifaService;
import com.factulab.servlet.util.ServletConstante;

/**
 * Servlet implementation class ResumenAtencionServlet
 */
public class CargarInsertarAnalisisFormServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(CargarInsertarAnalisisFormServlet.class);
	
	private static final long serialVersionUID = 1L;
	AnalisisService analisisService = new AnalisisService();
	PacienteService pacienteService = new PacienteService();
	InstitucionService institucionService = new InstitucionService();
	MedicoService medicoService = new MedicoService();
	TarifaService tarifaService = new TarifaService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CargarInsertarAnalisisFormServlet() {
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

	/**
	 * Carga Pantalla de Ingreso de Analisis.
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
			 /***************************************************
		     *                	VALIDAR
		     ***************************************************/
			usuarioLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
			if(usuarioLogin == null) {
				miLog.error("Usuario no logeado.");
				response.sendRedirect("index.jsp");
			}
			
			String txt_paciente = request.getParameter("idPaciente");
			String txt_medico = request.getParameter("idMedico");
			String txt_conIGV = request.getParameter("igv");
			String txt_institucion = request.getParameter("idInstitucion");
			
			Integer idPaciente;
			Integer idMedico; 
			boolean conIGV;
			Integer idInstitucion = null;
			try{ idPaciente = Integer.parseInt(txt_paciente);
			} catch(Exception e) { throw new FactulabException("IdPaciente["+txt_paciente+"] incorrecto.");}
			try{ idMedico = Integer.parseInt(txt_medico);
			} catch(Exception e) { throw new FactulabException("idMedico["+txt_medico+"] incorrecto.");}			
			if(txt_conIGV != null  && (txt_conIGV.equals("1") || txt_conIGV.equals("0"))){
			} else { throw new FactulabException("Tipo de Pago ["+txt_conIGV+"] incorrecto.");}	
			conIGV = txt_conIGV.equals("1");

			if(conIGV){
				try{ idInstitucion = Integer.parseInt(txt_institucion);
				} catch(Exception e) { throw new FactulabException("idInstitucion["+txt_institucion+"] incorrecto.");}
			} 

			/***************************************************
		     *                	INICIAR
		     ***************************************************/
			AtencionForm atencionForm = new AtencionForm();
			atencionForm.setPaciente(pacienteService.obtenerPacientePorID(idPaciente));
			atencionForm.setMedico(medicoService.obtenerMedicoPorID(idMedico));
			atencionForm.setlAnalisis(new ArrayList<AnalisisForm>());
			atencionForm.setPorcentajeDescuento(BigDecimal.ZERO);
			atencionForm.setConIGV(conIGV);
			
			//Datos de la Institucion
			if(!conIGV){
				idInstitucion = atencionForm.getPaciente().getIdInstitucion();
			}
			
			atencionForm.setInstitucion(institucionService.obtenerInstitucionPorID(idInstitucion));
			atencionForm.setlInstiAnali(institucionService.obtenerAnalisisConDescuento(idInstitucion));
			atencionForm.setTarifa(tarifaService.obtenerTarifaPorID(atencionForm.getInstitucion().getIdTarifa()));
			
			request.getSession().setAttribute(ServletConstante.SESSION_ATENCION, atencionForm);
			rd = request.getRequestDispatcher("insertarAnalisisForm.jsp");
			rd.forward(request, response);
			miLog.info("Nueva Atencion. Paso2. Medico["+atencionForm.getMedico().getIdMedico()+"] Paciente["+atencionForm.getPaciente().getIdPaciente()+ "] IGV["+atencionForm.getConIGV()+"]" + usuarioLogin.getLogUser());
		} catch (FactulabException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser());
			txtError = e.getMessage();
		} catch (DAOException e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} catch (Exception e) {
			miLog.error(e.getMessage()+usuarioLogin.getLogUser(),e);
			txtError = e.getMessage();
		} finally{
			if(txtError != null){
				request.setAttribute(ServletConstante.REQUEST_ERROR, txtError);
				rd = request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}
		}
		
	}
}