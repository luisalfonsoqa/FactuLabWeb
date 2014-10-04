package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Impresora;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;

/**
 * 
 * @author luis
 *
 */
public class UsuarioDAO {
	Logger miLog = Logger.getLogger(UsuarioDAO.class);
	
	/**
	 * Valida el Login del usuario
	 * @param txtUsuario
	 * @param txtClave
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Usuario validarUsuario(String txtUsuario, String txtClave) throws DAOException, FactulabException{
		String query = null; 
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Usuario bean = null;
			cn = new ConectaDB().getConexion();
			query = "select u.*, f.nombre 'nombreSede' from usuario u, franquicia f, impresora i where f.idfranquicia = u.idFranquicia and upper(u.usuario) like ? and u.clave like ?";
			ps = cn.prepareStatement(query);
			ps.setString(1, txtUsuario.toUpperCase());
			ps.setString(2, txtClave);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Usuario(rs.getInt("idusuario"), rs.getString("usuario"), rs.getString("nombre"), rs.getInt("tipoUsuario"), rs.getInt("idFranquicia"));
				bean.setNombreSede(rs.getString("nombreSede"));
			}
			
			ps.close();
			if(bean == null) throw new FactulabException("Usuario / Clave incorrecto.");
			if(bean.getIdTipoUsuario() == null) throw new FactulabException("Usuario no tiene asignado un Tipo de Usuario.");
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
	 * ObtenerImpresoras por Nombre
	 * @param txtDevice
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Impresora obtenerImpresoraPorNombre(String txtDevice) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Impresora bean = null;
			cn = new ConectaDB().getConexion();
			query = "select i.* from impresora i where upper(i.nombre) like ?";
			ps = cn.prepareStatement(query);
			ps.setString(1, "%"+txtDevice.toUpperCase()+"%");

			rs = ps.executeQuery();
			if (rs.next()) {
				/* bean = new Impresora(rs.getInt("idImpresora"), rs.getString("numeroTicket"), rs.getString("serieMaquina"), 
						rs.getInt("serieTicket"), rs.getString("nombre"), rs.getInt("activo");
				*/
				bean = new Impresora(rs.getInt("idImpresora"), rs.getString("serieMaquina"), 
				rs.getString("nombre"), rs.getInt("activo"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("La impresora "+txtDevice+" no esta regitrada en la base de datos.");
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
}