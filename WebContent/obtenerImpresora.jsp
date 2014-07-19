<%@ include file="header0.jsp"%>

<div align="center">
<form id="frmImpresora" action="ObtenerImpresoraServlet" method="post">
	<h2>Usuario del tipo Cajero.<H2>
	<h4>Ud. Contará con la Impresora <br/><br/><input type="text" id="idImpresora" name="device" disabled="disabled"/></h4>
	<!-- input type="hidden" value="EPSON L350 Series" name="device"/--> 
	<input id="btnObtenerImpresora" type="submit" class="button" value="Confirmar"/>
</form>
</div>
<SCRIPT type="text/javascript" LANGUAGE="JavaScript">

</SCRIPT>

<%@ include file="footer.jsp"%>