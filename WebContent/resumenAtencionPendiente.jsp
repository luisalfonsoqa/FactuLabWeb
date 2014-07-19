<%@page import="com.factulab.dao.form.AtencionForm"%>
<%@page import="com.factulab.dao.form.AnalisisForm"%>
<%@ include file="header.jsp"%>

<%
	AtencionPendienteForm atencionForm = (AtencionPendienteForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE);
%>

<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
			<tr>
				<td width="55%" align="right"><div style="font-size: 14px;">CONFIRMAR TICKET</div></td>
				<td width="45%" align="right" valign="top"><div align="right"><a href="/FactuLabWeb/CargarFacturarPendientesFormServlet" id="btnCancelarAtencion" class="button" ><span style="width: 100px; display: inline-block;">Cancelar Atención</span></a></div></td>
			</tr>
		</table>
		<form action="ResumenAtencionPendienteServlet" id="frmProcesarAtencion">
			<div class="f_accordion">
				<h3>DATOS DE LA INSTITUCIÓN</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 120px;"><label class="title">&nbsp;Institución:</label></td>
						<td style="width: 470px; display: inline-block;" >
							<input type="text" size="3" disabled="disabled" value="<%=atencionForm.getInstitucion().getIdInstitucion()%>"  style="display: inline;"/> - 
							<input type="text" size="40" id="nombreInstitucion" disabled="disabled" value="<%=atencionForm.getInstitucion().getNombre()%>" style="display: inline;"/>
						</td>
						<td style="width: 80px;"><label class="title">RUC:</label></td>
						<td style="width: 200px;"><input type="text" size="15" id="ruc" onkeypress="return isNumberKey(event);" maxlength="12" name="ruc" disabled="disabled" value="<%=atencionForm.getInstitucion().getRuc()%>" /></td>
					</tr>
					<tr>
						<td><label class="title">&nbsp;Dirección:</label></td>
						<td colspan="3"><input type="text" size="80" id="direccion" name="direcion"  disabled="disabled" value="<%=atencionForm.getInstitucion().getDireccion()%>" /></td>
					</tr>
				</table>
			</div>
			<div class="f_accordion">
			<h3>DATOS DEL COMPROBANTE</h3>
			<table border="0" style="width: 100%; padding: 0; margin: 0;" >
				<tr>
					<td style="width: 150px;"><label class="title">&nbsp;Tipo de Comprobante:</label></td>
					<td style="width: 300px; display: inline-block;" >
						<input type="radio" name="igv" value="1" size="5" style="display: inline;"/> Con IGV&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="igv" value="0" size="5" style="display: inline;"/> Sin IGV
					</td>
					<td style="width: 100px;"><label class="title">Tipo de Pago:</label></td>
					<td style="width: 350px; display: inline-block;" >
						<input type="radio" name="tipoPago" value="<%=ServiceConstante.TIPO_PAGO_CONTADO%>" size="5" style="display: inline;" checked="checked"/> Contado&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- input type="radio" name="tipoPago" value="< %=Constante.TIPO_PAGO_TARJETA%>" size="5" style="display: inline;"/> Tarjeta&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="tipoPago" value="< %=Constante.TIPO_PAGO_CONTADOTARJETA %>" size="5" style="display: inline;"/> Contado-Tarjeta -->
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
						<tbody style="font-size: 11px">
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
			<div align="center">
				<div align="right"><input id="btnPrintTicketPendiente" type="button" class="button" value="Exportar Atenciones"  style="display: inline;"/></div>
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
			</div>
			<br/>
			<table border="0" style="width: 100%; padding: 0; margin: 0; height: 20px; vertical-align: middle;" id="tableAgregarAnalisis" class="ui-widget-header">
			<tr>
				<td width="140px"></td>
				<td width="150px"><label>SUBTOTAL (S/.):</label></td>
				<td width="80px" align="left"><input type="text" size="8" disabled="disabled"  value="<%=atencionForm.getSubtotalString()%>"/></td>
				<td width="150px" align="right"><label>IGV (%):</label></td>
				<td width="150px" align="left"><input type="text" size="8" disabled="disabled"  value="<%=atencionForm.getIgvString()%>"/></td>
				<td width="150px" align="right"><label>TOTAL (S/.):</label></td>
				<td width="100px" align="center"><input type="text" size="8" disabled="disabled"  value="<%=atencionForm.getTotalConDescuentoString()%>"/></td>
				<td align="right">
					<input type="button" class="button" value="Procesar Atencion" id="btnConfirmarAtencion" />
				</td>
			</tr>
			</table>
			<input type="hidden" name="accion" value="procesar"/>
		</form>
		<br/><br/>
		<%
			if(atencionForm.getNumSerie() != null){
		%>
			<div class="ui-widget">
				<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
					<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<strong>INFORMACIÓN</strong> Atención Generada.<strong>
					</p>
				</div>
			</div>
		<%
			}
		%>
		<form id="frmReporteTicketEXCEL" action="ResumenAtencionPendienteServlet" target="_blank">
			<input type="hidden" name="accion" value="XLS"/>
		</form>
	</div>
</div>
<script>
	$(function() {
		var btnCancelarAtencion = $('#btnCancelarAtencion');
		var btnConfirmarAtencion = $('#btnConfirmarAtencion');
		var ruc = $('#ruc');
		var direccion = $('#direccion'); 
		var nombreInstitucion = $('#nombreInstitucion');
		var btnPrintTicketPendiente = $('#btnPrintTicketPendiente');
		
		btnCancelarAtencion.click(function(){
			var rpt = confirm("¿Esta seguro de Cancelar la atención?"); //validar
			if(rpt) $('#frmCancelarAtencion').submit();
		});
		
		btnConfirmarAtencion.click(function(){
			if($('input:radio[name=igv]:checked').val()===undefined){
				printError("Seleccione el \"Tipo de Comprobante\".");
				return false;
			}
			if($('input:radio[name=tipoPago]:checked').val()===undefined){
				printError("Seleccione el \"Tipo de Pago\".");
				return false;
			}
			
			if(($('input:radio[name=igv]:checked').val() == '1') && (ruc.val() == null || ruc.val().length < 11)) {
				printError("RUC de institución no valido.");
				printError("Ingrese el RUC para la Institución: "+nombreInstitucion.val());
				ruc.removeAttr('disabled');
				ruc.focus();
				return false;
			}
			
			if(($('input:radio[name=igv]:checked').val() == '1') && (direccion.val() == null || direccion.val().length < 1)) {
				printError("Dirección de institución no valido");
				//printError("Ingrese la direción para la Institución: "+nombreInstitucion.val());
				//direccion.removeAttr('disabled');
				//direccion.focus();
				return false;
			}
			
			//procesar
			var rpt = confirm("Se procesará la atención."); //validar
			if(rpt) {
				$('#frmProcesarAtencion').submit();
			}
		});
		
		btnPrintTicketPendiente.click(function(){
			$('#frmReporteTicketEXCEL').submit();
		});
		
	});
	
</script>

<%@ include file="footer.jsp"%>
