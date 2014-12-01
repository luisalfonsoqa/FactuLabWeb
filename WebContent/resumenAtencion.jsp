<%@page import="com.factulab.dao.form.AtencionForm"%>
<%@page import="com.factulab.dao.form.AnalisisForm"%>
<%@ include file="header.jsp"%>

<%
	AtencionForm atencionForm = (AtencionForm) request.getSession().getAttribute(ServletConstante.SESSION_ATENCION);
	List<Tarifa> tarifas = (List<Tarifa>) request.getAttribute("tarifas");
%>

<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
			<tr>
				<td width="55%" align="right"><div style="font-size: 14px;">CONFIRMAR TICKET</div></td>
				<td width="45%" align="right" valign="top"><div align="right"><a href="/FactuLabWeb/CargarInsertarAtencionFormServlet" id="btnCancelarAtencion" class="button" ><span style="width: 100px; display: inline-block;">Cancelar Atención</span></a></div></td>
			</tr>
		</table>
		<form action="ProcesarAtencionServlet" id="frmProcesarAtencion">
			<div class="f_accordion">
				<h3>DATOS DEL PACIENTE</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="width: 120px; height: 25px;"><label class="title">Paciente:</label></td>
						<td style="width: 470px; display: inline-block;" ><%=atencionForm.getPaciente().getApepat()%> <%=atencionForm.getPaciente().getApemat()%> <%=atencionForm.getPaciente().getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;[<%=atencionForm.getPaciente().getIdPaciente()%>]</td>
						<td style="width: 80px;"><label class="title">DNI:</label></td>
						<td style="width: 200px;"><%=atencionForm.getPaciente().getDni()%></td>
					</tr>
					<tr>
						<td style="height: 25px;"><label class="title">Dirección:</label></td>
						<td colspan="3"><%=atencionForm.getPaciente().getDireccion()%></td>
					</tr>
					<tr>
						<td style="height: 5px;" colspan="4"></td>
					</tr>
				</table>
			</div>
			<%
				if(atencionForm.getConIGV()){
			%>
			<div class="f_accordion" id="divDatosFactura">
				<h3>DATOS DE LA FACTURA</h3>
				<table border="0" style="width: 100%; padding: 0; margin: 0;" >
					<tr>
						<td style="height: 25px; width: 120px;">
							<label class="title">Institución:</label>
							<input type="hidden" name="idInstitucion" id="h_institucion" value="<%=atencionForm.getInstitucion().getIdInstitucion()%>" />
						</td>
						<td colspan="3" style="display: inline-block;">
							<%=atencionForm.getInstitucion().getNombre()%> &nbsp;&nbsp;&nbsp;&nbsp;[<%=atencionForm.getInstitucion().getIdInstitucion()%>]
						</td>
					</tr>
					<tr>
						<td style="height: 25px;"><label class="title">Dirección:</label></td>
						<td style="width: 470px;"><input type="text" size="80" id="direccionInstitucion"  name="direccion" disabled="disabled" value="<%=atencionForm.getInstitucion().getDireccion()%>" /></td>
						<td style="width: 80px;"><label class="title">RUC:</label></td>
						<td style="width: 200px;"><input type="text" size="15" id="ruc" name="ruc" onkeypress="return isNumberKey(event);" maxlength="12" disabled="disabled" style="display: inline;" value="<%=atencionForm.getInstitucion().getRuc()%>" /></td>
					</tr>
					<tr>
						<td style="height: 5px;" colspan="4"></td>
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
						<td style="width: 150px; height: 25px;"><label class="title" id="lblTipoComprobante">Tipo de Comprobante:</label></td>
						<td style="width: 200px; display: inline-block;"  >
							<div id="btnTipoComprobante">
								<%=atencionForm.getConIGV() ? "Con IGV" : "Sin IGV"%>
								<!-- input type="radio" name="igv" value="1" size="5" style="display: inline;" /> Con IGV&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="igv" value="0" size="5" style="display: inline;" /> Sin IGV&nbsp;&nbsp;&nbsp;&nbsp;-->
							</div>
						</td>
						<td style="width: 150px; height: 25px;"><label class="title" id="lblTipoPago">Tipo de Pago:</label></td>
						<td style="width: 400px; display: inline-block;">
							<div id="btnTipoPago">
								<input type="radio" name="tipoPago" value="<%=ServiceConstante.TIPO_PAGO_CONTADO%>" style="display: inline;"  size="5"/> Contado&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="tipoPago" value="<%=ServiceConstante.TIPO_PAGO_TARJETA%>" style="display: inline;"  size="5"/> Tarjeta&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="tipoPago" value="<%=ServiceConstante.TIPO_PAGO_CONTADOTARJETA%>" style="display: inline;" size="5"/> Contado-Tarjeta&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="tipoPago" id="rdTipoPagoCredito" value="<%=ServiceConstante.TIPO_PAGO_CREDITO%>" style="display: inline;" size="5" 
								<%if(!atencionForm.getConIGV()) out.println(" disabled='disabled' ");%> /> Crédito
							</div>
						</td>
					</tr>
					<tr>
						<td style="height: 5px;" colspan="4"></td>
					</tr>
				</table>
			</div>
			<div class="f_accordion">
			<h3>ATENCION <%=atencionForm.getConIGV()? "POR INSTITUCIÓN " : "PARTICULAR"%></h3>
			<table border="0"  style="width: 100%; padding: 0; margin: 0;" id="tableListadoAnalisis" class="ui-widget ui-widget-content">
				<thead>
		    		<tr class="ui-widget-header">
						<td style="width: 370px;"><label>Descripción</label></td>
						<td style="width: 250px;"><label>Sección</label></td>
						<td style="width: 60px;" align="center"><label>P Unit S/D</label></td>
						<td style="width: 60px;" align="center"><label>Desc (%)</label></td>
						<td style="width: 60px;" align="center"><label>P Unit C/D</label></td>
						<td style="width: 40px;" align="center"><label>Cant</label></td>
						<td style="width: 60px;" align="center"><label>P Final</label></td>
					</tr>
				</thead>
				<tbody style="font-size: 11px;">
					<%
						for (AnalisisForm a : atencionForm.getlAnalisis()) {
					%>
			       	<tr>
				       	<td><%=a.getNombre()%></td>
				       	<td><%=a.getNombreSeccion()%></td>
				       	<td align="right"><%=a.getPrecioUnitConTarifaString()%></td>
				       	<td align="right"><%=a.getDescuentoString()%></td>
				       	<td align="right"><%=a.getPrecioUnitConDescuentoString()%></td>
				 		<td align="center"><%=a.getCantidad()%></td>
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
				<td></td>
				<td width="120" align="right"><label>PRECIO VENTA (S/.):</label></td>
				<td width="80px" align="left"><input type="text" size="8" disabled="disabled" class="numero" value="<%=atencionForm.getTotalSinDescuentoString()%>"/></td>
				<td width="120" align="right"><label>TOTAL: (S/.):</label></td>
				<td width="100px" align="center"><input type="text" size="8" disabled="disabled" class="numero"  value="<%=atencionForm.getTotalConDescuentoString()%>"/></td>
				<td width="200px" align="right">
					<%
						if(atencionForm.getCodigoOmega() == null) {
					%>
						<input type="button" class="button" value="Procesar Ticket" id="btnConfirmarAtencion" />
					<%
						}
					%>
				</td>
			</tr>
			</table>
			<input type="hidden" name="accion" value="procesar"/>
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

<div id="dialogFormInstitucion">
  <form id="frmNuevaInstitucion" action="AjaxInstitucionServlet">
  	<input type="hidden" name="accion" value="nuevo"/>
  	<input type="hidden" name="tipo" value="ajax"/>
	  <table border="0">
	  	<tr>
			<td width="150px"><label >Tarifa:</label></td>
            <td width="200px">
            	<select name="idTarifa" id="sltTarifaNI">
	            	<%
	            		for(Tarifa t : tarifas)
	            		            		        			out.println("<option value='"+t.getIdTarifa()+"'>"+t.getNombre()+"</option>");
	            	%>
            	</select>
            </td>
       	</tr>
       	<tr>
        	<td><label >Tipo de Facturación:</label></td>
            <td>
				<select name="idTipoFacturacion" id="sltTipoFacturacionNI">
					<option value='<%=DAOConstante.BD_FACTURACION_INMEDIATA%>'>Inmediata</option>	
					<option value='<%=DAOConstante.BD_FACTURACION_MENSUAL%>'>Mensual</option>
            	</select>
            </td>
       	</tr>
       	
        <tr>
        	<td><label >Nombre:</label></td>
            <td><input name="nombre" id="txtNombreNI" maxlength="250" size="40"/></td>
       	</tr>
        <tr>
        	<td><label >RUC</label></td>
            <td><input name="ruc" id="txtRucNI" maxlength="15" /></td>
       	</tr>
        <tr>
        	<td><label >Dirección</label></td>
            <td><input name="direccion" id="txtDireccionNI" maxlength="250" size="50" /></td>
       	</tr>
       	<tr>
        	<td><label >Contacto</label></td>
            <td><input name="contacto" id="txtContactoNI" maxlength="200" /></td>
       	</tr>
       	<tr>
        	<td><label >Email Contacto</label></td>
            <td><input name="email" id="txtEmailNI" maxlength="200" size="50" /></td>
       	</tr>
	  </table>
  </form>
</div>

<script>
	$(function() {
		//$('#divDatosFactura').hide();
		//$('#lblTipoPago').hide();
		//$('#btnTipoPago').hide();
		
		var btnCancelarAtencion = $('#btnCancelarAtencion');
		var btnConfirmarAtencion = $('#btnConfirmarAtencion');
		var ruc = $('#ruc');
		var idInstitucion = $('#idInstitucion');
		var h_institucion =$('#h_institucion');
		var direccionInstitucion = $('#direccionInstitucion');
		//var nombreInstitucion = $('#nombreInstitucion');
		var nombreInstitucion = '<%=atencionForm.getInstitucion().getNombre()%>';
		var tPagoCredito = '<%=ServiceConstante.TIPO_PAGO_CREDITO%>';
		//var igv = $('input:radio[name=igv]:checked');
		var igv = '<%=atencionForm.getConIGV()%>';
		
		/*$( "input:radio[name=tipoComprobante]" ).on( "click", function() {
			if($(this).val() == tPagoCredito){
				$('#lblTipoComprobante').hide();
				$('#btnTipoComprobante').hide();
				$('#divDatosFactura').hide();
			} else {
				$('#lblTipoComprobante').show();
				$('#btnTipoComprobante').show();
				$('input:radio[name="igv"][value="0"]').attr('checked',true);
			}
		});
		*/
		/*
		$( "input:radio[name=igv]" ).on( "click", function() {
			if($(this).val() == '0'){
				$('#divDatosFactura').hide();
			} else {
				$('#divDatosFactura').show();
			}
		});
		*/
		btnCancelarAtencion.click(function(){
			var msg = "¿Seguro de volver al Inicio?";
			if(confirm(msg)) $('#frmCancelarAtencion').submit();
		});
		
		btnConfirmarAtencion.click(function(){
			var isTipoPagoCredito = (tPagoCredito === $('input:radio[name=tipoPago]:checked').val());
			/*
			if($('input:radio[name=igv]:checked').val()===undefined){
				printError("Seleccione el \"Tipo de Comprobante\".");
				return false;
			} 
			*/
			if($('input:radio[name=tipoPago]:checked').val()===undefined){
				printError("Seleccione el \"Tipo de Pago\".");
				return false;
			} 
			//if(tPagoCredito !== $('input:radio[name=tipoPago]:checked').val()){
				//if ($('input:radio[name=igv]:checked').val() == '1'){
				if (igv === 'true'){
					//ID
					if(idInstitucion.val() == ''){
						printError("Ingrese los datos de la factura. \"Institución\".");
						return false;
					}
					if(!isTipoPagoCredito){
						//ruc
						if(ruc.val() == null || $.trim(ruc.val()).length < 11) {
							printError("Ingrese el RUC para la institución: <br/><br/>"+nombreInstitucion);
							ruc.removeAttr('disabled');
							ruc.focus();
							return false;
						}
						//direccion
						if(direccionInstitucion.val() == null || $.trim(direccionInstitucion.val()).length < 1) {
							printError("Ingrese la dirección para la institución: <br/><br/>"+nombreInstitucion);
							direccionInstitucion.removeAttr('disabled');
							direccionInstitucion.focus();
							return false;
						}
					}
				}
			//} 
				
			var msg = "Se generará la Ticket.";
			if(isTipoPagoCredito) msg = "Se generará el Atención.";
			
			if(confirm(msg)) {
				$('#frmProcesarAtencion').submit();
			}
		});
		
		/************************************************************
		*					PARTE 2
		*************************************************************/
		var btnCargarDialogoInstitucion = $('#btnCargarDialogoInstitucion');
		var divMostrar = $('#divMostrar');
		var tipoBusqueda;
		var jsonList;
		var criterio=$("#sltCriterio");
		var texto=$("#txtTexto");
		
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

		$('#btnBuscar').click(function(){
			listar(tipoBusqueda,criterio.val(),texto.val());
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
			idInstitucion.val(institucion.idInstitucion);
			h_institucion.val(institucion.idInstitucion);
			//nombreInstitucion.val(institucion.nombre);
			direccionInstitucion.val(institucion.direccion);
			ruc.val(institucion.ruc);
			//$("#frmAnalisis").submit();
		}
		
		function preDisplayList(pi, jq){
		    page_index=pi;
		    items_per_page = 7;
		    max_elem = Math.min((page_index+1) * items_per_page, membersToList.length);
	        if(tipoBusqueda=='Institucion'){
	            displayListInstitucion(jsonList);
	        }
		    return false;
		}
		
		divMostrar.dialog({
		      autoOpen: false,
		      height: 400,
		      width: 600,
		      modal: true,
		      buttons: {
		        "Agregar": function() {
		        	if(jsonList.length!=null){
				   		if(tipoBusqueda=='Institucion'){
				   			var radInstitucion=$("input[name=seleccionInstitucion]:checked");
				   			for(p=0;p<jsonList.length;p++){
		                        if(jsonList[p].idInstitucion==radInstitucion.val()){
		                        	llenarDatosInstitucion(jsonList[p])
		                        }
		                    }
				   		}
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
		    var optInit = { callback: preDisplayList };
		    optInit.num_edge_entries = 0;
		    optInit.prev_text = "Prev";
		    optInit.next_text = "Next";
		    $("#Pagination").pagination(membersToList.length, optInit);
		   
		}
		
		var dialogFormInstitucion = $('#dialogFormInstitucion');
		var btnCargarDialogoNuevaInstitucion = $('#btnCargarDialogoNuevaInstitucion');
		  
		  dialogFormInstitucion.dialog({
	      autoOpen: false,
	      height: 320,
	      width: 500,
	      modal: true,
	      title: 'Nueva Institución',
	      buttons: {
	        "Aceptar": function() {
				var msg="";
				if($('#sltTarifaNI').val()==null)
		            msg+="<br/>- Tarifa";		
				if($('#sltTipoFacturacionNI').val()==null)
		            msg+="<br/>- Tipo de Facturación";		
		        if($('#txtNombreNI').val()==null || $.trim($('#txtNombreNI').val())=="")
		            msg+="<br/>- Nombre";
		        if($('#txtDireccionNI').val()==null || $.trim($('#txtDireccionNI').val()).length < 1)
		            msg+="<br/>- Dirección";
		        if($('#txtRucNI').val()==null || $.trim($('#txtRucNI').val()).length < 11)
		            msg+="<br/>- RUC";
				if(msg==""){
				    $.ajax({
					     url:'AjaxInstitucionServlet',
					     data: $("#frmNuevaInstitucion").serialize(),
					     dataType: "json",
					     contentType: 'application/json',
					     mimeType: 'application/json',
					     success: function (institucion) {
					    	 llenarDatosInstitucion(institucion);
					    	 //alert(institucion.idInstitucion);
					    	 //dialogFormInstitucion.dialog( "close" );
					    	 //dialogForm.dialog( "open" );//Paciente
					    	 //asignar el valor al combo del modal del paciente
					    	 
					    }
					 });
				    $( this ).dialog( "close" );
				} else { printError('Debe Ingresar:'+msg); }
	        },
	        "Cancelar": function() {
	          $( this ).dialog( "close" );
	        }
	      },
	      close: function() {
	    	$('#txtNombreNI').val('');
	    	$('#txtRucNI').val('');
	    	$('#txtDireccionNI').val('');
	    	$('#txtContactoNI').val('');
	    	$('#txtEmailNI').val('');
	      }
		});
		btnCargarDialogoNuevaInstitucion.click(function() {
	  		dialogFormInstitucion.dialog( "open" );
	   	});
		
	});
</script>

<%@ include file="footer.jsp"%>