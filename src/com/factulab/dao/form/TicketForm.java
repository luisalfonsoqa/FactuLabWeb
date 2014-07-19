package com.factulab.dao.form;

import java.io.Serializable;
import java.util.List;
/**
 * Reporte
 * @author Administrador
 *
 */
public class TicketForm implements Serializable {
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
	
	private String nombrePaciente;
	private String dniPaciente;
    private double subtotal;
    private double igv;
    private String tipopago;
    
    private String codigoOmega; //Ticket Pendientes
    private List<TicketDetalleForm> lDetalle;
    private String ruc;
    
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
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	public String getDniPaciente() {
		return dniPaciente;
	}
	public void setDniPaciente(String dniPaciente) {
		this.dniPaciente = dniPaciente;
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
	public List<TicketDetalleForm> getlDetalle() {
		return lDetalle;
	}
	public void setlDetalle(List<TicketDetalleForm> lDetalle) {
		this.lDetalle = lDetalle;
	}
	public String getCodigoOmega() {
		return codigoOmega;
	}
	public void setCodigoOmega(String codigoOmega) {
		this.codigoOmega = codigoOmega;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public Integer getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}
	@Override
	public String toString() {
		return "TicketForm [idTicket=" + idTicket + ", numTicket=" + numTicket
				+ ", numSerie=" + numSerie + ", nomusuario=" + nomusuario
				+ ", nomsede=" + nomsede + ", fecha=" + fecha + ", total="
				+ total + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", impresora=" + impresora + ", nombrePaciente="
				+ nombrePaciente + ", dniPaciente=" + dniPaciente
				+ ", subtotal=" + subtotal + ", igv=" + igv + ", tipopago="
				+ tipopago + ", codigoOmega=" + codigoOmega + ", ruc=" + ruc
				+ "]";
	}
}
