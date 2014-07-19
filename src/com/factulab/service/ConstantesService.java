package com.factulab.service;

import org.apache.log4j.Logger;

import com.factulab.dao.ConstantesBDDAO;
import com.factulab.dao.bean.ConstantesBD;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;

public class ConstantesService {
	Logger miLog = Logger.getLogger(ConstantesService.class);
	
	ConstantesBDDAO constantesBDDAO = new ConstantesBDDAO();
	public ConstantesBD cargarConstantesBD() throws FactulabException, DAOException{
		try{
			ConstantesBD constantesBD = new ConstantesBD();
			constantesBD.setlDepartamento(constantesBDDAO.listDepartamento());
			constantesBD.setlTipoPaciente(constantesBDDAO.listTipoPaciente());
			constantesBDDAO.obtenerParametros(constantesBD);
//			miLog.info("Se cargaron los parametros de la BD. ConstantesBD["+constantesBD.toString()+"]");
			return constantesBD;
		} catch(DAOException e){
			throw new DAOException(e);
		} catch(FactulabException e){
			throw new FactulabException(e.getMessage(),e); 
		}
	}
}
