package com.factulab.dao.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
numSerie	smallint	
numItem		smallint	
numTicket	bigint		
cantidad	smallint	
descripcion	varchar(200)	
importe		decimal(18, 2)	
idAtencion	int			
idTicketDetalle	bigint	
idTicket	bigint		
idAnalisis	smallint	

 * @author luis
 *
 */
public class TicketDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer numSerie;
    private Integer numItem;
    private long numTicket;
    private Integer cantidad;
    private String descripcion;
    private BigDecimal importe;

    private Integer idAtencion;
    private long idTicketDetalle;
    private long idTicket;
    private Integer idAnalisis;
	public Integer getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(Integer numSerie) {
		this.numSerie = numSerie;
	}
	public Integer getNumItem() {
		return numItem;
	}
	public void setNumItem(Integer numItem) {
		this.numItem = numItem;
	}
	public long getNumTicket() {
		return numTicket;
	}
	public void setNumTicket(long numTicket) {
		this.numTicket = numTicket;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public Integer getIdAtencion() {
		return idAtencion;
	}
	public void setIdAtencion(Integer idAtencion) {
		this.idAtencion = idAtencion;
	}
	public long getIdTicketDetalle() {
		return idTicketDetalle;
	}
	public void setIdTicketDetalle(long idTicketDetalle) {
		this.idTicketDetalle = idTicketDetalle;
	}
	public long getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(long idTicket) {
		this.idTicket = idTicket;
	}
	public Integer getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	@Override
	public String toString() {
		return "TicketDetalle [numSerie=" + numSerie + ", numItem=" + numItem
				+ ", numTicket=" + numTicket + ", cantidad=" + cantidad
				+ ", descripcion=" + descripcion + ", importe=" + importe
				+ ", idAtencion=" + idAtencion + ", idTicketDetalle="
				+ idTicketDetalle + ", idTicket=" + idTicket + ", idAnalisis="
				+ idAnalisis + "]";
	}
    
}

