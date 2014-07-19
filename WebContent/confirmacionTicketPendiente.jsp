<%@ include file="header.jsp"%>

<%
	AtencionPendienteForm atencionForm = (AtencionPendienteForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE);
%>

<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
				<tr>
					<td width="55%" align="right"><div style="font-size: 14px;">TICKET GENERADO</div></td>
					<td width="45%" align="right" valign="top"><div align="right"><a href="/FactuLabWeb/CargarInsertarAtencionFormServlet" id="btnCancelarAtencion" class="button" ><span style="width: 100px; display: inline-block;">Nuevo Ticket</span></a></div></td>
				</tr>
		</table>
		<form action="ResumenAtencionServlet" id="frmProcesarAtencion">
			<div class="f_accordion">
				<h3>DATOS DE LA INSTITUCIÓN</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 120px;"><label class="title">&nbsp;Institución:</label></td>
						<td style="width: 470px;" style="display: inline-block;" ><%=atencionForm.getInstitucion().getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;[<%=atencionForm.getInstitucion().getIdInstitucion()%>]</td>
						<td style="width: 80px;"><label class="title">RUC:</label></td>
						<td style="width: 200px;"><%=atencionForm.getInstitucion().getRuc()%></td>
					</tr>
					<tr>
						<td><label class="title">&nbsp;Dirección:</label></td>
						<td colspan="3"><%=atencionForm.getInstitucion().getDireccion()%></td>
					</tr>
				</table>
			</div>
				<div class="f_accordion">
				<h3>DATOS DEL COMPROBANTE</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 150px;"><label class="title">&nbsp;Tipo de Comprobante:</label></td>
						<td style="width: 300px; display: inline-block;" ><%=atencionForm.getConIGV() ? "Con IGV" : "Sin IGV"%></td>
						<td style="width: 100px;"><label class="title">Tipo de Pago:</label></td>
						<td style="width: 350px;">
						<%
							if(atencionForm.getTipoPago().equals(ServiceConstante.TIPO_PAGO_CONTADO )) {
						%>
							Contado
						<%
							}
						%>
						</td>
					</tr>
				</table>
				</div>
			<div class="f_accordion">
			<h3>RESUMEN</h3>
			<div align="center">
				<div align="left" style="width: 600px;">
					<table border="0" style="width: 100%; padding: 0; margin: 0;" id="tableListadoAnalisis" class="ui-widget ui-widget-content" >
						<thead>
				    		<tr class="ui-widget-header">
								<td style="width: 50px;"><label>Cantidad</label></td>
								<td style="width: 450px;"><label>Descripción</label></td>
								<td style="width: 100px;"><label>Valor Venta</label></td>
							</tr>
						</thead>
						<tbody style="font-size: 11px;">
							<%
								for (AtencionPendienteResumen a : atencionForm.getlAtencionResumen()) {
							%>
					       	<tr>
						       	<td><%=a.getCantidad()%></td>
						       	<td><%=a.getNombreAnalisis()%></td>
						       	<td><%=a.getMontoString()%></td>
					       	</tr>
							<%
								}
							%>
						</tbody>
					</table>
					</div>
				</div>
			</div>
			<div class="f_accordion">
			<h3>DETALLE DE ATENCIONES</h3>
			<table border="0"  style="width: 100%; padding: 0; margin: 0;" id="tableListadoAnalisis" class="ui-widget ui-widget-content">
				<thead>
		    		<tr class="ui-widget-header">
						<td style="width: 20px;"><label>Cant</label></td>
						<td style="width: 230px;"><label>Descripción</label></td>
						<td style="width: 230px;"><label>Paciente</label></td>
						<td style="width: 80px;"><label>Fecha</label></td>
						<td style="width: 50px;"><label>Omega</label></td>
						<td style="width: 80px;"><label>P Unit S/D</label></td>
						<td style="width: 50px;"><label>Descuento</label></td>
						<td style="width: 80px;"><label>P Unit C/D</label></td>
						<td style="width: 80px;"><label>Valor Venta</label></td>
					</tr>
				</thead>
				<tbody style="font-size: 11px">
					<%
						for (AtencionPendienteDetalle a : atencionForm.getlAtencionDetalle()) {
					%>
			       	<tr>
				       	<td><%=a.getCantidad()%></td>
				       	<td><%=a.getNombreAnalisis()%></td>
				       	<td><%=a.getNombrePaciente()%></td>
				       	<td><%=a.getFecha()%></td>
				       	<td><%=a.getCodOmega()%></td>
				       	<td><%=a.getPrecioUnitarioString()%></td>
				       	<td><%=a.getPorcentajeDescuentoString()%></td>
				       	<td><%=a.getPrecioString()%></td>
				       	<td><%=a.getMontoString()%></td>
			       	</tr>
					<%
						}
					%>
				</tbody>
			</table>
			</div>
			<table border="0" style="width: 100%; padding: 0; margin: 0; height: 20px; vertical-align: middle;" id="tableAgregarAnalisis" class="ui-widget-header">
			<tr>
				<td width="140px"></td>
				<td width="150px"><label>SUBTOTAL (S/.):</label></td>
				<td width="80px" align="left"><%=atencionForm.getSubtotalString()%></td>
				<td width="150px" align="right"><label>IGV (%):</label></td>
				<td width="150px" align="left"><%=atencionForm.getIgvString()%></td>
				<td width="150px" align="right"><label>TOTAL (S/.):</label></td>
				<td width="100px" align="center"><input type="text" size="8" disabled="disabled"  value="<%=atencionForm.getTotalConDescuentoString()%>"/></td>
				<td align="right">
					<!-- input type="button" class="button" value="Procesar Atencion" id="btnConfirmarAtencion" /-->
				</td>
			</tr>
			</table>
			<input type="hidden" name="accion" value="procesar"/>
		</form>
		<br/>
			<div class="ui-widget">
				<div class="ui-state-highlight ui-corner-all" style="padding: 0 .7em; display: inline-block; width: 100%">
					<table border="0" style="width: 100%">
					<tr>
						<td style="width: 30%">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								<strong>INFORMACIÓN</strong>&nbsp;&nbsp;&nbsp;&nbsp;Ticket Generado&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</p>
						</td>
						<td style="width: 70%">
							<input id="btnPrintTicket" type="button" class="button" value="Imprimir Ticket" style="display: inline;"/> 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnPrintTicketPendiente" type="button" class="button" value="Exportar Detalle Ticket"  style="display: inline;"/>
						</td>
					</tr>
					</table>
				</div>
			</div>
		
		<form id="frmReporteTicket" action="TicketPendienteServlet" target="_blank">
			<input type="hidden" name="formato" value="PDF"/>
			<input type="hidden" name="idTicket" id="h_idTicket" value="<%=atencionForm.getIdTicket()%>" />
		</form>
		
		<form id="frmReporteTicketEXCEL" action="TicketPendienteServlet" target="_blank">
			<input type="hidden" name="formato" value="XLS"/>
			<input type="hidden" name="idTicket" id="h_idTicket" value="<%=atencionForm.getIdTicket()%>" />
		</form>
		
	</div>
</div>
<script>
	$(function() {
		var btnCancelarAtencion = $('#btnCancelarAtencion');
		var btnPrintTicket = $('#btnPrintTicket');
		var btnPrintTicketPendiente = $('#btnPrintTicketPendiente');
		
		
		btnCancelarAtencion.click(function(){
			var msg = "¿Seguro de volver al Inicio?";
			if(confirm(msg)) $('#frmCancelarAtencion').submit();
		});
		
		btnPrintTicket.click(function(){
			$('#frmReporteTicket').submit();
		});
		
		btnPrintTicketPendiente.click(function(){
			$('#frmReporteTicketEXCEL').submit();
		});
	});
	
</script>

<%@ include file="footer.jsp"%>
