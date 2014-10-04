package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.ConstantesBD;
import com.factulab.dao.bean.Departamento;
import com.factulab.dao.bean.TipoPaciente;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;
import com.factulab.dao.util.DAOConstante;

public class ConstantesBDDAO {
	Logger miLog = Logger.getLogger(ConstantesBDDAO.class);
	
	public List<Departamento> listDepartamento() throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Departamento bean = null;
			cn = new ConectaDB().getConexion();
			List<Departamento> listDepartamento = new ArrayList<Departamento>(); 
			query = "select * from departamento";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Departamento();
				bean.setIdDepartamento(rs.getInt("idDepartamento"));
				bean.setNombre(rs.getString("departamento"));
				listDepartamento.add(bean);
			}

			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro Departamentos.");
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
//	public List<Provincia> listProvincia() throws DAOException, FactulabException {
//		String query = "";
//		try {
//			Provincia bean = null;
//			Connection cn = new ConectaDB().getConexion();
//			PreparedStatement ps;
//			List<Provincia> listDepartamento = new ArrayList<Provincia>(); 
//			query = "select p.* from provincia p ";
//			ps = cn.prepareStatement(query);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				bean = new Provincia();
//				bean.setNombre(rs.getString("provincia"));
//				bean.setIdProvincia(rs.getInt("idProvincia"));
//				bean.setIdDepartamento(rs.getInt("departamento"));
//				listDepartamento.add(bean);
//			}
//			ps.close();
//			if(bean == null) throw new FactulabException("No se encontro Provincias.");
//			return listDepartamento;
//		} catch (FactulabException e) {
//			throw new FactulabException(e.getMessage(),e);  
//		} catch (Exception e) {
//			throw new DAOException(query,e.getMessage(),e); 
//		}
//	}
//	
//	public List<Distrito> listDistrito() throws DAOException, FactulabException {
//		try {
//			Distrito bean = null;
//			Connection cn = new ConectaDB().getConexion();
//			PreparedStatement ps;
//			List<Distrito> listDepartamento = new ArrayList<Distrito>(); 
//
//			ps = cn.prepareStatement("select p.* from distrito p");
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				bean = new Distrito();
//				bean.setNombre(rs.getString("distrito"));
//				bean.setIdProvincia(rs.getInt("provincia"));
//				bean.setIdDistrito(rs.getInt("idDistrito"));
//				listDepartamento.add(bean);
//			}
//			ps.close();
//			if(bean == null) throw new FactulabException("No se encontro Distritos");
//			return listDepartamento;
//		} catch (FactulabException e) {
//			miLog.error(e);
//			throw new FactulabException(e.getMessage(),e);  
//		} catch (Exception e) {
//			miLog.error(e);
//			throw new DAOException(e); 
//		}
//	}
	public List<TipoPaciente> listTipoPaciente() throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			TipoPaciente bean = null;
			cn = new ConectaDB().getConexion();
			List<TipoPaciente> list = new ArrayList<TipoPaciente>(); 
			query = "select * from tipoPaciente";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TipoPaciente();
				bean.setIdTipoPaciente(rs.getInt("idTipoPaciente"));
				bean.setDescripcion(rs.getString("descripcion"));
				list.add(bean);
			}
			if(bean == null) throw new FactulabException("No se encontro Tipo de Pacientes");
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
	
	public void obtenerParametros(ConstantesBD c) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "select idParametro, valParametro from parametro where idParametro like 'OmegaDirSalida' or idParametro like 'OmegaDirSalidaBkup'";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String param = rs.getString("idParametro");
				if(param.equals(DAOConstante.BD_TAG_DIRECTORIO_LOCAL)) c.setOmegaLocal(rs.getString("valParametro"));
				if(param.equals(DAOConstante.BD_TAG_DIRECTORIO_REMOTO)) c.setOmegaRemoto(rs.getString("valParametro"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(c == null || c.getOmegaLocal() ==null ||c.getOmegaRemoto() == null) throw new FactulabException("No se cargaron los Parametros["+c.toString()+"]");
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
