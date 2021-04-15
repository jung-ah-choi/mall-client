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

@WebServlet("/UpdateClientPwController")
public class UpdateClientPwController extends HttpServlet {
	private ClientDao clientDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사 (로그인을 했을 경우에만 패스워드 변경 가능하게 함)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/client/updateClientPw.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사 (로그인을 했을 경우에만 패스워드 변경 가능하게 함)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		
		// dao 호출
		this.clientDao = new ClientDao();
		
		// form에서 입력한 정보 수집
		String clientPw = request.getParameter("clientPw");
		System.out.println(clientPw+"<-- UpdateClientPwController의 clientPw"); // 디버깅
		
		// 전처리
		Client client = new Client();
		// 해당 회원 이메일과, 입력한 비밀번호 값을 메소드에 넣기
		client.setClientMail(((Client)session.getAttribute("loginClient")).getClientMail());
		client.setClientPw(clientPw);
		
		// updatePw 메소드 호출
		clientDao.updateClientPw(client);
		
		// 세션초기화
		session.invalidate();
		
		// 비밀번호 변경 후 Index로 재요청
		response.sendRedirect(request.getContextPath()+"/IndexController");
	}

}
