package com.factulab.service.util;

import java.math.BigDecimal;

public class ServiceConstante {
	public static Integer VISTA_USUARIO_CAJA = 3;
	public static Integer VISTA_USUARIO_ADMINISTRADOR = 1; //Aun no usado
	
	
	public static Integer REDONDEO_DECIMAL = 2;
	public static String FORMATO_DECIMAL = "###0.00";
	
	public static String TIPO_PAGO_CONTADO = "1011";
	public static String TIPO_PAGO_CREDITO = "C";
	public static String TIPO_PAGO_TARJETA = "T";
	public static String TIPO_PAGO_CONTADOTARJETA = "E/T";
	
	public static BigDecimal IGV = new BigDecimal("18.00");
	public static BigDecimal BIGDECIMAL_100 = new BigDecimal("100.00");
	
	
	
	public static String TURNO_MAÑANA = "1";
	public static int TURNO_MAÑANA_INI = 0;
	public static int TURNO_MAÑANA_FIN = 14;
	public static String TURNO_TARDE = "2";
	public static int TURNO_TARDE_INI = 14;
	public static int TURNO_TARDE_FIN = 24;
	public static String TURNO_TODOS = "0";
	
	public static String CONCATENADOR = "|";
	
	/**
	 * AMBIENTE PRODUCCION
	 */
//	public static String DIRECTORIO_OMEGA_REMOTO = "R:\\";
//	public static String DIRECTORIO_OMEGA_LOCAL = "C:\\factulab_omegaBK\\";
	
	/**
	 * AMBIENTE DESARROLLO
	 */
	
//	public static String DIRECTORIO_OMEGA_REMOTO = "C:\\SistemaFacturacion\\Temporal\\Remoto\\";
//	public static String DIRECTORIO_OMEGA_LOCAL = "C:\\SistemaFacturacion\\Temporal\\Local\\";
}

