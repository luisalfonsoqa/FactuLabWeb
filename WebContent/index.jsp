<%@ include file="header0.jsp"%>
<form action="LoginServlet" method="post" id="frmLogin">
<br/><br/><br/>
<div style="width: 100%;" align="center" >
<div style="width: 75%;" align="center">
	<table style="border: 1px solid #CCCCCC; background-color: white; width: 400px;" border="0">
	 	<tr>
        	<td height="30" colspan="3" align="center" style="background-image:url(/FactuLabWeb/images/headerDatatable.jpg); vertical-align: middle;">
        		<span style="color: #FFFFFF; font-size: 16px;">USUARIO CAJA</span>
        </tr>
       	<tr>
        	<td width="130" rowspan="4" align="left" height="100%">
            	<!-- img src="/FactuLabWeb/images/logoLogin.jpg" alt="" width="110" height="171"/-->
          	</td>
      		<td colspan="2" height="60" align="center"><img src="/FactuLabWeb/images/login.gif" alt=""/></td>
        </tr>
		<tr>
			<td style="width: 100px;"><strong>Usuario:</strong></td>
			<td style="width: 300px;"><input type="text" name="usuario" id="idUsuario" onkeypress="return go_form(event);" tabindex="1"/></td>
		</tr>
		<tr>
			<td><strong>Clave:</strong></td>
			<td><input type="password" name="clave" id="idClave" onkeypress="return go_form(event);" tabindex="2" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<!--  input id="btnLogin" type="button" class="button" value=" Iniciar sesión "/-->
				<a href="javascript:valida();"><img src="/FactuLabWeb/images/loginBtn.jpg" alt="Login" border="0"/></a>
			</td>
		</tr>
	</table>
	<input type="hidden" name="device" />
</div>
</div>
</form>

<script>
$(function() {
	$('#idUsuario').focus();
});

function go_form(e) {
	if (e.keyCode == 13) {
	    valida();
	}
}

function valida(){
  	if ($('#idUsuario').val()=='')
    {
  		  printError("Ingrese el nombre del usuario.");
          $('#idUsuario').focus();
          return;
    }
   	if ($('#idClave').val()=='')
    {
   		$('#idClave').focus();
   		  printError("Ingrese la clave.");
          return;
    }
    document.forms[0].device.value = executeCommands();
    $('#frmLogin').submit();
}

function executeCommands(inputparms)
{
	var oShell = new ActiveXObject("WScript.Shell");
	sRegVal = 'HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows\\Device'
	sDefault = ""
	sDefault = oShell.RegRead(sRegVal)
	GetDefaultPrinter = sDefault
	return GetDefaultPrinter;
}
</script>
<%@ include file="footer.jsp"%>