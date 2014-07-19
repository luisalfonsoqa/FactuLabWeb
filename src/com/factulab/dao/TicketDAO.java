package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Ticket;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.TicketForm;
import com.factulab.dao.form.TicketPendienteForm;
import com.factulab.dao.util.ConectaDB;

public class TicketDAO {
	Logger miLog = Logger.getLogger(TicketDAO.class);
	
	/**
	 * Crea el Ticket
	 * @param ticket
	 * @param idImpresora
	 * @throws DAOException
	 */
	public void create(Ticket ticket, int idImpresora, int tipoTicket) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "EXEC FacturaLabSQL.dbo.InsertarTicket @idImpresora = ?, @idPaciente = ? , @idInstitucion = ?, @ruc = ?, "
					+ "@nombre = ?, @direccion = ?, @subtotal = ?, @igv = ?, @total = ?, @tipopago = ?, @nomsede = ?, @nomusuario = ?, @idFranquicia = ?, @impresora = ?, @idusuario = ?, @tipoTicket = ? ";
			ps = cn.prepareStatement(query);
			
			ps.setInt(1, idImpresora);
			ps.setInt(2, ticket.getIdPaciente());
			ps.setInt(3, ticket.getIdInstitucion());
			ps.setString(4, ticket.getRuc());
			ps.setString(5, ticket.getNombre());
			ps.setString(6, ticket.getDireccion());
			ps.setDouble(7, ticket.getSubtotal());
			ps.setDouble(8, ticket.getIgv());
			ps.setDouble(9, ticket.getTotal());
			ps.setString(10, ticket.getTipopago());
			ps.setString(11, ticket.getNomsede());
			ps.setString(12, ticket.getNomusuario());
			ps.setInt(13, ticket.getIdFranquicia());
			ps.setString(14, ticket.getImpresora());
			ps.setInt(15, ticket.getIdUsuario());
			ps.setInt(16, tipoTicket);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				ticket.setNumSerie(Integer.parseInt(rs.getString("serieTicket")));
		        ticket.setNumTicket(Long.parseLong(rs.getString("numeroTicket")));
		        ticket.setIdTicket(Integer.parseInt(rs.getString("idTicket")));
			}
			rs.close();
			ps.close();
			cn.close();
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);   
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * Buscar Informacion del Ticket 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public TicketForm find(long id) throws DAOException, FactulabException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TicketForm t = null;
		String query = null; 
		try {
			cn = new ConectaDB().getConexion();
			query ="EXEC FacturaLabSQL.dbo.ObtenerTicket @idTicket = ?";
			ps = cn.prepareStatement(query); 
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				t = new TicketForm();
				t.setIdTicket(rs.getInt("idTicket"));
		        t.setNumTicket(rs.getLong("numTicket"));
		        t.setNumSerie(rs.getInt("numSerie"));
		        t.setNomusuario(rs.getString("nomusuario"));
		        t.setNomsede(rs.getString("nomsede"));
		        
		        t.setFecha(rs.getString("fecha"));
		        t.setTotal(rs.getDouble("total"));
		        t.setNombre(rs.getString("nombre"));
		        t.setDireccion(rs.getString("direccion"));
		        t.setImpresora(rs.getString("impresora"));

		        t.setNombrePaciente(rs.getString("nombrePaciente"));
		        t.setDniPaciente(rs.getString("dniPaciente"));
		        t.setSubtotal(rs.getDouble("subtotal"));
		        t.setIgv(rs.getDouble("igv"));
		        t.setTipopago(rs.getString("tipopago"));
		        
		        t.setCodigoOmega(rs.getString("codigoOmega"));
		        t.setRuc(rs.getString("ruc"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(t == null) throw new FactulabException("No se encontro el Ticket["+id+"]");
			return t;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);  
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * Buscar informacion del Ticket Pendiente
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public TicketPendienteForm findPendiente(long id) throws DAOException, FactulabException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TicketPendienteForm t = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query ="	select t.idTicket, numTicket, t.numSerie, t.nomusuario, t.nomsede, "
					+ "(CONVERT(CHAR(11),t.fecha,101) + CONVERT(CHAR( 5),t.fecha,114)) 'fecha', t.total, t.nombre, t.direccion, t.impresora, "
					+ "t.tipopago, t.subtotal, t.igv, i.nombre 'nominstitucion', t.ruc "
					+ "from ticket t, institucion i where t.idTicket = ? and i.idInstitucion = t.idInstitucion";
			ps = cn.prepareStatement(query); 
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				t = new TicketPendienteForm();
				t.setIdTicket(rs.getInt("idTicket"));
		        t.setNumTicket(rs.getLong("numTicket"));
		        t.setNumSerie(rs.getInt("numSerie"));
		        t.setNomusuario(rs.getString("nomusuario"));
		        t.setNomsede(rs.getString("nomsede"));
		        
		        t.setFecha(rs.getString("fecha"));
		        t.setTotal(rs.getDouble("total"));
		        t.setNombre(rs.getString("nombre"));
		        t.setDireccion(rs.getString("direccion"));
		        t.setImpresora(rs.getString("impresora"));

		        t.setSubtotal(rs.getDouble("subtotal"));
		        t.setIgv(rs.getDouble("igv"));
		        t.setTipopago(rs.getString("tipopago"));
		        t.setNominstitucion(rs.getString("nominstitucion"));
		        t.setRuc(rs.getString("ruc"));
			}
			rs.close();
			ps.close();
			cn.close();
			if(t == null) throw new FactulabException("No se encontro el Ticket["+id+"]");
			return t;
		} catch (FactulabException e) {
			throw new FactulabException(e.getMessage(),e);  
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e);  
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (cn != null) cn.close();
			} catch (SQLException e) {
				miLog.error(e.getMessage(),e);
			}
		}
	}
}
