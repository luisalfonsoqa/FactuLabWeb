package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/***
 * Reporte
 * @author Administrador
 *
 */
public class ReporteAnalisis implements Serializable {
	private static final long serialVersionUID = 1L;
    private BigDecimal monto;
    private List<ReporteAnalisisDetalle> lDetalle;
    private Date fechaInicio;
    private Date fechaFin;
    private String turno;
    private Integer horaIncio;
    private Integer horaFin;
    private Integer idUsuario;
    
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public List<ReporteAnalisisDetalle> getlDetalle() {
		return lDetalle;
	}
	public void setlDetalle(List<ReporteAnalisisDetalle> lDetalle) {
		this.lDetalle = lDetalle;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public Integer getHoraIncio() {
		return horaIncio;
	}
	public void setHoraIncio(Integer horaIncio) {
		this.horaIncio = horaIncio;
	}
	public Integer getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(Integer horaFin) {
		this.horaFin = horaFin;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	@Override
	public String toString() {
		return "ReporteAnalisis [monto=" + monto + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", turno=" + turno
				+ ", horaIncio=" + horaIncio + ", horaFin=" + horaFin
				+ ", idUsuario=" + idUsuario + "]";
	}
}
