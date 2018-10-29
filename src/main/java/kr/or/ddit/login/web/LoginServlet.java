package kr.or.ddit.login.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.encrypt.sha.KISA_SHA256;
import kr.or.ddit.login.service.ILoginService;
import kr.or.ddit.login.service.LoginService;
import kr.or.ddit.model.BoardVO;
import kr.or.ddit.model.UserVO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private ILoginService service;
	private IBoardService bd_service;
	
	public LoginServlet () {
		service = LoginService.getInstance();
		bd_service = BoardService.getInstance();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userId = req.getParameter("user_id");
		String password = req.getParameter("user_pw");
		String rememberMe = req.getParameter("remember-me");

		//아이디 기억
		if(rememberMe == null){
			
			Cookie[] cookies = req.getCookies();
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("userId")){
					cookie.setMaxAge(0); // 0 : 바로삭제, -1 : 브라우저 재시작시 쿠키삭제 반영
					resp.addCookie(cookie);
				} else if(cookie.getName().equals("remember")){
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
				System.out.println(cookie.getName());
			}
		} else {
			//response 객체에 저장
			Cookie cookie = new Cookie("remember", "Y");
			Cookie useridCookie = new Cookie("userId", userId);
			resp.addCookie(cookie);
			resp.addCookie(useridCookie);
		}
		
		UserVO loginInfo = new UserVO();
		loginInfo.setUserId(userId);
		loginInfo.setPass(password);
		
		UserVO userVO = service.selectUser(loginInfo);
//		String encryptPass = KISA_SHA256.encrypt(password);
//		userVO.authPass(encryptPass);
		if(userVO != null){
			HttpSession session = req.getSession();
			
			session.setAttribute("S_userVO", userVO);
			List<BoardVO> bdListLeft = bd_service.selectBoardAllLeft();
			
			//왼쪽 메뉴는 설정부분이라고 생각하여 application사용
			getServletContext().setAttribute("bdListLeft", bdListLeft);
			
			RequestDispatcher rd = req.getRequestDispatcher("main.jsp");
			rd.forward(req, resp);
		} else {
			resp.sendRedirect("login/login.jsp");
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		
		resp.sendRedirect("/");
	}
}







