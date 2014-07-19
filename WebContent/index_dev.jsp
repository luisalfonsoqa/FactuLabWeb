<%@ include file="header0.jsp"%>
<form action="LoginServlet" method="post" id="frmLogin">

<br/><br/><br/>
<div style="width: 100%;" align="center" >
<div style="width: 75%;" align="center">
	<table border="0" style="padding: 1em; margin: 3em;">
		<tr>
			<td colspan="2" align="left"><h2>Ingrese su Usuario y Clave</h2></td>
		</tr>
		<tr>
			<td style="width: 100px;"><strong>Usuario:</strong></td>
			<td style="width: 300px;"><input type="text" name="usuario" value="jgonzales"/></td>
		</tr>
		<tr>
			<td><strong>Clave:</strong></td>
			<td><input type="password" name="clave" value="sandro13"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<br/>
				<input id="btnLogin" type="button" class="button" value=" Iniciar sesión "/>
			</td>
		</tr>
	</table>
	<input type="hidden" name="device" />
</div>
</div>
</form>

<script>
$('#btnLogin').click(function(){
	//valida();
	$('#frmLogin').submit();
});

function valida(){
    var device = executeCommands();
    document.forms[0].device.value = device;
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