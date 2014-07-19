package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
/***
 * Reporte
 * @author Administrador
 *
 */
public class ReporteAnalisisDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idAtencion;
    private String fecha;
    private String nombrePaciente;
    private String nombreMedico;
    private String nombreAnalisis;
    private String codigoOmega;
    private Integer cantidad;
    private BigDecimal monto;
    private boolean facturado;
    
	public Integer getIdAtencion() {
		return idAtencion;
	}
	public void setIdAtencion(Integer idAtencion) {
		this.idAtencion = idAtencion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	public String getNombreMedico() {
		return nombreMedico;
	}
	public void setNombreMedico(String nombreMedico) {
		this.nombreMedico = nombreMedico;
	}
	public String getNombreAnalisis() {
		return nombreAnalisis;
	}
	public void setNombreAnalisis(String nombreAnalisis) {
		this.nombreAnalisis = nombreAnalisis;
	}
	public String getCodigoOmega() {
		return codigoOmega;
	}
	public void setCodigoOmega(String codigoOmega) {
		this.codigoOmega = codigoOmega;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public boolean getFacturado() {
		return facturado;
	}
	public void setFacturado(boolean facturado) {
		this.facturado = facturado;
	}
	@Override
	public String toString() {
		return "ReporteAnalisisDetalle [idAtencion=" + idAtencion + ", fecha="
				+ fecha + ", nombrePaciente=" + nombrePaciente
				+ ", nombreMedico=" + nombreMedico + ", nombreAnalisis="
				+ nombreAnalisis + ", codigoOmega=" + codigoOmega
				+ ", cantidad=" + cantidad + ", monto=" + monto + ", facturado=" + facturado+"]";
	}
}
