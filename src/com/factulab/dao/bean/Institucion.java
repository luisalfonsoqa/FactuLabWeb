package com.factulab.dao.bean;

import java.io.Serializable;

public class Institucion implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idTarifa;
	private String nombre;
	private Integer idInstitucion;
	private Integer facMensual;
	private String direccion;
	private String ruc;
	private String emailContacto;
	private String nombreContacto;
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
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Integer getFacMensual() {
		return facMensual;
	}
	public void setFacMensual(Integer facMensual) {
		this.facMensual = facMensual;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getEmailContacto() {
		return emailContacto;
	}
	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}
	public String getNombreContacto() {
		return nombreContacto;
	}
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	@Override
	public String toString() {
		return "Institucion [idTarifa=" + idTarifa + ", nombre=" + nombre
				+ ", idInstitucion=" + idInstitucion + ", facMensual="
				+ facMensual + ", direccion=" + direccion + ", ruc=" + ruc
				+ ", emailContacto=" + emailContacto + ", nombreContacto="
				+ nombreContacto + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
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
		Institucion other = (Institucion) obj;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		return true;
	}
}
