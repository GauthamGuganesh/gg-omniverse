<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<jsp:include page="header.jsp" />
		<h2>Order your food</h2>
		
		<form action='orderReceived.html' method='POST' >
			<ul>
				<c:forEach items="${fullMenu}" var="menuItem">
					<li> ${menuItem} : <input type='text' name='item_${ menuItem.id}' /> </li>
				</c:forEach>	
			</ul>
		 	<input type='submit' />
		</form>
		<jsp:include page = "/footer.jsp" />
	</body>
</html>