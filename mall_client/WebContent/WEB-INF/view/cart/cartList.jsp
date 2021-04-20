<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cartList</title>
</head>
<body>	
	
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	
	<!-- cartList -->
	<h1>장바구니 목록</h1>
	<table border="1">
		<tr>
			<th>cartNo</th>
			<th>ebookNo</th>
			<th>ebookTitle</th>
			<th>cartDate</th>
			<th>삭제</th>
			<th>주문</th>
		</tr>
		<c:forEach var="m" items="${cartList}">
				<tr>
					<td>${m.cartNo}</td>
					<td>${m.ebookNo}</td>
					<td>${m.ebookTitle}</td>
					<td>${m.cartDate.substring(0,11)}</td>
					<!-- DeleteCartController - CartDao.deletCart() - redirect:/CartListController -->
					<td><a href="${pageContext.request.contextPath}/DeleteCartController?ebookNo=${m.ebookNo}">삭제</a></td>
					<!-- InsertOrdersController - insertOrders(),deleteCart():ISSUE 트랜잭션처리 - redirect:/OrdersListController -->
					<td><a href="${pageContext.request.contextPath}/InsertOrdersController?ebookNo=${m.ebookNo}">주문</a></td>
				</tr>
		</c:forEach>
	</table>
</body>
</html>