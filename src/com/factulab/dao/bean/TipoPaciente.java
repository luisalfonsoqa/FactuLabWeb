package com.factulab.dao.bean;

import java.io.Serializable;

public class TipoPaciente implements  Serializable{
	private static final long serialVersionUID = 1L;
	public Integer idTipoPaciente;
	public String descripcion;
	public Integer getIdTipoPaciente() {
		return idTipoPaciente;
	}
	public void setIdTipoPaciente(Integer idTipoPaciente) {
		this.idTipoPaciente = idTipoPaciente;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "TipoPaciente [idTipoPaciente=" + idTipoPaciente
				+ ", descripcion=" + descripcion + "]";
	}
}
