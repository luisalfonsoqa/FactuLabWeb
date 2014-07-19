package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Paciente;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.ConectaDB;
import com.factulab.dao.util.DAOConstante;

public class PacienteDAO {
	Logger miLog = Logger.getLogger(PacienteDAO.class);
	/**
	 * Buscar Paciente por ID
	 * @param idPaciente
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public Paciente find(Integer idPaciente) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Paciente bean = null;
			cn = new ConectaDB().getConexion();

			query = "select p.*, t.descripcion, i.nombre 'institucion', ta.nombre 'tarifa', po.provincia, de.departamento, di.distrito "
					+ "from Paciente p, Tipopaciente t, institucion i, tarifa ta, departamento de, provincia po, distrito di "
					+ "where p.idPaciente = ? and t.idTipoPaciente = p.idtipopaciente and i.idInstitucion = p.idInstitucion and ta.idTarifa = i.tarifa "
					+ "and p.iddistrito = di.idDistrito and di.provincia = po.idProvincia and po.departamento = de.idDepartamento";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idPaciente);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Paciente();
				bean.setIdInstitucion(rs.getInt("idInstitucion"));
				bean.setIdTipoPaciente(rs.getInt("idtipopaciente"));
				bean.setIdPaciente(rs.getInt("idPaciente"));
				bean.setFax(rs.getString("fax"));
				bean.setEmail(rs.getString("email"));
				bean.setHistClinica(rs.getString("hist_clinica"));
				bean.setCelular(rs.getString("celular"));
				bean.setTelefono(rs.getString("telefono"));
				bean.setDireccion(rs.getString("direccion"));
				String sexo = rs.getString("sexo");
				if(sexo != null && !sexo.isEmpty()) 
				bean.setSexo(sexo.charAt(0));
				
				bean.setIdDistrito(rs.getInt("iddistrito"));
				bean.setFecnac(rs.getDate("fecnac"));
				bean.setDni(rs.getString("dni"));
				bean.setApemat(rs.getString("apemat"));
				bean.setApepat(rs.getString("apepat"));
				bean.setNombre(rs.getString("nombre"));
				bean.setNombreTipoPaciente(rs.getString("descripcion"));
				bean.setNombreInstitucion(rs.getString("institucion"));
				bean.setNombreTarifa(rs.getString("tarifa"));
				bean.setNombreDepartamento(rs.getString("departamento"));
				bean.setNombreProvincia(rs.getString("distrito"));
				bean.setNombreDistrito(rs.getString("provincia"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(bean == null) throw new FactulabException("No se encontro el paciente con ID ."+idPaciente);
			return bean;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			try {
				if (ps != null) ps.close();
				if (cn != null) cn.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * Crear Paciente
	 * @param p
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public void create(Paciente p) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		PreparedStatement ps0 = null;
		ResultSet rs0 = null;
		ResultSet rs = null;
		try {
			cn = new ConectaDB().getConexion();
			ps0 = cn.prepareStatement("select idpaciente from paciente where dni like ?");
			ps0.setString(1, p.getDni().trim());
			rs0 = ps0.executeQuery();
			if(rs0.next()) {
				String msgError = "Error al grabar el paciente. El número de DNI["+p.getDni()+"] pertenece al Paciente["+rs0.getInt("idpaciente")+"].";
				ps0.close();
				throw new FactulabException(msgError);
			}
			
			query = "EXEC FacturaLabSQL.dbo.InsertarPaciente @idInstitucion = ?, @idtipopaciente = ? , @nombre = ?, @apepat = ?, @apemat = ?, "
					+ "@dni = ?, @fecnac = ?, @sexo = ?, @idDistrito= ?, @direccion = ?, "
					+ "@telefono = ?, @celular = ?, @email = ?, @hist_clinica = ?, @fax = ?";
			ps = cn.prepareStatement(query);
		
			ps.setInt(1, p.getIdInstitucion());
			ps.setInt(2, p.getIdTipoPaciente());
			ps.setString(3, p.getNombre());
			ps.setString(4, p.getApepat());
			ps.setString(5, p.getApemat());
			
			ps.setString(6, p.getDni());
			DateFormat formatter = new SimpleDateFormat(DAOConstante.BD_FORMATO_FECHA_INSERT);
			ps.setString(7, formatter.format(p.getFecnac()));
			ps.setString(8, p.getSexo()+"");
			ps.setInt(9, p.getIdDistrito());
			ps.setString(10, p.getDireccion());
			
			ps.setString(11, p.getTelefono());
			ps.setString(12, p.getCelular());
			ps.setString(13, p.getEmail());
			ps.setString(14, p.getHistClinica());
			ps.setString(15, p.getFax());
			rs = ps.executeQuery();
			if (rs.next()) {
				p.setIdPaciente(rs.getInt("idPaciente"));
			}
			rs.close();
			rs0.close();
			ps.close();
			ps0.close();
			cn.close();
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			try {
				if (ps != null) ps.close();
				if (ps0 != null) ps0.close();
				if (rs != null) rs.close();
				if (rs0 != null) rs0.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * Buscar Paciente por Apepaterno, Apematerno, nombre, DNI
	 * @param criterio
	 * @param texto
	 * @return
	 * @throws DAOException
	 * @throws FactulabException
	 */
	public List<Paciente> findPorCriterio(String criterio, String texto) throws DAOException, FactulabException {
		String query = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Paciente> list = new ArrayList<Paciente>();
			Paciente bean = null;
			cn = new ConectaDB().getConexion();

			query = "select TOP "+DAOConstante.BD_ROWMAX_MANT+" p.*, t.descripcion, i.nombre 'institucion', ta.nombre 'tarifa', po.provincia, de.departamento, di.distrito "
					+ "from Paciente p, Tipopaciente t, institucion i, tarifa ta, departamento de, provincia po, distrito di "
					+ "where t.idTipoPaciente = p.idtipopaciente and i.idInstitucion = p.idInstitucion and ta.idTarifa = i.tarifa "
					+ "and p.iddistrito = di.idDistrito and di.provincia = po.idProvincia and po.departamento = de.idDepartamento ";
			/*
			if(criterio.equals("nombre")){
				query += "and (lower(p.nombre) like '%"+texto.toLowerCase()+"%' OR lower(p.apepat) like '%"+texto.toLowerCase()+"%' OR lower(p.apemat) like '%"+texto.toLowerCase()+"%')";
			} else{
				query += "and lower(p."+criterio+") like '%"+texto.toLowerCase()+"%'";
			}
			*/		
			query += "and lower(p."+criterio+") like '%"+texto.toLowerCase()+"%' order by "+criterio;
			ps = cn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new Paciente();
				bean.setIdInstitucion(rs.getInt("idInstitucion"));
				bean.setIdTipoPaciente(rs.getInt("idtipopaciente"));
				bean.setIdPaciente(rs.getInt("idPaciente"));
				bean.setFax(rs.getString("fax"));
				bean.setEmail(rs.getString("email"));
				bean.setHistClinica(rs.getString("hist_clinica"));
				bean.setCelular(rs.getString("celular"));
				bean.setTelefono(rs.getString("telefono"));
				bean.setDireccion(rs.getString("direccion"));
				String sexo = rs.getString("sexo");
				if(sexo != null && !sexo.isEmpty()) 
				bean.setSexo(sexo.charAt(0));
				
				bean.setIdDistrito(rs.getInt("iddistrito"));
				bean.setFecnac(rs.getDate("fecnac"));
				bean.setDni(rs.getString("dni"));
				bean.setApemat(rs.getString("apemat"));
				bean.setApepat(rs.getString("apepat"));
				bean.setNombre(rs.getString("nombre"));
				bean.setNombreTipoPaciente(rs.getString("descripcion"));
				bean.setNombreInstitucion(rs.getString("institucion"));
				bean.setNombreTarifa(rs.getString("tarifa"));
				bean.setNombreDepartamento(rs.getString("departamento"));
				bean.setNombreProvincia(rs.getString("distrito"));
				bean.setNombreDistrito(rs.getString("provincia"));
				list.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			//if(list.isEmpty()) System.out.println("INFO: No se encontraron pacientes con "+criterio+"["+texto+"]");
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
}
