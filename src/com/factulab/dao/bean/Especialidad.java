package com.factulab.dao.bean;

import java.io.Serializable;

public class Especialidad implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idEspecialidad;
	private String nombre;
	public Integer getIdEspecialidad() {
		return idEspecialidad;
	}
	public void setIdEspecialidad(Integer idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Especialidad [idEspecialidad=" + idEspecialidad + ", nombre="
				+ nombre + "]";
	}
	
}
