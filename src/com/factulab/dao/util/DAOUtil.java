package com.factulab.dao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class DAOUtil {
	public static String formatearBigDecimal(BigDecimal bd) {
		if(bd == null) bd = BigDecimal.ZERO;
		return new DecimalFormat("###0.00").format(bd);
	}
	
	public static String formatearPorcentaje(BigDecimal bd) {
		if(bd == null) bd = BigDecimal.ZERO;
		return bd.toString()+"%";
	}
	
	public static Integer getEdad(Date fecnac) {
		Date fechaActual = new Date();
		long año = (long) 365.25;
		long msecFA = fechaActual.getTime();
		long msecFN = fecnac.getTime();
		long msecEdad = msecFA - msecFN;
		long dias = msecEdad / (24 * 60 * 60 * 1000);
		long años = dias / año;
		return (int) años;
	}
}
