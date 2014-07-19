<%@page import="com.factulab.dao.form.AtencionForm"%>
<%@page import="com.factulab.dao.form.AnalisisForm"%>
<%@ include file="header.jsp"%>

<%
	AtencionForm atencionForm = (AtencionForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION);
	boolean facturacionInmediata = !atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_CREDITO);
%>
<div align="center">
	<div style="width: 900px;" align="left">
			<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
				<tr>
					<td width="55%" align="right"><div style="font-size: 14px;"><%
						out.println(facturacionInmediata ? "TICKET GENERADO" : "ATENCION GENERADA");
					%></div></td>
					<td width="45%" align="right" valign="top"><div align="right"><a href="/FactuLabWeb/CargarInsertarAtencionFormServlet" id="btnCancelarAtencion" class="button" ><span style="width: 100px; display: inline-block;"><%
						out.println(facturacionInmediata ? "Nuevo Ticket" : "Nueva Atención");
					%></span></a></div></td>
				</tr>
			</table>
			<div class="f_accordion">
				<h3>DATOS DEL PACIENTE</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 120px;"><label class='title'>Paciente:</label></td>
						<td style="width: 470px; display: inline-block;"><%=atencionForm.getPaciente().getApepat()%> <%=atencionForm.getPaciente().getApemat()%> <%=atencionForm.getPaciente().getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;[<%=atencionForm.getPaciente().getIdPaciente()%>]</td>
						<td style="width: 80px;"><label class='title'>DNI:</label></td>
						<td style="width: 200px;"><%=atencionForm.getPaciente().getDni()%></td>
					</tr>
					<tr>
						<td><label class='title'>Dirección:</label></td>
						<td colspan="3"><%=atencionForm.getPaciente().getDireccion()%></td>
					</tr>
				</table>
			</div>
			<%
				if(atencionForm.getConIGV()) {
			%>
			<div class="f_accordion">
				<h3>DATOS DE LA INSTITUCIÓN</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 120px;"><label class='title'>Institución:</label></td>
						<td style="width: 470px;" style="display: inline-block;" ><%=atencionForm.getInstitucion().getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;[<%=atencionForm.getInstitucion().getIdInstitucion()%>]</td>
						<td style="width: 80px;"><label class='title'>RUC:</label></td>
						<td style="width: 200px;"><%=atencionForm.getInstitucion().getRuc()%></td>
					</tr>
					<tr>
						<td><label class='title'>Dirección:</label></td>
						<td colspan="3"><%=atencionForm.getInstitucion().getDireccion()%></td>
					</tr>
				</table>
			</div>
			<%
				}
			%>
			<div class="f_accordion">
				<h3>DATOS DEL COMPROBANTE</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 200px;"><label class='title'>Tipo de Comprobante:</label></td>
						<td style="width: 200px; display: inline-block;">&nbsp;&nbsp;&nbsp;&nbsp;
						<%
							if(facturacionInmediata){  
															if(atencionForm.getConIGV()) {
						%>
								Con IGV
							<%
							} else {
						%>
								Sin IGV
							<%
							}
														} else {
															out.println("---");
														}
						%>
						</td>
						<td style="width: 100px;"><label class="title"> Tipo de Pago:</label></td>
						<td style="width: 450px;">&nbsp;&nbsp;&nbsp;&nbsp;
						<%
							if(atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_CONTADO)){
								out.println("Contado");
							} else if(atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_TARJETA)){
								out.println("Tarjeta");
							} else if(atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_CONTADOTARJETA)){
								out.println("Contado-Tarjeta");
							} else if(atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_CREDITO)){
								out.println("Crédito");
							}
						%>
						</td>
					</tr>
				</table>
			</div>
			<div class="f_accordion">
			<h3>ATENCION</h3>
			<table border="0"  style="width: 100%; padding: 0; margin: 0;" id="tableListadoAnalisis" class="ui-widget ui-widget-content">
				<thead>
		    		<tr class="ui-widget-header">
						<td style="width: 80px;" align="center"><label>Cantidad</label></td>
						<td style="width: 200px;"><label>Descripción</label></td>
						<td style="width: 80px;" align="right"><label>P Unit S/D</label></td>
						<td style="width: 80px;" align="right"><label>Descuento</label></td>
						<td style="width: 80px;" align="right"><label>P Unit C/D</label></td>
						<td style="width: 80px;" align="right"><label>Valor Venta</label></td>
					</tr>
				</thead>
				<tbody>
					<%
						for (AnalisisForm a : atencionForm.getlAnalisis()) {
					%>
			       	<tr>
				       	<td align="center"><%=a.getCantidad()%></td>
				       	<td><%=a.getNombre()%></td>
				       	<td align="right"><%=a.getPrecioUnitConTarifaString()%></td>
				       	<td align="right"><%=atencionForm.getPorcentajeDescuentoString()%></td>
				       	<td align="right"><%=a.getPrecioUnitConDescuentoString()%></td>
				       	<td align="right"><%=a.getTotalConDescuentoString()%></td>
			       	</tr>
					<%
						}
					%>
				</tbody>
			</table>
			</div>
			<table border="0" style="width: 100%; padding: 0; margin: 0; height: 20px; vertical-align: middle;" id="tableAgregarAnalisis" class="ui-widget-header">
			<tr>
				<td width="100px"></td>
				<td width="190px"><label>PRECIO VENTA (S/.):</label></td>
				<td width="80px" align="left"><%=atencionForm.getTotalSinDescuentoString()%></td>
				<td width="150px" align="right"><label>DESC (%):</label></td>
				<td width="150px" align="left"><%=atencionForm.getPorcentajeDescuentoString()%></td>
				<td width="150px" align="right"><label>TOTAL: (S/.):</label></td>
				<td width="100px" align="center"><input type="text" size="8" disabled="disabled"  value="<%=atencionForm.getTotalConDescuentoString()%>"/></td>
				<td align="right"></td>
			</tr>
			</table>
		<br/>
		<%
			if(atencionForm.getCodigoOmega() != null){
		%>
			<div class="ui-widget">
				<div class="ui-state-error ui-corner-all" style="padding: 0 .7em; display: inline-block; width: 100%">
					<table border="0" style="width: 100%">
					<tr>
						<td style="width: 30%">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								<strong>INFORMACIÓN</strong>&nbsp;&nbsp;&nbsp;&nbsp;<%
									out.println(facturacionInmediata ? "Ticket Generado" : "Atención Generada");
								%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</p>
						</td>
						<td style="width: 70%">
							<input id="btnPrintHojaDeTrabajo" type="button" class="button" value="Imprimir Hoja de Trabajo" style="display: inline;"/> 
							<%
 								if(facturacionInmediata){
 							%>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnPrintTicket" type="button" class="button" value="Imprimir Ticket" style="display: inline;"/>
							<%
								}
							%>
							<div style="display: inline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
							<strong style="display: inline;" >Código Omega:</strong >&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text" size="8" id="codigoOmega" disabled="disabled" value="<%=atencionForm.getCodigoOmega()%>" style="display: inline;" />
						</td>
					</tr>
					</table>
				</div>
			</div>
		<%
			}
		%>
		<br/>
		
		<form id="frmReporteHT" action="HojaTrabajoServlet" target="_blank">
			<input type="hidden" name="idAtencion" id="h_idAtencion" value="<%=atencionForm.getIdAtencion()%>" />
		</form>
		<form id="frmReporteTicket" action="TicketServlet" target="_blank">
			<input type="hidden" name="idTicket" id="h_idTicket" value="<%=atencionForm.getIdTicket()%>" />
		</form>
	</div>
</div>
<script>
	$(function() {
		var btnCancelarAtencion = $('#btnCancelarAtencion');
		var btnPrintTicket = $('#btnPrintTicket');
		var btnPrintHojaDeTrabajo = $('#btnPrintHojaDeTrabajo');
		
		btnCancelarAtencion.click(function(){
			var msg = "¿Seguro de volver al Inicio?";
			if(confirm(msg)) $('#frmCancelarAtencion').submit();
		});
		
		btnPrintTicket.click(function(){
			$('#frmReporteTicket').submit();
			
		});
		
		btnPrintHojaDeTrabajo.click(function(){
			$('#frmReporteHT').submit();
			
		});
	});
</script>
<%@ include file="footer.jsp"%>
