package mall.client.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;
import mall.client.model.OrdersDao;
import mall.client.vo.Cart;
import mall.client.vo.Client;
import mall.client.vo.Orders;

@WebServlet("/InsertOrdersController")
public class InsertOrdersController extends HttpServlet {
	private CartDao cartDao;
	private OrdersDao ordersDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 세션 확인
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		
		// 의존객체 생성 후 주입
		this.cartDao = new CartDao();
		this.ordersDao = new OrdersDao();
		
		// request
		int ebookNo = Integer.parseInt(request.getParameter("ebookNo"));
		Client client = (Client)session.getAttribute("loginClient"); // session 가져오면서 Client로 형변환
		Orders orders = new Orders();
		orders.setEbookNo(ebookNo);
		orders.setClientNo(client.getClientNo());
		Cart cart = new Cart();
		cart.setEbookNo(ebookNo);
		
		// dao 호출
		// insert 후 delete 되기 전에 db에 문제가 발생할 경우? --> insert도 취소(roll back) --> 트랜잭션 처리
		cartDao.deleteCart(cart);
		ordersDao.insertOrders(orders);
		
		response.sendRedirect(request.getContextPath()+"/OrdersListController");
		
	}

}
