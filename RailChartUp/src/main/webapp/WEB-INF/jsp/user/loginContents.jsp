<%--
	Class Name : mainContents.jsp
	Description : 메인화면 내용
	Modification Information
	수정일         	수정자        수정내용
	----------  ------ ---------------------------
	2020.07.30     정다빈	최초 생성
	author : 미래전략사업팀 정다빈
	since : 2020.07.30 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<script>
	$(document).ready(function() {
		//console.log("로그인 컨텐츠");
		
		$("#loginForm").submit( function(event){
			event.preventDefault();
			// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
			let queryString = $(this).serialize();
			//id,pw,전화번호 체크
			//if(boardWriteCheck(form)){
				$.ajax({
					url: "/user/loginPost.do",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						//console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#changeBody").load("/main/main.do");
						}else{
							alert(json.msg);
						}
					},
					error : function() {
						//console.log("에러가 발생하였습니다."+json.msg);
					},
					//finally 기능 수행
					complete : function() {
						//console.log("파이널리.");
					}
				});
			//}
		});//btnSub
		
		// 회원가입
		$("#btnJoin").on("click",function(){
			$("#loginContainer").empty();
			$("#loginContainer").load("/user/userJoin.do");
		});
	});
</script>

<div class="login-title-wrapper">
	<div class="login-title"></div>
</div>
<div id="login-form-div2" style="display: flex;justify-content: center;">
	<form id="loginForm" name="loginForm" method="post" enctype="multipart/form-data">
		<table id ="loginTable">
			<tr>
				<td>ID</td>
				<td><input type="text" id="userId" name="userId" class="form-control"  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);"  required></td>
			</tr>
			<tr>
				<td>PASSWORD</td>
				<td><input type="password" id="userPw" name="userPw" class="form-control" maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);"  required></td>
			</tr>
		</table>
		<div class="btnDiv">
			<button id="btnSub" class="buttonDf">로그인</button>
			<button class="buttonDf" id="btnJoin">회원가입</button>
		</div>
	</form>
</div>

</html>