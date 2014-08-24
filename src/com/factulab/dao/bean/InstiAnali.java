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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAnalisis == null) ? 0 : idAnalisis.hashCode());
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
		InstiAnali other = (InstiAnali) obj;
		if (idAnalisis == null) {
			if (other.idAnalisis != null)
				return false;
		} else if (!idAnalisis.equals(other.idAnalisis))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		return true;
	}
    
}
