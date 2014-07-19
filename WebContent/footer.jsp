<%@page import="com.factulab.servlet.util.ServletConstante"%>
	<!-- Body END -->
<table style="width: 100%">
<tr>
<td align="center">
<br/>
<br/>
<%
	if(request.getAttribute(ServletConstante.REQUEST_ERROR) != null){ 
		out.println("<div style='color: red; font-size: 14px;' ><b>ERROR: </b>"+request.getAttribute(ServletConstante.REQUEST_ERROR)+"</div>");
	}

/* } catch(Exception e){
	throw new Exception(e);
	out.println("<div style='color: red'><b>ERROR_EXCEPTION: </b>");
	e.printStackTrace();
} */
%>

</td>
</tr>
</table>

<div id="messageError" title="Mensaje de Error">
  <p>
   	<div class="ui-widget" align="left">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em; display: inline-block; width: 300px" >
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
   			<strong>Error:</strong><div id="idMensajeError"></div>
   		</div>
   	</div>
  </p>
</div>
</body>
</html>