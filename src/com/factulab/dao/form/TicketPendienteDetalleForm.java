package com.factulab.dao.form;

import java.io.Serializable;
/**
 * Reporte
 * @author Administrador
 *
 */
public class TicketPendienteDetalleForm implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer cantidad;
    private String codOmega;
    private String nombre;
    private String descripcion;
    private String importe;
    private String fecha;
    private String nombrePaciente;
    private Integer idAnalisis;
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getCodOmega() {
		return codOmega;
	}
	public void setCodOmega(String codOmega) {
		this.codOmega = codOmega;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	@Override
	public String toString() {
		return "TicketPendienteDetalleForm [cantidad=" + cantidad
				+ ", codOmega=" + codOmega + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", importe=" + importe
				+ ", fecha=" + fecha + ", nombrePaciente=" + nombrePaciente
				+ ", idAnalisis=" + idAnalisis + "]";
	}
	public Integer getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	
}

