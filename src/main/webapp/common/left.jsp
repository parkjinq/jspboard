<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="col-sm-3 col-md-2 sidebar">
		<ul class="nav nav-sidebar">
			<li class="active"><a href="/main.jsp">Main <span class="sr-only">(current)</span></a></li>
			<li class="active"><a href="/boardManage">게시판 생성</a></li>
			
			<c:forEach items="${bdListLeft}" var="bd">
				<li class="active"><a href="/boardPageList?page=1&pageSize=10&bd_id=${bd.bd_id }">${bd.bd_title }</a></li>
			</c:forEach>
			
		</ul>
	</div>
