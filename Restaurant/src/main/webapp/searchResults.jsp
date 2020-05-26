<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	
	<body>
		<jsp:include page = "/header.jsp" />
		<h2>Menu</h2>
		<ul>
			 <!-- We can replace out.println this way. -->			
			 
			<c:forEach items="${menuItems}" var="menuItem">
				<li> ${menuItem}  </li>
			</c:forEach>	
			
		</ul>
		<jsp:include page = "/footer.jsp" />
	</body>
	
</html>