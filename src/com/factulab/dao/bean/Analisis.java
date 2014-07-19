package com.factulab.dao.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Analisis implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Integer seccion;
	private BigDecimal precio;
	private String idAnalisisOmega;
	private String abreviatura;
	private String unidadMedida;
	private Integer activo;
	private Integer idAnalisis;

	private String nombreSeccion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getIdAnalisisOmega() {
		return idAnalisisOmega;
	}

	public void setIdAnalisisOmega(String idAnalisisOmega) {
		this.idAnalisisOmega = idAnalisisOmega;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getIdAnalisis() {
		return idAnalisis;
	}

	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}

	public String getNombreSeccion() {
		return nombreSeccion;
	}

	public void setNombreSeccion(String nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
	}

	@Override
	public String toString() {
		return "Analisis [nombre=" + nombre + ", seccion=" + seccion
				+ ", precio=" + precio + ", idAnalisisOmega=" + idAnalisisOmega
				+ ", abreviatura=" + abreviatura + ", unidadMedida="
				+ unidadMedida + ", activo=" + activo + ", idAnalisis="
				+ idAnalisis + ", nombreSeccion=" + nombreSeccion + "]";
	}
	
}
