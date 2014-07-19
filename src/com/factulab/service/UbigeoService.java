package com.factulab.service;

import java.util.List;

import com.factulab.dao.UbigeoDAO;
import com.factulab.dao.bean.Distrito;
import com.factulab.dao.bean.Provincia;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;

public class UbigeoService {
	UbigeoDAO ubigeoDAO = new UbigeoDAO();
	public List<Provincia> listProvincia(Integer idDepartamento) throws FactulabException, DAOException{
		List<Provincia> list = new UbigeoDAO().listProvincia(idDepartamento);
		return list;
	}
	public List<Distrito> listDistrito(Integer idProvincia) throws FactulabException, DAOException{
		List<Distrito> list = new UbigeoDAO().listDistrito(idProvincia);
		return list;
	}
}
