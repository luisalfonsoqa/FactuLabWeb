package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Institucion;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;
import com.factulab.dao.util.DAOConstante;

public class InstitucionDAO {
	Logger miLog = Logger.getLogger(InstitucionDAO.class);
	
	/**
	 * Busca Institucion por ID
	 * @param idInstitucion
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Institucion find(Integer idInstitucion) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Institucion bean = null;
			cn = new ConectaDB().getConexion();
			query = "select i.* from Institucion i where i.idInstitucion = ? ";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idInstitucion);

			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Institucion();
				
				bean.setIdTarifa(rs.getInt("tarifa"));
				bean.setNombre(rs.getString("nombre"));
				bean.setIdInstitucion(rs.getInt("idInstitucion"));
				bean.setFacMensual(rs.getInt("facMensual"));

				bean.setDireccion(rs.getString("direccion"));
				bean.setRuc(rs.getString("ruc"));
				bean.setEmailContacto(rs.getString("emailcontacto"));
				bean.setNombreContacto(rs.getString("nombrecontacto"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro el Institucion con ID "+idInstitucion);
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
	
//	public List<Institucion> findInstitucionConAtencionesPendientes(Integer idFranquicia, Date fechaIni, Date fechaFin)
//			throws DAOException, FactulabException {
	/**
	 * Lista Instituciones con facturas pendientes
	 * @param idFranquicia
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Institucion> findInstitucionConAtencionesPendientes(Integer idFranquicia) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		List<Institucion> lInstitucion = new ArrayList<Institucion>();
		Institucion a = null;
		ResultSet rs = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "select distinct a.institucion, i.nombre from Atencion a, institucion i where a.facturado = 0  and i.idInstitucion = a.institucion ";
			if(idFranquicia != null) query += "and idFranquicia = ? ";
			//if(fechaIni != null && fechaFin !=null) query += "fecha between between '"+formato.format(fechaIni)+"' and '"+formato.format(fechaFin)+"' ";
			query += " order by nombre";
			ps = cn.prepareStatement(query);
			if(idFranquicia != null) ps.setInt(1, idFranquicia);
			rs = ps.executeQuery();
			while (rs.next()) {
				a = new Institucion();
				a.setIdInstitucion(rs.getInt("institucion"));
				a.setNombre(rs.getString("nombre"));
				lInstitucion.add(a);
			}
			rs.close();
			ps.close();
			cn.close();
			return lInstitucion;
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
	 * Lista Instituciones (id, nombre)
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Institucion> listHead() throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Institucion> list = new ArrayList<Institucion>();
			Institucion bean = null;
			cn = new ConectaDB().getConexion();
			query = "select i.* from Institucion i order by nombre";
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Institucion();
				bean.setIdInstitucion(rs.getInt("idInstitucion"));
				bean.setNombre(rs.getString("nombre"));
				list.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro Instituciones");
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
	/**
	 * Crea Institucion
	 * @param i
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public void create(Institucion i) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		PreparedStatement ps0 = null;
		ResultSet rs = null;
		ResultSet rs0 = null;
		try {
			cn = new ConectaDB().getConexion();
			ps0 = cn.prepareStatement("select 1 from institucion where lower(nombre) like ? or ruc = ?");
			ps0.setString(1, i.getNombre().trim());
			ps0.setString(2, i.getRuc().trim());
			rs0 = ps0.executeQuery();
			if(rs0.next()) {
				String msgError = "Error al grabar la institución. El nombre ["+i.getNombre()+"] o RUC["+i.getRuc()+"] existen.";
				ps0.close();
				throw new FactulabException(msgError);
			}
			
			rs0.close();
			ps0.close();
			
			query = "EXEC FacturaLabSQL.dbo.InsertarInstitucion @idTarifa = ?, @nombre = ? , @direccion = ?, @idFacMensual = ?, @ruc = ?, "
					+ "@emailcontacto = ?, @nombrecontacto = ?";
			ps = cn.prepareStatement(query);
		
			ps.setInt(1, i.getIdTarifa());
			ps.setString(2, i.getNombre().trim());
			ps.setString(3, i.getDireccion());
			ps.setInt(4, i.getFacMensual());
			ps.setString(5, i.getRuc().trim());
			
			ps.setString(6, i.getEmailContacto());
			ps.setString(7, i.getNombreContacto());
			rs = ps.executeQuery();
			if (rs.next()) {
				i.setIdInstitucion(rs.getInt("idInstitucion"));
			}
			rs.close();
			ps.close();
			cn.close();
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (rs0 != null) { try { rs0.close(); } catch (SQLException e) { } }
			if (ps0 != null) { try { ps0.close(); } catch (SQLException e) { } }
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Actualiza datos RUC, direccion de una institucion.
	 * @param idInstitucion
	 * @param ruc
	 * @param direccion
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public void updateDatosFactura(int idInstitucion, String ruc, String direccion) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "update institucion ";
			
			if(ruc != null && direccion != null) query += "set ruc = ?, direccion = ? where idInstitucion = ? ";
			else if(direccion != null) query += "set direccion = ? where idInstitucion = ? ";
			else if(ruc != null) query += "set ruc = ? where idInstitucion = ? ";
		
			ps = cn.prepareStatement(query);
			
			if(ruc != null && direccion != null) {
				ps.setString(1, ruc);
				ps.setString(2, direccion);
				ps.setInt(3, idInstitucion);
			}
			else if(direccion != null) {
				ps.setString(1, direccion);
				ps.setInt(2, idInstitucion);
			}
			else if(ruc != null) {
				ps.setString(1, ruc);
				ps.setInt(2, idInstitucion);
			}
			ps.executeUpdate();
			ps.close();
			cn.close();
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	
	/**
	 * Busca Institucion por criterio 
	 * @param criterio
	 * @param texto
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Institucion> findPorCriterio(String criterio, String texto, boolean all) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Institucion> list = new ArrayList<Institucion>();
			Institucion bean = null;
			cn = new ConectaDB().getConexion();
			query = "select TOP "+DAOConstante.BD_ROWMAX_MANT+" * from institucion where lower("+criterio+") like '%"+texto.toLowerCase()+"%' order by "+criterio;
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Institucion();
				int id = rs.getInt("idInstitucion");
				if(id != DAOConstante.BD_INSTITUCION_PARTICULAR){
					bean.setIdInstitucion(rs.getInt("idInstitucion"));
					bean.setNombre(rs.getString("nombre"));
					bean.setRuc(rs.getString("ruc"));
					bean.setDireccion(rs.getString("direccion"));
					list.add(bean);
				}
			}
			rs.close();
			ps.close();
			cn.close();
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


