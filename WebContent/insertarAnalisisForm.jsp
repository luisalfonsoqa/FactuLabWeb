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
					<td width="80px" align="center"><input type="text" name="idAnalisis" size="3" id="idAnalisis" onkeypress="return isNumberKey(event);"></td>
					<td>
						<img src="images/accept.png" alt="Aceptar Medico" id="btnAgregarAnalisis" width="25.5" height="25.5"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/magnifier.png" alt="Buscar Analisis" id="btnCargarDialogoAnalisis" width="26" height="26"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/cancelar.png" alt="Borrar Todo" id="btnLimpiarAnalisis" width="25.5" height="25.5"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" id="h_descuento" name="descuento"/>
						<input type="hidden" name="accion" id="accionAnalisis"/>						
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		</form>
		<table border="0"  style="width: 100%; padding: 0; margin: 0;" id="tableListadoAnalisis" class="ui-widget ui-widget-content">	
			<thead>
	    		<tr class="ui-widget-header">
					<td width="30"><label>Cód</label></td>
					<td width="220"><label>Nombre</label></td>
					<td width="150"><label>Sección</label></td>
					<td width="30"><label>Mon</label></td>
					<td width="70"><label>P. Uni</label></td>
					<td width="70"><label>P. Tar</label></td>
					<td width="40"><label>Cant.</label></td>
					<td width="70"><label>V. Venta</label></td>
					<td width="40"><label>Desc.</label></td>
					<td width="70"><label>P. Final</label></td>
					<td width="60"><label>Acción</label></td>
				</tr>
			</thead>
			<tbody style="font-size: 11px;">
				<%
					for (AnalisisForm a : atencion.getlAnalisis()){
									out.println("<tr><td>"+a.getIdAnalisis()+"</td><td>"+a.getNombre());
									if(a.getIdInstitucionDescuento() != null)
										out.println("<font color='red'>(*)</font>");
									out.println("</td>");
									out.println("<td>"+a.getNombreSeccion() +"</td><td>S/.</td>");
									out.println("<td>"+a.getPrecioUnitSinTarifaString()+"</td><td>"+a.getPrecioUnitConTarifaString()+"</td>");
									out.println("<td>"+a.getCantidad()+"</td><td>"+a.getTotalSinDescuentoString()+"</td>");
									out.println("<td>"+atencion.getPorcentajeDescuentoString()+"</td><td>"+a.getTotalConDescuentoString()+"</td>");
									out.println("<td><input type='button' class='button' onclick='fnEliminarAnalisis("+a.getIdAnalisis()+");' value='Eliminar'/></td></tr>");
								}
				%>
			</tbody>
		</table>
		
		<br/>
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 20px; vertical-align: middle;" id="tableAgregarAnalisis" class="ui-widget-header">
			<tr>
				<td width="100px"></td>
				<td width="190px"><label>PRECIO VENTA (S/.):</label></td>
				<td width="80px" align="left"><input type="text" size="8" id="precioVenta" disabled="disabled" value="<%=atencion.getTotalSinDescuentoString()%>"/></td>
				<td width="150px" align="right"><label>DESC (%):</label></td>
				<td width="150px" align="left" style="display: inline-block;"> 
					<input type="text" size="3" id="descuento" name="descuento" value="<%=atencion.getPorcentajeDescuento()%>" onkeypress="return isNumberKey(event);" style="display: inline;"/>  
					<!-- input type="button" class="button" value="B" id="btnAsignarDescuento"/-->
					<img src="images/accept.png" alt="Asignar Descuento" id="btnAsignarDescuento" width="25.5" height="25.5" style="display: inline;"/>
				</td>
				<td width="150px" align="right"><label>TOTAL: (S/.):</label></td>
				<td width="100px" align="center"><input type="text" size="8" id="total" disabled="disabled"  value="<%=atencion.getTotalConDescuentoString()%>"/></td>
				<td align="right">
					<input type="button" class="button" value="Procesar Atencion" id="btnResumenAtencion" />
				</td>
			</tr>
		</table>
		<div style="color: navy;"><%
			out.println("(*) Análisis con descuento por institución <strong>"+ atencion.getInstitucion().getNombre()+"</strong>");
		%></div>
		<input type="button" value="Eliminar Analisis" id="btnEliminarAnalisis" style="visibility: hidden;" />
		<form id="frmAtencion" action="ProcesarAtencionServlet">
			<input type="hidden" id="h_descuento2" name="descuento"/>
			<input type="hidden" id="accionAtencion" name="accion"/>
		</form>
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
	$(function() {		
		var btnAgregarAnalisis = $('#btnAgregarAnalisis');
		var btnEliminarAnalisis = $('#btnEliminarAnalisis');
		var btnLimpiarAnalisis = $('#btnLimpiarAnalisis');
		var btnAsignarDescuento = $('#btnAsignarDescuento');
		var btnResumenAtencion = $('#btnResumenAtencion');
		var btnCancelarAtencion = $('#btnCancelarAtencion');
		
		var h_descuento = $('#h_descuento');
		var h_descuento2 = $('#h_descuento2');
		var accionAnalisis = $('#accionAnalisis');
		var accionAtencion = $('#accionAtencion');
		var precioVenta = $('#precioVenta');
		var descuento = $('#descuento');

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
			h_descuento2.val(descuento.val());
			accionAtencion.val('resumen');
			$("#frmAtencion").submit();
		});
		
		btnAsignarDescuento.click(function(){
			 h_descuento.val(descuento.val());
			 accionAnalisis.val('actualizarDescuento');
			 $("#frmAnalisis").submit();
		});
		
		btnAgregarAnalisis.click(function(){
			if($("#idAnalisis").val() == ''){
				printError("Ingrese Analisis.");
				return;
			}
			h_descuento.val(descuento.val());
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
