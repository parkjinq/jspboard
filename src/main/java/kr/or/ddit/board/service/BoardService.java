package kr.or.ddit.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.board.dao.BoardDao;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.model.AttachmentsVO;
import kr.or.ddit.model.BoardVO;
import kr.or.ddit.model.CommentsVO;
import kr.or.ddit.model.PageVO;
import kr.or.ddit.model.PostsVO;

public class BoardService implements IBoardService {
	private IBoardDao dao = null;
	private static IBoardService service = null;

	private BoardService() {
		dao = BoardDao.getInstance();
	}

	public static IBoardService getInstance() {
		if (service == null) {
			service = new BoardService();
		}
		return service;
	}

	@Override
	public BoardVO selectBoardById(String bd_id) {
		BoardVO boardVO = dao.selectBoardById(bd_id);
		return boardVO;
	}

	@Override
	public List<BoardVO> selectBoardAllLeft() {
		List<BoardVO> bdList = dao.selectBoardAllLeft();
		return bdList;
	}

	@Override
	public List<BoardVO> selectBoardAll() {
		List<BoardVO> bdList = dao.selectBoardAll();
		return bdList;
	}

	@Override
	public Map<String, Object> selectBoardPosts(String bd_id) {
		List<PostsVO> psList = dao.selectBoardPosts(bd_id);
		int psCnt = dao.getNumBoardPosts(bd_id);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("psList", psList);
		resultMap.put("psCnt", psCnt);

		return resultMap;
	}

	@Override
	public Map<String, Object> selectBoardPostsPage(Map<String, Object> params) {

		params.get("pageVO");
		List<PostsVO> psListPage = dao.selectBoardPostsPage(params);
		int psCnt = dao.getNumBoardPosts((String) params.get("bd_id"));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("psList", psListPage);
		resultMap.put(
				"psCnt",
				(int) (Math.ceil((double) psCnt
						/ ((PageVO) params.get("pageVO")).getPageSize())));

		return resultMap;
	}

	@Override
	public int updateBoard(BoardVO boardVO) {
		int cnt = dao.updateBoard(boardVO);
		return cnt;
	}

	@Override
	public int insertBoard(BoardVO boardVO) {
		int cnt = dao.insertBoard(boardVO);
		return cnt;
	}

	@Override
	public Map<String, Object> selectPosts(String ps_id) {

		PostsVO postsVO = dao.selectPostsById(ps_id);
		List<CommentsVO> cmList = dao.selectCommentsById(ps_id);
		List<AttachmentsVO> attList = dao.selectAttachmentsById(ps_id);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("postsVO", postsVO);
		resultMap.put("cmList", cmList);
		resultMap.put("attList", attList);

		return resultMap;
	}

	@Override
	public int insertComments(CommentsVO commentsVO) {
		int cnt = dao.insertComments(commentsVO);
		return cnt;
	}

	@Override
	public int deleteComments(CommentsVO commentsVO) {
		int cnt = dao.deleteComments(commentsVO);
		return cnt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertPosts(Map<String, Object> params) {
		PostsVO postsVO = (PostsVO)params.get("postsVO");
		List<AttachmentsVO> insertList = (List<AttachmentsVO>)params.get("insertList");
		int cnt = 0;
		System.out.println("BoardService[122] ps_id2 : " + postsVO.getPs_id2());
		
		String ps_id = ("").equals(postsVO.getPs_id2()) ? dao.insertPosts(postsVO) : dao.insertPostsRe(postsVO);
		
		System.out.println("BoardService[126] ps_id : " + ps_id);
		
		if(ps_id != null){
			cnt++;
		}
		for(AttachmentsVO att : insertList){
			
			att.setPs_id(ps_id);
			System.out.println("BoardServlet[133] att : " + att);
			cnt += dao.insertAttachments(att);
		}
		
		return cnt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updatePosts(Map<String, Object> params) {
		//첨부파일도 같이
		//파라미터를 맵으로 받고
		//서비스안서 반복문 돌리자
		PostsVO postsVO = (PostsVO)params.get("postsVO");
		String[] deleteList = (String[])params.get("deleteList");
		List<AttachmentsVO> insertList = (List<AttachmentsVO>)params.get("insertList");
		
		int cnt = dao.updatePosts(postsVO);
		for(String str : deleteList){
			cnt += dao.deleteAttachments(str);
		}
		for(AttachmentsVO att : insertList){
			cnt += dao.insertAttachments(att);
		}
		
		return cnt;
	}

	@Override
	public int deletePosts(String ps_id) {
		int cnt = dao.deletePosts(ps_id);
		return cnt;
	}

}
