<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="mall.client.vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cartList</title>
</head>
<body>
<%
	// 장바구니 목록 받아오기
	List<Map<String, Object>> cartList = (List<Map<String, Object>>)(request.getAttribute("cartList"));
%>	
	
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
		</tr>
		<%	
			// 장바구니 목록 출력
			for(Map<String, Object> map : cartList) {
		%>
				<tr>
					<td><%=map.get("cartNo")%></td>
					<td><%=map.get("ebookNo")%></td>
					<td><%=map.get("ebookTitle")%></td>
					<td><%=map.get("cartDate")%></td>
					<td><a href="">삭제</a></td>
					<td><a href="">주문</a></td>
				</tr>
		<%
			}
		%>
	</table>
</body>
</html>