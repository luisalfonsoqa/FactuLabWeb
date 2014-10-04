package com.factulab.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.factulab.dao.bean.InstiAnali;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;

public class ServiceUtil {
	public static BigDecimal redondearNumero(BigDecimal bd) {
		bd = bd.setScale(ServiceConstante.REDONDEO_DECIMAL, BigDecimal.ROUND_HALF_UP);
		return bd;
	}
	
	/**
	 * Precio <-- Descuento por Institucion y Tarifa(idInstitucion, idAnalisis, precio)
	 * @param analisisForm
	 * @param analisis
	 * @param idInstitucion
	 */
    public static void obtenerPrecioConDescuentos(AtencionForm atencionForm, AnalisisForm analisisForm) throws Exception{
    	for (InstiAnali instiAnali : atencionForm.getlInstiAnali()) {
    		if(analisisForm.getIdAnalisis().compareTo(instiAnali.getIdAnalisis()) == 0){
    			//Set precio con Descuentos por Institucion 
    			analisisForm.setPrecioUnitSinTarifa(obtenerPrecioConDescuentosPorInstitucion(
    					instiAnali.getIdInstitucion(), 
    					instiAnali.getIdAnalisis(), 
            			analisisForm.getPrecioUnitSinTarifa(), 
            			instiAnali.getFactor(),
            			instiAnali.getTipoMonto()));
    			//Validacion Adicional
    			if(instiAnali.getIdInstitucion().compareTo(atencionForm.getInstitucion().getIdInstitucion()) != 0)
    				throw new FactulabException("Problema de integridad de Datos. La Institucion ["+atencionForm.getInstitucion().getIdInstitucion()+"] no coincide con la institución ["+instiAnali.getIdInstitucion()+"] del analisis con descuento por institucion.");
    			analisisForm.setIdInstitucionDescuento(atencionForm.getInstitucion().getIdInstitucion());
    		}
		}
    	
        //Descuentos por Tarifa
    	analisisForm.setPrecioUnitConTarifa(obtenerPrecioConDescuentosPorTarifa(atencionForm.getInstitucion().getIdInstitucion(), analisisForm.getPrecioUnitSinTarifa(), atencionForm.getTarifa().getFactor()));
    }

    /**
     * Descuento por Institucion (precioSinTarifa)
     */
    private static BigDecimal obtenerPrecioConDescuentosPorInstitucion(Integer idInstitucion, Integer idAnalisis, BigDecimal precio, BigDecimal factorInstitucion, Integer tipoFactorInstitucion){
		BigDecimal precioConDescuentoPorInstitucion = BigDecimal.ZERO;
		BigDecimal descuentoPorInstitucion = BigDecimal.ZERO;
		if (tipoFactorInstitucion == 1) {
			descuentoPorInstitucion = factorInstitucion;
		} else if (tipoFactorInstitucion == 2) {
			descuentoPorInstitucion = precio.multiply(factorInstitucion).divide(new BigDecimal("100"));
		}

		precioConDescuentoPorInstitucion = ServiceUtil.redondearNumero(precio.subtract(descuentoPorInstitucion));
//		System.out.println("Analisis con descuento idAnalisis["+idAnalisis+"] idInstitucion["+idInstitucion+"] Precio Unitario Final["+precioConDescuentoPorInstitucion+"]");
		return precioConDescuentoPorInstitucion;
	}

     /**
     * Descuento por Tarifa (PrecioUnitarioConDespuestPorInstitucionAnalisis)
     */
    private static BigDecimal obtenerPrecioConDescuentosPorTarifa(Integer idInstitucion, BigDecimal precio, BigDecimal factorTarifa){
        BigDecimal precioConDescuentoPorTarifa = BigDecimal.ZERO;
        BigDecimal descuentoPorTarifa = BigDecimal.ZERO;
        descuentoPorTarifa = precio.multiply(factorTarifa);
        precioConDescuentoPorTarifa =  ServiceUtil.redondearNumero(precio.subtract(descuentoPorTarifa));
        return precioConDescuentoPorTarifa;
    }

    /***************************************************
     *                CALCULAR IGV
     ***************************************************/
    
    public static BigDecimal calcularIGV(BigDecimal total) {
        BigDecimal porcentajeIGV = ServiceConstante.IGV;
        BigDecimal igv = total.multiply(porcentajeIGV).divide(ServiceConstante.BIGDECIMAL_100.add(porcentajeIGV), 2, RoundingMode.HALF_UP);
        return redondearNumero(igv);
    }
    public static BigDecimal calcularSubTotal(BigDecimal total) {
        BigDecimal porcentajeIGV =  ServiceConstante.IGV;
        BigDecimal subTotal = total.multiply(ServiceConstante.BIGDECIMAL_100).divide(ServiceConstante.BIGDECIMAL_100.add(porcentajeIGV), 2, RoundingMode.HALF_UP);
        return redondearNumero(subTotal);
    }
    
    /***************************************************
     *                UTILIDADES
     ***************************************************/
    
    
    
//    public static String llenaCerosIzquierda(int dato, int cantidad) {
//        StringBuilder resulta = new StringBuilder();
//        for (int i = 0; i < cantidad; i++) {
//            resulta.append("0");
//        }
//        resulta.append(dato);
//        resulta = new StringBuilder(resulta.substring(resulta.length() - cantidad));
//        return resulta.toString();
//    }
//
//    public static String llenaCerosIzquierda(BigInteger dato, int cantidad) {
//        StringBuilder resulta = new StringBuilder();
//        for (int i = 0; i < cantidad; i++) {
//            resulta.append("0");
//        }
//        resulta.append(dato);
//        resulta = new StringBuilder(resulta.substring(resulta.length() - cantidad));
//        return resulta.toString();
//    }
    
    
    
    /***************************************************
     *                EXCEPTION
     ***************************************************/
//    
//    public static void printSQLException(SQLException ex) {
//
//        for (Throwable e : ex) {
//            if (e instanceof SQLException) {
//                if (ignoreSQLException(
//                    ((SQLException)e).
//                    getSQLState()) == false) {
//
//                    e.printStackTrace(System.err);
//                    System.err.println("SQLState: " +
//                        ((SQLException)e).getSQLState());
//
//                    System.err.println("Error Code: " +
//                        ((SQLException)e).getErrorCode());
//
//                    System.err.println("Message: " + e.getMessage());
//
//                    Throwable t = ex.getCause();
//                    while(t != null) {
//                        System.out.println("Cause: " + t);
//                        t = t.getCause();
//                    }
//                }
//            }
//        }
//    }
//    
//    public static boolean ignoreSQLException(String sqlState) {
//
//        if (sqlState == null) {
//            System.out.println("The SQL state is not defined!");
//            return false;
//        }
//
//        // X0Y32: Jar file already exists in schema
//        if (sqlState.equalsIgnoreCase("X0Y32"))
//            return true;
//
//        // 42Y55: Table already exists in schema
//        if (sqlState.equalsIgnoreCase("42Y55"))
//            return true;
//
//        return false;
//    }



}
