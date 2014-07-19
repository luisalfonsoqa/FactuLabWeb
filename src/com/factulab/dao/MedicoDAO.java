package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Medico;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;
import com.factulab.dao.util.DAOConstante;

public class MedicoDAO {
	Logger miLog = Logger.getLogger(MedicoDAO.class);
	/**
	 * Busca Medico por ID
	 * @param id
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Medico find(Integer id) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Medico bean = null;
			cn = new ConectaDB().getConexion();
			query = "select i.* from medico i where i.idmedico = ?";
			ps = cn.prepareStatement(query);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Medico();
				bean.setIdEspecialidad(rs.getInt("especialidad"));
				bean.setDireccion(rs.getString("direccion"));
				bean.setCmp(rs.getString("cmp"));
				bean.setApemat(rs.getString("apemat"));
				bean.setApepat(rs.getString("apepat"));
				bean.setNombre(rs.getString("nombre"));
				bean.setIdMedico(rs.getInt("idMedico"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro el medico con ID ."+id);
			return bean;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * Busca Medico por criterio
	 * @param criterio
	 * @param texto
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Medico> findPorCriterio(String criterio, String texto) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Medico> list = new ArrayList<Medico>();
			Medico bean = null;
			cn = new ConectaDB().getConexion();
			query = "select TOP "+DAOConstante.BD_ROWMAX_MANT+" * from medico where lower("+criterio+") like '%"+texto.toLowerCase()+"%' order by "+criterio;
			//System.out.println(query);
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Medico();
				bean.setIdEspecialidad(rs.getInt("especialidad"));
				bean.setDireccion(rs.getString("direccion"));
				bean.setCmp(rs.getString("cmp"));
				bean.setApemat(rs.getString("apemat"));
				bean.setApepat(rs.getString("apepat"));
				bean.setNombre(rs.getString("nombre"));
				bean.setIdMedico(rs.getInt("idMedico"));
				list.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			//if(list.isEmpty()) System.out.println("INFO: No se encontraron medicos con "+criterio+"["+texto+"]");
			return list;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * Crea Medico
	 * @param m
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public void create(Medico m) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "EXEC FacturaLabSQL.dbo.InsertarMedico @nombre = ?, @apepat = ? , @apemat = ?, @cmp = ?, "
					+ "@especialidad = ?, @direccion = ?";
			ps = cn.prepareStatement(query);
		
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getApepat());
			ps.setString(3, m.getApemat());
			ps.setString(4, m.getCmp());
			ps.setInt(5, m.getIdEspecialidad());
			ps.setString(6, m.getDireccion());
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setIdMedico(rs.getInt("idMedico"));
			}
			rs.close();
			ps.close();
			cn.close();
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}

}

