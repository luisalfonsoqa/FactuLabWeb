package com.factulab.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Analisis;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.service.AnalisisService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AjaxAnalisisServlet
 */
public class AjaxAnalisisServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AjaxAnalisisServlet.class);
	
	private static final long serialVersionUID = 1L;
	AnalisisService analisisService = new AnalisisService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxAnalisisServlet() {
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
		ObjectMapper mapper = new ObjectMapper();
		String txtError = null;
		try {
			String criterio = request.getParameter("criterio");
			String texto = request.getParameter("texto");
			List<Analisis> lAnalisis = new ArrayList<>();
			if(criterio.equals("nombre")){
				lAnalisis = analisisService.obtenerPorCriterio(criterio, texto);
			} else {
				throw new FactulabException("Criterio no valido ["+criterio+"]");
			}
			mapper.writeValue(response.getOutputStream(), lAnalisis);
		} catch(DAOException e){
			miLog.error(e.getMessage(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage());
			txtError = e.getMessage();
		} finally {
			if(txtError != null){
				mapper.writeValue(response.getOutputStream(), new Error(txtError));
			}
		}
	}
}
