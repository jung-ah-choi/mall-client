<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateClientPw</title>
</head>
<body>
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	
	<!-- updateClientPw -->
	<h1>비밀번호 변경</h1>
	<form method = "post" action="<%=request.getContextPath()%>/UpdateClientPwController">
		<table>
			<tr>
				<th>새로운 비밀번호</th>
				<td>
					<input type = "password" name = "clientPw" required="required">
				</td>
			</tr>
		</table>
		<button type="submit">변경</button>
	</form>
</body>
</html>