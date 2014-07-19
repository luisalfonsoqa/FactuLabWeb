package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.TipoAtencion;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;

public class TipoAtencionDAO {
	Logger miLog = Logger.getLogger(TipoAtencionDAO.class);
	/**
	 * Lista Todos los Tipos de Atención
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<TipoAtencion> obtenerTipoAtenciones() throws DAOException, FactulabException{
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<TipoAtencion> lBean = new ArrayList<TipoAtencion>();
			TipoAtencion bean = null;
			cn = new ConectaDB().getConexion();
			query  = "select * from tipoatencion";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TipoAtencion(rs.getInt("idTipoAtencion"), rs.getString("nombre"));
				lBean.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null || lBean.isEmpty()) throw new FactulabException("No se encontraron los Tipo de Atenciones.");
			return lBean;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
}
