<%@page import="kr.or.ddit.model.PageVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Jsp</title>
<style type="text/css">
.kyuseung {
	width: 200px;
	height: 200px;
}
</style>

<%@ include file="/common/basicLib.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {

		var ev = "click";

		$(".postsDelete").on(ev, function() {
			console.log("posts-delete_click");
			$("#frm").submit();
		});
		
		$("#insertCm").on(ev, function() {
			console.log("comments_insert_click");
			var cm_cnt = $("#c_cm_cnt").val();
			$("#ci_cm_cnt").val(cm_cnt);
			$("#cmIns").submit();
		});
		
		$("#commentsDelete").on(ev, function() {
			console.log("comments_delete_click");
			var cm_id = $("#d_cmId").val();
			$("#d_cm_id").val(cm_id);
			$("#cmDel").submit();
		});
		
		$("#replyPosts").on(ev, function() {
			console.log("reply_insert_click");
			$("#frm2").submit();
		});
	});
</script>

</head>

<form id="frm" action="/postsDelete" method="post">
	<input type="hidden" id="ps_id" name="ps_id" value="${postsVO.ps_id}">
	<input type="hidden" id="bd_id" name="bd_id" value="${postsVO.bd_id}">
</form>
<form id="frm2" action="/postsInsert" method="get">
	<input type="hidden" id="bd_id" name="bd_id" value="${postsVO.bd_id}">
	<input type="hidden" name="ps_id2" value="${postsVO.ps_id}">
</form>

<body>

	<%@ include file="/common/header.jsp"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/common/left.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<form class="form-horizontal" role="form" action="/postsUpdateForm"	method="post">
					<div class="form-group">
						<input type="hidden" name="u_ps_id" value="${postsVO.ps_id}">
						<table class="table table-striped">
							<tr>
								<th>제목</th>
								<td colspan="2"><span>${postsVO.ps_title }</span></td>
							</tr>
							<tr>
								<th>글내용</th>
								<td colspan="2">
									<p>${postsVO.ps_cnt }</p>
								</td>
							</tr>
							<c:choose>
								<c:when test="${attList.size() == 0}">
									<tr>
										<th>첨부파일</th>
										<td colspan="2">첨부파일 없음</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach begin="0" end="${attList.size()-1}"
										varStatus="status">
										<tr>
											<th>${status.index == 0 ? "첨부파일" : ""}</th>
											<td colspan="2"><a href="/attFileDown?att_path=${attList[status.index].att_path}">${attList[status.index].att_path == null ? "" : attList[status.index].att_path}</a></td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</table>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<c:if test="${postsVO.userid eq S_userVO.userId}">
									<button type="submit" class="btn btn-default">수정</button>
									<input type="button" class="btn btn-default postsDelete" value="삭제">
								</c:if>
								<input type="button" id="replyPosts" class="btn btn-default" value="답글">
							</div>
						</div>
					</div>


					<table class="table table-striped">
						<c:choose>
							<c:when test="${cmList.size()== 0}">
								<tr>
									<th>댓글</th>
									<td colspan="2">댓글 없음</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach begin="0" end="${cmList.size()-1}" varStatus="status">
									<c:choose>
										<c:when test="${cmList[status.index].cm_del eq 'N'}">
											<tr>
												<th>${status.index == 0 ? "댓글" : "" }</th>
												<td>${cmList[status.index].cm_cnt }</td>
												<th>[${cmList[status.index].userId }/<fmt:formatDate
														value="${cmList[status.index].cm_date}" pattern="yyyy-MM-dd" />]
														<c:if test="${postsVO.userid eq S_userVO.userId}">
															<input type="button" id="commentsDelete" value="삭제">
														</c:if>
														<input type="hidden" id="d_cmId" value="${cmList[status.index].cm_id}">
												</th>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<th>${status.index == 0 ? "댓글" : "" }</th>
												<td colspan="2">삭제된 댓글 입니다.</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<tr>
							<td></td>
							<td colspan="2">
								<input type="text" id="c_cm_cnt" name="c_cm_cnt" style="width: 500px"> 
								<input type="button" id="insertCm" class="btn btn-default" value="댓글저장">
							</td>
						</tr>
					</table>
				</form>
				<form id="cmDel" action="/commentsDelete" method="post">
					<input type="hidden" id="d_ps_id" name="d_ps_id" value="${postsVO.ps_id}">
					<input type="hidden" id="d_cm_id" name="d_cm_id">
				</form>

				<form id="cmIns" action="/commentsInsert" method="post" class="form-horizontal">
					<input type="hidden" id="ci_cm_cnt" name="ci_cm_cnt">
					<input type="hidden" id="ci_ps_id" name="ci_ps_id" value="${postsVO.ps_id}">
					<input type="hidden" id="ci_userId" name="ci_userId" value="${S_userVO.userId}">
				</form>

			</div>
		</div>
	</div>
</body>
</html>

