package com.factulab.dao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DAOUtil {
	public static String formatearBigDecimal(BigDecimal bd) {
		if(bd == null) bd = BigDecimal.ZERO;
		return new DecimalFormat("###0.00").format(bd);
	}
	
	public static String formatearPorcentaje(BigDecimal bd) {
		if(bd == null) bd = BigDecimal.ZERO;
		return bd.toString()+"%";
	}
}
