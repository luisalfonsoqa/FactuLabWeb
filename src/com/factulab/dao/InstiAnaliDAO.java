package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.InstiAnali;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;

public class InstiAnaliDAO {
	Logger miLog = Logger.getLogger(InstiAnaliDAO.class);
	/*
	public InstiAnali find(Integer idInstitucion, Integer idAnalisis)
			throws DAOException, FactulabException {
		try {
			InstiAnali bean = null;
			Connection cn = new ConectaDB().getConexion();
			PreparedStatement ps;

			ps = cn.prepareStatement("select i.* from insti_anali i where i.idInstitucion = ? and i.idAnalisis = ?");
			ps.setInt(1, idInstitucion);
			ps.setInt(2, idAnalisis);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new InstiAnali();
				bean.setIdAnalisis(rs.getInt("idAnalisis"));
				bean.setFactor(rs.getDouble("monto"));
				bean.setIdInstitucion(rs.getInt("idInstitucion"));
				bean.setTipoMonto(rs.getInt("tipoMonto"));
			}
			ps.close();
			if(bean == null) throw new FactulabException("No se encontro el InstiAnali con ID de Institucion "+idInstitucion);
			return bean;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		}
	}
	*/
	/**
	 * Listar Analisis con descuento por instituciones
	 * @param idInstitucion
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<InstiAnali> listAnalisisConDescuentoPorInstitucion(Integer idInstitucion) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<InstiAnali> lBean = new ArrayList<InstiAnali>();
			InstiAnali bean = new InstiAnali();
			cn = new ConectaDB().getConexion();
			query = "select i.* from insti_anali i where i.idInstitucion = ? ";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idInstitucion);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new InstiAnali();
				bean.setIdAnalisis(rs.getInt("idAnalisis"));
				bean.setFactor(rs.getBigDecimal("monto"));
				bean.setIdInstitucion(rs.getInt("idInstitucion"));
				bean.setTipoMonto(rs.getInt("tipoMonto"));
				lBean.add(bean);
			}

			rs.close();
			ps.close();
			cn.close();
			//if(lBean == null || lBean.isEmpty()) System.out.println("No se encontro el InstiAnali con ID de Institucion "+idInstitucion);
			return lBean;
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

