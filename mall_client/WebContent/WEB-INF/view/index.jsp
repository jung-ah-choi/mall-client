<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index</title>
</head>
<body>
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	<!-- 메뉴1 로그인/회원가입/내정보 -->
	<!-- 장바구니(세션) -->
	<!-- 캘린더(이번달에 나온 책들) -->
	<!-- 베스트셀러(주문량) -->
	<!-- 메뉴2 카테고리 -->
	
	<!-- Ebook List의 카테고리 네비게이션 메뉴 -->
	<div>
		<!-- 전체 상품 출력 -->
		<a href="${pageContext.request.contextPath}/IndexController">All</a>	
		
		<!-- 카테고리 출력 -->
		<c:forEach var="e" items="${categoryList}">
			<a href="${pageContext.request.contextPath}/IndexController?categoryName=${e}">${e}</a>
		</c:forEach>
	</div>
	
	<!-- 한 페이지 당 ~개씩 보기 선택 -->
	<form action="${pageContext.request.contextPath}/IndexController" method="get">
		<select name="rowPerPage">
			<c:forEach var="i" begin="15" end="30" step="5">
				<c:if test="${rowPerPage == i}">
					<option value="${i}" selected="selected">${i}개씩</option>
				</c:if>
				<c:if test="${rowPerPage != i}">
					<option value="${i}">${i}개씩</option>
				</c:if>
			</c:forEach>
		</select>
		<button type="submit">보기</button>
	</form>
	
	<!-- best ebook 목록 (상품 5개 출력) -->
	<h3>Best Ebook</h3>
	<table border="1">
		<tr>
			<c:forEach var="m" items="${bestOrdersList}">
				<td>
					<div><img src="${pageContext.request.contextPath}/img/default.jpg"></div>
					<!-- EbookOneController로 ebookNo 넘겨줌 -->
					<div>
						<a href="${pageContext.request.contextPath}/EbookOneController?ebookNo=${m.ebookNo}">
							${m.ebookTitle}
						</a>
					</div>
					<div>￦${m.ebookPrice}</div>
				</td>
			</c:forEach>
		</tr>
	</table>
	
	<!-- ebook 목록 테이블 -->
	<h3>Ebook List</h3>
	<table border="1">
		<tr>
			<div style=display:none>${i = 0}</div> <!-- 5개씩 출력하기 위해서 i 값을 선언해줌 -->
			<c:forEach var="ebook" items="${ebookList}">
				<div style=display:none>${i = i + 1}</div> <!-- 목록 1개씩 출력할 때마다 i값도 1씩 더해줌 -->
				<td>
					<div><img src="${pageContext.request.contextPath}/img/default.jpg"></div>
					<!-- EbookOneController - EbookDao.selectEbookOne() - ebookOne.jsp -->
					<!-- ebookNo 같이 넘겨줌 -->
					<div>
						<a href="${pageContext.request.contextPath}/EbookOneController?ebookNo=${ebook.ebookNo}">
							${ebook.ebookTitle}
						</a>
					</div>
					<div>￦${ebook.ebookPrice}</div>
				</td>
				<c:if test="${i%5 == 0}"> <!-- 5개씩 출력하면서 tr을 닫고, 다시 열음 -->
					</tr><tr>
				</c:if>
			</c:forEach>
		</tr>
	</table>
	
	<!-- 페이징 (이전, 다음) 버튼 만들기 + 페이징 숫자 나오게 하기 + 카테고리별로 눌렀을 때 조건문으로 구분 -->
		<!-- 이전 버튼 -->
		<!-- 맨 첫 페이지에서 이전 버튼이 나오지 않게 함 -->
		<c:if test="${currentPage > 1}">
			<!-- 카테고리 메뉴를 선택하지 않았다면 -->
			<c:if test="${categoryName == null}">
				<a href="${pageContext.request.contextPath}/IndexController?currentPage=${currentPage-1}&rowPerPage=${rowPerPage}">이전</a>
			</c:if>
			<!-- 카테고리 메뉴를 선택했다면 -->
			<c:if test="${categoryName != null}">
				<a href="${pageContext.request.contextPath}/IndexController?currentPage=${currentPage-1}&rowPerPage=${rowPerPage}&categoryName=${categoryName}">이전</a>
			</c:if>
		</c:if>
		
		<!-- 다음 버튼 -->
		<!-- 마지막 페이지의 번호이자, 총 몇 페이지인지 알 수 있음 -->
		<div style=display:none>${lastPage = totalRow / rowPerPage}</div>
		
		<!-- 나머지가 있다면 rowPerPage보다 적은 마지막 게시물들을 보여주기 위해서, 마지막 페이지 한 개 더 추가-->
		<c:if test="${totalRow % rowPerPage != 0}">
			<div style=display:none>${lastPage += 1}</div>
		</c:if>
		
		<!-- 마지막 페이지에서 다음 버튼이 안 보이게 함 -->
		<c:if test="${currentPage < lastPage}">
			<!-- 카테고리 메뉴를 선택하지 않았다면 -->
			<c:if test="${categoryName == null}">
				<a href="${pageContext.request.contextPath}/IndexController?currentPage=${currentPage+1}&rowPerPage=${rowPerPage}">다음</a>
			<!-- 카테고리 메뉴를 선택했다면 -->
			</c:if>
			<c:if test="">
				<a href="${pageContext.request.contextPath}/IndexController?currentPage=${currentPage+1}&rowPerPage=${rowPerPage}&categoryName=${categoryName}">다음</a>
			</c:if>
		</c:if>
	<!-- 페이징 끝! -->
	
	<!-- 검색기능 -->
	<form action="${pageContext.request.contextPath}/IndexController" method="get">
		ebookTitle:
		<input type="text" name="searchWord">
		<button type="submit">검색</button>
	</form>
</body>
</html>