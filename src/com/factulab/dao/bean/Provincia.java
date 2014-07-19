package com.factulab.dao.bean;

import java.io.Serializable;

public class Provincia implements Serializable{
	private static final long serialVersionUID = 1L;
	public Integer idProvincia;
	public String nombre;
	public Integer idDepartamento;
	public Integer getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}
	public Integer getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Provincia [idProvincia=" + idProvincia + ", nombre=" + nombre
				+ ", idDepartamento=" + idDepartamento + "]";
	}
}
