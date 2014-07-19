package com.factulab.dao.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class InstiAnali implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer idAnalisis;
    private Integer idInstitucion;
    private Integer tipoMonto;
    private BigDecimal factor;
    
	public Integer getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Integer getTipoMonto() {
		return tipoMonto;
	}
	public void setTipoMonto(Integer tipoMonto) {
		this.tipoMonto = tipoMonto;
	}
	public BigDecimal getFactor() {
		return factor;
	}
	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
	@Override
	public String toString() {
		return "InstiAnali [idAnalisis=" + idAnalisis + ", idInstitucion="
				+ idInstitucion + ", tipoMonto=" + tipoMonto + ", factor="
				+ factor + "]";
	}
    
}
