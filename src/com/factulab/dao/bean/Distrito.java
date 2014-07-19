package com.factulab.dao.bean;

public class Distrito {
	public Integer idDistrito;
	public String nombre;
	public Integer idProvincia;
	public Integer getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}
	@Override
	public String toString() {
		return "Distrito [idDistrito=" + idDistrito + ", nombre=" + nombre
				+ ", idProvincia=" + idProvincia + "]";
	}
}
