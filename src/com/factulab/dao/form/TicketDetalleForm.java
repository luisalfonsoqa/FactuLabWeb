package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Reporte
 * @author Administrador
 *
 */
public class TicketDetalleForm implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer cantidad;
    private String nombre;
    private BigDecimal monto;
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	@Override
	public String toString() {
		return "TicketDetalleForm [cantidad=" + cantidad + ", nombre=" + nombre
				+ ", monto=" + monto + "]";
	}
}

