package com.factulab.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.PacienteDAO;
import com.factulab.dao.bean.Paciente;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;

public class PacienteService {
	Logger miLog = Logger.getLogger(PacienteService.class);
	PacienteDAO pacienteDAO = new PacienteDAO();
	/**
	 * Buscar paciente por ID
	 * @param idPaciente
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public Paciente obtenerPacientePorID(Integer idPaciente) throws FactulabException, DAOException{
		Paciente paciente = pacienteDAO.find(idPaciente);
		//paciente.setEdad(obtenerEdad(paciente.getFecnac()));
		return paciente;
	}
	/**
	 * Buscar paciente por criterio
	 * @param criterio
	 * @param texto
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public List<Paciente> obtenerPorCriterio(String criterio, String texto) throws FactulabException, DAOException{
		List<Paciente> list = pacienteDAO.findPorCriterio(criterio, texto);
		return list;
	}
	/**
	 * Crear paciente
	 * @param p
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public void create(Paciente p) throws FactulabException, DAOException{
		pacienteDAO.create(p);
	}
}
