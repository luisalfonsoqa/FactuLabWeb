package com.factulab.service;

import java.util.List;

import com.factulab.dao.TarifaDAO;
import com.factulab.dao.bean.Tarifa;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;

public class TarifaService {
	TarifaDAO tarifaDAO = new TarifaDAO();
	/**
	 * Tarifa por ID
	 * @param id
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public Tarifa obtenerTarifaPorID(Integer id) throws FactulabException, DAOException {
		Tarifa tarifa = tarifaDAO.find(id);
		return tarifa;
	}
	/**
	 * Listar Tarifas (id, nombre)
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Tarifa> listarTarifasHead() throws DAOException, FactulabException {
		return tarifaDAO.listHead();
	}
}
