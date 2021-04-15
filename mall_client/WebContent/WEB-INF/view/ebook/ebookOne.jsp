<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mall.client.vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ebookOne</title>
</head>
<body>
<%
	// controller에서 받아오면서 형변환
	Ebook ebook = (Ebook)(request.getAttribute("ebook"));
%>	
	
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	
	<!-- ebookOne -->
	<h1>ebookOne</h1>
	<table border="1">
		<tr>
			<th>ebookNo</th>
			<td><%=ebook.getEbookNo()%></td>
		</tr>
		
		<tr>
			<th>categoryName</th>
			<td><%=ebook.getCategoryName()%></td>
		</tr>
		
		<tr>
			<th>ebookTitle</th>
			<td><%=ebook.getEbookTitle()%></td>
		</tr>
		
		<tr>
			<th>ebookAuthor</th>
			<td><%=ebook.getEbookAuthor()%></td>
		</tr>
		
		<tr>
			<th>ebookState</th>
			<td><%=ebook.getEbookState()%></td>
		</tr>
		
		<tr>
			<th>ebookImage</th>
			<td><img src="<%=request.getContextPath()%>/img/<%=ebook.getEbookImg()%>"></td>
		</tr>
		
		<tr>
			<th>ebookISBN</th>
			<td><%=ebook.getEbookISBN()%></td>
		</tr>
		
		<tr>
			<th>ebookCompany</th>
			<td><%=ebook.getEbookCompany()%></td>
		</tr>
		
		<tr>
			<th>ebookDate</th>
			<td><%=ebook.getEbookDate()%></td>
		</tr>
		
		<tr>
			<th>ebookSummary</th>
			<td><%=ebook.getEbookSummary()%></td>
		</tr>
		
		<tr>
			<th>ebookPrice</th>
			<td>￦<%=ebook.getEbookPrice()%></td>
		</tr>
		
		<tr>
			<th>ebookPageCount</th>
			<td><%=ebook.getEbookPageCount()%>pages</td>
		</tr>
	</table>
	<!-- InsertCartController?ebookNo - CartDao.insertCart() - redirect: CartListController -->
	<a href="<%=request.getContextPath()%>/InsertCartController?ebookNo=<%=ebook.getEbookNo()%>">
		<%
			if(session.getAttribute("loginClient") == null 
				|| ebook.getEbookState().equals("품절")
				|| ebook.getEbookState().equals("절판")
				|| ebook.getEbookState().equals("구편절판")) {
		%>
				<button type="button" disabled="disabled">장바구니 추가</button>
		<%
			} else {
		%>
				<button type="button">장바구니 추가</button>
		<%	
			}
		%>
	</a>
</body>
</html>