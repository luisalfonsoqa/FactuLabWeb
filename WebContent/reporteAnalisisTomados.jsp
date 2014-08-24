<%@ include file="header.jsp"%>
<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
			<tr>
				<td width="100%" align="center"><div style="font-size: 14px;">REPORTES DE CAJERO</div></td>
			</tr>
		</table>
		<div class="f_accordion">
			<h3>ANALISIS TOMADOS</h3>
			<form id="frmAnalisisTomados" action="ReporteAnalisisServlet"  >
				<table style="width: 100%;" border="0">
					<tr>
						<td style="width: 100px; height: 30px;" >Fecha Inicio:</td>
						<td style="width: 150px;">
							<input type="text" name="fechaIni" class="datepicker" id="fechaIni" style="display: inline-block;" size="12"> 
						</td>
						<td style="width: 100px;">Turno:</td>
						<td style="width: 150px;">							
							<select name="turno" id="idturno" >
								<option value="0">---- Todos ----</option>
								<option value="1">Turno Mañana</option>
								<option value="2">Turno Tarde</option>
							</select>
						</td>
						<td style="width: 400px;"></td>
					</tr>
					<tr>
						<td style="height: 30px;">Fecha Fin:</td>
						<td><input type="text" name="fechaFin" class="datepicker" id="fechaFin" style="display: inline-block;" size="12"></td>
						<td></td><td></td>
						<td align="center" valign="bottom" >
							<a id="btnReporteCajeroXLS" class="button"><span style="width: 100px; display: inline-block;">Reporte (<%=uLogin.getUsuario() %>)</span></a>
							<a id="btnReporteAnalisisPDF" class="button"><span style="width: 100px; display: inline-block;">Reporte Analisis</span></a>
							<input type="hidden" name="formato" id="formato"/>
							<input type="hidden" name="accion" id="accion"/>
						</td>
					</tr>
				</table>
				<br/>
			</form>
		</div>
	</div>
</div>
<script>
	$(function() {
		var btnReporteCajeroXLS = $('#btnReporteCajeroXLS');
		var btnReporteAnalisisPDF = $('#btnReporteAnalisisPDF');
		var formato = $('#formato');
		var accion = $('#accion');
		var fechaIni =$('#fechaIni');
		var fechaFin =$('#fechaFin');
		btnReporteCajeroXLS.click(function(){
			var msgError = '';
			if(fechaFin.val() == '') msgError = 'Ingrese la Fecha Final';
			if(fechaIni.val() == '') msgError = 'Ingrese la Fecha Inicio';
			
			accion.val('repCajero');
			if(msgError == '') {
				formato.val('XLS');
				$('#frmAnalisisTomados').submit();
			} else {
				printError(msgError);
			}
		});
		
		btnReporteAnalisisPDF.click(function(){
			var msgError = '';
			if(fechaFin.val() == '') msgError = 'Ingrese la Fecha Final';
			if(fechaIni.val() == '') msgError = 'Ingrese la Fecha Inicio';
			
			accion.val('repAnalisis');
			if(msgError == '') {
				formato.val('PDF');
				$('#frmAnalisisTomados').submit();
			} else {
				printError(msgError);
			}
		});
	});
	
</script>

<%@ include file="footer.jsp"%>
