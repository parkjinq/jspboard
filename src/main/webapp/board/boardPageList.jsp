<%@page import="kr.or.ddit.model.PageVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<%@ include file="/common/basicLib.jsp"%>
<style type="text/css">
	.userClick {
		cursor : pointer;
	}
</style>
<script>
	$(document).ready(function() {
		console.log("document.ready");
		
		var ev = "click";
		
		$(".postsClick").on(ev, function() {
			console.log("document.ready");
// 			var ps_id = $(this).children()[0].innerText;
// 			var ps_id = $(this).children().eq(0).text();
			var ps_id = $(this).children().eq(0).find('input').val();
			
			$("#ps_id").val(ps_id);
			$("#frm").submit();
		});
	});
	
</script>

</head>

<form id="frm" action="/postsDetail" method="get">
	<input type="hidden" id="ps_id" name="ps_id">
</form>

<body>
	<%@ include file="/common/header.jsp"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/common/left.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="row">
					<div class="col-sm-8 blog-main">
						<h2 class="sub-header">${bd_title } 게시판</h2>
						<div class="table-responsive">
							<table class="table table-striped table-hover">
								<tr>
									<th>게시글번호</th>
									<th>제목</th>
									<th>작성자아이디</th>
									<th>작성일시</th>
								</tr>
								
								<c:forEach items="${psList }" var="ps">
									<c:choose>
										<c:when test="${ps.ps_del eq 'N'}">
											<tr class="postsClick">
												<td>${ps.rnum}<input type="hidden" value="${ps.ps_id}"> </td>
												<td>${ps.ps_title}</td>
												<td>${ps.userid}</td>
												<td><fmt:formatDate value="${ps.ps_date}" pattern="yyyy-MM-dd"/></td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td></td>
												<th>삭제된 게시글입니다.</th>
												<td colspan="2"></td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>

						<a class="btn btn-default pull-right" href="/postsInsert?bd_id=${bd_id }">새글 등록</a>

						<div class="text-center">
							<ul class="pagination">
								<li><a href="/boardPageList?page=1&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }" aria-label="Previous"> <span
										aria-hidden="true">&laquo;</span>
								</a></li>
								<li><a href="/boardPageList?page=1&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }"><<</a></li>
								
								<c:set var="startIndex" value="0"></c:set>
								<c:set var="endIndex" value="0"></c:set>
								<c:set var="pagingNum" value="0"></c:set>
								<c:set var="pageAllNum" value="${psCnt }"></c:set>
								
								<c:choose>
									<c:when test="${psCnt != 0}">
										<c:forEach begin="0" end="${(pageVO.page -1)/10 }" varStatus="status">
											<c:set var="startIndex" value="${(status.index * 10) + 1 }"></c:set>
											<c:set var="endIndex" value="${startIndex + 9 }"></c:set>
										</c:forEach>
										<c:if test="${endIndex > pageAllNum }">
											<c:set var="endIndex" value="${pageAllNum }"></c:set>
										</c:if>
										
									</c:when>
									<c:otherwise>
										<c:set var="startIndex" value="1"></c:set>
										<c:set var="endIndex" value="1"></c:set>
									</c:otherwise>
								</c:choose>
								<li><a href="/boardPageList?page=${startIndex == 1 ? 1 : startIndex-10}&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }"><</a></li>
								
								<c:forEach begin="${startIndex }" end="${endIndex }" varStatus="status">
									<li><a href="/boardPageList?page=${status.index }&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }">${status.index }</a></li>
								</c:forEach>

								<li><a href="/boardPageList?page=${startIndex + 5 > pageAllNum ? startIndex : startIndex + 10}&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }">></a></li>
								<li><a href="/boardPageList?page=${pageAllNum }&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }">>></a></li>
								<li><a href="/boardPageList?page=${pageAllNum }&pageSize=10&bd_id=${bd_id }&bd_title=${bd_title }" aria-label="Next"> <span
										aria-hidden="true">&raquo;</span>
								</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
