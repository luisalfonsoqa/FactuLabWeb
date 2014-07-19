package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Tarifa;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;

public class TarifaDAO {
	Logger miLog = Logger.getLogger(TarifaDAO.class);
	/**
	 * Buscar tarifa por ID
	 * @param id
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Tarifa find(Integer id) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Tarifa bean = null;
			cn = new ConectaDB().getConexion();
			query = "select i.* from tarifa i where i.idTarifa = ?";
			ps = cn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Tarifa();
				bean.setFactor(rs.getBigDecimal("factor"));
				bean.setIdTarifa(rs.getInt("idtarifa"));
				bean.setNombre(rs.getString("nombre"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro la Tarifa con ID ."+id);
			return bean;
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
	/**
	 * Listar Tarifas (id, nombre)
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Tarifa> listHead() throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Tarifa> list = new ArrayList<Tarifa>();
			Tarifa bean = null;
			cn = new ConectaDB().getConexion();
			query = "select t.* from tarifa t ";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Tarifa();
				bean.setIdTarifa(rs.getInt("idTarifa"));
				bean.setNombre(rs.getString("nombre"));
				list.add(bean);
			}
			ps.close();
			if(bean == null) throw new FactulabException("No se encontraron Tarifas");
			return list;
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
