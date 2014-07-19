package com.factulab.dao.bean;

import java.io.Serializable;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private String usuario;
	private String nombre;
	private String clave;
	private Integer idTipoUsuario;

	private Integer idSede;
	private String nombreSede;
	
	private Integer idImpresora;
	private boolean imprimeTickets;
	private String nombreImpresora;
	private String serieImpresora;
	private String id;
	
	public Usuario() {
		super();
	}
	public Usuario(Integer idUsuario, String usuario, String nombre,
			Integer idTipoUsuario, Integer idSede) {
		super();
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.nombre = nombre;
		this.idTipoUsuario = idTipoUsuario;
		this.idSede = idSede;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Integer getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(Integer idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	
	public Integer getIdSede() {
		return idSede;
	}
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}
	public String getNombreSede() {
		return nombreSede;
	}
	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}
	public Integer getIdImpresora() {
		return idImpresora;
	}
	public void setIdImpresora(Integer idImpresora) {
		this.idImpresora = idImpresora;
	}
	public boolean isImprimeTickets() {
		return imprimeTickets;
	}
	public void setImprimeTickets(boolean imprimeTickets) {
		this.imprimeTickets = imprimeTickets;
	}
	public String getSerieImpresora() {
		return serieImpresora;
	}
	public void setSerieImpresora(String serieImpresora) {
		this.serieImpresora = serieImpresora;
	}
	public String getNombreImpresora() {
		return nombreImpresora;
	}
	public void setNombreImpresora(String nombreImpresora) {
		this.nombreImpresora = nombreImpresora;
	}
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", usuario=" + usuario
				+ ", nombre=" + nombre + ", clave=" + clave
				+ ", idTipoUsuario=" + idTipoUsuario + ", idSede=" + idSede
				+ ", nombreSede=" + nombreSede + ", idImpresora=" + idImpresora
				+ ", imprimeTickets=" + imprimeTickets + ", nombreImpresora="
				+ nombreImpresora + ", serieImpresora=" + serieImpresora
				+ ", id=" + id + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogUser(){
		return "<user><usuario>"+this.usuario+"</usuario><id>"+this.id+"</id></user>";
	}
}
