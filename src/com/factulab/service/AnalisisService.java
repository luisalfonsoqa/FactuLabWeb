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
		 /***
		  * IDAnalisis Existente
		  */
		 for (int x = 0; x < atencionForm.getlAnalisis().size(); x++) {
			 if(analisis.getIdAnalisis().compareTo(atencionForm.getlAnalisis().get(x).getIdAnalisis()) ==0){
				
            	 agregarAnalisisExistente(atencionForm, atencionForm.getlAnalisis().get(x), x);
            	 return;
             }
         }
		 
		 /**
          * IDAnalisis Nuevo
          */
		 AnalisisForm analisisForm = new AnalisisForm(analisis);
		 analisisForm.setCantidad(1);
		 analisisForm.setDescuento(BigDecimal.ZERO);
		 //miLog.info("Agregar Analisis nuevo al listado. analisisForm["+analisisForm.toString() + "] atencionForm["+ atencionForm.toString()+"]");
         agregarAnalisisNuevo(atencionForm, analisisForm);
	}
	
	public void eliminarDelaAtencion(AtencionForm atencionForm, Integer idAnalisis) throws ServiceException{
		 for (int x = 0; x < atencionForm.getlAnalisis().size(); x++) {
            if (0 == idAnalisis.compareTo(atencionForm.getlAnalisis().get(x).getIdAnalisis())) {
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
			
			//Set values to AtencionForm from AnalisisForm 
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
			String[] detalle = null;
			
		   BigDecimal descuentoPorUnidad  = BigDecimal.ZERO;
		   BigDecimal precioUnitarioConDescuento = BigDecimal.ZERO;
		   BigDecimal subTotalAcomulado = BigDecimal.ZERO;
		   BigDecimal subTotal = BigDecimal.ZERO;
		   BigDecimal totalAcomulado = BigDecimal.ZERO;
		   BigDecimal total = BigDecimal.ZERO;
		   BigDecimal cantidad = BigDecimal.ZERO;
		
		   for (AnalisisForm analisisForm : atencionForm.getlAnalisis()) {
			   detalle = new String[13];
			   /* LEYENDA
			    0. precio unitario con tarifa
				1. descuento por unidad
				2. precio unitario con descuento - sinredondear
				3. precio unitario con descuento - redondeado
				4. cantidad
				5. subtotal - sin redondear
				6. subtotal - redondeado
				7. subtotal acomulado
				8. total - sin redondear
				9. total - redondeado
				10 total - acomulado
			    */
			   
		       /**
		        * Recalculamos el Total sin descuento
		        */
			   cantidad = new BigDecimal(analisisForm.getCantidad());
			   
//			   System.out.println(analisisForm.getPrecioUnitConTarifa());
			   detalle[0] = analisisForm.getPrecioUnitConTarifa().toString();
//			   System.out.println(analisisForm.getCantidad());
			   detalle[4] = analisisForm.getCantidad().toString();
			   subTotal = analisisForm.getPrecioUnitConTarifa().multiply(cantidad);
//			   System.out.println(subTotal);
			   detalle[5] = subTotal.toString();
			   subTotal = ServiceUtil.redondearNumero(subTotal);
//			   System.out.println(subTotal);
			   detalle[6] = subTotal.toString();
		       subTotalAcomulado = subTotalAcomulado.add(subTotal); //TOTAL ACOMULADO SIN DESCUENTO
		       analisisForm.setTotalSinDescuento(subTotal);
//		       System.out.println(subTotalAcomulado);
		       detalle[7] = subTotalAcomulado.toString();
		       
		       
		       //System.out.println("AnalisisServioce - actualizarAcomulados - subTotal - "+analisisForm.getTotalSinDescuento() + " "+subTotal);
		
		       /**
		        * Recalculamos el Total con el porcentaje de descuento ingresado (%)
		        */
		       //Descuento por Atencion
		       //descuentoPorUnidad = analisisForm.getPrecioUnitConTarifa().multiply(atencionForm.getPorcentajeDescuento()).divide(new BigDecimal("100"));
		       //Descuento por Analisis
		       descuentoPorUnidad = analisisForm.getPrecioUnitConTarifa().multiply(analisisForm.getDescuento()).divide(new BigDecimal("100"));
//		       System.out.println(descuentoPorUnidad);
		       detalle[1] = descuentoPorUnidad.toString();
		       precioUnitarioConDescuento = analisisForm.getPrecioUnitConTarifa().subtract(descuentoPorUnidad);
//		       System.out.println(precioUnitarioConDescuento);
		       detalle[2] = precioUnitarioConDescuento.toString();
		       precioUnitarioConDescuento = ServiceUtil.redondearNumero(precioUnitarioConDescuento);
//		       System.out.println(precioUnitarioConDescuento);
		       detalle[3] = precioUnitarioConDescuento.toString();
		       analisisForm.setPrecioUnitConDescuento(precioUnitarioConDescuento);
		       total = precioUnitarioConDescuento.multiply(cantidad);
		       
//		       System.out.println(total);
		       detalle[8] = total.toString();
		       total = ServiceUtil.redondearNumero(total);
//		       System.out.println(total);
		       detalle[9] = total.toString();
		       analisisForm.setTotalConDescuento(total);
		       totalAcomulado = totalAcomulado.add(total); //TOTAL ACOMULADO CON DESCUENTO
		       detalle[10] = totalAcomulado.toString();
		       //System.out.println("AnalisisServioce - actualizarAcomulados - total - "+analisisForm.getTotalConDescuento() + " "+total);
		       
		       analisisForm.setDetalleMonto(detalle);
//		       System.out.println(analisisForm.getDetalleMonto());
		   }
		   atencionForm.setTotalConDescuento(totalAcomulado);
		   atencionForm.setTotalSinDescuento(subTotalAcomulado);
		} catch(Exception ex){
			throw new ServiceException(ex); 
		}
	};

}
