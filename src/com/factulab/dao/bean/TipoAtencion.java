package com.factulab.dao.bean;

import java.io.Serializable;

public class TipoAtencion implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idTipoAtencion;
	private String nombre;
	public Integer getIdTipoAtencion() {
		return idTipoAtencion;
	}
	public void setIdTipoAtencion(Integer idTipoAtencion) {
		this.idTipoAtencion = idTipoAtencion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "TipoAtencion [idTipoAtencion=" + idTipoAtencion + ", nombre="
				+ nombre + "]";
	}
	public TipoAtencion(Integer idTipoAtencion, String nombre) {
		super();
		this.idTipoAtencion = idTipoAtencion;
		this.nombre = nombre;
	}
	public TipoAtencion() {
		super();
	}

}
