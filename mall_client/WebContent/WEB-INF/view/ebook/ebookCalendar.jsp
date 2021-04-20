<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ebookCalendar</title>
</head>
<body>
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>

	<!-- ebookCalendar -->
	<h1>Ebook Calendar</h1>
	
	<div>
		<a href="${pageContext.request.contextPath}/EbookCalendarController?currentYear=${preYear}&currentMonth=${preMonth}">
			이전달
		</a>
		<span>${currentYear}년</span> <span>${currentMonth}월</span>
		<a href="${pageContext.request.contextPath}/EbookCalendarController?currentYear=${nextYear}&currentMonth=${nextMonth}">
			다음달
		</a>
	</div>
	
	<table border="1">
		<tr>
			<td>일</td>
			<td>월</td>
			<td>화</td>
			<td>수</td>
			<td>목</td>
			<td>금</td>
			<td>토</td>
		</tr>
		<tr>
			<!-- 공백 + endDay 만큼 td가 필요 -->
			<c:forEach var="i" begin="1" end="${endDay+(firstDayOfWeek-1)}" step="1">
				<c:if test="${i-(firstDayOfWeek-1) <= 0}">
					<td>&nbsp;</td>
				</c:if>
				<c:if test="${i-(firstDayOfWeek-1) > 0}">
					<td>${i-(firstDayOfWeek-1)}</td>
				</c:if>
				
				<!-- 토요일 숫자값은 7이므로, 날짜 요일 값을 7로 나눈 나머지가 0이라면 토요일이라는 의미! -->
				<!-- 날짜가 토요일 일 때마다, 다음 행으로 넘겨주는 메소드 -->
				<c:if test="${i%7 == 0}">
					</tr><tr>
				</c:if>
			</c:forEach>
			
			<!-- td 반복 후(endDay까지), 채워지지 않은 자리가 있다면 공백을 채워줌 -->
			<c:if test="${(endDay+(firstDayOfWeek-1)) % 7 != 0}">
				<c:forEach begin="1" end="${7-((endDay+(firstDayOfWeek-1)) % 7)}" step="1">
					<td>&nbsp;</td>
				</c:forEach>
			</c:if>
		</tr>
	</table>
</body>
</html>