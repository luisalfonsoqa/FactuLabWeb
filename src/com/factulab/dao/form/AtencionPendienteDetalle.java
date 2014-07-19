package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.factulab.dao.util.DAOUtil;

public class AtencionPendienteDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idAnalisis;
	private Integer cantidad;
	private BigDecimal monto;
	private BigDecimal precio;
	private BigDecimal precioUnitario;
	private Integer idAtencion;
	
	private String nombrePaciente;
	private String nombreAnalisis;
	private Date fecha;
	private String codOmega;
	private BigDecimal porcentajeDescuento;
	public Integer getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
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
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
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
	public String getNombreAnalisis() {
		return nombreAnalisis;
	}
	public void setNombreAnalisis(String nombreAnalisis) {
		this.nombreAnalisis = nombreAnalisis;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCodOmega() {
		return codOmega;
	}
	public void setCodOmega(String codOmega) {
		this.codOmega = codOmega;
	}
	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	@Override
	public String toString() {
		return "AtencionPendienteDetalle [idAnalisis=" + idAnalisis
				+ ", cantidad=" + cantidad + ", monto=" + monto + ", precio="
				+ precio + ", precioUnitario=" + precioUnitario
				+ ", idAtencion=" + idAtencion + ", nombrePaciente="
				+ nombrePaciente + ", nombreAnalisis=" + nombreAnalisis
				+ ", fecha=" + fecha + ", codOmega=" + codOmega
				+ ", porcentajeDescuento=" + porcentajeDescuento + "]";
	}
	public String getPorcentajeDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.porcentajeDescuento);
	}
	public String getPrecioString(){
		return DAOUtil.formatearBigDecimal(this.precio);
	}
	public String getMontoString(){
		return DAOUtil.formatearBigDecimal(this.monto);
	}
	public String getPrecioUnitarioString(){
		return DAOUtil.formatearBigDecimal(this.precioUnitario);
	}
}
