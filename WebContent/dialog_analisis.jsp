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
        if(tipoBusqueda=='Analisis'){
            displayListAnalisis(jsonList);
        }
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
            	if(tipoBusqueda=='Analisis'){
            		llenarDatosAnalisis(rad.val());
            	}
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
    $('#btnCargarDialogoAnalisis').click(function(){
    	tipoBusqueda = "Analisis";
    	var jsonCriterio=[{valor:'nombre',texto:'Nombre'}, {valor:'seccion',texto:'seccion'}];
		
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
	function displayListAnalisis(json){
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
    }
	 function llenarDatosAnalisis(idAnalisis){
		 alert(idAnalisis);
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

<input type="button" class="button" id="btnCargarDialogoAnalisis" value="Cargar Analisis"/>  