package com.factulab.servlet.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.factulab.service.util.ServiceConstante;

public class ServletUtil {
	    public static void getReporteXLS(HttpServletResponse response, HashMap parametros, String filename, String reporte, JRBeanCollectionDataSource data) throws Exception{
			response.setContentType("application/vnd.ms-excel");
	        ServletOutputStream servletOutputStream = response.getOutputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        FileInputStream fis;
	        BufferedInputStream bufferedInputStream;
	        fis = new FileInputStream(reporte);
	        bufferedInputStream = new BufferedInputStream(fis);
	          
	        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, data);
	        JRXlsExporter exporterXLS = new JRXlsExporter();
	        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);

	        exporterXLS.exportReport();
	        response.setHeader("Content-disposition", "attachment; filename="+filename); 
	        response.setContentLength(baos.size());
	        baos.writeTo(servletOutputStream);
	 
	        fis.close();
	        bufferedInputStream.close();
		}
	    
	    public static void getReportePDF(HttpServletResponse response, HashMap parametros, String filename, String reporte, JRBeanCollectionDataSource data) throws Exception{
	    	 response.setContentType("application/pdf");
			 
	        // set input and output stream
	        ServletOutputStream servletOutputStream = response.getOutputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        FileInputStream fis;
	        BufferedInputStream bufferedInputStream;
	        fis = new FileInputStream(reporte);
	        bufferedInputStream = new BufferedInputStream(fis);

	        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, data);
	        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
	 
	        response.setContentLength(baos.size());
	        response.setHeader("Content-disposition", "attachment; filename="+filename); 
	        baos.writeTo(servletOutputStream);
	 
	        fis.close();
	        bufferedInputStream.close();
		}
	    /***************************************************
	     *                UTILIDADES
	     ***************************************************/
	    public static String llenaCerosIzquierda(int dato, int cantidad) {
	        StringBuilder resulta = new StringBuilder();
	        for (int i = 0; i < cantidad; i++) {
	            resulta.append("0");
	        }
	        resulta.append(dato);
	        resulta = new StringBuilder(resulta.substring(resulta.length() - cantidad));
	        return resulta.toString();
	    }
	    
	    public static String llenaCerosIzquierda(BigInteger dato, int cantidad) {
	        StringBuilder resulta = new StringBuilder();
	        for (int i = 0; i < cantidad; i++) {
	            resulta.append("0");
	        }
	        resulta.append(dato);
	        resulta = new StringBuilder(resulta.substring(resulta.length() - cantidad));
	        return resulta.toString();
	    }
	    public static String llenaCerosIzquierda(long dato, int cantidad) {
	        StringBuilder resulta = new StringBuilder();
	        for (int i = 0; i < cantidad; i++) {
	            resulta.append("0");
	        }
	        resulta.append(dato);
	        resulta = new StringBuilder(resulta.substring(resulta.length() - cantidad));
	        return resulta.toString();
	    }
	    
	    public static String obtenerDescripcionTipoPago(String codigoTipoPago){
	        String tPago = "";
	        if (codigoTipoPago.toLowerCase().equals(ServiceConstante.TIPO_PAGO_CONTADO.toLowerCase())) {
	            tPago = "Contado";
	        } else if (codigoTipoPago.toLowerCase().equals(ServiceConstante.TIPO_PAGO_TARJETA.toLowerCase())) {
	            tPago = "Tarjeta";
	        } else if (codigoTipoPago.toLowerCase().equals(ServiceConstante.TIPO_PAGO_CONTADOTARJETA.toLowerCase())) {
	            tPago = "Contado - Tarjeta";
	        } else if (codigoTipoPago.toLowerCase().equals(ServiceConstante.TIPO_PAGO_CREDITO.toLowerCase())) {
	            tPago = "Credito";
	        }
	        return tPago;
	    }
	}

