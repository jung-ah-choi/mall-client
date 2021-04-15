package mall.client.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;
import mall.client.model.ClientDao;
import mall.client.vo.Client;

@WebServlet("/DeleteClientController")
public class DeleteClientController extends HttpServlet {
	private ClientDao clientDao;
	private CartDao cartDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 여부 확인
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect("/IndexController");
			return;
		}
		
		// dao 호출
		this.cartDao = new CartDao();
		this.clientDao = new ClientDao();
		Client client = new Client();
		// 세션에 있는 회원 이메일 값을 가져옴
		client.setClientMail(((Client)session.getAttribute("loginClient")).getClientMail());
		
		// 장바구니 삭제 메소드와 회원탈퇴 메소드 실행
		this.cartDao.deleteCartByClient(client);
		this.clientDao.deleteClient(client);
		
		// 세션 초기화
		session.invalidate();

		// 삭제 완료 후 재요청
		response.sendRedirect(request.getContextPath()+"/CartListController");
	}
}
