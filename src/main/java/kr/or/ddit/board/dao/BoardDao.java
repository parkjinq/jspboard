package kr.or.ddit.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.SqlFactoryBuilder;
import kr.or.ddit.model.AttachmentsVO;
import kr.or.ddit.model.BoardVO;
import kr.or.ddit.model.CommentsVO;
import kr.or.ddit.model.PostsVO;

public class BoardDao implements IBoardDao {

	private static IBoardDao dao = null;
	private SqlSessionFactory factory = null;
	private SqlSession session = null;
	
	private BoardDao() {
		factory = SqlFactoryBuilder.getSqlSessionFactory();
		session = factory.openSession();
	}
	
	public static IBoardDao getInstance() {
		if(dao == null){
			dao = new BoardDao();
		}
		return dao;
	}
	
	@Override
	public BoardVO selectBoardById(String bd_id) {
		BoardVO boardVO = session.selectOne("board.selectBoardById", bd_id);
		return boardVO;
	}
	
	@Override
	public List<BoardVO> selectBoardAllLeft() {
		List<BoardVO> bdList = session.selectList("board.selectBoardAllLeft");
		return bdList;
	}

	@Override
	public List<BoardVO> selectBoardAll() {
		List<BoardVO> bdList = session.selectList("board.selectBoardAll");
		return bdList;
	}

	@Override
	public List<PostsVO> selectBoardPosts(String bd_id) {
		List<PostsVO> psList = session.selectList("board.selectBoardPosts", bd_id);
		return psList;
	}

	@Override
	public int getNumBoardPosts(String bd_id) {
		int psCnt = session.selectOne("board.getNumBoardPosts", bd_id);
		return psCnt;
	}

	@Override
	public List<PostsVO> selectBoardPostsPage(Map<String, Object> params) {
		List<PostsVO> psList = session.selectList("board.selectBoardPostsPage",params);
		return psList;
	}

	@Override
	public int updateBoard(BoardVO boardVO) {
		int cnt = session.update("board.updateBoard", boardVO);
		
		session.commit();//데이터가 확정이 되니까 필요
		
		return cnt;
	}

	@Override
	public int insertBoard(BoardVO boardVO) {
		int cnt = session.insert("board.insertBoard", boardVO);
//		Object obj = session.insert("board.insertBoard", boardVO);
//		if(obj == null)
//			cnt = 1;
		
		session.commit();//데이터가 확정이 되니까 필요
		
		return cnt;
	}

	@Override
	public PostsVO selectPostsById(String ps_id) {
		PostsVO postsVO = session.selectOne("board.selectPostsById", ps_id);
		return postsVO;
	}

	@Override
	public List<CommentsVO> selectCommentsById(String ps_id) {
		List<CommentsVO> cmList = new ArrayList<CommentsVO>();
		cmList = session.selectList("board.selectCommentsById", ps_id);
		return cmList;
	}

	@Override
	public List<AttachmentsVO> selectAttachmentsById(String ps_id) {
		List<AttachmentsVO> attList = new ArrayList<AttachmentsVO>();
		attList = session.selectList("board.selectAttachmentsById", ps_id);
		return attList;
	}

	@Override
	public int insertComments(CommentsVO commentsVO) {
		int cnt = session.insert("board.insertComments", commentsVO);
		session.commit();
		return cnt;
	}

	@Override
	public int deleteComments(CommentsVO commentsVO) {
		int cnt = session.update("board.deleteComments", commentsVO);
		session.commit();
		return cnt;
	}

	@Override
	public String insertPosts(PostsVO postsVO) {
		int cnt = session.insert("board.insertPosts", postsVO);

		String str = "00" + postsVO.getPs_id();
		str = str.substring(postsVO.getPs_id().length() - 1);
		session.commit();
		System.out.println("boardDao[129] ps_id : " + str);
		return "ps" + str;
	}
	
	@Override
	public String insertPostsRe(PostsVO postsVO) {
		int cnt = session.insert("board.insertPostsRe", postsVO);
		
		String str = "00" + postsVO.getPs_id();
		str = str.substring(postsVO.getPs_id().length() - 1);
		session.commit();
		System.out.println("boardDao[137] ps_id : " + str);
		return "ps" + str;
	}

	@Override
	public int insertAttachments(AttachmentsVO attachmentsVO) {
		int cnt = session.insert("board.insertAttachments", attachmentsVO);
		session.commit();
		return cnt;
	}

	@Override
	public int updatePosts(PostsVO postsVO) {
		int cnt = session.update("board.updatePosts",postsVO);
		session.commit();
		return cnt;
	}

	@Override
	public int deletePosts(String ps_id) {
		int cnt = session.update("board.deletePosts", ps_id);
		session.commit();
		return cnt;
	}

	@Override
	public int deleteAttachments(String att_id) {
		int cnt = session.delete("board.deleteAttachments", att_id);
		session.commit();
		return cnt;
	}

}
