<%@ include file="header.jsp"%>
<%
	List<Institucion> lInstitucion = (List<Institucion>) request.getAttribute("instituciones"); 
List<Tarifa> tarifas = (List<Tarifa>) request.getAttribute("tarifas");
List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
%>
<div align="center">
	<div style="width: 900px;" align="left">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 30px" class="ui-widget-header">
			<tr>
				<td width="100%" align="center"><div style="font-size: 14px;">INGRESAR PACIENTE / MEDICO</div></td>
			</tr>
		</table>

		<div class="f_accordion">
			<h3>DATOS DE LA ATENCION</h3>
			<form id="frmAnalisis" action="CargarInsertarAnalisisFormServlet">
			<table border="0" style="width: 100%; padding: 0; margin: 0;" >
				<tr>
					<td width="150px"><label>&nbsp;Tipo de Atención:</label></td>
					<td colspan="2">
							<input type="hidden" id="h_paciente" name="idPaciente"/>
							<input type="hidden" id="h_medico" name="idMedico"/>			
							<select name="igv" id="sltIGV">
			            		<option value=''>--- Seleccione ---</option>	
			            		<option value='0'>Particular</option>
			            		<option value='1'>Por Institución</option>
		            		</select>		            		
					</td>
				</tr>
				<tr>
					<td><label id=lblInstitucion>&nbsp;Institución:</label></td>
					<td width="600px">
						<select name="idInstitucion" id="sltInstitucion">
		            		<option value=''>--- Seleccione ---</option>	
			            	<%
				            		for(Institucion d : lInstitucion)
				            			            		        			out.println("<option value='"+d.getIdInstitucion()+"'>"+d.getNombre()+"</option>");
				            	%>
		            	</select>
					</td>
					<td width="150px" align="right"><input id="btnCargarDialogoNuevaInstitucion" type="button" class="button" value="Nueva Institución" /></td>
				</tr>
			</table>
			</form>
		</div>
		
		<div class="f_accordion">
			<h3>DATOS DE LA PACIENTE</h3>
			<table border="0" style="width: 900px;">
			<tr>
				<td width="100px"><label>&nbsp;ID Paciente:</label></td>
				<td colspan="4">
					<form id="frmPaciente" style="display: inline-block;">
						<input type="text" name="idPaciente" id="idPaciente" size="5" onkeypress="return isNumberKey(event);" style="display: inline;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/accept.png" alt="Aceptar Paciente" id="btnObtenerPaciente" width="25.5" height="25.5" style="display: inline;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/magnifier.png" alt="Buscar Paciente" id="btnCargarDialogoPaciente" width="26" height="26" style="display: inline;"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/add.png" alt="Agregar Paciente" id="btnCargarDialogoNuevoPaciente" width="25.5" height="25.5" style="display: inline;"/>
						<input type="hidden" name="accion" value="buscarID" style="display: inline;"/>
					</form>
				</td>
				<td width="200" align="right"></td>
			</tr>
			<tr>
				<td><label>&nbsp;Paciente:</label></td>
				<td colspan="5"><input type="text" size="80" id="nombrePaciente" disabled="disabled"/></td>
			</tr>
			<tr>
				<td><label>&nbsp;DNI:</label></td>
				<td width="200px"><input type="text" size="20" id="dni" disabled="disabled"/></td>
				<td width="100px"><label >T. Paciente:</label></td>
				<td width="200px"><input type="text" size="20" id="tipoPaciente"  disabled="disabled"/></td>
				<td width="100px"><label >Hist. Clinica:</label></td>
				<td><input type="text" size="20" id="histClinica" disabled="disabled"/></td>
			</tr>
			<tr>
				<td><label>&nbsp;Teléfono:</label></td>
				<td><input type="text" size="20" id="telefono" disabled="disabled"/></td>
				<td><label>Celular:</label></td>
				<td><input type="text" size="20" id="celular" disabled="disabled"/></td>
				<td><label>Fax:</label></td>
				<td><input type="text" size="20" id="fax" disabled="disabled"/></td>
			</tr>
			<tr>
				<td><label>&nbsp;Nacimiento:</label></td>
				<td><input type="text" size="20" id="fecnac" disabled="disabled"/></td>
				<td><label>Sexo:</label></td>
				<td><input type="text" size="20" id="sexo" disabled="disabled"/></td>
				<td><label>Edad:</label></td>
				<td><input type="text" size="20" id="edad" disabled="disabled"></td>
			</tr>
			<tr>
				<td><label>&nbsp;Dirección:</label></td>
				<td colspan="5"><input type="text" size="80" id="direccion" name="direccion"  disabled="disabled"/></td>
			</tr>
			<tr>
				<td><label>&nbsp;Departamento:</label></td>
				<td><input type="text" size="20"  id="departamento"  disabled="disabled"/></td>
				<td><label>Provincia:</label></td>
				<td><input type="text" size="20"  id="provincia"  disabled="disabled"/></td>
				<td><label>Distrito:</label></td>
				<td><input type="text" size="20"  id="distrito"  disabled="disabled"/></td>
			</tr>
			<tr>
				<td><label>&nbsp;Institución:</label></td>
				<td colspan="3" >
					<div style="display: inline-block;">
					<input type="text" size="5" id="idInstitucion" disabled="disabled" style="display: inline;"/> - 
					<input type="text" size="50" id="nombreInstitucion" disabled="disabled" style="display: inline;"/>
					</div>
				</td>
				<td><label>Tarifa:</label></td>
				<td><input type="text" size="20" id="tarifa"   disabled="disabled"/></td>
			</tr>
			</table>
		</div>
		
		<div class="f_accordion">
			<h3>DATOS DEL MEDICO</h3>
			<table border="0" style="width: 900px;">
				<tr>
					<td width="100px"><label>&nbsp;ID Médico:</label></td>
					<td width="300px">
						<form id="frmMedico" style="display: inline-block;">			
							<input type="text" name="idMedico" size="5" id="idMedico" onkeypress="return isNumberKey(event);" style="display: inline;" />&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="images/accept.png" alt="Aceptar Medico" id="btnObtenerMedico" width="25.5" height="25.5" style="display: inline;" />&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="images/magnifier.png" alt="Buscar Medico" id="btnCargarDialogoMedico" width="26" height="26" style="display: inline;" />&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="images/add.png" alt="Agregar Médico" id="btnCargarDialogoNuevoMedico" width="25.5" height="25.5" style="display: inline;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="hidden" name="accion" value="buscarID"/>
						</form>
					</td>
					<td width="100px">
						<label >Médico:</label>
					</td>
					<td width="400px" >
						<input type="text" size="80" id="nombreMedico" disabled="disabled"/>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- form id="frmAnalisis" action="CargarInsertarAnalisisFormServlet">
			<input type="hidden" id="h_paciente" name="idPaciente"/>
			<input type="hidden" id="h_medico" name="idMedico"/>			
			<input type="hidden" id="h_igv" name="igv"/>
		</form-->
		<form action="CargarInsertarAtencionFormServlet">
		<table border="0" style="width: 100%; padding: 0; margin: 0; height: 20px; vertical-align: middle;" class="ui-widget-header">
			<tr>
				<td width="660px"></td>
				<td width="150px">
					<input id="btnGenerarAtencion" type="button" class="button" value="Generar Atención" />
				</td>
				<td width="150px">
					<input id="btnCancelarAtencion" type="submit" class="button" value="Cancelar Atención" />
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
 
<div id="divMostrar">
    <table id="tblCriterio" cellpadding="3">
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

<div id="dialogForm">
  <form id="frmNuevoPaciente" action="AjaxPacienteServlet">
  	<input type="hidden" name="accion" value="nuevo"/>
	  <table id="dialogFormContenido" border="0">
	  	<tr>
			<td width="100px"><label >Institución:</label></td>
            <td colspan="3">
            	<select name="idInstitucion" id="sltInstitucionNP">
            		<option value=''>--- Seleccione ---</option>	
	            	<%
		            		for(Institucion d : lInstitucion)
		            			            		        			out.println("<option value='"+d.getIdInstitucion()+"'>"+d.getNombre()+"</option>");
		            	%>
            	</select>
            </td>
        	<td width="100px;"><label >Tipo de Paciente:</label></td>
            <td width="200px">
            	<select name="idTipoPaciente" id="sltTipoPacienteNP">
            	<%
            		for(TipoPaciente d : constanteBD.getlTipoPaciente())
            	            	        			out.println("<option value='"+d.getIdTipoPaciente()+"'>"+d.getDescripcion()+"</option>");
            	%>
            	</select>
            </td>
       	</tr>
        <tr>
        	<td><label >Nombre:</label></td>
            <td width="200px;" ><input name="nombre" id="txtNombreNP" maxlength="50"  /></td>
        	<td width="100px;" ><label >Paterno:</label></td>
            <td width="200px;" ><input name="apepat" id="txtPaternoNP" maxlength="50" /></td>
        	<td><label >Materno:</label></td>
            <td><input name="apemat" id="txtMaternoNP" maxlength="50" /></td>
       	</tr>
        <tr>
        	<td><label >DNI:</label></td>
            <td><input name="dni" id="txtDniNP" maxlength="8" onkeypress="return isNumberKey(event);" /></td>
        	<td><label >Fecha Nac:</label></td>
            <td><input type="text" id="txtNacimientoNP" name="fecnac" class="datepicker_nacimiento" style="display: inline-block;" size="12"></td>
       		<td><label >Sexo:</label></td>
        	<td  style="display: inline-block;">	
        		<input type="radio" name="sexo" value="M" checked="checked" style="display: inline;"> M&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="sexo" value="F" style="display: inline;"> F&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
       </tr>
       <tr>
       		<td><label >Departamento:</label></td>
        	<td>
        		<select name="idDepartamento" id="sltDepartamentoNP">
	        		<option value=''>--- Seleccione ---</option>	
	        		<%
		        			for(Departamento d : constanteBD.getlDepartamento())
		        				        			        			out.println("<option value='"+d.getIdDepartamento()+"'>"+d.getNombre()+"</option>");
		        		%>
        		</select>
        		</td>
       		<td><label >Provincia:</label></td>
            <td>
            	<select name="idProvincia" id="sltProvinciaNP" >
            		<option value=''>--- Seleccione ---</option>
        		</select>
            </td>
           	<td><label >Distrito:</label></td>
            <td>
            	<select name="idDistrito" id="sltDistritoNP" >
            		<option value=''>--- Seleccione ---</option>
        		</select>
            </td>
        </tr>
     	<tr>
        	<td><label >Dirección:</label></td>
            <td colspan="3"><input name="direccion" id="txtDireccionNP" maxlength="200" /></td>
       	</tr>
        <tr>
        	<td><label >Teléfono:</label></td>
            <td><input name="telefono" id="txtTelefonoNP" maxlength="40" /></td>
			<td><label >Celular:</label></td>
            <td><input name="celular" id="txtCelularNP"  maxlength="50" /></td>
        	<td><label >Hist. Clinica:</label></td>
            <td><input name="histClinica" id="txtHistClinicaNP" maxlength="50" /></td>
		</tr>
        <tr>
        	<td><label >Email:</label></td>
            <td><input name="email" id="txtEmailNP"  maxlength="50" /></td>
			<td><label >Fax:</label></td>
            <td><input name="fax" id="txtFaxNP" maxlength="50" /></td>
            <td></td>
            <td></td>
        </tr>
	  </table>
  </form>
</div>

<div id="dialogFormInstitucion">
  <form id="frmNuevaInstitucion" action="AjaxInstitucionServlet">
  	<input type="hidden" name="accion" value="nuevo"/>
  	<input type="hidden" name="tipo" value="all"/>
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

<div id="dialogFormMedico">
  <form id="frmNuevoMedico" action="AjaxMedicoServlet">
  	<input type="hidden" name="accion" value="nuevo"/>
	  <table border="0">
	  	<tr>
			<td width="120px"><label >Nombre:</label></td>
            <td width="200px"><input name="nombre" id="txtNombreNM" maxlength="50" size="30"/></td>
       	</tr>
       	<tr>
        	<td><label >Ape. Paterno:</label></td>
            <td><input name="apepat" id="txtApePatNM" maxlength="50" size="30"/></td>
       	</tr>
       	
        <tr>
        	<td><label >Ape. Materno:</label></td>
            <td><input name="apemat" id="txtApeMatNM" maxlength="50" size="30"/></td>
       	</tr>
        <tr>
        	<td><label >CMP</label></td>
            <td><input name="cmp" id="txtCmpNM" maxlength="20" size="20"/></td>
       	</tr>
        <!-- tr>
        	<td>Dirección</td>
            <td><input name="direccion" id="txtDireccionNI" maxlength="250" size="60" /></td>
       	</tr-->
       	<tr>
        	<td><label >Especialidad</label></td>
            <td><select name="idEspecialidad" id="sltEspecialidadNM">
					<option value=''>--- Seleccione ---</option>	
					<%
							for(Especialidad e : especialidades)
											        			out.println("<option value='"+e.getIdEspecialidad()+"'>"+e.getNombre()+"</option>");
						%>
            	</select>
            </td>
       	</tr>
	  </table>
  </form>
</div>

<script>
	$(function() {
		
		
		var btnObtenerPaciente = $('#btnObtenerPaciente');
		var btnObtenerMedico= $('#btnObtenerMedico');
		var btnGenerarAtencion = $('#btnGenerarAtencion');
		//MEDICO
		var h_medico = $('#h_medico');
		var nombreMedico = $('#nombreMedico');
		var t_paciente = $('#idPaciente');
		var t_medico = $('#idMedico');
		
		//PACIENTE
		var h_paciente = $('#h_paciente');
		var dni = $('#dni');
		var histClinica = $('#histClinica');
		var telefono = $('#telefono');
		var celular = $('#celular');
		var fax = $('#fax');
		var fecnac = $('#fecnac');
		var idInstitucion = $('#idInstitucion');
		var sexo = $('#sexo');
		var direccion = $('#direccion');
		var nombrePaciente = $('#nombrePaciente');
		
		var tipoPaciente = $('#tipoPaciente');
		var institucion = $('#nombreInstitucion');
		var departamento = $('#departamento');
		var provincia = $('#provincia');
		var distrito = $('#distrito');
		var tarifa = $('#tarifa');
		var edad = $('#edad');
		
		var btnCargarDialogoPaciente = $('#btnCargarDialogoPaciente');
		var btnCargarDialogoMedico = $('#btnCargarDialogoMedico');
		var divMostrar = $('#divMostrar');
		var tipoBusqueda;
		var jsonList;
		var criterio=$("#sltCriterio");
		var texto=$("#txtTexto");
		
		//Tipo de Atencion (Institucion)
		var lblInstitucion = $('#lblInstitucion');
		var sltInstitucion = $('#sltInstitucion');
		var sltIGV = $('#sltIGV');
		lblInstitucion.hide();
		sltInstitucion.hide();
		
		
		btnCargarDialogoPaciente.click(function(){
        	var jsonCriterio=[{valor:'nombre',texto:'Nombre'},
        	{valor:'apepat',texto:'Paterno'},
        	{valor:'apemat',texto:'Materno'},
            {valor:'dni',texto:'DNI'}
        	];
			for(x=0;x<jsonCriterio.length;x++){
				$('<option/>').attr('value',jsonCriterio[x].valor).text(jsonCriterio[x].texto).appendTo(criterio);
			}
			tipoBusqueda="Paciente";
			divMostrar.dialog( "open" );
			divMostrar.dialog({
		        title:"Buscar Paciente"
		 	});
		});
		btnCargarDialogoMedico.click(function(){
        	var jsonCriterio=[{valor:'nombre',texto:'Nombre'},
            {valor:'apepat',texto:'Paterno'},
            {valor:'apemat',texto:'Materno'},
            {valor:'cmp',texto:'CMP'}
        	];
			for(x=0;x<jsonCriterio.length;x++){
				$('<option/>').attr('value',jsonCriterio[x].valor).text(jsonCriterio[x].texto).appendTo(criterio);
			}
			tipoBusqueda="Medico";
			divMostrar.dialog( "open" );
			divMostrar.dialog({
		        title:"Buscar Medico"
		 	});
		});
		
		$('#btnBuscar').click(function(){
			listar(tipoBusqueda,criterio.val(),texto.val());
		});
		    
		function preDisplayList(pi, jq){
		    page_index=pi;
		    items_per_page = 7;
		    max_elem = Math.min((page_index+1) * items_per_page, membersToList.length);

	        if(tipoBusqueda=='Paciente'){
	            displayListPaciente(jsonList);
	        } else if(tipoBusqueda=='Medico'){
	            displayListMedico(jsonList);
	        }
		    return false;
		}
		
		function displayListPaciente(json){
	        $("#tblLista").empty();
	        var htmlText="<tr><th width='50'></th><th width='50'>Codigo</th><th width='200'>Nombre</th><th width='50'>DNI</th></tr>";
	        $("#tblLista").append(htmlText);
	        if(json.length!=null){
	            for(var x=page_index*items_per_page;x<max_elem;x++){
	                htmlText="";
	                htmlText+="<tr>";//<input type="radio" name="radSexo" id="radSexoM" value="M"/>
	                htmlText+="<td><input type='radio' name='seleccionPaciente' value='"+json[x].idPaciente+"'/></td>";
	                htmlText+="<td align='center'>"+json[x].idPaciente+"</td>";
	                htmlText+="<td align='left'>"+json[x].apepat+" "+json[x].apemat + " " +json[x].nombre+"</td>";
	                htmlText+="<td align='center'>"+json[x].dni+"</td>";
	                htmlText+="</tr>";
	                $("#tblLista").append(htmlText);
	            }
	        }
	    }
		
		function displayListMedico(json){
	        $("#tblLista").empty();
	        var htmlText="<tr><th width='50'></th><th width='50'>Codigo</th><th width='200'>Nombre</th><th>CMP</th></tr>";
	        $("#tblLista").append(htmlText);
	        if(json.length!=null){
	            for(var x=page_index*items_per_page;x<max_elem;x++){
	                htmlText="";
	                htmlText+="<tr>";//<input type="radio" name="radSexo" id="radSexoM" value="M"/>
	                htmlText+="<td><input type='radio' name='seleccionMedico' value='"+json[x].idMedico+"'/></td>";
	                htmlText+="<td align='center'>"+json[x].idMedico+"</td>";
	                htmlText+="<td align='left'>"+json[x].apepat+" "+json[x].apemat + " " +json[x].nombre+"</td>";
	                htmlText+="<td align='center'>"+json[x].cmp+"</td>";
	                htmlText+="</tr>";
	                $("#tblLista").append(htmlText);
	            }
	        }
	    }

		divMostrar.dialog({
		      autoOpen: false,
		      height: 400,
		      width: 400,
		      modal: true,
		      buttons: {
		        "Agregar": function() {
		        	if(jsonList.length!=null){
		        		if(tipoBusqueda=='Paciente'){
				        	var radPaciente=$("input[name=seleccionPaciente]:checked");
				        	for(p=0;p<jsonList.length;p++){
		                        if(jsonList[p].idPaciente==radPaciente.val()){
		                        	llenarDatosPaciente(jsonList[p])
		                        }
		                    }
				   		} else if(tipoBusqueda=='Medico'){
				        	var radMedico=$("input[name=seleccionMedico]:checked");
				        	for(p=0;p<jsonList.length;p++){
		                        if(jsonList[p].idMedico==radMedico.val()){
		                        	llenarDatosMedico(jsonList[p])
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
		  
		 function llenarDatosPaciente(paciente){
			 t_paciente.val(paciente.idPaciente);
			 h_paciente.val(paciente.idPaciente);
	    	 dni.val(paciente.dni);
	    	 histClinica.val(paciente.histClinica);
	    	 telefono.val(paciente.telefono);
	    	 celular.val(paciente.celular);
	    	 fax.val(paciente.fax);
	    	 fecnac.val(paciente.fecnac);
	    	 idInstitucion.val(paciente.idInstitucion);
	    	 sexo.val(paciente.sexo);
	    	 direccion.val(paciente.direccion);
	    	 nombrePaciente.val(paciente.apepat + " " + paciente.apemat + " " + paciente.nombre);
	    	 
	    	 tipoPaciente.val(paciente.nombreTipoPaciente);
	    	 institucion.val(paciente.nombreInstitucion);
	    	 departamento.val(paciente.nombreDepartamento);
	    	 provincia.val(paciente.nombreProvincia);
	    	 distrito.val(paciente.nombreDistrito);
	    	 tarifa.val(paciente.nombreTarifa);
	    	 edad.val(paciente.edad);
		 }
		 function llenarDatosMedico(medico){
			 t_medico.val(medico.idMedico);
			 h_medico.val(medico.idMedico);
	    	 nombreMedico.val(medico.apepat + " "+medico.apemat+" "+medico.nombre);
		 }
		        
	  	
		/**************************************************************
		* 					PARTE 2
		***************************************************************/
		
		btnObtenerPaciente.click(function(){
			if($("#idPaciente").val() == ''){
				printError("Ingrese Paciente.");
				return;
			}
			 $.ajax({
			     url:'AjaxPacienteServlet',
			     data: $("#frmPaciente").serialize(),
			     dataType: "json",
			     contentType: 'application/json',
			     mimeType: 'application/json',
			     success: function (data) {
			    	 if(data.msgError === undefined) {
			    		 llenarDatosPaciente(data);
			    	 } else {
			    		 printError(data.msgError);
			    	 }
			     }
			 });
		});
		
		btnObtenerMedico.click(function(){
			if($("#idMedico").val() == ''){
				printError("Ingrese Medico.");
				return;
			}
			
			 $.ajax({
			     url:'AjaxMedicoServlet',
			     data: $("#frmMedico").serialize(),
			     dataType: "json",
			     contentType: 'application/json',
			     mimeType: 'application/json',
			     success: function (data) {
			    	 if(data.msgError === undefined) {
			    		 h_medico.val(data.idMedico);
				    	 nombreMedico.val(data.apepat + " "+data.apemat+" "+data.nombre);
			    	 } else {
			    		 printError(data.msgError);
			    	 }
			    }
			 });
		});
		
		btnGenerarAtencion.click(function(){
			if(h_paciente.val() == null || h_paciente.val() == ''){
				printError("Debe seleccionar el Paciente.");
				return false;
			}
			if(h_medico.val() == null || h_medico.val() == ''){
				printError("Debe seleccionar el Médico.");
				return false;
			}
			if(sltIGV.val() == ''){
				printError("Debe seleccionar el Tipo de Atención.");
				return false;
			}
			if(sltIGV.val() == '1' && sltInstitucion.val() == ''){
				printError("Debe seleccionar la Institución");
				return false;
			}
			$("#frmAnalisis").submit();
		});
		
		/**************************************************************
		* 					PARTE 3
		***************************************************************/
		var dialogForm = $('#dialogForm');
		var btnCargarDialogoNuevoPaciente = $('#btnCargarDialogoNuevoPaciente');
		var cmbDepartamentoNP=$("#sltDepartamentoNP");
		var cmbProvinciaNP=$("#sltProvinciaNP");
		var cmbDistritoNP=$("#sltDistritoNP");
		//var cmbInstitucionNP=$("#sltInstitucionNP");
		//var cmbTipoPacienteNP=$("#sltTipoPacienteNP");
		  
		  dialogForm.dialog({
	      autoOpen: false,
	      height: 320,
	      width: 900,
	      modal: true,
	      title: 'Nuevo Paciente',
	      buttons: {
	        "Aceptar": function() {
				var arrChkBox = document.getElementsByName("sexo");
				var sexo = '';
				if($(arrChkBox[0]).attr('checked')) sexo=$(arrChkBox[0]).val();
				else sexo=$(arrChkBox[1]).val();
				var msg="";
				    	if($('#sltInstitucionNP').val()==null)
				            msg+="<br/>- Institucion";
				        if($('#sltTipoPacienteNP').val()==null)
				            msg+="<br/>- Tipo de Paciente";
				        if($('#txtNombreNP').val()==null || $.trim($('#txtNombreNP').val())=="")
				            msg+="<br/>- Nombre";
				        if($('#txtPaternoNP').val()==null || $.trim($('#txtPaternoNP').val())=="")
				            msg+="<br/>- Apellido Paterno";
				        if($('#txtMaternoNP').val()==null || $.trim($('#txtMaternoNP').val())=="")
				            msg+="<br/>- Apellido Materno";
				        if($('#txtNacimientoNP').val()==null || $.trim($('#txtNacimientoNP').val())=="")
				            msg+="<br/>- Fecha de Nacimiento";
				        if($('#txtDniNP').val()==null || $.trim($('#txtDniNP').val())=="")
				             msg+="<br/>- DNI";
				        if($('#sltDistritoNP').val() == null || $.trim($('#sltDistritoNP').val())=="")
				        	 msg+="<br/>- Distrito";
				        if($('#sltInstitucionNP').val() == null || $.trim($('#sltInstitucionNP').val())=="")
				        	msg+="<br/>- Institución";
				        
				if(msg==""){
				    $.ajax({
					     url:'AjaxPacienteServlet',
					     data: $("#frmNuevoPaciente").serialize(),
					     dataType: "json",
					     contentType: 'application/json',
					     mimeType: 'application/json',
					     success: function (data) {
					    	 if(data.msgError === undefined) {
					    		 llenarDatosPaciente(data);
						    	 dialogForm.dialog( "close" );
					    	 } else {
					    		 printError(data.msgError);
					    	 }
					    }
					 });
				} else { printError('Debe Ingresar:'+msg); }
	        },
	        "Cancelar": function() {
	          $( this ).dialog( "close" );
	        }
	      },
	      close: function() {
	    	$('#txtFaxNP').val('');
	    	$('#txtEmailNP').val('');
	    	$('#txtHistClinicaNP').val('');
	    	$('#txtCelularNP').val('');
	    	$('#txtTelefonoNP').val('');
	    	
	    	$('#txtDireccionNP').val('');
	    	$('#txtNacimientoNP').val('');
	    	$('#txtDniNP').val('');
	    	$('#txtMaternoNP').val('');
	    	$('#txtPaternoNP').val('');
	    	
	    	$('#txtNombreNP').val('');
	      }
		});
		  
		btnCargarDialogoNuevoPaciente
	      .click(function() {
	    	  dialogForm.dialog( "open" );
	      });
		
		cmbDepartamentoNP.bind('click change', function() {
			if(cmbDepartamentoNP.val() != ''){
				comboProvincia(cmbDepartamentoNP.val());
			} else {
				cmbProvinciaNP.empty();
	            cmbDistritoNP.empty();
	            $('<option/>').attr('value','').text('--- Seleccione ---').appendTo(cmbProvinciaNP);
	            $('<option/>').attr('value','').text('--- Seleccione ---').appendTo(cmbDistritoNP);
			}
		});
		cmbProvinciaNP.bind('click change', function() {
			if(cmbProvinciaNP.val() != ''){
		   		comboDistrito(cmbProvinciaNP.val());
			} else {
				cmbDistritoNP.empty();
				$('<option/>').attr('value','').text('--- Seleccione ---').appendTo(cmbDistritoNP);
			}
	   	});
		function comboProvincia(txtCodigo){
			var url='AjaxProvinciaServlet';
		    var datos="codigo="+txtCodigo;
		    $.ajax({
		    	url: url,
		        type: "POST",
		        data: datos,
				dataType: 'json',
		        success: function(result){
		        	cmbProvinciaNP.empty();
		            cmbDistritoNP.empty();
		            $('<option/>').attr('value','').text('--- Seleccione ---').appendTo(cmbProvinciaNP);
		            $('<option/>').attr('value','').text('--- Seleccione ---').appendTo(cmbDistritoNP);
		            for(x=0;x<result.length;x++){
		            	$('<option/>').attr('value',result[x].idProvincia).text(result[x].nombre).appendTo(cmbProvinciaNP);
		           	}
				}
			});
		}
		function comboDistrito(txtCodigo){
			var url='AjaxDistritoServlet';
		    var datos="codigo="+txtCodigo;
		    $.ajax({
		    	url: url,
		        type: "POST",
		        data: datos,
				dataType: 'json',
		        success: function(result){
		        	cmbDistritoNP.empty();
		        	$('<option/>').attr('value','').text('--- Seleccione ---').appendTo(cmbDistritoNP);
		            for(x=0;x<result.length;x++){
		            	$('<option/>').attr('value',result[x].idDistrito).text(result[x].nombre).appendTo(cmbDistritoNP);
		           	}
				}
			});
		}
		
		/**************************************************************
		* 					PARTE 4
		***************************************************************/
		
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
		        //if($('#txtDireccionNI').val()==null || $.trim($('#txtDireccionNI').val()).length < 1)
		        //msg+="<br/>- Dirección";
		        if($('#txtRucNI').val()==null || $.trim($('#txtRucNI').val()).length < 11)
		            msg+="<br/>- RUC";
				if(msg==""){
					//$("#frmNuevaInstitucion").submit();
					 $.ajax({
					     url:'AjaxInstitucionServlet',
					     data: $("#frmNuevaInstitucion").serialize(),
					     dataType: "json",
					     contentType: 'application/json',
					     mimeType: 'application/json',
					     success: function (data) {
					    	 if(data.msgError === undefined) {
					    		 $('<option/>').attr('value',data.idInstitucion).text(data.nombre).appendTo($('#sltInstitucionNP'));
					    		 $('<option/>').attr('value',data.idInstitucion).text(data.nombre).appendTo($('#sltInstitucion'));
					    		 dialogFormInstitucion.dialog( "close" );
					    	 } else {
					    		 printError(data.msgError);
					    	 }
					    }
					 });
					
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
		
		/**************************************************************
		* 					PARTE 5
		***************************************************************/
		
		var dialogFormMedico = $('#dialogFormMedico');
		var btnCargarDialogoNuevoMedico = $('#btnCargarDialogoNuevoMedico');
		  
		dialogFormMedico.dialog({
	      autoOpen: false,
	      height: 250,
	      width: 600,
	      modal: true,
	      title: 'Nuevo Médico',
	      buttons: {
	        "Aceptar": function() {
				var msg="";
				if($('#txtNombreNM').val()==null  || $.trim($('#txtNombreNM').val())=="") msg+="<br/>- Nombre";		
				if($('#txtApePatNM').val()==null  || $.trim($('#txtApePatNM').val())=="") msg+="<br/>- Apellido paterno";
				if($('#txtApeMatNM').val()==null  || $.trim($('#txtApeMatNM').val())=="") msg+="<br/>- Apellido materno";
				if($('#txtCmpNM').val()==null  || $.trim($('#txtCmpNM').val())=="") msg+="<br/>- CMP";
				if($('#sltEspecialidadNM').val()==null || $('#sltEspecialidadNM').val()== "") msg+="<br/>- Especialidad";
		        //if($('#txtDireccionNM').val()==null || $.trim($('#txtDireccionNM').val())=="") msg+="<br/>- Dirección";
				if(msg==""){
				    $.ajax({
					     url:'AjaxMedicoServlet',
					     data: $("#frmNuevoMedico").serialize(),
					     dataType: "json",
					     contentType: 'application/json',
					     mimeType: 'application/json',
					     success: function (data) {
					    	 if(data.msgError === undefined) {
					    		 t_medico.val(data.idMedico);
						    	 h_medico.val(data.idMedico);
						    	 nombreMedico.val(data.apepat + " "+data.apemat+" "+data.nombre);
						    	 dialogFormMedico.dialog( "close" );
					    	 } else {
					    		 printError(data.msgError);
					    	 } 
					    }
					 });
				} else { printError('Debe Ingresar:'+msg); }
				
	        },
	        "Cancelar": function() {
	          $( this ).dialog( "close" );
	        }
	      },
	      close: function() {
	    	$('#txtNombreNM').val('');
	    	$('#txtApePatNM').val('');
	    	$('#txtApeMatNM').val('');
	    	$('#txtCmpNM').val('');
	      }
		});
		btnCargarDialogoNuevoMedico.click(function() {
	  		dialogFormMedico.dialog( "open" );
	   	});
		
		/**************************************************************
		* 					PARTE 6
		***************************************************************/
		sltIGV.change(function() {
			if($(this).val()==='1') {
				lblInstitucion.show();
				sltInstitucion.show();
			} else {
				lblInstitucion.hide();
				sltInstitucion.hide();
			}
		});
	});
	
</script>
<%@ include file="footer.jsp"%>
