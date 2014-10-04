package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Analisis;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;

public class AnalisisDAO {
	Logger miLog = Logger.getLogger(AnalisisDAO.class);
	
	/**
	 * Buscar Analisis por ID 
	 * @param id
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Analisis find(Integer id) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Analisis bean = null;
			cn = new ConectaDB().getConexion();
			query = "select a.*, s.nombre 'nombreSeccion' from analisis a, seccion s where s.idSeccion = a.seccion and a.idanalisis= ? and a.activo = 1";
			ps = cn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Analisis();
				bean.setIdAnalisis(rs.getInt("idAnalisis"));
				bean.setNombre(rs.getString("nombre"));
				bean.setSeccion(rs.getInt("seccion"));
				bean.setPrecio(rs.getBigDecimal("precio"));
				bean.setIdAnalisisOmega(rs.getString("idAnalisisOmega"));
				bean.setAbreviatura(rs.getString("abreviatura"));
				bean.setUnidadMedida(rs.getString("unidadMedida"));
				bean.setNombreSeccion(rs.getString("nombreSeccion"));
				bean.setActivo(rs.getInt("activo"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro el analisis con ID ."+id);
			return bean;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);  
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Buscar analisis por criterio
	 * @param criterio
	 * @param texto
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Analisis> findPorCriterio(String criterio, String texto) throws DAOException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Analisis> list = new ArrayList<Analisis>();
			Analisis bean = null;
			cn = new ConectaDB().getConexion();
			query = "select * from analisis where LOWER("+criterio+") like '%"+texto.toLowerCase()+"%' and activo = 1 ";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Analisis();
				bean.setIdAnalisis(rs.getInt("idAnalisis"));
				bean.setNombre(rs.getString("nombre"));
				bean.setSeccion(rs.getInt("seccion"));
				bean.setPrecio(rs.getBigDecimal("precio"));
				bean.setIdAnalisisOmega(rs.getString("idAnalisisOmega"));
				bean.setAbreviatura(rs.getString("abreviatura"));
				bean.setUnidadMedida(rs.getString("unidadMedida"));
				bean.setNombreSeccion(rs.getString("seccion"));
				bean.setActivo(rs.getInt("activo"));
				list.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			return list;
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);  
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
}
