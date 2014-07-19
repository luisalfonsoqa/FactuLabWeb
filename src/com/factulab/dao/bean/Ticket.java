package com.factulab.dao.bean;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idTicket;
    private int numSerie;
    private long numTicket;
    private Date fecha;
    private Integer idPaciente;

    private Integer idInstitucion;
    private String ruc;
    private String nombre;
    private String direccion;
    private double subtotal;
    
    private double igv;
    private double total;
    private int estado;
    private String tipopago;
    private String nomsede;

    private String impresora;
    private String nomusuario;
    private Integer idUsuario;
    private Integer idFranquicia;
    
	public Integer getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}
	public int getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(int numSerie) {
		this.numSerie = numSerie;
	}
	public long getNumTicket() {
		return numTicket;
	}
	public void setNumTicket(long numTicket) {
		this.numTicket = numTicket;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
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
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getTipopago() {
		return tipopago;
	}
	public void setTipopago(String tipopago) {
		this.tipopago = tipopago;
	}
	public String getNomsede() {
		return nomsede;
	}
	public void setNomsede(String nomsede) {
		this.nomsede = nomsede;
	}
	public String getImpresora() {
		return impresora;
	}
	public void setImpresora(String impresora) {
		this.impresora = impresora;
	}
	public String getNomusuario() {
		return nomusuario;
	}
	public void setNomusuario(String nomusuario) {
		this.nomusuario = nomusuario;
	}
	public Integer getIdFranquicia() {
		return idFranquicia;
	}
	public void setIdFranquicia(Integer idFranquicia) {
		this.idFranquicia = idFranquicia;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", numSerie=" + numSerie
				+ ", numTicket=" + numTicket + ", fecha=" + fecha
				+ ", idPaciente=" + idPaciente + ", idInstitucion="
				+ idInstitucion + ", ruc=" + ruc + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", subtotal=" + subtotal
				+ ", igv=" + igv + ", total=" + total + ", estado=" + estado
				+ ", tipopago=" + tipopago + ", nomsede=" + nomsede
				+ ", impresora=" + impresora + ", nomusuario=" + nomusuario
				+ ", idFranquicia=" + idFranquicia + ", idUsuario=" + idUsuario + "]";
	}
}