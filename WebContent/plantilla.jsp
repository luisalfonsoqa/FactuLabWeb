<%@ include file="header0.jsp"%>
<script>
	$(function() {
		$( "#accordion" ).accordion();
	    $( "#tabs" ).tabs();
	});
	
</script>
<style>
  .ui-menu { width: 150px; }
  </style>
<!-- 
	<div>
		<h2 class="header_titulo">Accordion</h2>
		<div id="accordion">
		<h3>First</h3>
		<div>Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet.</div>
	</div>
	
	<div>
		<div id="tabs">
		  <ul>
		    <li><a href="#tabs-1">Nunc tincidunt</a></li>
		    <li><a href="#tabs-2">Proin dolor</a></li>
		    <li><a href="#tabs-3">Aenean lacinia</a></li>
		  </ul>
		  <div id="tabs-1">
		    <p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo. Vivamus sed magna quis ligula eleifend adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique tempus lectus.</p>
		  </div>
		  <div id="tabs-2">
		    <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
		  </div>
		  <div id="tabs-3">
		    <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
		    <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
		  </div>
		</div>
	</div>
	
	<div>
	<div>
	  <h1>Existing Users:</h1>
	  <table id="users" class="ui-widget ui-widget-content">
	    <thead>
	      <tr class="ui-widget-header ">
	        <th>Name</th>
	        <th>Email</th>
	        <th>Password</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr>
	        <td>John Doe</td>
	        <td>john.doe@example.com</td>
	        <td>johndoe1</td>
	      </tr>
	    </tbody>
	  </table>
	</div>
</div>
</div>-->

<ul id="menu" class="menu">
  <li><a href="#"><span class="ui-icon ui-icon-disk"></span>Save</a></li>
  <li><a href="#"><span class="ui-icon ui-icon-zoomin"></span>Zoom In</a></li>
  <li><a href="#"><span class="ui-icon ui-icon-zoomout"></span>Zoom Out</a></li>
  <li class="ui-state-disabled"><a href="#"><span class="ui-icon ui-icon-print"></span>Print...</a></li>
  <li>
    <a href="#">Playback</a>
    <ul>
      <li><a href="#"><span class="ui-icon ui-icon-seek-start"></span>Prev</a></li>
      <li><a href="#"><span class="ui-icon ui-icon-stop"></span>Stop</a></li>
      <li><a href="#"><span class="ui-icon ui-icon-play"></span>Play</a></li>
      <li><a href="#"><span class="ui-icon ui-icon-seek-end"></span>Next</a></li>
    </ul>
  </li>
</ul>
 
<%@ include file="footer.jsp"%>
