package com.factulab.service;

import java.util.List;

import com.factulab.dao.EspecialidadDAO;
import com.factulab.dao.bean.Especialidad;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.service.exception.ServiceException;

public class EspecialidadService {
	EspecialidadDAO especialidadDAO = new EspecialidadDAO();
	/**
	 * Lista de todas las Especialidades
	 * @return
	 * @throws DAOException
	 * @throws ServiceException
	 * @throws FactulabException
	 */
	public List<Especialidad> listarEspecialidades() throws DAOException, FactulabException {
		return especialidadDAO.list();
	}
}
