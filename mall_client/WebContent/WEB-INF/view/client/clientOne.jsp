<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mall.client.vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>clientOne</title>
</head>
<body>
<%
	// 형변환
	Client clientOne = (Client)(request.getAttribute("clientOne"));
%>	
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	
	<!-- clientOne -->
	<h1>회원정보</h1>
	<table border="1">
		<thead>
			<tr>
				<th>clientMail</th>
				<th>clientDate</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><%=clientOne.getClientMail()%></td>
				<td><%=clientOne.getClientDate()%></td>
			</tr>
		</tbody>
	</table>
</body>
</html>