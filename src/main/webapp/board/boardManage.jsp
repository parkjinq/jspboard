<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<script type="text/javascript">

	$('document').ready(function() {
// 		$("#createBoard").on("click", function() {
// 			//생성할 게시판 제목
// 			var bd_title = $("#c_bd_title").val();
// 			//생성할 게시판 사용여부
// 			var bd_use = $("#c_bd_use option:selected").val();
			
// 			console.log("bd_title : " + bd_title + "/ bd_use : " + bd_use);
			
			
// 		});
		
		$(".updateBoard").on("click", function() {
			var tr = $(this).parent().parent();
			var td = tr.children();
			
			//수정할 게시판 이름 값
			var bd_title = td.eq(0).children().val();
			//수정할 게시판 ID 값
			var bd_id = td.eq(0).children().eq(1).val();
			//수정할 게시판 사용여부 값
			var id = document.getElementById(bd_id);
			var bd_use = id.options[id.selectedIndex].value;
			
			console.log("bd_title : " + bd_title + "/ bd-id : " + bd_id + "/ bd-use : " + bd_use);
			
			location.href = "/boardUpdate?bd_id=" + bd_id + "&bd_title=" + bd_title + "&bd_use=" + bd_use;
		});
	});
	
</script>
</head>

<body>

	<%@ include file="/common/header.jsp"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/common/left.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="row">
					<div class="col-sm-8 blog-main">
						<h2 class="sub-header">게시판 리스트</h2>
						<div class="table-responsive">
						
						<form id="bd_insert" action="/boardInsert" method="post">
							<table class="table table-striped table-hover">
								<tr>
									<th>게시판 이름</th>
									<th>사용여부</th>
									<th>생성/수정</th>
								</tr>
								<tr style="background-color: powderblue">
										<td><input type="text" id="c_bd_title" name="c_bd_title" value="" /></td>
										<td><select id="c_bd_use" name="c_bd_use">
												<option value="Y">사용</option>
												<option value="N">미사용</option>
										</select></td>
										<td><input type="submit" id="createBoard" value="생성"></td>
								</tr>
								<c:forEach items="${bdListAll }" var="bd">
									<tr>
										<td><input type="text" name="${bd.bd_id }"
											value="${bd.bd_title}" />
											<input type="hidden" value="${bd.bd_id }"> </td>
										<td><select id="${bd.bd_id }" name="bd_use">
												<c:choose>
													<c:when test="${bd.bd_use == 'Y'}">
														<option value="Y" selected="selected">사용</option>
														<option value="N">미사용</option>
													</c:when>
													<c:otherwise>
														<option value="Y">사용</option>
														<option value="N" selected="selected">미사용</option>
													</c:otherwise>
												</c:choose>
										</select></td>
										<td><input type="button" class="updateBoard" value="수정"></td>
									</tr>
								</c:forEach>
							</table>
						</form>
							
						</div>
						<div class="text-center"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
