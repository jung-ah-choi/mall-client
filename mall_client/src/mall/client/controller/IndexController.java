package mall.client.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.client.model.EbookDao;
import mall.client.model.OrdersDao;
import mall.client.vo.Ebook;

// C -> M -> V
@WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	private OrdersDao ordersDao;
	private EbookDao ebookDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 분석
		// 카테고리별 정렬을 위해 기본값이 null인 변수 선언, view에서 카테고리를 선택하면 그 값으로 설정되게 함
		String categoryName = null;
		if(request.getParameter("categoryName") != null) {
			categoryName = request.getParameter("categoryName");
		}
		System.out.println(categoryName+"<-- IndexController의 categoryName");
		
		// ebook 검색 기능을 위해 기본값이 null인 변수 선언, view에서 검색어를 입력하면 그 값으로 설정되게 함
		String searchWord = null;
		if(request.getParameter("searchWord") != null) {
			searchWord = request.getParameter("searchWord");
		}
		System.out.println(searchWord+"<-- IndexController의 searchWord");
		
		// 페이징을 위해서 현재 페이지를 1로 설정, 넘어온 페이지 번호가 존재할 경우에 현재 페이지에 값 넣어주기
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		System.out.println(currentPage+"<-- IndexController의 currentPage");
		
		// 변수 선언
		// 페이지 당 행의 수
		int rowPerPage = 15;
		
		// 시작 행
		int beginRow = (currentPage - 1) * rowPerPage;
		
		// Dao 호출
		// ordersDao 호출
		this.ordersDao = new OrdersDao();
		
		// 베스트 셀러 ebook 목록
		List<Map<String, Object>> bestOrdersList = this.ordersDao.selectBestOrdersList();
		
		// ebookDao 호출
		this.ebookDao = new EbookDao();
		
		// 전체 행의 개수
		int totalRow = this.ebookDao.totalCount(categoryName, searchWord);
		System.out.println(totalRow+"<-- IndexController의 totalRow");
		
		// 카테고리 목록
		List<String> categoryList = this.ebookDao.categoryList();
		
		// ebook 목록
		List<Ebook> ebookList = null;
		// view에서 기능 사용에 따라서 ebookList 값을 다르게 복사 (categoryName, searchWord)
		// 검색어가 없다면,
		if(searchWord == null) {
			ebookList = this.ebookDao.selectEbookListByPageAndCategoryName(beginRow, rowPerPage, categoryName);
		} else { // 검색어가 있다면 두번째 메소드 실행
			ebookList = this.ebookDao.selectEbookListByPageAndSearchWord(beginRow, rowPerPage, searchWord);
		}
		
		// index.jsp에 값을 보내기 위해 request 객체에 값을 저장함  
		request.setAttribute("bestOrdersList", bestOrdersList);
		request.setAttribute("ebookList", ebookList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("searchWord", searchWord);
		
		// View forwarding
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
		rd.forward(request, response);
	}

}
