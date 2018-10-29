<%@page import="kr.or.ddit.model.PageVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
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
	.kyuseung{width:200px;height:200px}
	.fileListPJ{display:flex}
</style>

<%@ include file="/common/basicLib.jsp"%>

<!-- 다음 주소 API -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<!-- jquery ui api -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- jquery ui css -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  
  
<script src="/SE2/js/HuskyEZCreator.js"></script>


<script>

	var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.
	var temp = "";
	
	$(document).ready(function() {
		//추가 가능한 첨부파일 개수
		var index = 5-$("#attSize").val()-1;
		console.log("시작 추가 가능한 첨부파일 수index : " + index);
		// Editor Setting
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors, // 전역변수 명과 동일해야 함.
			elPlaceHolder : "smarteditor", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
			sSkinURI : "/SE2/SmartEditor2Skin.html", // Editor HTML
			fCreator : "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
			htParams : {
				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseToolbar : true,
				// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseVerticalResizer : true,
				// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
				bUseModeChanger : true, 
			}
		});

		// 전송버튼 클릭이벤트
		$("#savebutton").click(function(){
			if(confirm("저장하시겠습니까?")) {
				// id가 smarteditor인 textarea에 에디터에서 대입
				oEditors.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);

				// 이부분에 에디터 validation 검증
				if(validation()) {
					$("#frm").submit();
				}
			}
		})
		
		$(".addFile").on("click", function() {
			//기존 첨부파일 개수를 제외하고 input file타입 생성
			//클론으로 복사하고
			//파일 id name
			//버튼 아이디 네임 수정, 버큰은 del버튼으로 변경
				
			if(index > 0){
				var tr = document.createElement('tr');
				tr.id = "att" + index;
				
				var td1 = document.createElement('td');
				var td2 = document.createElement('td');
				var div = document.createElement('div');
				div.className = "fileListPJ";
				
				
				var file = document.createElement('input')
				file.type = "file";
				
				for(var i = 1; i <= index; i++){
					var id = "#file" + i;
					if($(id).length > 0){
						
					} else {
						console.log("사용 가능한 아이디 : file" + i );
						file.name = "file" + i;
						file.id = "file" + i;
					}
				}
				
				
				
				var del = document.createElement('input')
				del.type = "button";
				del.className = "btn btn-default delFile" + index;
				del.id = "delFile" + index;
				del.value = "-";
				del.onclick = function() {delFile(tr)}
				
				div.appendChild(file);
				div.appendChild(del);
				td2.appendChild(div);
				
				tr.appendChild(td1);
				tr.appendChild(td2);
				
				$(this).parent().parent().parent().after(tr);
				
				index--;
			}
				
			console.log("추가 후 추가 가능한 첨부파일 수index : " + index);
		});
		
		//기존첨부파일 삭제
		$(".delFile").on("click", function() {
			if($(this).prev().val() != null){
				//첨부파일 이름 임시로 저장
				temp += $(this).prev().val() + ";";
				$("#temp").val(temp);
			}
			// tr을 지운다
			$(this).closest('tr').remove();
			index++;
			console.log("[기존파일]삭제 후 추가 가능한 첨부파일 수index : " + index);
		});
		
		//추가첨부파일 삭제
		function delFile(tr) {
			tr.remove();
			index++;
			console.log("[추가파일]삭제 후 추가 가능한 첨부파일 수index : " + index);
		}
	});
	
	// 필수값 Check
	function validation(){
		var contents = $.trim(oEditors[0].getContents());
		if(contents === '<p>&nbsp;</p>' || contents === ''){ // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
			alert("내용을 입력하세요.");
			oEditors.getById['smarteditor'].exec('FOCUS');
			return false;
		}

		return true;
	}
</script>
</head>

<body>

	<%@ include file="/common/header.jsp"%>

	<div class="container-fluid">
	
		<!-- 필요한 변수 선언 부분 -->
		<c:set var="att_max" value="5"/> <!-- 첨부파일 최대 개수 -->
		<c:set var="attSize" value="${attList.size()}"/>
		<input type="hidden" id="attSize" value="${attSize}">
		
		<div class="row">
		
			<%@ include file="/common/left.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<form id="frm" action="/postsUpdate" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">
					<input type="hidden" name="bd_id" value="${bd_id}">
					<input type="hidden" id="temp" name="temp">
					<table class="table table-striped" id="tableTag">
						<tr>
							<th>제목</th>
							<td><input type="text" id="ps_title" name="ps_title" value="${postsVO.ps_title}">
								<input type="hidden" name="ps_id" value="${postsVO.ps_id}">
							</td>
						</tr>
						<tr>
							<th>글내용</th>
							<td>
							<!-- =============================================================================== -->
							
<%-- 								<%@ include file="/SE2/index.jsp" %> --%>
							
							<textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width:766px; height:412px;">
								${postsVO.ps_cnt}
							</textarea> 
							
							<!-- =============================================================================== -->
							</td>
						</tr>
						<c:forEach items="${attList}" var="att" varStatus="status">
							<tr id="${status.index}">
								<th>${status.index == 0 ? "첨부파일(기존)" : ""}</th>
								<td>
									<div class="fileListPJ">
										<input type="text" value="${att.att_path}" readonly="readonly">
										<input type="hidden" value="${att.att_id}">
										<input type="button" value="-" class="btn btn-default delFile">
									</div>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<th>첨부파일(추가)</th>
							<td>
								<div class="fileListPJ">
									<input type="file" id="file5" name="file5" size="40px">
									<input type="button" value="+" class="btn btn-default addFile">
								</div>
							</td>
						</tr>
					</table>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" id="savebutton" class="btn btn-default">저장</button>
							<button type="reset" class="btn btn-default">취소</button>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
</body>
</html>

