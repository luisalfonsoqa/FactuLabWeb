package com.factulab.dao.form;

import java.io.Serializable;
/**
 * Reporte
 * @author Administrador
 *
 */
public class HojaTrabajoDetalleForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Integer cantidad;
	private String abreviatura;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	@Override
	public String toString() {
		return "HojaTrabajoForm [nombre=" + nombre + ", cantidad=" + cantidad
				+ ", abreviatura=" + abreviatura + "]";
	}

}
