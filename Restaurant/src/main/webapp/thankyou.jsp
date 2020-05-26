<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!--  prefix='c' is the local variable for the library -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!--  format library of JSTL -->

<html>
	<body>
		<jsp:include page="header.jsp" />
		<h2>Order your food</h2>
		
		<!--  Using 'out' tag from tag library. Prints value to screen -->
		
		Thank you - your order has been received. You need to pay
		
		<!-- Formatting for total(present in request object). 
			 Formatting type is 'currency'(gives currency symbol for currencyCode we set in servlet.
			 CurrencyCode is set in servlet request object. (INR or USD etc)
			-->
				
		<fmt:setLocale value="en_US"/>
		<fmt:formatNumber value="${total}" type="currency" />
		
		
		<p> Current status of your order ${ status } <input type="button" value="Refresh Status" onclick="updateStatus()" /></p>
		<!-- (or) 
		
		 Thank you - your order has been received. You need to pay $ ${ total } -->
		 
		<jsp:include page="footer.jsp" />		
	</body>
</html>
