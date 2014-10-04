package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Especialidad;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;

public class EspecialidadDAO {
	Logger miLog = Logger.getLogger(EspecialidadDAO.class);
	
	/**
	 * Lista las especialidades
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Especialidad> list() throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Especialidad> list = new ArrayList<Especialidad>();
			Especialidad bean = null;
			cn = new ConectaDB().getConexion();
			query = "select i.* from Especialidad i order by nombre";
			ps = cn.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Especialidad();
				bean.setIdEspecialidad(rs.getInt("idEspecialidad"));
				bean.setNombre(rs.getString("nombre"));
				list.add(bean);
			}

			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro Especialidades.");
			return list;
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

