<script src="js/jquery-1.10.2.js"></script>
<body onload="valida();">
Módulo de Cajeros! Cargando... </body>
<form action="LoginServlet" method="get" id="frmLogin">
<input type="hidden" name="device"/>
</form>
<script>
function valida(){
    document.forms[0].device.value = executeCommands();
    $('#frmLogin').submit();
}

function executeCommands(inputparms)
{
	var oShell = new ActiveXObject("WScript.Shell");
	sRegVal = 'HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows\\Device';
	sDefault = "";
	sDefault = oShell.RegRead(sRegVal);
	GetDefaultPrinter = sDefault;
	return GetDefaultPrinter;
}
</script>
