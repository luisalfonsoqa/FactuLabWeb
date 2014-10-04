package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Distrito;
import com.factulab.dao.bean.Provincia;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;


public class UbigeoDAO {
	Logger miLog = Logger.getLogger(UbigeoDAO.class);
	
	/**
	 * Listar provincias por Departamento
	 * @param idDepartamento
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Provincia> listProvincia(Integer idDepartamento) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Provincia bean = null;
			cn = new ConectaDB().getConexion();
			List<Provincia> listDepartamento = new ArrayList<Provincia>(); 
			query = "select p.* from provincia p where p.departamento = ?";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idDepartamento);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Provincia();
				bean.setNombre(rs.getString("provincia"));
				bean.setIdProvincia(rs.getInt("idProvincia"));
				bean.setIdDepartamento(rs.getInt("departamento"));
				listDepartamento.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro Provincias con IdDistrito["+idDepartamento+"]");
			return listDepartamento;
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
	 * Listar Distritos por Provincia
	 * @param idProvincia
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Distrito> listDistrito(Integer idProvincia) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Distrito bean = null;
			cn = new ConectaDB().getConexion();
			List<Distrito> listDepartamento = new ArrayList<Distrito>(); 
			query = "select p.* from distrito p where p.provincia = ?";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idProvincia);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Distrito();
				bean.setNombre(rs.getString("distrito"));
				bean.setIdProvincia(rs.getInt("provincia"));
				bean.setIdDistrito(rs.getInt("idDistrito"));
				listDepartamento.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro Distritos con IdProvincia["+idProvincia+"]");
			return listDepartamento;
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
}
