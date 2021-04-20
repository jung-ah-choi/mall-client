<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>clientOne</title>
</head>
<body>
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
				<td>${clientOne.clientMail}</td>
				<td>${clientOne.clientDate}</td>
			</tr>
		</tbody>
	</table>
	<!-- UpdateClientPwController.doGet() forward - updateClientPw.jsp -->
	<!-- UpdateClientPwController.doPost() - ClientDao.updateClientPw() - session.invalidate() (Controller 안에) - redirect:/IndexController -->
	<a href="${pageContext.request.contextPath}/UpdateClientPwController"><button type="button">비밀번호 수정</button></a>
	<!-- DeleteClientController - CartDao.deleteCartByClient(mail),ClientDao.deleteClient() - session.invalidate() (Controller 안에) - redirect:/IndexController -->
	<a href="${pageContext.request.contextPath}/DeleteClientController"><button type="button">회원탈퇴</button></a>
</body>
</html>