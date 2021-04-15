package mall.client.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;
import mall.client.vo.Cart;
import mall.client.vo.Client;

@WebServlet("/InsertCartController")
public class InsertCartController extends HttpServlet {
	// 장바구니 추가 시키는 dao 가져옴
	private CartDao cartDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 여부 확인
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect("/IndexController");
			return;
		}
		int ebookNo = Integer.parseInt(request.getParameter("ebookNo"));
		// dao 호출
		this.cartDao = new CartDao();
		Cart cart = new Cart();
		cart.setEbookNo(ebookNo);
		cart.setClientMail(((Client)session.getAttribute("loginClient")).getClientMail());
		
		// 카트 안에 동일한 ebook이 존재하는지 확인 코드 추가
		if(this.cartDao.selectClientMail(cart)) {
			this.cartDao.insertCart(cart);
		} else {
			System.out.println("카트 안에 중복된 상품이 있습니다.");
		}
		
		response.sendRedirect(request.getContextPath()+"/CartListController");
	}

}
