package com.factulab.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import com.factulab.dao.AnalisisDAO;
import com.factulab.dao.bean.Analisis;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;
import com.factulab.service.exception.ServiceException;
import com.factulab.service.util.ServiceUtil;


public class AnalisisService {
	Logger miLog = Logger.getLogger(AnalisisService.class);
	 
	AnalisisDAO analisisDAO = new AnalisisDAO();
	public Analisis obtenerAnalisisPorID(Integer idAnalisis) throws FactulabException, DAOException{
		Analisis analisis = analisisDAO.find(idAnalisis);
		return analisis;
	}
	
	public void agregarAlaAtencion(AtencionForm atencionForm, Analisis analisis) throws ServiceException{
		 for (int x = 0; x < atencionForm.getlAnalisis().size(); x++) {
            	 if(analisis.getIdAnalisis().compareTo(atencionForm.getlAnalisis().get(x).getIdAnalisis()) ==0){
            	 /***
            	  * IDAnalisis Existente
            	  */
            	 agregarAnalisisExistente(atencionForm, atencionForm.getlAnalisis().get(x), x);
            	 return;
             }
         }
		 
		 /**
          * IDAnalisis Nuevo
          */
		 
		 AnalisisForm analisisForm = new AnalisisForm(analisis);
		 analisisForm.setCantidad(1);
		 //miLog.info("Agregar Analisis nuevo al listado. analisisForm["+analisisForm.toString() + "] atencionForm["+ atencionForm.toString()+"]");
         agregarAnalisisNuevo(atencionForm, analisisForm);
	}
	
	public void eliminarDelaAtencion(AtencionForm atencionForm, Integer idAnalisis) throws ServiceException{
		 for (int x = 0; x < atencionForm.getlAnalisis().size(); x++) {
            if (idAnalisis == atencionForm.getlAnalisis().get(x).getIdAnalisis()) {
            	eliminarAnalisis(atencionForm, atencionForm.getlAnalisis().get(x));
            	break;
            }
        }
	}
	
	public void actualizarDescuentoAtencion(AtencionForm atencionForm) throws ServiceException{
		actualizarAcomulados(atencionForm);
	}

	public List<Analisis> obtenerPorCriterio(String criterio, String texto) throws FactulabException, DAOException{
		return analisisDAO.findPorCriterio(criterio, texto);
	}
	
	/**
	 * PRIVADOS
	 */
	
	/**
	 * El AnalisiForm debe contar con la cantidad y todos los datos
	 * @param atencionForm
	 * @param analisisForm
	 */
	private void agregarAnalisisNuevo(AtencionForm atencionForm, AnalisisForm analisisForm) throws ServiceException{
		try{
			//System.out.println("ID de analisis no encontrado, agregando AnalisisForm["+analisisForm.getIdAnalisis()+"]");
	        //Actualizamos el precio, considerando el descuento por Institucion y Tarifa
			ServiceUtil.obtenerPrecioConDescuentos(atencionForm, analisisForm);
	        atencionForm.getlAnalisis().add(analisisForm);
	        actualizarAcomulados(atencionForm);
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
	
	  
	/**
	 * Agregar un analisis existente
	 * @param atencionForm
	 * @param index
	 */
	private void agregarAnalisisExistente(AtencionForm atencionForm, AnalisisForm analisisForm, Integer index) throws ServiceException{
		try{
	//		System.out.println("ID de analisis encontrado, actualizando  AnalisisForm["+analisisForm.getIdAnalisis()+"] Index["+index+"]");
			analisisForm.setCantidad(analisisForm.getCantidad()+1);
			actualizarAcomulados(atencionForm);
			atencionForm.getlAnalisis().set(index, analisisForm);
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
	
	/**
	 * Eliminas un analisis
	 * @param atencionForm
	 * @param index
	 */
	private void eliminarAnalisis(AtencionForm atencionForm, AnalisisForm analisisForm) throws ServiceException{
		try {	
			atencionForm.getlAnalisis().remove(analisisForm);
			actualizarAcomulados(atencionForm);
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	}
	
	/**
	 * Calcula TOTAL, SUBTOTAL, TOTAL ACOMULADO, SUBTOTAL ACOMULADO
	 * Se redondea el (redondea (precio unitario - descuento) x la cantidad)
	 * @param atencionForm
	 */
	private void actualizarAcomulados(AtencionForm atencionForm) throws ServiceException{
		try{
		   BigDecimal descuentoPorUnidad  = BigDecimal.ZERO;
		   BigDecimal precioUnitarioConDescuento = BigDecimal.ZERO;
		   BigDecimal subTotalAcomulado = BigDecimal.ZERO;
		   BigDecimal subTotal = BigDecimal.ZERO;
		   BigDecimal totalAcomulado = BigDecimal.ZERO;
		   BigDecimal total = BigDecimal.ZERO;
		
		   for (AnalisisForm analisisForm : atencionForm.getlAnalisis()) {
		       /**
		        * Recalculamos el Total sin descuento
		        */
			   subTotal = ServiceUtil.redondearNumero(analisisForm.getPrecioUnitConTarifa().multiply(new BigDecimal(analisisForm.getCantidad())));
		       subTotalAcomulado = subTotalAcomulado.add(subTotal); //TOTAL ACOMULADO SIN DESCUENTO
		       analisisForm.setTotalSinDescuento(subTotal);
		       //System.out.println("AnalisisServioce - actualizarAcomulados - subTotal - "+analisisForm.getTotalSinDescuento() + " "+subTotal);
		
		       /**
		        * Recalculamos el Total con descuento
		        */
		       descuentoPorUnidad = analisisForm.getPrecioUnitConTarifa().multiply(atencionForm.getPorcentajeDescuento()).divide(new BigDecimal("100"));
		       precioUnitarioConDescuento = ServiceUtil.redondearNumero(analisisForm.getPrecioUnitConTarifa().subtract(descuentoPorUnidad));
		       analisisForm.setPrecioUnitConDescuento(precioUnitarioConDescuento);
		       total = precioUnitarioConDescuento.multiply(new BigDecimal(Integer.toString(analisisForm.getCantidad())));
		       analisisForm.setTotalConDescuento(total);
		       totalAcomulado = totalAcomulado.add(total); //TOTAL ACOMULADO CON DESCUENTO
		       //System.out.println("AnalisisServioce - actualizarAcomulados - total - "+analisisForm.getTotalConDescuento() + " "+total);
		   }
		   atencionForm.setTotalConDescuento(totalAcomulado);
		   atencionForm.setTotalSinDescuento(subTotalAcomulado);
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	};

}
