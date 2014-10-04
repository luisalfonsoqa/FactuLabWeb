package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Atencion;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AtencionPendiente;
import com.factulab.dao.form.AtencionPendienteDetalle;
import com.factulab.dao.form.HojaTrabajoForm;
import com.factulab.dao.form.ReporteAnalisisDetalle;
import com.factulab.dao.util.ConectaDB;
import com.factulab.dao.util.DAOConstante;
import com.factulab.dao.util.DAOUtil;

public class AtencionDAO {
	Logger miLog = Logger.getLogger(AtencionDAO.class);
	/**
	 * Crear Atencion
	 * @param atencion
	 * @throws DAOException
	 */
	public void create(Atencion atencion) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "EXEC FacturaLabSQL.dbo.InsertarAtencion @idInstitucion = ?, @idMedico = ?,"
					+ " @idPaciente = ?, @idUsuario = ?, @totalSinDescuento = ?, @descuento = ?, @total = ?, @idSede = ? ";
			ps = cn.prepareStatement(query);
			ps.setInt(1, atencion.getIdInstitucion());
			ps.setInt(2, atencion.getIdMedico());
			ps.setInt(3, atencion.getIdPaciente());
			ps.setInt(4, atencion.getIdUsuario());
			ps.setBigDecimal(5, atencion.getSubtotal());
			ps.setBigDecimal(6, atencion.getDescuento());
			ps.setBigDecimal(7, atencion.getTotal());
			ps.setInt(8, atencion.getIdFranquicia());
			rs = ps.executeQuery();
			if (rs.next()) {
				atencion.setIdAtencion(rs.getInt("idAtencion"));
				atencion.setCodOmega(rs.getString("codigoOmega"));
			}
			ps.close();
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);  
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Facturar Atencion por ID
	 * @param idAtencion
	 * @throws DAOException
	 */
	public void facturarAtencion(Integer idAtencion) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "update atencion set facturado = 1 where idAtencion = ? ";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idAtencion);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);  
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Obtener HT
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public HojaTrabajoForm obtenerHT(long id) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HojaTrabajoForm bean = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "select a.codOmega, a.idAtencion, (p.apepat + ' '+ p.apemat + ' '+p.nombre) 'nombrePaciente', p.fecnac from atencion a, paciente p where a.paciente = p.idPaciente and a.idAtencion = ?";
			ps = cn.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new HojaTrabajoForm();
				bean.setCodigoOmega(rs.getString("codOmega"));
				bean.setNombrePaciente(rs.getString("nombrePaciente"));
				bean.setIdAtencion(rs.getInt("idAtencion"));
				Date fecnac = rs.getDate("fecnac");
				bean.setEdad(DAOUtil.getEdad(fecnac));
			}
			ps.close();
			return bean;
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Facturar atenciones por IDS
	 * @param idInstitucion
	 * @param ids
	 * @throws DAOException
	 */
	public void facturarAtenciones(Integer idInstitucion, String ids) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "update atencion set facturado = 1 where institucion = ?  and idAtencion in ("+ids+")";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idInstitucion);
			ps.executeUpdate();
			ps.close();
			cn.close();
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Listar atenciones pendientes por institucion
	 * @param idInstitucion
	 * @param sede
	 * @param fechaIni
	 * @param fechaFin
	 * @return
	 * @throws DAOException
	 */
	public List<AtencionPendiente> findPendientesPorInstitucion(Integer idInstitucion, Integer sede, Date fechaIni, Date fechaFin) throws DAOException{
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat formato = new SimpleDateFormat(DAOConstante.BD_FORMATO_FECHA_SELECT);
		List<AtencionPendiente> lAtencion = new ArrayList<AtencionPendiente>();
		AtencionPendiente a = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			//String query = "select a.*, p.nombre, p.apepat, p.apemat, i.nombre 'nombreInstitucion' from Atencion a, Paciente p, Institucion i where a.facturado = 0 and p.idPaciente= a.paciente and i.idInstitucion = a.institucion and a.institucion = ? and a.idFranquicia = ? ";
			query = "select a.*, p.nombre, p.apepat, p.apemat from Atencion a, Paciente p where a.facturado = 0 and p.idPaciente= a.paciente and a.institucion = ? and a.idFranquicia = ? ";
			if(fechaIni != null && fechaFin !=null){
				//query += " and Convert(varchar(10),CONVERT(date,fecha,106),103) between '"+formato.format(fechaIni)+"' and '"+formato.format(fechaFin)+"' ";
				query += " and CONVERT(DATE, a.fecha, 111) between CONVERT(DATE, '"+formato.format(fechaIni)+"', 111)  and CONVERT(DATE, '"+formato.format(fechaFin)+"', 111)";
			}
			
			ps = cn.prepareStatement(query);
			ps.setInt(1, idInstitucion);
			ps.setInt(2, sede);
			rs = ps.executeQuery();
			while (rs.next()) {
				a = new AtencionPendiente();
				a.setCodOmega(rs.getString("codOmega"));
				a.setDescuento(rs.getBigDecimal("descuento"));
				a.setEstado(rs.getString("estado"));
				a.setFacturado(rs.getInt("facturado"));
				a.setFecha(rs.getDate("fecha"));
				
				a.setIdAtencion(rs.getInt("idAtencion"));
				a.setIdFranquicia(rs.getInt("idFranquicia"));
				a.setIdInstitucion(rs.getInt("institucion"));
				a.setIdMedico(rs.getInt("medico"));
				a.setIdPaciente(rs.getInt("paciente"));
				
				a.setIdUsuario(rs.getInt("usuario"));
				a.setSubtotal(rs.getBigDecimal("subtotal"));
				a.setTotal(rs.getBigDecimal("total"));
				//a.setNombreInstitucion(rs.getString("nombreInstitucion"));
				a.setNombrePaciente(rs.getString("apepat") +" "+ rs.getString("apemat") +" "+ rs.getString("nombre"));
				lAtencion.add(a);
			}
			rs.close();
			ps.close();
			cn.close();
			return lAtencion;
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Detalle de atenciones sin facturar por institucion
	 * @param ids
	 * @param idInstitucion
	 * @return
	 * @throws DAOException
	 */
	public List<AtencionPendienteDetalle> findDetallePendientesPorInstitucion(String ids, Integer idInstitucion) throws DAOException{
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AtencionPendienteDetalle> lAtencion = new ArrayList<AtencionPendienteDetalle>();
		AtencionPendienteDetalle a = null;
		String query = null;
		
		try {
			cn = new ConectaDB().getConexion();
			query = "select p.apepat, p.apemat, p.nombre 'nombrePaciente', d.*, n.nombre 'nombreAnalisis', a.fecha, a.codOmega, a.descuento from Atencion a, Paciente p, atenciondetalle d, Analisis n "+
							"where a.facturado = 0 and p.idPaciente= a.paciente and d.idAtencion = a.idAtencion and n.idAnalisis = d.idAnalisis "+
							"and a.institucion = ? and a.idAtencion in ("+ids+") ";
			ps = cn.prepareStatement(query);
			ps.setInt(1, idInstitucion);
			rs = ps.executeQuery();
			while (rs.next()) {
				a = new AtencionPendienteDetalle();
				a.setNombrePaciente(rs.getString("apepat") +" "+ rs.getString("apemat") + " "+ rs.getString("nombrePaciente"));
				a.setIdAtencion(rs.getInt("idAtencion"));
				a.setIdAnalisis(rs.getInt("idAnalisis"));
				a.setCantidad(rs.getInt("cantidad"));
				a.setMonto(rs.getBigDecimal("monto"));
				a.setPrecio(rs.getBigDecimal("precio"));
				a.setPrecioUnitario(rs.getBigDecimal("precioUnitario"));
				a.setNombreAnalisis(rs.getString("nombreAnalisis"));
				a.setFecha(rs.getDate("fecha"));
				a.setCodOmega(rs.getString("codOmega"));
				a.setPorcentajeDescuento(rs.getBigDecimal("descuento"));
				lAtencion.add(a);
			}
			rs.close();
			ps.close();
			cn.close();
			return lAtencion;
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
	/**
	 * Buscar Atenciones Tomadas por usuario
	 * @param idUsuario
	 * @param fechaIni
	 * @param fechaFin
	 * @param horaIni
	 * @param horaFin
	 * @return
	 * @throws DAOException
	 */
	public List<ReporteAnalisisDetalle> findAtencionesTomadas(Integer idUsuario, Date fechaIni, Date fechaFin, Integer horaIni, Integer horaFin) throws DAOException{
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReporteAnalisisDetalle> lDetalle = new ArrayList<ReporteAnalisisDetalle>();
		ReporteAnalisisDetalle a = null;
		SimpleDateFormat formato = new SimpleDateFormat(DAOConstante.BD_FORMATO_FECHA_SELECT);
		String query = null;
		
		try {
			cn = new ConectaDB().getConexion();
			query = "select d.idAtencion, a.facturado, (CONVERT(CHAR(11),a.fecha,101) + CONVERT(CHAR( 5),a.fecha,114)) 'fecha', p.apepat + ' ' + p.apemat + ' '+ p.nombre 'nombrePaciente', m.apepat + ' '+ m.apemat + ' ' +  m.nombre 'nombreMedico' ,l.nombre 'nombreAnalisis', a.codOmega, d.cantidad, d.monto "
					+ "from atenciondetalle d, atencion a, paciente p, medico m, analisis l WHERE p.idPaciente = a.paciente and d.idAtencion = a.idAtencion and m.idMedico = a.medico and l.idAnalisis = d.idAnalisis ";
			if(idUsuario != null) query += "and a.usuario = ? "
			+ "and CONVERT(DATE, a.fecha, 111) between CONVERT(DATE, '"+formato.format(fechaIni)+"', 111) and CONVERT(DATE, '"+formato.format(fechaFin)+"', 111) ";
			if(horaIni != null && horaFin != null) 
				query += "and CONVERT(INT,CONVERT(CHAR(2),a.fecha,114)) between "+horaIni+" and "+horaFin;
			query += " order by fecha";
			
			ps = cn.prepareStatement(query);
			if(idUsuario != null) ps.setInt(1, idUsuario);
			rs = ps.executeQuery();
			while (rs.next()) {
				a = new ReporteAnalisisDetalle();
				a.setIdAtencion(rs.getInt("idAtencion"));
				a.setFecha(rs.getString("fecha"));
				a.setNombrePaciente(rs.getString("nombrePaciente").toUpperCase());
				a.setNombreMedico(rs.getString("nombreMedico").toUpperCase());
				a.setNombreAnalisis(rs.getString("nombreAnalisis").toUpperCase());
				a.setCantidad(rs.getInt("cantidad"));
				a.setCodigoOmega(rs.getString("codOmega"));
				a.setMonto(rs.getBigDecimal("monto"));
				
				Integer int_facturado = rs.getInt("facturado");
				if(int_facturado == 0) a.setFacturado(false);
				else if(int_facturado == 1) a.setFacturado(true);
				else { 
					rs.close();
					ps.close();
					cn.close();
					throw new FactulabException("Indicador de facturacion erroneo.");
				}
				lDetalle.add(a);
			}
			rs.close();
			ps.close();
			cn.close();
			return lDetalle;
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
}

