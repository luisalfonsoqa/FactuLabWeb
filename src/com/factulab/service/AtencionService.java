package com.factulab.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.AtencionDAO;
import com.factulab.dao.AtencionDetalleDAO;
import com.factulab.dao.bean.Atencion;
import com.factulab.dao.bean.AtencionDetalle;
import com.factulab.dao.bean.ConstantesBD;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;
import com.factulab.dao.form.AtencionPendiente;
import com.factulab.dao.form.AtencionPendienteDetalle;
import com.factulab.dao.form.AtencionPendienteForm;
import com.factulab.dao.form.AtencionPendienteResumen;
import com.factulab.dao.form.HojaTrabajoDetalleForm;
import com.factulab.dao.form.HojaTrabajoForm;
import com.factulab.dao.form.ReporteAnalisis;
import com.factulab.dao.form.TicketPendienteDetalleForm;
import com.factulab.dao.form.TicketPendienteForm;
import com.factulab.device.Omega;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceConstante;
import com.factulab.service.util.ServiceUtil;

public class AtencionService {
	Logger miLog = Logger.getLogger(AtencionService.class);
	AtencionDetalleDAO atencionDetalleDAO = new AtencionDetalleDAO();
	AtencionDAO atencionDAO = new AtencionDAO();
	
//	public void cargarResumenAtencion(AtencionForm atencionForm) {
//		System.out.println("CargarResumen AtencionForm["+atencionForm.toString()+"]");
//	}
	
	public void crearAtencion(AtencionForm atencionForm, Usuario usuarioLogin, ConstantesBD constantesBD) throws FactulabException, DAOException, ServiceException {
		try{
			Atencion atencion = new Atencion();
	        atencion.setIdInstitucion(atencionForm.getInstitucion().getIdInstitucion());
	        atencion.setIdMedico(atencionForm.getMedico().getIdMedico());
	        atencion.setIdPaciente(atencionForm.getPaciente().getIdPaciente());
	        atencion.setIdUsuario(usuarioLogin.getIdUsuario());
	        atencion.setSubtotal(atencionForm.getTotalSinDescuento());
	        atencion.setDescuento(atencionForm.getPorcentajeDescuento()); 
	        if(atencion.getDescuento() == null) atencion.setDescuento(BigDecimal.ZERO);
	        atencion.setTotal(atencionForm.getTotalConDescuento());
	        atencion.setIdFranquicia(usuarioLogin.getIdSede());
	        atencionDAO.create(atencion);
//	        miLog.info("Nueva Atencion creada ["+atencion+"] creada. "+usuarioLogin.getLogUser());
	        AtencionDetalle detalle = null;
	        for (AnalisisForm analisis : atencionForm.getlAnalisis()) {
	        	detalle = new AtencionDetalle();
	        	detalle.setIdAtencion(atencion.getIdAtencion());
	        	detalle.setIdAnalisis(analisis.getIdAnalisis());
	        	detalle.setCantidad(analisis.getCantidad());
	        	detalle.setMonto(analisis.getTotalConDescuento());
	        	detalle.setPrecio(analisis.getPrecioUnitConTarifa());
	        	detalle.setPrecioUnitario(analisis.getPrecioUnitConDescuento());
	        	detalle.setDetalleMonto(analisis.getDetalleMontoString());
	        	atencionDetalleDAO.create(detalle);
			}
	        atencionForm.setIdAtencion(atencion.getIdAtencion());
	    	atencionForm.setCodigoOmega(atencion.getCodOmega());
	    	miLog.info("Nueva Atencion["+atencion.toString()+"] creada con ["+atencionForm.getlAnalisis().size()+"] AtencionDetalles."+usuarioLogin.getLogUser());
	    	new Omega().generaArchivoDesdeAtencion(atencionForm, constantesBD.getOmegaLocal(), constantesBD.getOmegaRemoto());
	    	miLog.info("Archivo Omega ["+atencionForm.getCodigoOmega()+"] generado."+usuarioLogin.getLogUser());
		} catch(DAOException ex){
			throw new DAOException(ex); 
		} catch(FactulabException ex){
			throw new DAOException(ex); 
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
	
	public void facturarAtencion(AtencionForm atencionForm) throws DAOException {
		 atencionDAO.facturarAtencion(atencionForm.getIdAtencion());
	}
	
	public void facturarAtencionesPendientes(Integer idInstitucion, String lstIds) throws DAOException {
		atencionDAO.facturarAtenciones(idInstitucion, lstIds);
	}
	
	public List<HojaTrabajoDetalleForm> obtenerHojaTrabajoDetalle(long id) throws DAOException {
		return atencionDetalleDAO.obtenerDetalleHT(id);
	}
	
	public HojaTrabajoForm obtenerHojaTrabajo(long id) throws DAOException {
		return atencionDAO.obtenerHT(id);
	}
	
	public List<AtencionPendiente> obtenerAtencionesPendientes(Integer idInstitucion, Integer sede, Date fechaIni, Date fechaFin) throws DAOException{
		List<AtencionPendiente> lAtencion =  atencionDAO.findPendientesPorInstitucion(idInstitucion, sede, fechaIni, fechaFin);		
		return lAtencion;
	}
	
	public List<AtencionPendienteDetalle> obtenerAtencionesPorID(Integer idInstitucion, String lstIds) throws DAOException {
		List<AtencionPendienteDetalle> list = new ArrayList<AtencionPendienteDetalle>();
		list = atencionDAO.findDetallePendientesPorInstitucion(lstIds, idInstitucion);
		return list;
	}
	
	/***
	 * Creado para obtener el resumen de Tickets
	 * @param ticket
	 * @return
	 * @throws ServiceException
	 */
	public List<AtencionPendienteResumen> obtenerAtencionPendienteResumen(TicketPendienteForm ticket) throws ServiceException {
		List<AtencionPendienteResumen> lResumen = new ArrayList<AtencionPendienteResumen>();
		try{
			AtencionPendienteResumen resumen = new AtencionPendienteResumen();
			AtencionPendienteResumen r;
			Integer index;
			boolean nuevo;
			
			BigDecimal total = BigDecimal.ZERO;
			for (TicketPendienteDetalleForm d : ticket.getlDetalle()) {
				index = null;
				nuevo = true;
				resumen = new AtencionPendienteResumen(d.getIdAnalisis(), d.getCantidad(), d.getDescripcion(), new BigDecimal(d.getImporte()));
				/**
				 * NEW / EDIT
				 */
				index = lResumen.indexOf(resumen);
				if(index != -1) {
					r = lResumen.get(index);
					r.setCantidad(r.getCantidad()+d.getCantidad());
					r.setMonto(r.getMonto().add(new BigDecimal(d.getImporte())));
					nuevo = false;
				}
				if(nuevo)
					lResumen.add(resumen);
				/**
				 * TOTAL
				 */
				total = total.add(new BigDecimal(d.getImporte()));
			}
			//ticket.setlAtencionResumen(lResumen);
			//ticket.setTotalConDescuento(total);
			
			/**
			 * ATENCION PENDIENTE VIENE CON: TOTAL/SUBTOTAL/IGV
			 */
			//BigDecimal subtotal = BigDecimal.ZERO;
			//BigDecimal igv = BigDecimal.ZERO;
			//subtotal = Util.calcularSubTotal(total);
			//igv = Util.calcularIGV(total);
			//ticket.setSubtotal(subtotal);
			//ticket.setIgv(igv);
			return lResumen;
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
	/**
	 * Creado para obtener el resumen de Atenciones
	 * @param atencion
	 * @return
	 * @throws ServiceException
	 */
	public List<AtencionPendienteResumen> obtenerAtencionPendienteResumen(AtencionPendienteForm atencion) throws ServiceException {
		List<AtencionPendienteResumen> lResumen = new ArrayList<AtencionPendienteResumen>();
		try{
			AtencionPendienteResumen resumen = new AtencionPendienteResumen();
			AtencionPendienteResumen r;
			Integer index;
			boolean nuevo;
			
			BigDecimal total = BigDecimal.ZERO;
			for (AtencionPendienteDetalle d : atencion.getlAtencionDetalle()) {
				index = null;
				nuevo = true;
				resumen = new AtencionPendienteResumen(d.getIdAnalisis(), d.getCantidad(), d.getNombreAnalisis(), d.getMonto());
				/**
				 * NEW / EDIT
				 */
				index = lResumen.indexOf(resumen);
				if(index != -1) {
					r = lResumen.get(index);
					r.setCantidad(r.getCantidad()+d.getCantidad());
					r.setMonto(r.getMonto().add(d.getMonto()));
					nuevo = false;
				}
				if(nuevo)
					lResumen.add(resumen);
				/**
				 * TOTAL
				 */
				total = total.add(d.getMonto());
			}
			atencion.setlAtencionResumen(lResumen);
			atencion.setTotalConDescuento(total);
			
			/**
			 * ATENCION PENDIENTE VIENE CON: TOTAL/SUBTOTAL/IGV
			 */
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal igv = BigDecimal.ZERO;
			subtotal = ServiceUtil.calcularSubTotal(total);
			igv = ServiceUtil.calcularIGV(total);
			atencion.setSubtotal(subtotal);
			atencion.setIgv(igv);
			return lResumen;
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
	
	public ReporteAnalisis obtenerAtencionesTomadas(Integer idUsuario, Date fechaIni, Date fechaFin, String turno) throws DAOException, FactulabException, ServiceException {
		try{
			ReporteAnalisis rep = new ReporteAnalisis();
			
			if(turno.equals(ServiceConstante.TURNO_MAÑANA)){
				rep.setFechaFin(fechaFin);
				rep.setFechaInicio(fechaIni);
				rep.setHoraIncio(ServiceConstante.TURNO_MAÑANA_INI);
				rep.setHoraFin(ServiceConstante.TURNO_MAÑANA_FIN);
				rep.setTurno(ServiceConstante.TURNO_MAÑANA);
			} else if(turno.equals(ServiceConstante.TURNO_TARDE)){
				rep.setFechaFin(fechaFin);
				rep.setFechaInicio(fechaIni);
				rep.setHoraIncio(ServiceConstante.TURNO_TARDE_INI);
				rep.setHoraFin(ServiceConstante.TURNO_TARDE_FIN);
				rep.setTurno(ServiceConstante.TURNO_TARDE);
			} else if(turno.equals(ServiceConstante.TURNO_TODOS)){
				rep.setFechaFin(fechaFin);
				rep.setFechaInicio(fechaIni);
				rep.setHoraIncio(null);
				rep.setHoraFin(null);
				rep.setTurno(ServiceConstante.TURNO_TODOS);
			}
			
			/** POR DEFECTO CARGA SOLO LAS ATENCIONES DEL USUARIO **/
			rep.setIdUsuario(idUsuario);
			rep.setlDetalle(atencionDAO.findAtencionesTomadas(rep.getIdUsuario(), rep.getFechaInicio(), rep.getFechaFin(), rep.getHoraIncio(), rep.getHoraFin()));
			
			return rep;
		} catch(DAOException ex){
			throw new DAOException(ex); 
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
}
