package kr.or.ddit.login.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 무효화
		//1.session 객체를 확보
		//2.session invalidate 메소드를 통해 무효화
		
		//servlet
		//	page : 존재하지 않음
		//	request : 메소드 인자로 제공
		//	sesison : requeat객체에서 획득가능
		//	application : getServletContext()
		//jsp(내장객체)
		//	pageContext
		//	request
		//	session
		//	application
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
