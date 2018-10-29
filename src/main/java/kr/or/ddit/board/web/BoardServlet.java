package kr.or.ddit.board.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.model.AttachmentsVO;
import kr.or.ddit.model.BoardVO;
import kr.or.ddit.model.CommentsVO;
import kr.or.ddit.model.PageVO;
import kr.or.ddit.model.PostsVO;
import kr.or.ddit.model.UserVO;

@WebServlet(urlPatterns = {"/boardPageList", "/boardManage", "/boardUpdate", "/boardInsert", "/postsDetail", "/commentsInsert", "/commentsDelete"})
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IBoardService service = null;
	
	public BoardServlet() {
		service = BoardService.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		
		if(uri.equals("/boardPageList")){
			boardPageList(request, response);
		} else if(uri.equals("/boardManage")){
			boardManage(request, response);
		} else if(uri.equals("/boardUpdate")){
			boardUpdate(request, response);
		} else if(uri.equals("/postsDetail")) {
			postsDetail(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		
		if(uri.equals("/boardInsert")){
			boardInsert(request, response);
		} else if (uri.equals("/commentsInsert")) {
			try {
				commentsInsert(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (uri.equals("/commentsDelete")) {
			commentsDelete(request, response);
		} 
	}

	private void commentsDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cm_id = request.getParameter("d_cm_id");
		String ps_id = request.getParameter("d_ps_id");
		
		System.out.println(" cm_id : " + cm_id + " ps_id : " + ps_id);
		
		CommentsVO commentsVO = new CommentsVO();
		commentsVO.setCm_id(cm_id);
		request.setAttribute("ps_id", ps_id);
		
		int cnt = service.deleteComments(commentsVO);
		if(cnt == 0){
			System.out.println("삭제 실패");
		} else {
			System.out.println("삭제 성공");
			postsDetail(request, response);
		}
		
	}

	@SuppressWarnings("unchecked")
	private void postsDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//해당 게시글의 정보, 첨부파일, 댓글 모두 필요
		String ps_id = null;
		if(request.getAttribute("ps_id") == null){
			ps_id = request.getParameter("ps_id");
		} else {
			ps_id = (String) request.getAttribute("ps_id");
		}
		
		Map<String, Object> resultMap = service.selectPosts(ps_id);
		
		PostsVO postsVO = (PostsVO) resultMap.get("postsVO");
		List<CommentsVO> cmList = (List<CommentsVO>) resultMap.get("cmList");
		List<AttachmentsVO> attList = (List<AttachmentsVO>) resultMap.get("attList");
		
		
		UserVO userVO = (UserVO) request.getSession().getAttribute("S_userVO");
		request.setAttribute("postsVO", postsVO);
		request.setAttribute("cmList", cmList);
		request.setAttribute("attList", attList);
		
		request.getRequestDispatcher("/board/postsDetail.jsp").forward(request, response);
	}

	private void boardInsert(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserVO loginInfo = (UserVO) request.getSession().getAttribute("S_userVO");
		String bd_title = request.getParameter("c_bd_title");
		String bd_use = request.getParameter("c_bd_use");
		
		String userid = loginInfo.getUserId();
		BoardVO boardVO = new BoardVO();
		boardVO.setBd_title(bd_title);
		boardVO.setBd_use(bd_use);
		boardVO.setUser_id(userid);
		
		int cnt = service.insertBoard(boardVO);
		if(cnt == 0){
			System.out.println("생성 실패");
		} else {
			System.out.println("생성 성공");
			boardManage(request, response);
		}
	}

	private void boardUpdate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bd_id = request.getParameter("bd_id");
		String bd_title = request.getParameter("bd_title");
		String bd_use = request.getParameter("bd_use");
		
		BoardVO boardVO = new BoardVO();
		boardVO.setBd_title(bd_title);
		boardVO.setBd_use(bd_use);
		boardVO.setBd_id(bd_id);
		
		int cnt = service.updateBoard(boardVO);
		if(cnt == 0){
			System.out.println("수정 실패");
		} else {
			System.out.println("수정 성공");
			boardManage(request, response);
//			request.getRequestDispatcher("/boardManage").forward(request, response);
		}
		
	}

	private void boardPageList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bd_id = request.getParameter("bd_id");
		BoardVO boardVO = service.selectBoardById(bd_id);
		String bd_title = boardVO.getBd_title();
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		PageVO pageVO = new PageVO(page, pageSize);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageVO", pageVO);
		params.put("bd_id", bd_id);
		
		Map<String, Object> resultMap = service.selectBoardPostsPage(params);
		@SuppressWarnings("unchecked")
		List<PostsVO> psList = (List<PostsVO>) resultMap.get("psList");
		int psCnt = (int) resultMap.get("psCnt");
		
		request.setAttribute("bd_id", bd_id);
		request.setAttribute("bd_title", bd_title);
		request.setAttribute("psList", psList);
		request.setAttribute("psCnt", psCnt);
		request.setAttribute("pageVO", pageVO);
		
		request.getRequestDispatcher("/board/boardPageList.jsp").forward(request, response);
	}

	private void boardManage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<BoardVO> bdListLeft = service.selectBoardAllLeft();
		//왼쪽 메뉴는 설정부분이라고 생각하여 application사용
		getServletContext().setAttribute("bdListLeft", bdListLeft);
		
		List<BoardVO> bdList = service.selectBoardAll();
		request.setAttribute("bdListAll", bdList);
		
		request.getRequestDispatcher("/board/boardManage.jsp").forward(request, response);
	}

	private void commentsInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		System.out.println("cm_cnt : " + request.getParameter("ci_cm_cnt"));
		String cm_cnt = request.getParameter("ci_cm_cnt");
		String userId = request.getParameter("ci_userId");
		String ps_id = request.getParameter("ci_ps_id");
		request.setAttribute("ps_id", ps_id);
		
		CommentsVO commentsVO = new CommentsVO();
		commentsVO.setUserId(userId);
		commentsVO.setPs_id(ps_id);
		commentsVO.setCm_cnt(cm_cnt);
		
		int cnt = service.insertComments(commentsVO);
		
		if(cnt == 0){
			System.out.println("댓글 저장 실패");
		} else {
			System.out.println("댓글 저장 성공");
			postsDetail(request, response);
		}
	}

}
