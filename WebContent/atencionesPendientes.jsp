<%@ include file="header.jsp"%>
<%
	AtencionPendienteForm atencion = (AtencionPendienteForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION_PENDIENTE);
	List<Institucion> lInstitucion = (ArrayList<Institucion>) request.getSession().getAttribute(ServletConstante.SESSION_INSTITUCIONES_PENDIENTES);

	if(atencion != null && atencion.getlAtencion() == null) atencion.setlAtencion(new ArrayList<AtencionPendiente>());
	if(lInstitucion == null) lInstitucion = new ArrayList<Institucion>();
	
	String txt_idInstitucion = request.getParameter("idInstitucion");
	if(txt_idInstitucion == null) txt_idInstitucion = "";
	
	String txt_fechaIni = request.getParameter("fechaIni");
	String txt_fechaFin = request.getParameter("fechaFin");
	if(txt_fechaIni == null) txt_fechaIni = "";
	if(txt_fechaFin == null) txt_fechaFin = "";
	
	/* Integer idInstitucion = null;
	try{
		if(txt_idInstitucion != null) 
	idInstitucion = Integer.parseInt(txt_idInstitucion);
	} catch(Exception e){
		throw new FactulabException("IdInstitucion["+txt_idInstitucion+"] incorrecto."+e.getMessage());
	} */
%>
<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
			<tr>
				<td width="100%" align="center"><div style="font-size: 14px;">GENERAR ATENCION PENDIENTE</div></td>
			</tr>
		</table>
		<input type="hidden" id="h_institucion_old" value="<%=txt_idInstitucion%>"/>
		<input type="hidden" id="h_fechaIni_old" value="<%=txt_fechaIni%>"/>
		<input type="hidden" id="h_fechaFin_old" value="<%=txt_fechaFin%>"/>
		<div class="f_accordion">
			<h3>BUSCAR ATENCIONES POR INSTITUCION</h3>
			<form id="frmBuscarPendiente" action="AtencionPendienteServlet"  >
				<table style="width: 100%;" border="0">
					<tr>
						<td style="width: 150px;">Instituciones:</td>
						<td colspan="3">
							<select name="idInstitucion" id="idInstitucion" >
							<option value="">Seleccione</option>
								<%
									for(Institucion i : lInstitucion){ 
																								out.println("<option value='"+i.getIdInstitucion()+"'");
																								//if(idInstitucion != null && idInstitucion == i.getIdInstitucion()) out.println(" selected='selected' ");
																								out.println(">"+i.getNombre()+"</option>");
																							}
								%>
							</select>
						</td>
						<td rowspan="2">
							<input id="btnBuscar" type="button" class="button" value="Listar Anteciones"/>
							<input type="hidden" name="accion" value="buscar" />
						</td>
					</tr>
					<tr>
						<td>Fecha Inicio</td>
						<td style="width: 150px;">
							<input type="text" name="fechaIni" class="datepicker" id="fechaIni" style="display: inline-block;" size="12"> 
						</td>
						<td style="width: 100px;">Fecha Fin</td>
						<td style="width: 150px;">
							<input type="text" name="fechaFin" class="datepicker" id="fechaFin" style="display: inline-block;" size="12"> 
						</td>
					</tr>
				</table>
				<br/>
			</form>
		</div>
		<div class="f_accordion">
			<h3>ATENCIONES PENDIENTES</h3>
			<table style="width: 100%;"><tr><td align="right"><input id="btnSelecAll" type="button" class="button" value="Seleccionar Todos"/>&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnDetalle" type="button" class="button" value="Ver Detalles"/></td></tr></table>
		</div>
		<form id="frmDetallePendiente" action="ResumenAtencionPendienteServlet" style="width: 100%; padding: 0; margin: 0;">
				<table border="0"  style="width: 100%; padding: 0; margin: 0;" id="tableListadoAnalisis" class="ui-widget ui-widget-content">
					<tr class="ui-widget-header">
						<td width="150px;">Código Omega</td>
						<td width="150px;">Fecha</td>
						<td width="300px;">Paciente</td>
						<td width="150px;">Total</td>
						<td width="100px;">Seleccione</td>
					</tr>
					<%
						if(atencion != null){ 
													for(AtencionPendiente a: atencion.getlAtencion()){
					%>
						<tr style="font-size: 11px;">
							<td align="center"><%=a.getCodOmega()%></td>
							<td align="center"><%=a.getFecha()%></td>
							<td><%=a.getNombrePaciente()%></td>
							<td align="right"><%=a.getTotal()%></td>
							<td align="center"><input type="checkbox" name="selected" value="<%=a.getIdAtencion()%>"/></td>
						</tr>
					<%
						} 
												}
					%>
				</table>
				<input type="hidden" name="accion" value="detalle" />
				<input type="hidden" name="h_idInstitucion" value="detalle" />
			</form>
	</div>
</div>
<script>
	$(function() {
		var btnBuscar = $('#btnBuscar');
		var btnDetalle = $('#btnDetalle');
		var btnSelecAll = $('#btnSelecAll');
		var idInstitucion = $('#idInstitucion');
		var h_idInstitucion =  $('#h_idInstitucion');
		var h_institucion_old = $('#h_institucion_old');
		var h_fechaIni_old = $('#h_fechaIni_old');
		var h_fechaFin_old = $('#h_fechaFin_old');
		var fechaIni = $('#fechaIni');
		var fechaFin = $('#fechaFin');

		idInstitucion.val(h_institucion_old.val());
		fechaIni.val(h_fechaIni_old.val());
		fechaFin.val(h_fechaFin_old.val());
		
		btnBuscar.click(function(){
			if(idInstitucion.val() == ''){
				printError("Seleccioné la Institución.");
				return;
			}
			$("#frmBuscarPendiente").submit();
		});
		
		btnSelecAll.click(function(){
			$("input:checkbox[name=selected]").each(function(){    
		         $(this).attr("checked", true );
			});
		});
		
		btnDetalle.click(function(){
			if($('input:checkbox[name=selected]:checked').val()===undefined){
				printError("Seleccione atenciones.");
				return false;
			}
			h_idInstitucion.val(idInstitucion.val());
			$("#frmDetallePendiente").submit();
		});
	});
	
</script>

<%@ include file="footer.jsp"%>