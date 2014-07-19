package com.factulab.dao.bean;

import java.io.Serializable;
import java.util.Date;

public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer idPaciente;
    private String nombre;
    private String apepat;
    private String apemat;
    private String dni;
    private Date fecnac;
    private Character sexo;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private String histClinica;
    private Integer idDistrito;
    private Integer idTipoPaciente;
    private Integer idInstitucion;
    private String fax;
    private String nombreTipoPaciente;
    private String nombreInstitucion;
    private String nombreDepartamento;
    private String nombreDistrito;
    private String nombreProvincia; 
    private String nombreTarifa;
    private Integer edad;
    
	public Integer getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApepat() {
		return apepat;
	}
	public void setApepat(String apepat) {
		this.apepat = apepat;
	}
	public String getApemat() {
		return apemat;
	}
	public void setApemat(String apemat) {
		this.apemat = apemat;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getFecnac() {
		return fecnac;
	}
	public void setFecnac(Date fecnac) {
		this.fecnac = fecnac;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHistClinica() {
		return histClinica;
	}
	public void setHistClinica(String histClinica) {
		this.histClinica = histClinica;
	}
	public Integer getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}
	public Integer getIdTipoPaciente() {
		return idTipoPaciente;
	}
	public void setIdTipoPaciente(Integer idTipoPaciente) {
		this.idTipoPaciente = idTipoPaciente;
	}
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getNombreTipoPaciente() {
		return nombreTipoPaciente;
	}
	public void setNombreTipoPaciente(String nombreTipoPaciente) {
		this.nombreTipoPaciente = nombreTipoPaciente;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	public String getNombreDistrito() {
		return nombreDistrito;
	}
	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public String getNombreTarifa() {
		return nombreTarifa;
	}
	public void setNombreTarifa(String nombreTarifa) {
		this.nombreTarifa = nombreTarifa;
	}
	public Character getSexo() {
		return sexo;
	}
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	public Integer getEdad() {
		if(fecnac != null && edad == null) {
			Date fechaActual = new Date();
			long año = (long) 365.25;
			long msecFA = fechaActual.getTime();
			long msecFN = fecnac.getTime();
			long msecEdad = msecFA - msecFN;
			long dias = msecEdad / (24 * 60 * 60 * 1000);
			long años = dias / año;
			return (int) años;
		}
		return null;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	@Override
	public String toString() {
		return "Paciente [idPaciente=" + idPaciente + ", nombre=" + nombre
				+ ", apepat=" + apepat + ", apemat=" + apemat + ", dni=" + dni
				+ ", fecnac=" + fecnac + ", sexo=" + sexo + ", direccion="
				+ direccion + ", telefono=" + telefono + ", celular=" + celular
				+ ", email=" + email + ", histClinica=" + histClinica
				+ ", idDistrito=" + idDistrito + ", idTipoPaciente="
				+ idTipoPaciente + ", idInstitucion=" + idInstitucion
				+ ", fax=" + fax + ", nombreTipoPaciente=" + nombreTipoPaciente
				+ ", nombreInstitucion=" + nombreInstitucion
				+ ", nombreDepartamento=" + nombreDepartamento
				+ ", nombreDistrito=" + nombreDistrito + ", nombreProvincia="
				+ nombreProvincia + ", nombreTarifa=" + nombreTarifa
				+ ", edad=" + edad + "]";
	}
	
}
