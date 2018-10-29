package kr.or.ddit.board.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.model.AttachmentsVO;
import kr.or.ddit.model.BoardVO;
import kr.or.ddit.model.CommentsVO;
import kr.or.ddit.model.PostsVO;

public interface IBoardService {
	
	/**
	 * 
	* Method : selectBoardById
	* 작성자 : jin
	* 변경이력 :
	* @param bd_id
	* @return
	* Method 설명 : 아이디에 해당하는 게시판VO 반환하는 메서드
	 */
	public BoardVO selectBoardById(String bd_id);
	
	/**
	 * 
	* Method : selectBoardAll
	* 작성자 : jin
	* 변경이력 :
	* @return
	* Method 설명 : 모든 BoardVO리스트를 반환하는 메서드
	 */
	public List<BoardVO> selectBoardAll();
	
	/**
	 * 
	* Method : selectBoardAllLeft
	* 작성자 : jin
	* 변경이력 :
	* @return
	* Method 설명 : left.jsp에 띄어지는 BoardVO리스트를 반환하는 메서드
	 */
	public List<BoardVO> selectBoardAllLeft();
	
	/**
	 * 
	* Method : selectBoardPosts
	* 작성자 : jin
	* 변경이력 :
	* @param bd_id
	* @return
	* Method 설명 : bd_id코드에 맞는 게시글(PostsVO)리스트, 개수 반환하는 메서드
	 */
	public Map<String, Object> selectBoardPosts(String bd_id);
	
	/**
	 * 
	* Method : selectBoardPostsPage
	* 작성자 : jin
	* 변경이력 :
	* @param params
	* @return
	* Method 설명 : 해당페이지의 페이지크기만큼 PostsVO리스트를 반환하는 메서드
	 */
	public Map<String, Object> selectBoardPostsPage(Map<String, Object> params);
	
	/**
	 * 
	* Method : updateBoard
	* 작성자 : jin
	* 변경이력 :
	* @param boardVO
	* @return
	* Method 설명 : 게시판 정보 수정하는 메서드
	 */
	public int updateBoard(BoardVO boardVO);
	
	/**
	 * 
	* Method : insertBoard
	* 작성자 : jin
	* 변경이력 :
	* @param boardVO
	* @return
	* Method 설명 : 새로운 게시판 생성하는 메서드
	 */
	public int insertBoard(BoardVO boardVO);
	
	/**
	 * 
	* Method : selectPosts
	* 작성자 : jin
	* 변경이력 :
	* @param ps_id
	* @return
	* Method 설명 : 해당 게시글에 대한 게시글정보, 댓글, 첨부파일 불러오는 메서드
	 */
	public Map<String, Object> selectPosts(String ps_id);
	
	/**
	 * 
	* Method : insertComments
	* 작성자 : jin
	* 변경이력 :
	* @param commentdVO
	* @return
	* Method 설명 : 댓글을 insert하는 메서드
	 */
	public int insertComments(CommentsVO commentsVO);
	
	/**
	 * 
	* Method : deleteComments
	* 작성자 : jin
	* 변경이력 :
	* @param commentdVO
	* @return
	* Method 설명 : 댓글을 삭제하는 쿼리문
	 */
	public int deleteComments(CommentsVO commentsVO);
	
	/**
	 * 
	* Method : insertPosts
	* 작성자 : jin
	* 변경이력 :
	* @param postsVO
	* @return
	* Method 설명 : 게시글  insert하는 메서드
	 */
	public int insertPosts(Map<String, Object> params);
	
	/**
	 * 
	* Method : updatePosts
	* 작성자 : jin
	* 변경이력 :
	* @param postsVO
	* @return
	* Method 설명 : 게시글 수정하는 메서드
	 */
	public int updatePosts(Map<String, Object> params);
	
	/**
	 * 
	* Method : deletePosts
	* 작성자 : jin
	* 변경이력 :
	* @param ps_id
	* @return
	* Method 설명 : 게시글을 삭제여부를 Y로 바수정해주는 메서드
	 */
	public int deletePosts(String ps_id);
}
