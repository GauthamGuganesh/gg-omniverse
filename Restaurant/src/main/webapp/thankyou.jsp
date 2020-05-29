<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!--  prefix='c' is the local variable for the library -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!--  format library of JSTL -->

<html>
	<body>
		<jsp:include page="header.jsp" />
		<h2>Order your food</h2>
		
		<script type="text/javascript">
		<!-- Writing AJAX call to server -->
		
		function updateStatus()
		{
		    var request = new XMLHttpRequest();
		    // client CALLBACK checking for successful response from server
		    request.onreadystatechange = function() {  // Don't use arrow functions in AJAX
		        if(this.readyState == 4)
		        {
		        	let resp = JSON.parse(this.responseText);
		            document.getElementById("status").innerHTML = resp['status'];
		            document.getElementById("time").innerHTML = "Last updated " + resp['time'];	            
		        }
		    }

		    request.open("GET", "/updateStatus?id=${id}", true);
		    request.send();
		}
			
	/*	window.setInterval(function(){
			updateStatus();
		}, 2000); */ // setInterval outside the function updateStatus(), else won't be invoked automatically.
		
		</script>
		
		<!--  Using 'out' tag from tag library. Prints value to screen -->
		
		Thank you - your order has been received. You need to pay
		
		<!-- Formatting for total(present in request object). 
			 Formatting type is 'currency'(gives currency symbol for currencyCode we set in servlet.
			 CurrencyCode is set in servlet request object. (INR or USD etc)
			-->
				
		<fmt:setLocale value="en_US"/>
		<fmt:formatNumber value="${total}" type="currency" />
		
		
		<p> Current status of your order <span id="status">${ status }</span> <input type="button" value="Refresh Status" onclick="updateStatus()" /></p>
		<p id = "time"></p>
		<!-- (or) 

		 Thank you - your order has been received. You need to pay $ ${ total } -->
		 
		<jsp:include page="footer.jsp" />		
	</body>
</html>
