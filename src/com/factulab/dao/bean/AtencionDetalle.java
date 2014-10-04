package com.factulab.dao.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class AtencionDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idAnalisis;
	private Integer cantidad;
	private BigDecimal monto;
	private BigDecimal precio;
	private BigDecimal precioUnitario;
	private Integer idAtencion;
	private String detalleMonto;
	public Integer getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public Integer getIdAtencion() {
		return idAtencion;
	}
	public void setIdAtencion(Integer idAtencion) {
		this.idAtencion = idAtencion;
	}
	@Override
	public String toString() {
		return "AtencionDetalle [idAnalisis=" + idAnalisis + ", cantidad="
				+ cantidad + ", monto=" + monto + ", precio=" + precio
				+ ", precioUnitario=" + precioUnitario + ", idAtencion="
				+ idAtencion + "]";
	}
	public String getDetalleMonto() {
		return detalleMonto;
	}
	public void setDetalleMonto(String detalleMonto) {
		this.detalleMonto = detalleMonto;
	}
}
