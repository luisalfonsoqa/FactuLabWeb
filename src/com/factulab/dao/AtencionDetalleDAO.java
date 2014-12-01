package com.factulab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.AtencionDetalle;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.form.HojaTrabajoDetalleForm;
import com.factulab.dao.util.ConectaDB;

public class AtencionDetalleDAO {
	Logger miLog = Logger.getLogger(AtencionDAO.class);
	/**
	 * Crear detalle de la atencion
	 * @param detalle
	 * @throws DAOException
	 */
	public void create(AtencionDetalle detalle) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "EXEC FacturaLabSQL.dbo.InsertarAtencionDetalle @idAtencion = ?, @idAnalisis = ?,"
					+ " @cantidad = ?, @totalConDescu = ?, @precioUniSinDesc = ?, @precioUni = ?, @detalleMonto = ?, @descuento = ? ";
			ps = cn.prepareStatement(query);
			ps.setInt(1, detalle.getIdAtencion());
			ps.setInt(2, detalle.getIdAnalisis());
			ps.setInt(3, detalle.getCantidad());
			ps.setBigDecimal(4, detalle.getMonto());
			ps.setBigDecimal(5, detalle.getPrecio());
			ps.setBigDecimal(6, detalle.getPrecioUnitario());
			ps.setString(7, detalle.getDetalleMonto());
			ps.setBigDecimal(8, detalle.getDescuento());
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
	 * Obtener informacion del Hoja de trabajo
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<HojaTrabajoDetalleForm> obtenerDetalleHT(long id) throws DAOException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HojaTrabajoDetalleForm>  lista = new ArrayList<HojaTrabajoDetalleForm>();
		HojaTrabajoDetalleForm bean = null;
		String query = null;
		try {
			cn = new ConectaDB().getConexion();
			query = "select a.nombre, d.cantidad, a.abreviatura from atenciondetalle d, analisis a where a.idAnalisis = d.idAnalisis and d.idAtencion = ?";
			ps = cn.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new HojaTrabajoDetalleForm();
				bean.setAbreviatura(rs.getString("abreviatura"));
				bean.setNombre(rs.getString("nombre"));
				bean.setCantidad(rs.getInt("cantidad"));
				lista.add(bean);
			}
			rs.close();
			ps.close();
			cn.close();
			return lista;
		} catch (Exception e) {
			throw new DAOException(query,e.getMessage(),e); 
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (cn != null) { try { cn.close(); } catch (SQLException e) { } }
		}
	}
}
