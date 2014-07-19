<jsp:include page="header0.jsp"></jsp:include>
<script>
  $(function() {
	  var tblLista = $("#tblLista");
	  var dialogBox = $("#dialogBox");
	  var tipoBusqueda = "";
	  var sltCriterio = $("#sltCriterio");
	  var membersToList; 
	  var page_index; 
	  var items_per_page;
	  var max_elem;
	  var txt_texto  = $('#txtTexto');
	  
	/** PAGINAR **/
	$('#btnBuscarTipoBusqueda').click(function(){
		listar(tipoBusqueda,sltCriterio.val(),txt_texto.val());
	});
	function listar(tipo,cri,tex){
		var url = 'Ajax'+tipoBusqueda+'Servlet';
	  	var datos = 'criterio='+sltCriterio.val()+"&texto="+txt_texto.val()+"&accion=buscar";
	  	$.ajax({
			url: url,
			data: datos,
			dataType: "json",
			contentType: 'application/json',
			mimeType: 'application/json',
			success: function (result) {
				jsonList=result;
		        paginarLista(jsonList);
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
	    	  
	  
    /** REGISTRAR **/
    function preDisplayList(pi, jq){
        page_index=pi;
        items_per_page = 7;
        max_elem = Math.min((page_index+1) * items_per_page, membersToList.length);
        if(tipoBusqueda=='Paciente'){
            displayListPaciente(jsonList);
        }else if(tipoBusqueda=='Medico'){
            displayListMedico(jsonList);
        }
        /* else if(tipoBusqueda=='Analisis'){
            displayListAnalisis(jsonList);
        } */
        return false;
    }
    dialogBox.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
          "Aceptar": function() {
            var bValid = true;
            bValid = bValid && jsonList.length!=null;
            
            if ( bValid ) {
            	var rad=$("input[name=seleccion"+tipoBusqueda+"]:checked");
            	if(tipoBusqueda=='Paciente'){
		        	for(var p=0;p<jsonList.length;p++){
                        if(jsonList[p].idPaciente==rad.val()){
                        	llenarDatosPaciente(jsonList[p])
                        }
                    }
            	} else if(tipoBusqueda=='Medico'){
            		for(var p=0;p<jsonList.length;p++){
                        if(jsonList[p].idMedico==rad.val()){
                        	llenarDatosMedico(jsonList[p])
                        }
                    }
            	}/* else if(tipoBusqueda=='Analisis'){
            		$("#idAnalisis").val(rad.val());
        			h_descuento.val(descuento.val());
        			accionAnalisis.val('agregar');
        			$("#frmAnalisis").submit();
            	} */
	      	  $( this ).dialog( "close" );
            }
          },
          "Cancelar": function() {
            $( this ).dialog( "close" );
          }
        },
        close: function() {
      		tblLista.empty();
      		sltCriterio.empty();
      		txt_texto.val('');
			$("#Pagination").empty();
        }
      });
    
    /** DIALOGO POR C/U**/
    $('#btnCargarDialogoMedico').button().click(function() {
    	 tipoBusqueda = "Medico";
    	 var jsonCriterio=[{valor:'nombre',texto:'Nombre'}, {valor:'apepat',texto:'Paterno'},{valor:'apemat',texto:'Materno'}, {valor:'cmp',texto:'CMP'}];
    	 
    	 for(var x=0;x<jsonCriterio.length;x++){ $('<option/>').attr('value',jsonCriterio[x].valor).text(jsonCriterio[x].texto).appendTo(sltCriterio); }
    	 dialogBox.dialog({
           position: ['center', 'center'],
           width:450,
           title:"Buscar "+tipoBusqueda,
           modal:true
       	});
		dialogBox.dialog( "open" );
   	});
    
    $('#btnCargarDialogoPaciente').click(function(){
    	tipoBusqueda = "Paciente";
    	var jsonCriterio=[{valor:'nombre',texto:'Nombre'}, {valor:'apepat',texto:'Paterno'}, {valor:'apemat',texto:'Materno'}, {valor:'dni',texto:'DNI'}];
		
    	 for(var x=0;x<jsonCriterio.length;x++){ $('<option/>').attr('value',jsonCriterio[x].valor).text(jsonCriterio[x].texto).appendTo(sltCriterio); }
    	 dialogBox.dialog({
           position: ['center', 'center'],
           width:450,
           title:"Buscar "+tipoBusqueda,
           modal:true
       	});
		dialogBox.dialog( "open" );
	});
    
    
    /** DISPLAY POR C/U**/
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
	/* function displayListAnalisis(json){
		$("#tblLista").empty();
	    var htmlText="<tr><th width='50'></th><th width='50'>Codigo</th><th width='200'>Nombre</th><th>Seccion</th><th width='50'>Precio</th></tr>";
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
    } */
	 function llenarDatosMedico(medico){
    	 alert(medico.idMedico);
    	 /*
		 t_medico.val(medico.idMedico);
		 h_medico.val(medico.idMedico);
		 nombreMedico.val(medico.apepat + " "+medico.apemat+" "+medico.nombre);
		 */
	 }
	 function llenarDatosPaciente(paciente){
		 alert(paciente.idPaciente);
		 /*
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
    	 */
	 }
	  
  });
</script>

<div id="dialogBox">
  	<div style="width: 100%" align="center">
		<table style="width: 70%; border-spacing: 1em; padding-top: 0.5em; padding-bottom: 1em;" >
  			<tr>
	  			<td>Por:</td>
	  			<td><select name="sltCriterio" id="sltCriterio"></select></td>
	  			<td><input type="text" name="txtTexto" id="txtTexto" size="20"/></td>
	  			<td><input type="button" id="btnBuscarTipoBusqueda" value="Buscar" class="button"/></td>
  			</tr>
  		</table>
  	</div>	
  	<div id="Pagination" class="pagination"></div>
	<br/>
    <table id="tblLista"></table>
</div>

<input type="button" class="button" id="btnCargarDialogoMedico" value="Cargar Medico"/>
<input type="button" class="button" id="btnCargarDialogoPaciente" value="Cargar Paciente"/>  