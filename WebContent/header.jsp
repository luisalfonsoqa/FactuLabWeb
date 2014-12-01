<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<title>FactuLab Web</title>
<link rel="stylesheet" href="css/jquery.pagination.css" type="text/css"/>
<link href="css/humanity/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/jquery.pagination.js"></script>
<script src="js/factulab.js"></script>
<style>
body {
	/* font: 62.5% "Trebuchet MS", sans-serif;*/
	font: 70% "Trebuchet MS", sans-serif;
	/*margin: 50px;*/
}
form{
	padding: 0;
	margin: 0;
}
.header_titulo {
	margin-top: 2em;
}
.numero { 
	text-align:right; 
}
label.title {
	font-weight: bold;
	padding-left: 0.5em;
}
label, input { display:block; }
input.text { width:95%; padding: .4em; }


</style>
<!-- link rel="stylesheet" href="css/css3menu1/style.css" type="text/css" /-->
</head>
<body bgcolor="#F2F2F2">
<% //try { %>
<%@page import="java.util.*"%>
<%@page import="com.factulab.dao.form.*"%>
<%@page import="com.factulab.dao.bean.*"%>
<%@page import="com.factulab.dao.util.DAOConstante"%>
<%@page import="com.factulab.servlet.util.*"%>
<%@page import="com.factulab.service.util.ServiceConstante"%>
<%@page import="com.factulab.service.util.ServiceConstante"%>
<%@page import="com.factulab.service.util.ServiceUtil"%>
<%@page import="com.factulab.servlet.util.ServletConstante"%>
<%@page import="com.factulab.servlet.util.ServletUtil"%>
<%
	Usuario uLogin = (Usuario) request.getSession().getAttribute(ServletConstante.SESSION_USUARIO);
	ConstantesBD constanteBD = (ConstantesBD) request.getSession().getAttribute(ServletConstante.SESSION_CONSTANTE);

if (request.getSession() == null  || uLogin == null) {
	response.sendRedirect("index.jsp");
}
%>
<!-- background-image: url('/FactuLabWeb/images/red-bg.jpg');  -->
<table border="0" style="width: 100%; margin: 0; padding: 0;"  >
<tr>
	<td style="height: 60px; width: 100%; font-size: 100%; background-color: #F5F3DB; color: #AE0000;" align="center">
		<table border="0" style="width: 100%">
		<tr align="center">
			<td><h3 style="width: 500px;">SITEMA DE FACTURACION - FACTULAB</h3></td>
			<td rowspan="2" valign="bottom">
				<!-- form action="LogoffServlet" method="post"> 
				<input id="btnLogOff" type="submit" class="button" value="Cerrar Sesión"/>
				</form-->
			</td>	
		</tr>
		<tr>
			<td align="right">
			<table border="0" style="width: 1300px; padding: 0; margin: 0;" >
				<tr>
				<td width="200px;" align="right"><strong>BIENVENIDO:</strong></td>
				<td width="350px;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%= uLogin.getNombre() %> / <%= uLogin.getUsuario() %></td>
				<td width="150px;" align="right"><strong>TIPO DE USUARIO:</strong></td>
				<td width="100px;" align="left">&nbsp;&nbsp;&nbsp;&nbsp; Cajero <!-- %= uLogin.getIdTipoUsuario() %--></td>
				<td width="100px;" align="right"><strong>IMPRESORA:</strong></td>
				<td width="400px;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<a title="<%= uLogin.getNombreImpresora() %>"><%= uLogin.getSerieImpresora() %></a></td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
	</td>
</tr>
<!--tr>
<td align="center">
	<div align="center" style="width: 900px;">
		<ul id="css3menu1" class="topmenu"> 
			<li class="topfirst"><a href="/FactuLabWeb/principal.jsp" style="height:15px;line-height:15px;"><span>Inicio</span></a></li>
			<li class="topmenu"><a href="/FactuLabWeb/CargarInsertarAtencionFormServlet" style="height:15px;line-height:15px;"><span>Nueva Atención</span></a></li>
			<li class="topmenu"><a href="/FactuLabWeb/CargarFacturarPendientesFormServlet" style="height:15px;line-height:15px;"><span>Pendientes</span></a></li>
			<li class="toplast"><a href="/FactuLabWeb/reporteAnalisisTomados.jsp" style="height:15px;line-height:15px;"><span>Análisis Tomados</span></a></li>
		</ul>
	</div>
</td>
</tr-->
<tr>
<td align="center">
	<div align="center" style="width: 1200px;">
		<a href="/FactuLabWeb/principal.jsp" class="button"><span style="width: 150px; display: inline-block;">Inicio</span></a>&nbsp;&nbsp;&nbsp;
		<a href="/FactuLabWeb/CargarInsertarAtencionFormServlet" class="button"><span style="width: 150px; display: inline-block;">Nueva Atención</span></a>&nbsp;&nbsp;&nbsp;
		<a href="/FactuLabWeb/CargarFacturarPendientesFormServlet" class="button"><span style="width: 150px; display: inline-block;">Pendientes</span></a>&nbsp;&nbsp;&nbsp;
		<a href="/FactuLabWeb/reporteAnalisisTomados.jsp" class="button"><span style="width: 150px; display: inline-block;">Reporte</span></a>&nbsp;&nbsp;&nbsp;
		<a href="/FactuLabWeb/LogoffServlet" class="button"><span style="width: 150px; display: inline-block;">Cerrar Sesión</span></a>
	</div>
</td>
</tr>
</table>
<br/>
	<!-- BODY -->