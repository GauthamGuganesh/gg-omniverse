<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<body>
	<jsp:include page="/header.jsp" />
	<h2>Update Orders</h2>
	
	<table style="border: 1px solid;">
		<tr><th>Order ID</th><th>Price</th><th>Status</th></tr>
		<c:forEach items="${orders}" var="order" > 
			<tr>
				<td>${order.id}</td>
				<td> $ ${order.orderTotal}</td>
				<td>
					<form action='processorder.html' method='POST' >
						<input type="hidden" name="id" value="${order.id}" />
						<select name="status">
							<option value="${order.status}" selected>${order.status}</option>
							<c:forEach items="${statuses}" var="item">
								<c:if test="${!item.equals(order.status)}">
									<option value="${item}">${item}</option> 
								</c:if>
							</c:forEach>
						</select>
						<input type="submit" value="Update"/>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>