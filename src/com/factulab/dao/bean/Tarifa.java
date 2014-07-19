package com.factulab.dao.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tarifa implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idTarifa;
	private String nombre;
	private BigDecimal factor;
	public Integer getIdTarifa() {
		return idTarifa;
	}
	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getFactor() {
		return factor;
	}
	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
	@Override
	public String toString() {
		return "Tarifa [idTarifa=" + idTarifa + ", nombre=" + nombre
				+ ", factor=" + factor + "]";
	}
	
}
