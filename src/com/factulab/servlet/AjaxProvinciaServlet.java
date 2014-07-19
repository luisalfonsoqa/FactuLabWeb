package com.factulab.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Provincia;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.exception.FactulabError;
import com.factulab.service.UbigeoService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AjaxProvinciaServlet
 */
public class AjaxProvinciaServlet extends HttpServlet {
	Logger miLog = Logger.getLogger(AjaxProvinciaServlet.class);
	
	private static final long serialVersionUID = 1L;
	UbigeoService ubigeoService = new UbigeoService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxProvinciaServlet() {
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
		try{
			String codigo = request.getParameter("codigo");
			Integer idDepartamento = null;
			try{
				idDepartamento = Integer.parseInt(codigo); 
			} catch(Exception ex){
				throw new FactulabException("IdDepartamento no ["+codigo+"]");
			}
			List<Provincia> list = ubigeoService.listProvincia(idDepartamento);
			mapper.writeValue(response.getOutputStream(), list);
		} catch(DAOException e){
			miLog.error(e.getMessage(),e);
			txtError = e.getMessage();
		} catch(FactulabException e){
			miLog.error(e.getMessage());
			txtError = e.getMessage();
		} finally {
			if(txtError != null){
				mapper.writeValue(response.getOutputStream(), new FactulabError(txtError));
			}
		}
	}
}
