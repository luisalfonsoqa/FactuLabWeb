package com.factulab.dao.form;

import java.io.Serializable;
import java.util.List;
/**
 * Reporte
 * @author Administrador
 *
 */
public class HojaTrabajoForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombrePaciente;
	private Integer idAtencion;
	private String codigoOmega;
	private Integer edad;

	private List<HojaTrabajoDetalleForm> lDetalle;

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public Integer getIdAtencion() {
		return idAtencion;
	}

	public void setIdAtencion(Integer idAtencion) {
		this.idAtencion = idAtencion;
	}

	public String getCodigoOmega() {
		return codigoOmega;
	}

	public void setCodigoOmega(String codigoOmega) {
		this.codigoOmega = codigoOmega;
	}

	public List<HojaTrabajoDetalleForm> getlDetalle() {
		return lDetalle;
	}

	public void setlDetalle(List<HojaTrabajoDetalleForm> lDetalle) {
		this.lDetalle = lDetalle;
	}
	@Override
	public String toString() {
		return "HojaTrabajoForm [nombrePaciente=" + nombrePaciente
				+ ", idAtencion=" + idAtencion + ", codigoOmega=" + codigoOmega
				+ ", edad=" + edad + "]";
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}
}
