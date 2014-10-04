package com.factulab.dao.form;

import java.io.Serializable;
import java.math.BigDecimal;

import com.factulab.dao.bean.Analisis;
import com.factulab.dao.util.DAOUtil;
import com.factulab.service.util.ServiceConstante;

public class AnalisisForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Integer seccion;
	private String idAnalisisOmega;
	private String abreviatura;
	private Integer idAnalisis;
	private String nombreSeccion;
	
	private BigDecimal precioUnitSinTarifa;
	private BigDecimal precioUnitConTarifa;
	private BigDecimal precioUnitConDescuento;
	private Integer cantidad;
	private BigDecimal totalSinDescuento;
	private BigDecimal totalConDescuento;
	private Integer idInstitucionDescuento; 

	private String[] detalleMonto;
	
	
	public AnalisisForm(Analisis analisis) {
		super();
		this.nombre = analisis.getNombre();
		this.seccion = analisis.getSeccion();
		this.idAnalisisOmega = analisis.getIdAnalisisOmega();
		this.abreviatura = analisis.getAbreviatura();
		this.idAnalisis = analisis.getIdAnalisis();
		this.nombreSeccion = analisis.getNombreSeccion();
		this.precioUnitSinTarifa = analisis.getPrecio();
	}
	public AnalisisForm() {
		super();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getSeccion() {
		return seccion;
	}
	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}
	public String getIdAnalisisOmega() {
		return idAnalisisOmega;
	}
	public void setIdAnalisisOmega(String idAnalisisOmega) {
		this.idAnalisisOmega = idAnalisisOmega;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public Integer getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	public String getNombreSeccion() {
		return nombreSeccion;
	}
	public void setNombreSeccion(String nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
	}
	public BigDecimal getPrecioUnitSinTarifa() {
		return precioUnitSinTarifa;
	}
	public void setPrecioUnitSinTarifa(BigDecimal precioUnitSinTarifa) {
		this.precioUnitSinTarifa = precioUnitSinTarifa;
	}
	public BigDecimal getPrecioUnitConTarifa() {
		return precioUnitConTarifa;
	}
	public void setPrecioUnitConTarifa(BigDecimal precioUnitConTarifa) {
		this.precioUnitConTarifa = precioUnitConTarifa;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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
	public BigDecimal getPrecioUnitConDescuento() {
		return precioUnitConDescuento;
	}
	public void setPrecioUnitConDescuento(BigDecimal precioUnitConDescuento) {
		this.precioUnitConDescuento = precioUnitConDescuento;
	}
	@Override
	public String toString() {
		return "AnalisisForm [nombre=" + nombre + ", seccion=" + seccion
				+ ", idAnalisisOmega=" + idAnalisisOmega + ", abreviatura="
				+ abreviatura + ", idAnalisis=" + idAnalisis
				+ ", nombreSeccion=" + nombreSeccion + ", precioUnitSinTarifa="
				+ precioUnitSinTarifa + ", precioUnitConTarifa="
				+ precioUnitConTarifa + ", cantidad=" + cantidad
				+ ", totalSinDescuento=" + totalSinDescuento
				+ ", totalConDescuento=" + totalConDescuento
				+ ", idInstitucionDescuento=" + idInstitucionDescuento + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAnalisis == null) ? 0 : idAnalisis.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnalisisForm other = (AnalisisForm) obj;
		if (idAnalisis == null) {
			if (other.idAnalisis != null)
				return false;
		} else if (!idAnalisis.equals(other.idAnalisis))
			return false;
		return true;
	}
	
	public String getPrecioUnitSinTarifaString(){
		return DAOUtil.formatearBigDecimal(this.precioUnitSinTarifa);
	}
	public String getPrecioUnitConTarifaString(){
		return DAOUtil.formatearBigDecimal(this.precioUnitConTarifa);
	}
	public String getTotalSinDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.totalSinDescuento);
	}
	public String getTotalConDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.totalConDescuento);
	}
	public String getPrecioUnitConDescuentoString(){
		return DAOUtil.formatearBigDecimal(this.precioUnitConDescuento);
	}
	public Integer getIdInstitucionDescuento() {
		return idInstitucionDescuento;
	}
	public void setIdInstitucionDescuento(Integer idInstitucionDescuento) {
		this.idInstitucionDescuento = idInstitucionDescuento;
	}
	public String[] getDetalleMonto() {
		return detalleMonto;
	}
	public void setDetalleMonto(String[] detalleMonto) {
		this.detalleMonto = detalleMonto;
	}
	public String getDetalleMontoString(){
		if(detalleMonto != null && detalleMonto.length > 0) {
			StringBuilder s = new StringBuilder();
			for (String d: detalleMonto) {
				s.append(d).append(ServiceConstante.CONCATENADOR);
			}
			return s.toString();
		} else {
			return "";
		}
	}
}
