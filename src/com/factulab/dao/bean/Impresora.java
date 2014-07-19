package com.factulab.dao.bean;

import java.math.BigInteger;

public class Impresora {
	
	private Integer idImpresora;
	private BigInteger numeroTicket;
	private String serieMaquina;
	private Integer serieTicket;
	private String nombre;
	private Integer activo;
	
	public Impresora() {
		super();
	}
	public Impresora(Integer idImpresora, BigInteger numeroTicket,
			String serieMaquina, Integer serieTicket, String nombre,
			Integer activo) {
		super();
		this.idImpresora = idImpresora;
		this.numeroTicket = numeroTicket;
		this.serieMaquina = serieMaquina;
		this.serieTicket = serieTicket;
		this.nombre = nombre;
		this.activo = activo;
	}
	public Impresora(Integer idImpresora, String serieMaquina, String nombre, Integer activo) {
		super();
		this.idImpresora = idImpresora;
		this.serieMaquina = serieMaquina;
		this.nombre = nombre;
		this.activo = activo;
	}

	public Integer getIdImpresora() {
		return idImpresora;
	}
	public void setIdImpresora(Integer idImpresora) {
		this.idImpresora = idImpresora;
	}
	public BigInteger getNumeroTicket() {
		return numeroTicket;
	}
	public void setNumeroTicket(BigInteger numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	public String getSerieMaquina() {
		return serieMaquina;
	}
	public void setSerieMaquina(String serieMaquina) {
		this.serieMaquina = serieMaquina;
	}
	public Integer getSerieTicket() {
		return serieTicket;
	}
	public void setSerieTicket(Integer serieTicket) {
		this.serieTicket = serieTicket;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	@Override
	public String toString() {
		return "Impresora [idImpresora=" + idImpresora + ", numeroTicket="
				+ numeroTicket + ", serieMaquina=" + serieMaquina
				+ ", serieTicket=" + serieTicket + ", nombre=" + nombre
				+ ", activo=" + activo + "]";
	}
	
}
