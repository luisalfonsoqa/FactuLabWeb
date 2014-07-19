package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AtencionPendiente implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idFranquicia;
	private BigDecimal total;
	private BigDecimal descuento;
	private String codOmega;
	private String estado;
	private Integer facturado;
	private Date fecha;
	private BigDecimal subtotal;
	private Integer idPaciente;
	private Integer idUsuario;
	private Integer idMedico;
	private Integer idInstitucion;
	private Integer idAtencion;
	
	private String nombrePaciente;
	private String nombreInstitucion;

	public Integer getIdFranquicia() {
		return idFranquicia;
	}
	public void setIdFranquicia(Integer idFranquicia) {
		this.idFranquicia = idFranquicia;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public String getCodOmega() {
		return codOmega;
	}
	public void setCodOmega(String codOmega) {
		this.codOmega = codOmega;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getFacturado() {
		return facturado;
	}
	public void setFacturado(Integer facturado) {
		this.facturado = facturado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public Integer getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(Integer idMedico) {
		this.idMedico = idMedico;
	}
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Integer getIdAtencion() {
		return idAtencion;
	}
	public void setIdAtencion(Integer idAtencion) {
		this.idAtencion = idAtencion;
	}
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	@Override
	public String toString() {
		return "AtencionPendiente [idFranquicia=" + idFranquicia + ", total="
				+ total + ", descuento=" + descuento + ", codOmega=" + codOmega
				+ ", estado=" + estado + ", facturado=" + facturado
				+ ", fecha=" + fecha + ", subtotal=" + subtotal
				+ ", idPaciente=" + idPaciente + ", idUsuario=" + idUsuario
				+ ", idMedico=" + idMedico + ", idInstitucion=" + idInstitucion
				+ ", idAtencion=" + idAtencion + ", nombrePaciente="
				+ nombrePaciente + ", nombreInstitucion=" + nombreInstitucion
				+ "]";
	}
	
}
