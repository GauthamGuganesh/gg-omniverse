<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	
	<body>
		<jsp:include page = "/header.jsp" />
		<h2>Menu</h2>
		<ul>
			
			<c:forEach items="${ menuItems }" var="menuItem" >
			 
			 <!--  menuItem.description will be interpreted as menuItem.getDescription by server.
				 	menuItem alone will call the toString() method. -->
				 	
				<c:if test = "${menuItem.price <= 10}" > <!-- Diplays items with price <= 10. .price interpreted as getPrice() -->
					<li> ${menuItem} - ${menuItem.description} </li> 
				</c:if>
			</c:forEach>
			
		</ul>
		<jsp:include page = "/footer.jsp" />
	</body>
	
</html>

<!--  Other JSTL core library tags 
  
c:catch
c:choose
c:if
c:import
c:forEach
c:forTokens
c:out
c:otherwise
c:param
c:redirect
c:remove
c:set
c:url
c:when 

Refer JSTL Java docs and learn-->