package com.factulab.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.TicketDAO;
import com.factulab.dao.TicketDetalleDAO;
import com.factulab.dao.bean.Ticket;
import com.factulab.dao.bean.TicketDetalle;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;
import com.factulab.dao.form.AtencionPendienteDetalle;
import com.factulab.dao.form.AtencionPendienteForm;
import com.factulab.dao.form.TicketDetalleForm;
import com.factulab.dao.form.TicketForm;
import com.factulab.dao.form.TicketPendienteDetalleForm;
import com.factulab.dao.form.TicketPendienteForm;
import com.factulab.dao.util.DAOConstante;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceUtil;

public class TicketService {
	Logger miLog = Logger.getLogger(TicketService.class);
	
	TicketDAO ticketDAO = new TicketDAO();
	TicketDetalleDAO ticketDetalleDAO = new TicketDetalleDAO();
	
	public void crearTicket(AtencionForm atencionForm, Usuario usuarioLogin) throws DAOException, ServiceException {
		try{
		    /***************************************************
		     *                TICKET 
		     ***************************************************/
			Ticket ticket= new Ticket();
			ticket.setIdPaciente(atencionForm.getPaciente().getIdPaciente());
			ticket.setIdInstitucion(atencionForm.getInstitucion().getIdInstitucion());
			ticket.setNomsede(usuarioLogin.getNombreSede());
			ticket.setNomusuario(usuarioLogin.getUsuario());
			ticket.setIdFranquicia(usuarioLogin.getIdSede());
			ticket.setImpresora(usuarioLogin.getSerieImpresora());
			ticket.setTipopago(atencionForm.getTipoPago());
			ticket.setIdUsuario(usuarioLogin.getIdUsuario());
			
			/**
			 * TOTAL/SUBTOTAL/IGV
			 */
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal igv = BigDecimal.ZERO;
			BigDecimal total = atencionForm.getTotalConDescuento();
			if(atencionForm.getConIGV()){
				ticket.setRuc(atencionForm.getInstitucion().getRuc());
				ticket.setNombre(atencionForm.getInstitucion().getNombre());
				ticket.setDireccion(atencionForm.getInstitucion().getDireccion());
				subtotal = ServiceUtil.calcularSubTotal(atencionForm.getTotalConDescuento());
				igv = ServiceUtil.calcularIGV(atencionForm.getTotalConDescuento());
			} else {
				ticket.setRuc(null);
				ticket.setNombre(atencionForm.getPaciente().getNombre());
				ticket.setDireccion(atencionForm.getPaciente().getDireccion());
			}
			ticket.setSubtotal(subtotal.doubleValue());
			ticket.setIgv(igv.doubleValue());
			ticket.setTotal(total.doubleValue());
			ticketDAO.create(ticket, usuarioLogin.getIdImpresora(), DAOConstante.BD_TIPO_TICKET_INMEDIATA);
//			miLog.info("Ticket["+ticket+"] creado.");
			atencionForm.setNumSerie(ticket.getNumSerie());
			atencionForm.setNumTicket(ticket.getNumTicket());
			atencionForm.setIdTicket(ticket.getIdTicket());
			
			/***************************************************
		     *                TICKET DETALLE 
		     ***************************************************/
			 TicketDetalle detalle = null;
			 int item = 0;
		     for (AnalisisForm a : atencionForm.getlAnalisis()) {
		    	detalle = new TicketDetalle();
		        detalle.setCantidad(a.getCantidad());
		        detalle.setIdAnalisis(a.getIdAnalisis());
		        detalle.setDescripcion(a.getNombre());
		        detalle.setIdAtencion(atencionForm.getIdAtencion());
		        detalle.setIdTicket(ticket.getIdTicket());
		        detalle.setImporte(a.getTotalConDescuento());
		        detalle.setNumItem(item);
		        detalle.setNumSerie(ticket.getNumSerie());
		        detalle.setNumTicket(ticket.getNumTicket());
	        	ticketDetalleDAO.create(detalle);
	        	item++;
			}
		     miLog.info("Nuevo Ticket["+ticket.toString()+"] creado con ["+atencionForm.getlAnalisis().size()+"] TicketDetalles."+usuarioLogin.getLogUser());
		} catch(DAOException ex){
			throw new DAOException(ex); 
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	} 
	
	public void crearTicketPendiente(AtencionPendienteForm atencionForm, Usuario usuarioLogin) throws DAOException, ServiceException {
		try{
		    /***************************************************
		     *                TICKET 
		     ***************************************************/
			Ticket ticket= new Ticket();
			//ticket.setIdPaciente(atencionForm.getPaciente().getIdPaciente());
			ticket.setIdPaciente(0);
			ticket.setIdInstitucion(atencionForm.getInstitucion().getIdInstitucion());
			ticket.setNomsede(usuarioLogin.getNombreSede());
			ticket.setNomusuario(usuarioLogin.getUsuario());
			ticket.setIdUsuario(usuarioLogin.getIdUsuario());
			ticket.setIdFranquicia(usuarioLogin.getIdSede());
			ticket.setImpresora(usuarioLogin.getSerieImpresora());
			ticket.setTipopago(atencionForm.getTipoPago());
			
			ticket.setRuc(atencionForm.getInstitucion().getRuc());
			ticket.setNombre(atencionForm.getInstitucion().getNombre());
			ticket.setDireccion(atencionForm.getInstitucion().getDireccion());
			
			/**
			 * TOTAL/SUBTOTAL/IGV
			 */
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal igv = BigDecimal.ZERO;
			BigDecimal total = atencionForm.getTotalConDescuento();
			if(atencionForm.getConIGV()){
				subtotal = ServiceUtil.calcularSubTotal(atencionForm.getTotalConDescuento());
				igv = ServiceUtil.calcularIGV(atencionForm.getTotalConDescuento());
			} 
			ticket.setSubtotal(subtotal.doubleValue());
			ticket.setIgv(igv.doubleValue());
			ticket.setTotal(total.doubleValue());
			ticketDAO.create(ticket, usuarioLogin.getIdImpresora(),DAOConstante.BD_TIPO_TICKET_PENDIENTE);
//			miLog.info("Ticket["+ticket+"] Pendiente creado.");
			atencionForm.setNumSerie(ticket.getNumSerie());
			atencionForm.setNumTicket(ticket.getNumTicket());
			atencionForm.setIdTicket(ticket.getIdTicket());
			
			/***************************************************
		     *                TICKET DETALLE 
		     ***************************************************/
			 TicketDetalle detalle = null;
			 int item = 0;
		     for (AtencionPendienteDetalle a : atencionForm.getlAtencionDetalle()) {
		    	detalle = new TicketDetalle();
		        detalle.setCantidad(a.getCantidad());
		        detalle.setIdAnalisis(a.getIdAnalisis());
		        detalle.setDescripcion(a.getNombreAnalisis());
		        detalle.setIdAtencion(a.getIdAtencion());
		        detalle.setIdTicket(ticket.getIdTicket());
		        detalle.setImporte(a.getMonto()); //validar
		        detalle.setNumItem(item);
		        detalle.setNumSerie(ticket.getNumSerie());
		        detalle.setNumTicket(ticket.getNumTicket());
	        	ticketDetalleDAO.create(detalle);
	        	item++;
			}
	    	miLog.info("Nuevo TicketPendiente["+ticket+"] creado con ["+atencionForm.getlAtencionDetalle().size()+"] TicketDetalles."+usuarioLogin.getLogUser());
		} catch(DAOException ex){
			throw new DAOException(ex); 
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	} 
	
	public TicketForm obtenerTicketForm(long idticket) throws DAOException, FactulabException {
		return ticketDAO.find(idticket);
	}  
	
	public List<TicketDetalleForm> obtenerTicketDetalleForm(long idticket) throws DAOException, FactulabException {
		return ticketDetalleDAO.find(idticket);
	} 
	
	public TicketPendienteForm obtenerTicketPendienteForm(long idticket) throws DAOException, FactulabException {
		return ticketDAO.findPendiente(idticket);
	}
	
	public List<TicketPendienteDetalleForm> obtenerTicketPendinteDetalleForm(long idticket) throws DAOException {
		return ticketDetalleDAO.findPendiente(idticket);
	} 
}
