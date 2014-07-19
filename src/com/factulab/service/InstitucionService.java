package com.factulab.service;

import java.util.Date;
import java.util.List;

import com.factulab.dao.InstiAnaliDAO;
import com.factulab.dao.InstitucionDAO;
import com.factulab.dao.bean.InstiAnali;
import com.factulab.dao.bean.Institucion;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.service.exception.ServiceException;

public class InstitucionService {
	InstitucionDAO institucionDAO = new InstitucionDAO();
	InstiAnaliDAO instiAnaliDAO = new InstiAnaliDAO();
	
	/**
	 * Obtener Institución
	 * @param id
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public Institucion obtenerInstitucionPorID(Integer id) throws FactulabException, DAOException{
		Institucion institucion = institucionDAO.find(id);
		return institucion;
	}
	
	/**
	 * Listado de Analisis con descuento por institucion
	 * @param id
	 * @return
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public List<InstiAnali> obtenerAnalisisConDescuento(Integer id) throws FactulabException, DAOException{
		List<InstiAnali> instiAnali = instiAnaliDAO.listAnalisisConDescuentoPorInstitucion(id);
		return instiAnali;
	}
	
	/**
	 * TODO: ESTO DEBE SER CAMBIADO SI EXISTEN USUARIOS QUE PUEDAN FACTURAR MAS DE UNA FRANQUICIA
	 * @param uLogin
	 * @param fechaIni
	 * @param fechaFin
	 * @return
	 * @throws DAOException
	 * @throws ServiceException
	 * @throws FactulabException
	 */
	public List<Institucion> obtenerInstitucionesConAntecionesPendientes(Usuario uLogin, Date fechaIni, Date fechaFin) throws DAOException, FactulabException {
//		return institucionDAO.findInstitucionConAtencionesPendientes(uLogin.getIdSede(), fechaIni, fechaFin);
		return institucionDAO.findInstitucionConAtencionesPendientes(uLogin.getIdSede());
	}
	
	/**
	 * Lista Instituciones (id y nombre)
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Institucion> listarInstitucionesHead() throws DAOException, FactulabException {
		return institucionDAO.listHead();
	}
	
	/**
	 * Nueva institucion
	 * @param i
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public void create(Institucion i) throws FactulabException, DAOException{
		institucionDAO.create(i);
	}
	
	/**
	 * Actualiza los datos de la institucion necesarios para Facturar el ticket
	 * @param idInstitucion
	 * @param ruc
	 * @param direccion
	 * @throws FactulabException
	 * @throws DAOException
	 */
	public void updateDatosFactura(int idInstitucion, String ruc, String direccion) throws FactulabException, DAOException{
		institucionDAO.updateDatosFactura(idInstitucion, ruc, direccion);
	}
	
	public List<Institucion> obtenerPorCriterio(String criterio, String texto, boolean all) throws FactulabException, DAOException{
		List<Institucion> list = institucionDAO.findPorCriterio(criterio, texto, all);
		if(list.isEmpty()) throw new FactulabException("No se encontraron instituciones "+criterio+"["+texto+"]");
		return list;
	}
}
