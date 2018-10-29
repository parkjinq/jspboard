package kr.or.ddit.board.web;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.model.AttachmentsVO;
import kr.or.ddit.model.CommentsVO;
import kr.or.ddit.model.PostsVO;
import kr.or.ddit.model.UserVO;
import kr.or.ddit.util.StringUtil;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/postsInsert", "/postsDelete", "/postsUpdate", "/postsUpdateForm" })
public class PostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IBoardService service = BoardService.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String ps_id2 = request.getParameter("ps_id2");
		String groupid = request.getParameter("groupid");
		request.setAttribute("ps_id2", ps_id2);
		request.setAttribute("groupid", groupid);
		
		
		String bd_id = request.getParameter("bd_id");
		request.setAttribute("bd_id", bd_id);
		request.getRequestDispatcher("board/postsInsert.jsp").forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		
		if(uri.equals("/postsDelete")){
			postsDelete(request, response);
		} else if (uri.equals("/postsInsert")) {
			postsInsert(request, response);
		} else if (uri.equals("/postsUpdate")) {
			postsUpdate(request, response);
		} else if (uri.equals("/postsUpdateForm")) {
			postsUpdateForm(request, response);
		}
	}

	private void postsUpdate(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println("=============postsUpdate=============");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String ps_title = request.getParameter("ps_title");
		String ps_cnt = request.getParameter("smarteditor");
		String ps_id = request.getParameter("ps_id");
		String bd_id = request.getParameter("bd_id");
		
		PostsVO postsVO = new PostsVO();
		postsVO.setPs_title(ps_title);
		postsVO.setPs_cnt(ps_cnt);
		postsVO.setPs_id(ps_id);
		//수정해줄 게시판 정보 맵에 등록
		params.put("postsVO", postsVO);
		
		String[] temp = StringUtil.tempUtil(request.getParameter("temp"));
		//기존 첨부파일 삭제
		//삭제해줄 첨부파일아이디리스트 맵에 등록
		params.put("deleteList", temp);
		
		//추가 첨부파일 추가
		//추가해줄 첨부파일 리스트 맵에 등록
		List<AttachmentsVO> attList = new ArrayList<AttachmentsVO>();
		for(int i = 1; i <= 5; i++){
			if(request.getPart("file" + i) != null){
				Part attPart = request.getPart("file" + i);
				String contentDisposition = attPart.getHeader("Content-disposition");
				String att = StringUtil.stringUtil(contentDisposition);
				if(!("").equals(att)){
					String att_path = getServletContext().getRealPath("/attachments");
					
					System.out.println("결과 : " + att_path + File.separator + att);
					attPart.write(att_path + File.separator + att);
					attPart.delete();
					
					//파일 저장
					AttachmentsVO attachmentsVO = new AttachmentsVO();
					attachmentsVO.setAtt_path(att);
					attachmentsVO.setPs_id(ps_id);
					attList.add(attachmentsVO);
					//
					System.out.println("profilePart(Header) : " + attPart.getHeader("Content-disposition"));
					System.out.println("profilePart(Type) : " + attPart.getContentType());
				}
			}
		}
		
		params.put("insertList", attList);
		
		int cnt = service.updatePosts(params);
		System.out.println("[postsServlet 109]cnt : " + cnt);
		System.out.println("=====================================");
		if(cnt > 0){
			response.sendRedirect("/postsDetail?ps_id=" + ps_id);
		} else {
//			response.sendRedirect("/postsInsert?bd_id=" + bd_id + "&groupid=" + groupid);
//			request.setAttribute("ps_id", ps_id);
//			request.getRequestDispatcher("/postsupdate").forward(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private void postsUpdateForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//해당 게시글의 정보, 첨부파일
		String ps_id = request.getParameter("u_ps_id");
		
		Map<String, Object> resultMap = service.selectPosts(ps_id);
		
		PostsVO postsVO = (PostsVO) resultMap.get("postsVO");
		List<AttachmentsVO> attList = (List<AttachmentsVO>) resultMap.get("attList");
		
		request.setAttribute("postsVO", postsVO);
		request.setAttribute("attList", attList);
		
		request.getRequestDispatcher("/board/postsUpdate.jsp").forward(request, response);
	}

	private void postsDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String ps_id = request.getParameter("ps_id");
		String bd_id = request.getParameter("bd_id");
		int cnt = service.deletePosts(ps_id);
		if(cnt > 0){
			response.sendRedirect("/boardPageList?page=1&pageSize=10&bd_id=" + bd_id);
		} else {
			request.setAttribute("ps_id", ps_id);
			request.getRequestDispatcher("/postsDetail").forward(request, response);
		}
	}

	private void postsInsert(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		System.out.println("=============postsInsert=============");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		UserVO userVO = (UserVO) session.getAttribute("S_userVO");
		String ps_title =  request.getParameter("ps_title");
		
		String ps_cnt = request.getParameter("smarteditor");
		
		String bd_id = request.getParameter("bd_id");
		String userId = userVO.getUserId();
		String ps_id2 = request.getParameter("ps_id2") == null ? "" : request.getParameter("ps_id2");
		
		PostsVO postsVO = new PostsVO();
		postsVO.setPs_title(ps_title);
		postsVO.setPs_cnt(ps_cnt);
		postsVO.setBd_id(bd_id);
		postsVO.setUserid(userId);
		postsVO.setPs_id2(ps_id2);
		
		params.put("postsVO", postsVO);
		System.out.println("postsServlet [186]postsVO : " + postsVO);
		
		List<AttachmentsVO> attList = new ArrayList<AttachmentsVO>();
		for(int i = 1; i <= 5; i++){
			if(request.getPart("file" + i) != null){
				Part attPart = request.getPart("file" + i);
				String contentDisposition = attPart.getHeader("Content-disposition");
				String att = StringUtil.stringUtil(contentDisposition);
					if(!("").equals(att)){
						String att_path = getServletContext().getRealPath("/attachments");
						
						System.out.println("결과" + att_path + File.separator + att);
						attPart.write(att_path + File.separator + att);
						attPart.delete();
						
						//파일 저장
						AttachmentsVO attachmentsVO = new AttachmentsVO();
						attachmentsVO.setAtt_path(att);
						attList.add(attachmentsVO);
						//
						System.out.println("profilePart(Header) : " + attPart.getHeader("Content-disposition"));
						System.out.println("profilePart(Type) : " + attPart.getContentType());
						System.out.println("userId : " + userId + "\nprofile : " + att);
					}
			}
		}
		params.put("insertList", attList);
		
		int cnt = service.insertPosts(params);
		
		System.out.println("=====================================");
		if(cnt > 0){
			System.out.println("작성성공");
			response.sendRedirect("/boardPageList?page=1&pageSize=10&bd_id=" + bd_id);
		} else {
			System.out.println("작성실패");
			response.sendRedirect("/postsInsert?bd_id=" + bd_id);
//			request.setAttribute("bd_id", bd_id);
//			request.getRequestDispatcher("/postsInsert").forward(request, response);
		}
	}
	
}
