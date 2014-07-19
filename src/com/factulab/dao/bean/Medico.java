package com.factulab.dao.bean;

import java.io.Serializable;

public class Medico implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idEspecialidad;
	private String direccion;
	private String cmp;
	private String apemat;
	private String apepat;
	private String nombre;
	private Integer idMedico;
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCmp() {
		return cmp;
	}
	public void setCmp(String cmp) {
		this.cmp = cmp;
	}
	public String getApemat() {
		return apemat;
	}
	public void setApemat(String apemat) {
		this.apemat = apemat;
	}
	public String getApepat() {
		return apepat;
	}
	public void setApepat(String apepat) {
		this.apepat = apepat;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(Integer idMedico) {
		this.idMedico = idMedico;
	}
	public Integer getIdEspecialidad() {
		return idEspecialidad;
	}
	public void setIdEspecialidad(Integer idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}
	@Override
	public String toString() {
		return "Medico [idEspecialidad=" + idEspecialidad + ", direccion="
				+ direccion + ", cmp=" + cmp + ", apemat=" + apemat
				+ ", apepat=" + apepat + ", nombre=" + nombre + ", idMedico="
				+ idMedico + "]";
	}
	
}
