package mall.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.*;
import mall.client.vo.*;

@WebServlet("/ClientOneController")
public class ClientOneController extends HttpServlet {
	private ClientDao clientDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사 (로그인 했는지 안했는지), 로그인 아니면 redirect
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") != null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		
		// dao 호출
		this.clientDao = new ClientDao();
		// 로그인 된 mail의 정보를 가져옴
		Client clientOne = clientDao.selectClientOne(((Client)(session.getAttribute("loginClient"))).getClientMail());
		
		// View forward
		request.setAttribute("clientOne", clientOne);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/client/clientOne.jsp");
		rd.forward(request, response);
	}

}
