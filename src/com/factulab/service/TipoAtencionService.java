package com.factulab.service;

import java.util.List;

import com.factulab.dao.TipoAtencionDAO;
import com.factulab.dao.bean.TipoAtencion;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;

public class TipoAtencionService {
	TipoAtencionDAO tipoAtencionDAO = new TipoAtencionDAO();
	public List<TipoAtencion> obtenerTipoAtenciones() throws FactulabException, DAOException{
		List<TipoAtencion> lista = tipoAtencionDAO.obtenerTipoAtenciones();
		return lista;
	}
}
