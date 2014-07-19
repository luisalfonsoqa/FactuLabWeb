package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;

import com.factulab.dao.util.DAOUtil;

public class AtencionPendienteResumen implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idAnalisis;
	private Integer cantidad;
	private String nombreAnalisis;
	private BigDecimal monto;
	public String getMontoString(){
		return DAOUtil.formatearBigDecimal(this.monto);
	}
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
	public String getNombreAnalisis() {
		return nombreAnalisis;
	}
	public void setNombreAnalisis(String nombreAnalisis) {
		this.nombreAnalisis = nombreAnalisis;
	}
	@Override
	public String toString() {
		return "AtencionPendienteResumen [idAnalisis=" + idAnalisis
				+ ", cantidad=" + cantidad + ", nombreAnalisis="
				+ nombreAnalisis + ", monto=" + monto + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAnalisis == null) ? 0 : idAnalisis.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtencionPendienteResumen other = (AtencionPendienteResumen) obj;
		if (idAnalisis == null) {
			if (other.idAnalisis != null)
				return false;
		} else if (!idAnalisis.equals(other.idAnalisis))
			return false;
		return true;
	}
	public AtencionPendienteResumen(Integer idAnalisis, Integer cantidad,
			String nombreAnalisis, BigDecimal monto) {
		super();
		this.idAnalisis = idAnalisis;
		this.cantidad = cantidad;
		this.nombreAnalisis = nombreAnalisis;
		this.monto = monto;
	}
	public AtencionPendienteResumen() {
		super();
	}
	
}
