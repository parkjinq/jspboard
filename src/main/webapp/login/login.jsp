<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
<!-- 	<meta name="viewport" content="width=device-width, initial-scale=1"> -->
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<!--     <meta name="description" content=""> -->
<!--     <meta name="author" content=""> -->

<!-- 왜안됨 파비콘 ㅠㅠ -->
<link rel="icon" href="/css/favicon.ico">

<title>login.jsp</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
 	
 <script type="text/javascript">
 	function getCookie(cookieName) {
 		var cookieStr = document.cookie
 		var cookieList = cookieStr.split("; ");
		var resultStr = null;
		
		for(var i = 0; i < cookieList.length; i++){
			if(cookieList[i].startsWith(cookieName + "=")){
				resultStr = cookieList[i].substring((cookieName + "=").length);
				break;
			}
		}
		
		return resultStr;
	}
 	
 	$('document').ready(function() {
 		console.log("test");
		 	var userId = getCookie("userId");
	 	if(getCookie("remember") == "Y"){
		 	$("#userId").val(userId);
		 	$("#remember-me").attr("checked", true);	//						  checked/unchecked
		 	$("#remember-me").prop("checked", true);	//attr이랑 조금 다름 구글링 해봐 , true/false
	 	} else {
		 	$("#remember-me").attr("checked", false);
	 	}
	});
 	
 </script>
 
  </head>

  <body>

    <div class="container">

      <form class="form-signin" method="post" action="/login">
        <h1 class="form-signin-heading" style="text-align: center;">Login :♭</h1>
        <label for="inputEmail" class="sr-only">Id address</label>
        <input type="text" id="userId" class="form-control" placeholder="아이디" required autofocus name="user_id">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="비밀번호" required name="user_pw">
        <div class="checkbox">
          <label>
            <input type="checkbox" id="remember-me" name="remember-me" value="remember-me" > Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>










<!-- </head> -->
<!-- <body> -->
<%-- 	<%-- 	브라우저 주소줄에 입력한 경우 : get --%>
<%-- 			form 캐그의 method 속성 : get/post--%> 
<!-- 	<form method="get" action="/dditLogin"> -->
<!-- 	GET방식(servlet) -->
<!-- 		<br />User ID : <input type="text" name="user_id" value="brown"> -->
<!-- <!-- 		<br />User ID : <input type="text" name="user_id" value="샐리"> --> 
<!-- 		<br />User PW : <input type="password" name="user_pw" value="pass1234"> -->
<!-- 		<br /><input type="submit" value="로그인"> -->
<!-- 	</form> -->
	
<!-- 	<br /> -->
	
<!-- 	<form method="post" action="/dditLogin"> -->
<!-- 	POST방식(servlet) -->
<!-- 		<br />User ID : <input type="text" name="user_id" value="brown"> -->
<!-- 		<br />User PW : <input type="password" name="user_pw" value="pass1234"> -->
<!-- 		<br /><input type="submit" value="로그인"> -->
<!-- 	</form> -->
	
	
	
<%-- <br /><form method="post" action="/login/loginProcess.jsp">
	POST방식
		<br />User ID : <input type="text" name="user_id" value="jin_id1">
		<br />User ID : <input type="text" name="user_id" value="jin_id2">
		<br />User ID : <input type="text" name="user_id" value="jin_id3">
		<br />User PW : <input type="password" name="user_pw" value="jin_pw2">
		<br /><input type="submit" value="로그인">
	</form>--%>
<!-- </body> -->
<!-- </html> -->