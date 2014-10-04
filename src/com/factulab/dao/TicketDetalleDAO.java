package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.TicketDetalle;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.TicketDetalleForm;
import com.factulab.dao.form.TicketPendienteDetalleForm;
import com.factulab.dao.util.ConectaDB;

public class TicketDetalleDAO {
	Logger miLog = Logger.getLogger(TicketDetalleDAO.class);
	
	/**
	 * Crea Detalle de Tickets
	 * @param ticketDetalle
	 * @throws DAOException
	 */
	public void create(TicketDetalle ticketDetalle) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "EXEC FacturaLabSQL.dbo.InsertarTicketDetalle @numSerie = ?, @numItem = ? , @numTicket = ?, @cantidad = ?, "
					+ "@descripcion = ?, @importe = ?, @idAtencion = ?, @idTicket = ?, @idAnalisis = ? ";
			ps = cn.prepareStatement(query);
			
			ps.setInt(1, ticketDetalle.getNumSerie());
			ps.setInt(2, ticketDetalle.getNumItem());
			ps.setLong(3, ticketDetalle.getNumTicket());
			ps.setInt(4, ticketDetalle.getCantidad());
			ps.setString(5, ticketDetalle.getDescripcion());
			ps.setBigDecimal(6,  ticketDetalle.getImporte());
			ps.setInt(7, ticketDetalle.getIdAtencion());
			ps.setLong(8,ticketDetalle.getIdTicket());
			ps.setInt(9, ticketDetalle.getIdAnalisis());
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
	 * Busca el detalle del ticket (Cantidad, descripcion, importe
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<TicketDetalleForm> find(long id) throws DAOException, FactulabException{
		List<TicketDetalleForm> list = new ArrayList<TicketDetalleForm>();
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TicketDetalleForm t = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query ="SELECT cantidad, descripcion, importe FROM ticketdetalle where idticket = ?";
			ps = cn.prepareStatement(query); 
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				t = new TicketDetalleForm();
				t.setCantidad(rs.getInt("cantidad"));
				t.setNombre(rs.getString("descripcion"));
				t.setMonto(rs.getBigDecimal("importe"));
		        list.add(t);
			}
			rs.close();
			ps.close();
			cn.close();
			if(list.isEmpty()) throw new FactulabException("No se encontraron TicketDetalle para el ticket["+id+"]");
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
	 * Busca el detalle de tickets pendientes
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<TicketPendienteDetalleForm> findPendiente(long id) throws DAOException {
		List<TicketPendienteDetalleForm> list = new ArrayList<TicketPendienteDetalleForm>();
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TicketPendienteDetalleForm t = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query ="select d.idanalisis, d.cantidad, a.codOmega, d.descripcion, d.importe, (CONVERT(CHAR(11),a.fecha,101) + CONVERT(CHAR( 5),a.fecha,114)) 'fecha', "
					+ "(p.apepat +' '+ p.apemat + ' '+ p.nombre) 'nombrePaciente' from ticketdetalle d, atencion a, paciente p where a.idAtencion = d.idAtencion and p.idPaciente = a.paciente and idticket = ?";
			ps = cn.prepareStatement(query); 
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				t = new TicketPendienteDetalleForm();
				t.setCantidad(rs.getInt("cantidad"));
				t.setIdAnalisis(rs.getInt("idanalisis"));
				t.setCodOmega(rs.getString("codOmega"));
				t.setDescripcion(rs.getString("descripcion"));
				t.setImporte(rs.getBigDecimal("importe").toString());
				t.setFecha(rs.getString("fecha"));
				t.setNombrePaciente(rs.getString("nombrePaciente"));
		        list.add(t);
			}
			rs.close();
			ps.close();
			cn.close();
			if(list.isEmpty()) throw new FactulabException("No se encontraron TicketDetalle para el ticket["+id+"]");
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

