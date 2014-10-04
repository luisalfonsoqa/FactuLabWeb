package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.factulab.dao.bean.InstiAnali;
import com.factulab.dao.bean.Institucion;
import com.factulab.dao.bean.Medico;
import com.factulab.dao.bean.Paciente;
import com.factulab.dao.bean.Tarifa;
import com.factulab.dao.util.DAOUtil;

public class AtencionForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<AnalisisForm> lAnalisis;
	private BigDecimal totalConDescuento;
	private BigDecimal totalSinDescuento;
	private BigDecimal subtotal;
	private Paciente paciente;
	private Medico medico;
	private Institucion institucion;
	private Tarifa tarifa;
	
	private List<InstiAnali> lInstiAnali;
	private BigDecimal porcentajeDescuento;
	private String codigoOmega;
	private Integer idAtencion;
	
	private Boolean conIGV;
	private String tipoPago;
	private Integer numSerie;
	private long numTicket;
	private long idTicket;
	
	public List<AnalisisForm> getlAnalisis() {
		return lAnalisis;
	}
	public void setlAnalisis(List<AnalisisForm> lAnalisis) {
		this.lAnalisis = lAnalisis;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public BigDecimal getTotalSinDescuento() {
		return totalSinDescuento;
	}
	public void setTotalSinDescuento(BigDecimal totalSinDescuento) {
		this.totalSinDescuento = totalSinDescuento;
	}
	public BigDecimal getTotalConDescuento() {
		return totalConDescuento;
	}
	public void setTotalConDescuento(BigDecimal totalConDescuento) {
		this.totalConDescuento = totalConDescuento;
	}
	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	public Tarifa getTarifa() {
		return tarifa;
	}
	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}
	public List<InstiAnali> getlInstiAnali() {
		return lInstiAnali;
	}
	public void setlInstiAnali(List<InstiAnali> lInstiAnali) {
		this.lInstiAnali = lInstiAnali;
	}
	
	public String getCodigoOmega() {
		return codigoOmega;
	}
	public void setCodigoOmega(String codigoOmega) {
		this.codigoOmega = codigoOmega;
	}
	
	public Boolean getConIGV() {
		return conIGV;
	}
	public void setConIGV(Boolean conIGV) {
		this.conIGV = conIGV;
	}
	
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	
	public Integer getIdAtencion() {
		return idAtencion;
	}
	public void setIdAtencion(Integer idAtencion) {
		this.idAtencion = idAtencion;
	}
	public Integer getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(Integer numSerie) {
		this.numSerie = numSerie;
	}
	public long getNumTicket() {
		return numTicket;
	}
	public void setNumTicket(long numTicket) {
		this.numTicket = numTicket;
	}
	
	public long getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(long idTicket) {
		this.idTicket = idTicket;
	}
	
	@Override
	public String toString() {
		return "AtencionForm [totalConDescuento=" + totalConDescuento
				+ ", totalSinDescuento=" + totalSinDescuento + ", subtotal="
				+ subtotal + ", paciente=" + paciente + ", medico=" + medico
				+ ", institucion=" + institucion + ", tarifa=" + tarifa
				+ ", porcentajeDescuento=" + porcentajeDescuento
				+ ", codigoOmega=" + codigoOmega + ", idAtencion=" + idAtencion
				+ ", conIGV=" + conIGV + ", tipoPago=" + tipoPago
				+ ", numSerie=" + numSerie + ", numTicket=" + numTicket
				+ ", idTicket=" + idTicket +"]";
	}
	public String getTotalConDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.totalConDescuento);
	}
	public String getTotalSinDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.totalSinDescuento);
	}
	public String getPorcentajeDescuentoString(){
		return DAOUtil.formatearPorcentaje(this.porcentajeDescuento);
	}
}
