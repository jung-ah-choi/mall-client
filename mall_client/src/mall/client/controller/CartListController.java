package mall.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;

@WebServlet("/CartListController")
public class CartListController extends HttpServlet {
	private CartDao cartDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사 (로그인 했는지 안했는지), 로그인 아니면 redirect
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return; // 코드 중지
		}
		
		// 변수 초기화
		request.setCharacterEncoding("utf-8");
		int cartNo = Integer.parseInt(request.getParameter("cartNo"));
		int ebookNo = Integer.parseInt(request.getParameter("ebookNo"));
		String ebookTitle = request.getParameter("ebookTitle");
		String cartDate = request.getParameter("cartDate");
		
		// dao 호출
		this.cartDao = new CartDao();
		List<Map<String, Object>> cartList = this.cartDao.selectCartList();
		
		// View forward
		request.setAttribute("cartList", cartList);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/cart/cartList.jsp");
		rd.forward(request, response);
	}

}
