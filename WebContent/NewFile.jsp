<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/jquery.pagination.css" type="text/css"/>
<link href="css/blitzer/jquery-ui-1.10.4.custom.css" rel="stylesheet">
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
</head>
<body>
<form id="frmDemo" action="AjaxMedicoServlet">
<input type="text" name="accion" id="idAccion" value="nuevo"/>
<input type="text" name="nombre" id="idNombre"  value="ásñesa"/>
<input type="button" id="send" value="Send"/>
</form>
<script>
$('#send').click(function(){
	alert('asdas2');
	alert(encodeURIComponent($('#idNombre').val()));
	alert(decodeURIComponent(encodeURIComponent($('#idNombre').val())));
	//$("#frmDemo").submit();
	$.ajax({
		url:'AjaxMedicoServlet',
		data: {
			accion : $('#idAccion').val(),
			nombre : $('#idNombre').val()
			/*accion : encodeURIComponent($('#idAccion').val()),
			nombre : encodeURIComponent($('#idNombre').val())*/
		},
		dataType: "html",
		contentType: "application/xhtml+xml",
		mimeType: 'application/json',
		success: function (data) {
			alert(data);
		}
	});
});
 </script>
</body>
</html>