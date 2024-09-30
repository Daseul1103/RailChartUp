<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>

<script type="text/javascript">

// 커밋확인용 주석

	$(document).ready(function() {
		//console.log("로그인 화 면");
		$("#loginContainer").load("/user/loginContents.do");
		//로고 클릭 시 메인화면
		$("#loginHeader").on("click", function(){
			$("#changeBody").empty();
			$("#changeBody").load("${path}");
		});
	});
</script>

	<!-- end #menu -->
	<!-- end #content -->
	<div class="login-all-wrapper">
		<div id="loginImg"></div>
	
		<!-- contents -->
		<div id="loginDiv">
			<div id="login-header-wrapper">
				<div id="loginHeader"></div>
			</div>
			<div class="login-title-container" id="loginContainer">
				
			</div>
		</div>
	</div>
</html>