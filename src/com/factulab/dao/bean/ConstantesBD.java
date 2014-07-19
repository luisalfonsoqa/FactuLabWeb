package com.factulab.dao.bean;

import java.io.Serializable;
import java.util.List;

public class ConstantesBD implements Serializable{
	private static final long serialVersionUID = 1L;
	public List<Departamento> lDepartamento;
//	public List<Distrito> lDistrito;
//	public List<Provincia> lProvincia;
	public List<TipoPaciente> lTipoPaciente;
	public String omegaRemoto;
	public String omegaLocal;
	//public BigDecimal igv;
	
	public List<Departamento> getlDepartamento() {
		return lDepartamento;
	}
	public void setlDepartamento(List<Departamento> lDepartamento) {
		this.lDepartamento = lDepartamento;
	}
//	public List<Distrito> getlDistrito() {
//		return lDistrito;
//	}
//	public void setlDistrito(List<Distrito> lDistrito) {
//		this.lDistrito = lDistrito;
//	}
//	public List<Provincia> getlProvincia() {
//		return lProvincia;
//	}
//	public void setlProvincia(List<Provincia> lProvincia) {
//		this.lProvincia = lProvincia;
//	}
	public List<TipoPaciente> getlTipoPaciente() {
		return lTipoPaciente;
	}
	public void setlTipoPaciente(List<TipoPaciente> lTipoPaciente) {
		this.lTipoPaciente = lTipoPaciente;
	}
	public String getOmegaRemoto() {
		return omegaRemoto;
	}
	public void setOmegaRemoto(String omegaRemoto) {
		this.omegaRemoto = omegaRemoto;
	}
	public String getOmegaLocal() {
		return omegaLocal;
	}
	public void setOmegaLocal(String omegaLocal) {
		this.omegaLocal = omegaLocal;
	}
	@Override
	public String toString() {
		return "ConstantesBD [omegaRemoto=" + omegaRemoto + ", omegaLocal="
				+ omegaLocal + "]";
	}
}
