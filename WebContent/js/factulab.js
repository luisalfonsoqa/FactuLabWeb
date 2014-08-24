
	$(function() {
		$(".button").button();
		$( ".f_accordion" ).accordion();
		$( ".dialog" ).dialog();
	    $( "#tabs" ).tabs();
	    $(".text1").addClass("ui-corner-all");
	    //$( ".menu" ).menu();
	    
	  
	    $( ".datepicker").datepicker({
	        showOn: "button",
	        buttonImage: "images/calendar.gif",
	        buttonImageOnly: true,
	        dateFormat: "yy-mm-dd",
	        changeMonth: true,
            changeYear: true
	    });
	    
	    $(".datepicker_nacimiento" ).datepicker({
	        showOn: "button",
	        buttonImage: "images/calendar.gif",
	        buttonImageOnly: true,
	        dateFormat: "dd-mm-yy",
	        defaultDate: "01-01-1970",
	        changeMonth: true,
            changeYear: true
	    });
	    
	    $( "#messageError" ).dialog({
	        modal: true,
	        height: 250,
		    width: 350,
	        autoOpen: false,
	        buttons: {
	        	Ok: function() {
	            	$( this ).dialog( "close" );
	            }
	       	}
	   });
	});
	
	function printError(msgError){
		 $('#idMensajeError').html(msgError);
		 $('#messageError').dialog( "open" );
	}
    function isNumberKey(evt)
    {
       var charCode = (evt.which) ? evt.which : event.keyCode
       if (charCode > 31 && (charCode < 48 || charCode > 57))
          return false;

       return true;
    }