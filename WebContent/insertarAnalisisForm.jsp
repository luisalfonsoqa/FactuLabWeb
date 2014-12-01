<%@ include file="header.jsp"%>
<%
	AtencionForm atencion = (AtencionForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION);
%>

<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
			<tr>
				<td width="55%" align="right"><div style="font-size: 14px;">INGRESAR ANALISIS</div></td>
				<td width="45%" align="right" valign="top"><div align="right"><a href="/FactuLabWeb/CargarInsertarAtencionFormServlet" id="btnCancelarAtencion" class="button" ><span style="width: 100px; display: inline-block;">Cancelar Atención</span></a></div></td>
			</tr>
		</table>
		<form id="frmAnalisis" action="AnalisisServlet" style="padding: 0; margin: 0;">
			<div class="f_accordion">
				<h3>DATOS DE LA ATENCIÓN</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
				<tr>
					<td width="150px"><label class="title">&nbsp;Tipo Atención:</label></td>
					<td width="300px"><%=atencion.getConIGV()? "Por Institución" : "Particular"%></td>
					<td width="150px"><%
						if(atencion.getConIGV()) {
					%><label class="title">&nbsp;Institución:</label> <%
 	}
 %></td>
					<td width="300px"><%=atencion.getConIGV()? atencion.getInstitucion().getNombre() : ""%></td>
				</tr>
				<tr>
					<td><label class="title">&nbsp;Paciente:</label></td>
					<td width="300px" style="display: inline-block;"><%=atencion.getPaciente().getApepat()%> <%=atencion.getPaciente().getApemat()%> <%=atencion.getPaciente().getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;[<%=atencion.getPaciente().getIdPaciente()%>]</td>
					<td width="150px" ><label class="title">&nbsp;Medico:</label></td>
					<td width="300px" style="display: inline-block;"><%=atencion.getMedico().getApepat()%> <%=atencion.getMedico().getApemat()%> <%=atencion.getMedico().getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;[<%=atencion.getMedico().getIdMedico()%>]</td>
				</tr>
				</table>
			</div>
			<!-- div class="f_accordion">
				<h3>INGRESE LA INSTITUCION</h3>
				<table border="0" style="width: 900px; padding: 0; margin: 0;" >
				<tr>
					<td width="100px"><label>&nbsp;Datos de Factura:</label></td>
					<td colspan="3">
						<div style="inline-block;">			
							<img src="images/magnifier.png" alt="Buscar Institución" id="btnCargarDialogoInstitucion" width="26" height="26" style="display: inline;" />&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						<input type="hidden" name="idInstitucion" id="h_institucion"/>
					</td>
				</tr>
				<tr>
					<td><label >&nbsp;Institución:</label></td>
					<td width="400px">
						<input type="text" size="5" id="idInstitucion" disabled="disabled" onkeypress="return isNumberKey(event);" style="display: inline;"
							< % if(atencion.getInstitucion() != null) out.println(" value="+ atencion.getInstitucion().getIdInstitucion()); %>  
							 />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" size="60" id="nombreInstitucion" disabled="disabled"
							< % if(atencion.getInstitucion() != null) out.println(" value="+ atencion.getInstitucion().getNombre()); %>
						/>
					</td>
					<td width="100px" align="left"><label >RUC:</label></td>
					<td width="300px">
						<input type="text" size="20" id="ruc" disabled="disabled"
						< % if(atencion.getInstitucion() != null) out.println(" value="+ atencion.getInstitucion().getRuc()); %>
						/>
					</td>
				</tr>
				</table>
			</div-->
		
		<div class="f_accordion">
			<h3>AGREGAR ANALISIS</h3>
			<table border="0" style="width: 100%; padding: 0; margin: 0;" id="tableAgregarAnalisis">
				<tr>
					<td width="150px" height="35"><label>&nbsp;Código de Análisis:</label></td>
					<td width="80px" align="center"><input type="text" name="idAnalisis" size="3" id="idAnalisis" class="numero" onkeypress="return isNumberKey(event);"></td>
					<td>
						<img src="images/accept.png" alt="Aceptar Medico" id="btnAgregarAnalisis" width="25.5" height="25.5"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/magnifier.png" alt="Buscar Analisis" id="btnCargarDialogoAnalisis" width="26" height="26"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/cancelar.png" alt="Borrar Todo" id="btnLimpiarAnalisis" width="25.5" height="25.5"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="accion" id="accionAnalisis"/>						
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		</form>
		<form id="frmAnalisisDetalle" action="AnalisisServlet" style="padding: 0; margin: 0;">
			<input type="hidden" name="accion" id="accionAnalisisDetalle" value="actualizarDescuento"/>
			<table border="0"  style="width: 100%; padding: 0; margin: 0;" class="ui-widget ui-widget-content">	
				<thead>
		    		<tr class="ui-widget-header">
						<td width="30" align="center"><label>Cód</label></td>
						<td width="310"><label>Nombre</label></td>
						<td width="140"><label>Sección</label></td>
						<td width="60" align="center"><label>P. Uni</label></td>
						<td width="60" align="center"><label>P. Tar</label></td>
						<td width="60" align="center"><label>Desc (%)</label></td>
						<td width="50" align="center"><label>Cant.</label></td>
						<td width="60" align="center"><label>V. Venta</label></td>
						<td width="50" align="center"><label>P. Final</label></td>
						<td width="80" align="center"><label>Acción</label></td>
					</tr>
				</thead>
				<tbody style="font-size: 11px;">
					<%
					for (AnalisisForm a : atencion.getlAnalisis()){
						out.println("<tr><td align='center'>"+a.getIdAnalisis()+"</td><td>"+a.getNombre());
						if(a.getIdInstitucionDescuento() != null) out.println("<font color='red'>(*)</font>");
						out.println("</td>");
						out.println("<td>"+a.getNombreSeccion() +"</td>");
						out.println("<td align='right'>"+a.getPrecioUnitSinTarifaString()+"</td><td align='right'>"+a.getPrecioUnitConTarifaString()+"</td>");
						out.println("<td align='right'><input type='text' size='3' name='descuento' onchange='displayBtnAsignarDescuento();' onfocus='return focusIfZero(this);' onblur='return blurIfZero(this);' class='numero' value='"+a.getDescuento()+"'/></td>");
						out.println("<td align='center'>"+a.getCantidad()+"</td>");
						out.println("<td align='right'>"+a.getTotalSinDescuentoString()+"</td>");
						out.println("<td align='right'>"+a.getTotalConDescuentoString()+"</td>");
						out.println("<td align='center'><input type='button' class='button' onclick='fnEliminarAnalisis("+a.getIdAnalisis()+");' value='Eliminar'/></td></tr>");
					}
					%>
				</tbody>
			</table>
		</form>
		<br/>	
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 20px; vertical-align: middle;" id="tableAgregarAnalisis" class="ui-widget-header">
			<tr>
				<td align="right"></td>
				<td width="120px" align="right"><label>PRECIO VENTA (S/.):</label></td>
				<td width="80px" align="left"><input type="text" size="7" id="precioVenta" disabled="disabled" class="numero" value="<%=atencion.getTotalSinDescuentoString()%>"/></td>
				<td width="120px" align="right"><label>TOTAL (S/.):</label></td>
				<td width="100px" align="center"><input type="text" size="7" id="total" disabled="disabled" class="numero" value="<%=atencion.getTotalConDescuentoString()%>"/></td>
				<td width="200px" align="right">
					<input style="width: 150px; display: inline-block;" type="button" class="button" value="Asignar Descuentos" id="btnAsignarDescuento"/>
					<input style="width: 150px; display: inline-block;" type="button" class="button" value="Procesar Atencion" id="btnResumenAtencion"/>
				</td>
			</tr>
		</table>
		<div style="color: navy;"><%
			out.println("(*) Análisis con descuento por institución <strong>"+ atencion.getInstitucion().getNombre()+"</strong>");
		%></div>
		<input type="button" value="Eliminar Analisis" id="btnEliminarAnalisis" style="visibility: hidden;" />
		<form id="frmAtencion" action="ProcesarAtencionServlet">
			<input type="hidden" id="accionAtencion" name="accion"/>
		</form>
		<!-- 
		< % Integer numAnalisis = atencion.getlAnalisis().size();
			if(numAnalisis > 0) {
		%>
		<div class="f_accordion">
			<h3>DETALLE EN SOLES</h3>
			<table border="0" style="width: 100%; padding: 0; margin: 0;" >
			<tr><td align="center">
				<table border="0" style="width: 700px; padding: 0; margin: 0;" id="tableDescuentos">
					<tr class="ui-widget-header" align="center">
						<td colspan="3">Precio Unitario</td>
						<td rowspan="2" width="50">Cant</td>
						
						<td width="150" rowspan="2">Precio Venta</td>
						<td width="150" rowspan="2">Total</td>
						<!-- td colspan="2">Acomulado</td-- >
					</tr>
					<tr class="ui-widget-header" align="center">
						<td width="100">Valor Ini</td>
						<td width="100">Desc</td>
						<td width="150">Valor Fin</td>
						<!-- td>SubTotal</td>
						<td>Total </td-- >
					</tr>
					< % for (AnalisisForm a : atencion.getlAnalisis()){ %>
					<tr align="right">
						<td>< %=a.getDetalleMonto()[0]%></td>
						<td>< %=a.getDetalleMonto()[5]%></td>
						
						<td>
						< % if(!a.getDetalleMonto()[6].equals(a.getDetalleMonto()[7])) { %>
							<font color="grey">< %=a.getDetalleMonto()[6]%></font> -  
						< % } %>
						< %=a.getDetalleMonto()[7]%>
						</td>
						
						<td align="center">< %=a.getDetalleMonto()[1]%></td>
						
						<td>
						< % if(!a.getDetalleMonto()[2].equals(a.getDetalleMonto()[3])) { %>
							<font color="grey">< %=a.getDetalleMonto()[2]%></font> -  
						< % } %>
						< %=a.getDetalleMonto()[3]%>
						</td>
						
						<td>
						< % if(!a.getDetalleMonto()[8].equals(a.getDetalleMonto()[9])) { %>
							<font color="grey">< %=a.getDetalleMonto()[8]%></font> -  
						< % } %>
						< %=a.getDetalleMonto()[9]%>
						</td>
						<!-- td>< %=a.getDetalleMonto()[4]%></td>
						<td>< %=a.getDetalleMonto()[10]%></td-- >
					</tr>
					< % } %>
					<tr align="right"  class="ui-widget-header">
						<td colspan="4"></td>
						<td><b>S/. < %=atencion.getlAnalisis().get(numAnalisis-1).getDetalleMonto()[4] %></b></td>
						<td><b>S/. < %=atencion.getlAnalisis().get(numAnalisis-1).getDetalleMonto()[10] %></b></td>
						<!-- td></td>
						<td></td-- >
					</tr>
				</table>
			</td></tr>
			</table>
		</div>
		< % } %-->
	</div>
</div>



<div id="divMostrar" align="center">
    <table id="tblCriterio" cellpadding="3" border="0" >
     	<tr>
        	<td><label >Por: </label></td>
            <td>
            	<select name="sltCriterio" id="sltCriterio">
	            </select>
            </td>
            <td><input type="text" name="txtTexto" id="txtTexto" size="20"/></td>
            <td><input type="button" id="btnBuscar" value="Buscar" class="button"/></td>
       	</tr>
   	</table>
   	<br/>
   	<div>
	   	<div id="Pagination" class="pagination"></div>
	    <br/>
	    <table id="tblLista">
	    </table>
    </div>
</div>
<script>
	function fnEliminarAnalisis(id){
		$('#idAnalisis').val(id);
		$('#btnEliminarAnalisis').click();
	}
	function displayBtnAsignarDescuento(){
		$('#btnAsignarDescuento').show();
		$('#btnResumenAtencion').hide();
	}	
	function focusIfZero(obj){
		if($(obj).val() === '0') $(obj).val('');
	}
	function blurIfZero(obj){
		if($(obj).val() === '') $(obj).val('0');
	}
	
	
	$(function() {		
		var btnAgregarAnalisis = $('#btnAgregarAnalisis');
		var btnEliminarAnalisis = $('#btnEliminarAnalisis');
		var btnLimpiarAnalisis = $('#btnLimpiarAnalisis');
		var btnAsignarDescuento = $('#btnAsignarDescuento');
		var btnResumenAtencion = $('#btnResumenAtencion');
		var btnCancelarAtencion = $('#btnCancelarAtencion');
		
		var accionAnalisis = $('#accionAnalisis');
		var accionAtencion = $('#accionAtencion');
		var precioVenta = $('#precioVenta');

		var btnCargarDialogoAnalisis = $('#btnCargarDialogoAnalisis');
		var divMostrar = $('#divMostrar');
		var tipoBusqueda;
		var jsonList;
		var criterio=$("#sltCriterio");
		var texto=$("#txtTexto");
		
		var membersToList; //agregado
		var page_index; //agregado
		var items_per_page; //agregado
		var max_elem; //agregado
		
		btnAsignarDescuento.hide();
		
		btnCargarDialogoAnalisis.click(function(){
        	var jsonCriterio=[{valor:'nombre',texto:'Nombre'}];
        	
			for(x=0;x<jsonCriterio.length;x++){
				$('<option/>').attr('value',jsonCriterio[x].valor).text(jsonCriterio[x].texto).appendTo(criterio);
			}
			tipoBusqueda="Analisis";
			divMostrar.dialog( "open" );
			divMostrar.dialog({
		        title:"Buscar Analisis"
		 	});
		});
		
		$('#btnBuscar').click(function(){
			listar(tipoBusqueda,criterio.val(),texto.val());
		});
		    
		function preDisplayList(pi, jq){
			page_index=pi;
	        items_per_page = 7;
	        max_elem = Math.min((page_index+1) * items_per_page, membersToList.length);
	        if(tipoBusqueda=='Analisis'){
	        	displayListAnalisis(jsonList);
	        }
	        return false;
		}
		
		function displayListAnalisis(json){
			$("#tblLista").empty();
		    var htmlText="<tr><th width='20'></th><th width='50'>Codigo</th><th width='300'>Nombre</th><th>Seccion</th><th width='50'>Precio</th></tr>";
		    $("#tblLista").append(htmlText);
		    if(json.length!=null){
		    	for(var x=page_index*items_per_page;x<max_elem;x++){
		        	htmlText="";
		            htmlText+="<tr>";
		            htmlText+="<td align='left'><input type='radio' name='seleccionAnalisis' value='"+json[x].idAnalisis+"'/></td>";
		            htmlText+="<td align='left'>"+json[x].idAnalisis+"</td>";
		            htmlText+="<td align='left'>"+json[x].nombre+"</td>";
		            htmlText+="<td align='left'>"+json[x].nombreSeccion+"</td>";
		            htmlText+="<td align='left'>"+json[x].precio+"</td>";
		            htmlText+="</tr>";
		            $("#tblLista").append(htmlText);
		    	}
		 	}
	    }

		divMostrar.dialog({
		      autoOpen: false,
		      height: 400,
		      width: 600,
		      modal: true,
		      buttons: {
		        "Agregar": function() {
		        	if(jsonList.length!=null){
		        		if(tipoBusqueda=='Analisis'){
				        	var radAnalisis=$("input[name=seleccionAnalisis]:checked");
				        	for(p=0;p<jsonList.length;p++){
		                        if(jsonList[p].idAnalisis==radAnalisis.val()){
		                        	llenarDatosAnalisis(jsonList[p])
		                        }
		                    }
				   		}/* else if(tipoBusqueda=='Institucion'){
				   			var radInstitucion=$("input[name=seleccionInstitucion]:checked");
				   			for(p=0;p<jsonList.length;p++){
		                        if(jsonList[p].idInstitucion==radInstitucion.val()){
		                        	llenarDatosInstitucion(jsonList[p])
		                        }
		                    }
				   		} */
				   	}
				   	jsonList=null;
		        	$( this ).dialog( "close" );
		        },
		        "Cancelar": function() {
		          $( this ).dialog( "close" );
		        }
		      },
		      close: function() {
		    		criterio.empty();
					$("#tblLista").empty();
					$("#Pagination").empty();
					texto.val('');
		      }
		});
		
	  	function listar(txtClase,txtCriterio,txtTexto){
	  		var url = 'Ajax'+txtClase+'Servlet';
	  		var datos = 'criterio='+txtCriterio+"&texto="+txtTexto+"&accion=buscar";
	  		$.ajax({
			     url: url,
			     data: datos,
			     dataType: "json",
			     contentType: 'application/json',
			     mimeType: 'application/json',
			     success: function (data) {
			    	 if(data.msgError === undefined) {
			    		 jsonList=data;
			             paginarLista(jsonList);
			    	 } else {
			    		 printError(data.msgError);
			    	 }
			    }
			});
	  	}
	  	
		function paginarLista(json){ 
		    membersToList=json;
	        var optInit = {
	            callback: preDisplayList
	        };
	        optInit.num_edge_entries = 0;																							//NUMERO DE EDGE ENTRIES
	        optInit.prev_text = "Prev";																								//TEXTO PREV
	        optInit.next_text = "Next";
	        $("#Pagination").pagination(membersToList.length, optInit);
		}

		function llenarDatosAnalisis(analisis){
			 $("#idAnalisis").val(analisis.idAnalisis);
			 btnAgregarAnalisis.click();
		 }
		      
		/************************************************************
		*					PARTE 2
		*************************************************************/
		btnResumenAtencion.click(function(){
			if(precioVenta.val() == null || precioVenta.val() == '' || precioVenta.val() == '0,00' || precioVenta.val() == '0.00'){
				printError("Precio de Venta no valido.");
				return false;
			}	
			accionAtencion.val('resumen');
			$("#frmAtencion").submit();
		});
		
		btnAsignarDescuento.click(function(){
			 $("#frmAnalisisDetalle").submit();
		});
		
		btnAgregarAnalisis.click(function(){
			if($("#idAnalisis").val() == ''){
				printError("Ingrese Analisis.");
				return;
			}
			accionAnalisis.val('agregar');
			$("#frmAnalisis").submit();
		});
		
		btnEliminarAnalisis.click(function(){
			accionAnalisis.val('eliminar');
			$("#frmAnalisis").submit();
		});
		
		btnLimpiarAnalisis.click(function(){
			accionAnalisis.val('limpiar');
			$("#frmAnalisis").submit();
		});
		
		btnCancelarAtencion.click(function(){
			var rpt = confirm("¿Esta seguro de Cancelar la atención?"); 
			if(rpt) $('#frmCancelarAnalisis').submit();
		});
	
		/************************************************************
		*					PARTE 3
		*************************************************************/
		/*
		var t_institucion = $('#idInstitucion');
		var h_institucion = $('#h_institucion');
		var btnCargarDialogoInstitucion = $('#btnCargarDialogoInstitucion');
		
		btnCargarDialogoInstitucion.click(function(){
	    	var jsonCriterio=[{valor:'nombre',texto:'Nombre'},
	    	{valor:'ruc',texto:'RUC'}
	    	];
			for(x=0;x<jsonCriterio.length;x++){
				$('<option/>').attr('value',jsonCriterio[x].valor).text(jsonCriterio[x].texto).appendTo(criterio);
			}
			tipoBusqueda="Institucion";
			divMostrar.dialog( "open" );
			divMostrar.dialog({
		        title:"Buscar Institución"
		 	});
		});

		function displayListInstitucion(json){
	        $("#tblLista").empty();
	        var htmlText="<tr><th width='20'></th><th width='50'>Codigo</th><th width='80'>RUC</th><th width='400'>Nombre</th></tr>";
	        $("#tblLista").append(htmlText);
	        if(json.length!=null){
	            for(var x=page_index*items_per_page;x<max_elem;x++){
	                htmlText="";
	                htmlText+="<tr>";
	                htmlText+="<td><input type='radio' name='seleccionInstitucion' value='"+json[x].idInstitucion+"'/></td>";
	                htmlText+="<td align='center'>"+json[x].idInstitucion+"</td>";
	                htmlText+="<td align='left'>"+json[x].ruc+"</td>";
	                htmlText+="<td align='left'>&nbsp;&nbsp;"+json[x].nombre+"</td>";
	                htmlText+="</tr>";
	                $("#tblLista").append(htmlText);
	            }
	        }
	    }
		
		function llenarDatosInstitucion(institucion){
			t_institucion.val(institucion.idInstitucion);
			h_institucion.val(institucion.idInstitucion);
			accionAnalisis.val('agregarInstitucion');
			$("#frmAnalisis").submit();
		}
		*/
	});
</script>
<%@ include file="footer.jsp"%>
