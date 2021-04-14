package mall.client.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.ClientDao;
import mall.client.vo.Client;

@WebServlet("/InsertClientController")
public class InsertClientController extends HttpServlet {
	private ClientDao clientDao;
	// 폼 : C -> V
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사 (로그인 했는지 안했는지), 로그인 아니면 redirect
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") != null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/client/insertClient.jsp").forward(request, response);
	}
	// action : C -> M -> redirect
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// dao 호출
		this.clientDao = new ClientDao();
		
		// form에서 입력한 정보 수집
		String clientMail = request.getParameter("clientMail");
		String clientPw = request.getParameter("clientPw");
		// 디버깅
		System.out.println(clientMail+"<-- insertClientController의 clientMail");
		System.out.println(clientPw+"<-- insertClientController의 clientPw");
		
		// 1. 이메일 중복 여부
		String returnClientMail = clientDao.selectClientMail(clientMail);
		// 중복 시에 콘솔 메세지 출력 후 회원가입 페이지로 다시 이동
		if(returnClientMail != null) { // null이 아니라는 것은 중복된다는 것!
			System.out.println("이미 회원가입이 된 이메일 입니다. 다른 이메일을 사용해주세요.");
			response.sendRedirect(request.getContextPath()+"/InsertClientController");
		}
		
		// 2. 회원가입을 위해서 회원 정보 clientDao에 저장
		Client client = new Client();
		client.setClientMail(clientMail);
		client.setClientPw(clientPw);
		// insert 메소드 호출
		clientDao.insertClient(client);
		
		// 회원가입 완료 후 Index로 재요청
		response.sendRedirect(request.getContextPath()+"/IndexController");
	}
}
