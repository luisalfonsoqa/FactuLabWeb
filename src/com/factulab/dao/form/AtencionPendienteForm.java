package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.factulab.dao.bean.Institucion;
import com.factulab.dao.bean.Paciente;
import com.factulab.dao.bean.Tarifa;
import com.factulab.dao.util.DAOUtil;

public class AtencionPendienteForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Institucion institucion;
	private Integer idInstitucion;
	private Tarifa tarifa;
	private List<AtencionPendiente> lAtencion;
	private List<AtencionPendienteDetalle> lAtencionDetalle;
	private List<AtencionPendienteResumen> lAtencionResumen;
	private BigDecimal totalConDescuento; //TOTAL
	private BigDecimal totalSinDescuento;
	private BigDecimal porcentajeDescuento;
	private String codigoOmega;
	private Integer idAtencion;
	private Integer numSerie;
	private long numTicket;
	private long idTicket;
	private Boolean conIGV;
	private String tipoPago;
	private String lstIds;
	private BigDecimal subtotal;
	private BigDecimal igv;
	private Paciente paciente;
	
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
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	public List<AtencionPendiente> getlAtencion() {
		return lAtencion;
	}
	public void setlAtencion(List<AtencionPendiente> lAtencion) {
		this.lAtencion = lAtencion;
	}
	public Tarifa getTarifa() {
		return tarifa;
	}
	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}
	public BigDecimal getTotalConDescuento() {
		return totalConDescuento;
	}
	public void setTotalConDescuento(BigDecimal totalConDescuento) {
		this.totalConDescuento = totalConDescuento;
	}
	public BigDecimal getTotalSinDescuento() {
		return totalSinDescuento;
	}
	public void setTotalSinDescuento(BigDecimal totalSinDescuento) {
		this.totalSinDescuento = totalSinDescuento;
	}
	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}	
	public String getCodigoOmega() {
		return codigoOmega;
	}
	public void setCodigoOmega(String codigoOmega) {
		this.codigoOmega = codigoOmega;
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
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	public List<AtencionPendienteDetalle> getlAtencionDetalle() {
		return lAtencionDetalle;
	}
	public void setlAtencionDetalle(List<AtencionPendienteDetalle> lAtencionDetalle) {
		this.lAtencionDetalle = lAtencionDetalle;
	}
	
	public List<AtencionPendienteResumen> getlAtencionResumen() {
		return lAtencionResumen;
	}
	public void setlAtencionResumen(List<AtencionPendienteResumen> lAtencionResumen) {
		this.lAtencionResumen = lAtencionResumen;
	}
	
	public String getLstIds() {
		return lstIds;
	}
	public void setLstIds(String lstIds) {
		this.lstIds = lstIds;
	}
	
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getIgv() {
		return igv;
	}
	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}
	
	@Override
	public String toString() {
		return "AtencionPendienteForm [institucion=" + institucion
				+ ", idInstitucion=" + idInstitucion + ", tarifa=" + tarifa
				+ ", totalConDescuento=" + totalConDescuento
				+ ", totalSinDescuento=" + totalSinDescuento
				+ ", porcentajeDescuento=" + porcentajeDescuento
				+ ", codigoOmega=" + codigoOmega + ", idAtencion=" + idAtencion
				+ ", numSerie=" + numSerie + ", numTicket=" + numTicket
				+ ", idTicket=" + idTicket + ", conIGV=" + conIGV
				+ ", tipoPago=" + tipoPago + ", lstIds=" + lstIds
				+ ", subtotal=" + subtotal + ", igv=" + igv + ", paciente="
				+ paciente + "]";
	}
	public String getTotalConDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.totalConDescuento);
	}
	public String getSubtotalString(){
		return DAOUtil.formatearBigDecimal(this.subtotal);
	}
	public String getIgvString(){
		return DAOUtil.formatearBigDecimal(this.igv);
	}
	public String getPorcentajeDescuentoString(){
		return DAOUtil.formatearPorcentaje(this.porcentajeDescuento);
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}	
	
}
