<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
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
	<%
		List<Map<String, Object>> ebookListByMonth = (List<Map<String, Object>>)request.getAttribute("ebookListByMonth");
		int currentYear = (Integer)request.getAttribute("currentYear");
		int currentMonth = (Integer)request.getAttribute("currentMonth");
		int endDay = (Integer)request.getAttribute("endDayOfMonth");
		int firstDayOfWeek = (Integer)request.getAttribute("fisrtDayOfWeek");
		
		// 이전달, 다음달, 연도 출력을 위한 메소드
		int preYear = currentYear;
		int preMonth = currentMonth - 1;
		if(preMonth == 0) {
			preMonth = 12;
			preYear -= 1;
		}
		
		int nextYear = currentYear;
		int nextMonth = currentMonth + 1;
		if(nextMonth == 13) {
			nextMonth = 1;
			nextYear += 1;
		}
	%>
	
	<!-- n행 7열 -->
	<div>
		<a href="<%=request.getContextPath()%>/EbookCalendarController?currentYear=<%=preYear%>&currentMonth=<%=preMonth%>">이전달</a>
		<span><%=currentYear%>년</span>
		<span><%=currentMonth%>월</span>
		<a href="<%=request.getContextPath()%>/EbookCalendarController?currentYear=<%=nextYear%>&currentMonth=<%=nextMonth%>">다음달</a>
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
			<!-- 첫 날의 요일이 시작될 때까지 공백을 출력함 -->
		<%
			for(int i=1; i<firstDayOfWeek; i++) {
		%>
		            <td>&nbsp;</td>
		<%
			}
		         
		for(int i=1; i<=endDay; i++) {
		%>            
            <td>
            	<%=i%> <!-- 날짜 (~일) -->
            	<%
            		for(Map<String, Object> m : ebookListByMonth) {
            			if(i == (Integer)m.get("d")) { // 이 날짜가 일치한다는건, 신간이 존재한다는 의미
            	%>
            				<div>
	            				<a href="<%=request.getContextPath()%>/EbookOneController?ebookNo=<%=m.get("ebookNo")%>">
	            					<%
		            					String ebookTitle = (String)m.get("ebookTitle");
		            					
	            					// ebookTitle이 10자 이상이면 뒷부분은 ...으로 출력함
	            						if(ebookTitle.length() > 10) {
		            				%>
		            						<%=ebookTitle.substring(0,10)%>...
		            				<%		
		            					} else {
	            					%>
	            							<%=ebookTitle%>
	            					<%
		            					}
	            					%>
	            				</a>
            				</div>
            	<%
            			}
            		}
            	%>
            </td>
		<%
            	if(firstDayOfWeek%7==0){ // 토요일은 숫자 7값에 해당함
		%>               
            	</tr><tr>
		<%               
            	}
		
            	firstDayOfWeek += 1;
         }
         firstDayOfWeek -= 1;
         
         if((firstDayOfWeek%7) != 0){ // 요일값이 토요일이 아니라면
            for(int i=1; i<=7-(firstDayOfWeek%7); i++){
		%>               
		<!-- 마지막 날 뒤의 남은 빈칸을 채움 -->
            <td></td> 
		<%               
            }
         }
		%>
      </tr>
   </table>
</body>
</html>