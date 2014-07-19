package com.factulab.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.MedicoDAO;
import com.factulab.dao.bean.Medico;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.service.exception.ServiceException;

public class MedicoService {
	Logger miLog = Logger.getLogger(MedicoService.class);
	MedicoDAO medicoDAO = new MedicoDAO();
	/**
	 * Buscar Medico Por ID
	 * @param idMedico
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public Medico obtenerMedicoPorID(Integer idMedico) throws FactulabException, DAOException {
		return medicoDAO.find(idMedico);
	}
	/**
	 * Lista Medicos por criterio
	 * @param criterio
	 * @param texto
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public List<Medico> obtenerPorCriterio(String criterio, String texto) throws FactulabException, DAOException{
		List<Medico> list = medicoDAO.findPorCriterio(criterio, texto);
		return list;
	}
	/**
	 * Crea Medico
	 * @param m
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public void create(Medico m) throws FactulabException, DAOException{
		medicoDAO.create(m);
	}
}
