package com.factulab.dao.form;

import java.io.Serializable;
import java.util.List;
/***
 * Reporte
 * @author Administrador
 *
 */
public class TicketPendienteForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idTicket;
	private long numTicket;
	private int numSerie;
	private String nomusuario;
	private String nomsede;
	
	private String fecha;
	private double total;
	private String nombre;
	private String direccion;
	private String impresora;
	
    private double subtotal;
    private double igv;
    private String tipopago;
    private String nominstitucion;
    
    private List<TicketPendienteDetalleForm> lDetalle;
    private List<AtencionPendienteResumen> lAtencionResumen;
    private String ruc;
	public Integer getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}
	public long getNumTicket() {
		return numTicket;
	}
	public void setNumTicket(long numTicket) {
		this.numTicket = numTicket;
	}
	public int getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(int numSerie) {
		this.numSerie = numSerie;
	}
	public String getNomusuario() {
		return nomusuario;
	}
	public void setNomusuario(String nomusuario) {
		this.nomusuario = nomusuario;
	}
	public String getNomsede() {
		return nomsede;
	}
	public void setNomsede(String nomsede) {
		this.nomsede = nomsede;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getImpresora() {
		return impresora;
	}
	public void setImpresora(String impresora) {
		this.impresora = impresora;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getIgv() {
		return igv;
	}
	public void setIgv(double igv) {
		this.igv = igv;
	}
	public String getTipopago() {
		return tipopago;
	}
	public void setTipopago(String tipopago) {
		this.tipopago = tipopago;
	}
	public List<TicketPendienteDetalleForm> getlDetalle() {
		return lDetalle;
	}
	public void setlDetalle(List<TicketPendienteDetalleForm> lDetalle) {
		this.lDetalle = lDetalle;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	@Override
	public String toString() {
		return "TicketPendienteForm [idTicket="
				+ idTicket + ", numTicket=" + numTicket + ", numSerie="
				+ numSerie + ", nomusuario=" + nomusuario + ", nomsede="
				+ nomsede + ", fecha=" + fecha + ", total=" + total
				+ ", nombre=" + nombre + ", direccion=" + direccion
				+ ", impresora=" + impresora + ", subtotal=" + subtotal
				+ ", igv=" + igv + ", tipopago=" + tipopago
				+ ", nominstitucion=" + nominstitucion + ", ruc=" + ruc + "]";
	}
	public String getNominstitucion() {
		return nominstitucion;
	}
	public void setNominstitucion(String nominstitucion) {
		this.nominstitucion = nominstitucion;
	}
	public List<AtencionPendienteResumen> getlAtencionResumen() {
		return lAtencionResumen;
	}
	public void setlAtencionResumen(List<AtencionPendienteResumen> lAtencionResumen) {
		this.lAtencionResumen = lAtencionResumen;
	}
}
