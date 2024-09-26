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
	<script type="text/javascript">
	//이메일 체크
	$(document).ready( function() {
		var schChkId=false;
		//console.log("회원가입 페이지");
		
		var form = document.userInsertForm;
		form.userId.focus();
		
		//로고 클릭 시 메인화면
		$("#loginHeader").on("click", function(){
			$("#changeBody").empty();
			$("#changeBody").load("${path}");
		});
		
		//전화번호 선택
		$('#selectPhone').change(function(){ 
			$("#selectPhone option:selected").each(function () { 
				if($(this).val()== '1'){ //직접입력일 경우
					$("#userPhone1").val(''); //값 초기화 
					$("#userPhone1").attr("disabled",false); //활성화 
				}else{ //직접입력이 아닐경우
					$("#userPhone1").val($(this).text()); //선택값 입력 
					$("#userPhone1").attr("disabled",true); //비활성화 
				} 
			}); 
		});
		//이메일 선택
		$('#selectEmail').change(function(){ 
			$("#selectEmail option:selected").each(function () { 
				if($(this).val()== '1'){ //직접입력일 경우
					$("#userEmail2").val(''); //값 초기화 
					$("#userEmail2").attr("disabled",false); //활성화 
				}else{ //직접입력이 아닐경우
					$("#userEmail2").val($(this).text()); //선택값 입력 
					$("#userEmail2").attr("disabled",true); //비활성화 
				} 
			}); 
		});


		$("#userInsertForm").submit( function(event){
			//console.log("회원가입 등록 버튼 클릭");
			event.preventDefault();
			//분리된 email,전화번호 통합
			if($("#userEmail1").val().length>0){
				var email = $("#userEmail1").val()+"@"+$("#userEmail2").val();
				$("#userEmail").val(email);
			}else{
				$("#userEmail").val("");
			}
			
			//유효성 체크
			schChkId=schChkKey($("#searchBtn"),schChkId);
			
			if(boardWriteCheck(form) && telChk() && schChkId){
				
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: "/user/insertUser.ajax",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						//console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							alert("정상적으로 회원가입 되셨습니다");
							$("#content").empty();
							$("#content").load("/user/userList.do");
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
			}
		});
		
		//키워드 조회시
		$("#searchBtn").on("click",function(){
			schChkId=schChkKey(this,schChkId);
		});
		
		//취소
		$("#btnCancel").on("click",function(){
			$("#content").empty();
			$("#content").load("/user/userList.do");
		});
		
		
	});//ready

</script>
</head>
<body>
	<div class="title">
		<h3>사용자 등록</h3>
	</div>
	<form id="userInsertForm" name="userInsertForm" method="post" enctype="multipart/form-data">
		<div class="tbMng-nonbt">
			<table class='tbList-nonbt'>
				<tr>
					<td><label><span class="required"></span>ID</label><p class="rem">영문 또는 숫자 6~10자</p></td>
					<td>
						<div class ="searchBtnDiv">
							<input type="text" id="userId" name="userId" class="form-control"  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" required>
							<div id="searchBtn" name="searchBtn_userId" class="searchBtn"></div>
						</div>
						<p class="valChk" id="spanKey"></p>				
					</td>
				</tr>
				<tr>
					<td><label><span class="required"></span>비밀번호</label><p class="rem">영문 또는 숫자 8~10자</p></td>
					<td>
						<input type="password" id="userPw" name="userPw" class="form-control"  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" required>
						<span class="valChk" id="span1"></span>
					</td>
				</tr>
				<tr>
					<td><label><span class="required"></span>비밀번호 확인</label><p class="rem">영문 또는 숫자 8~10자</p></td>
					<td>
						<input type="password" id="userPw2" name="userPw2" class="form-control"  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" required>
						<span class="valChk" id="span2"></span>
					</td>
				</tr>
				<tr>
					<td><label><span class="required"></span>이름</label></td>
					<td>
						<input type="text" name="userName" class="form-control"  maxlength="20" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" required>
						<span class="valChk" id="span3"></span>
					</td>
				</tr>
				<tr>
					<td><label>직급</label></td>
					<td>
						<input type="text" name="userRank"  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>부서</label></td>
					<td>
						<input type="text" name="userDept"  maxlength="20" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>전화번호</label></td>
					<td>
					<input type="hidden" id ="userPhone" name="userPhone" class="form-control">
						<div class= inputPhone>
							<select style="width:50px;margin-right:10px" name="selectPhone" id="selectPhone">
								<option value="02">02</option><!-- 서울 -->
								<option value="032">032</option><!-- 경기 -->
								<option value="010" selected>010</option>
				                <option value="011">011</option><!-- 경기-->
				                <option value="019">019</option><!-- 부산-->
				                <option value="1">직접입력</option>
							</select>
							<input type="text" id="userPhone1" class="form-control" maxlength="4" onkeydown='onlyNumber(this)' onkeyup='onlyNumber(this)' disabled value="010">
							<p> - </p>
							<input type="text" id="userPhone2" maxlength="4" onkeydown='onlyNumber(this)' onkeyup='onlyNumber(this)' class="form-control">
							<p> - </p>
							<input type="text" id="userPhone3" maxlength="4" onkeydown='onlyNumber(this)' onkeyup='onlyNumber(this)' class="form-control">
						</div>					
					</td>
				</tr>
				<tr>
					<td><label>Email</label></td>
					<td>
						<input type="hidden" id ="userEmail" name="userEmail" class="form-control">
						<div class= inputPhone>
							<input type="text" class="form-control" id="userEmail1" style="width:70px;"  maxlength="20" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);">
							<p> @ </p>
							<input type="text" class="form-control" id="userEmail2" style="width:100px;" maxlength="20" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" disabled value="arex.or.kr">
							<select style="width:100px;margin-right:10px" id="selectEmail">
								<option value="arex.or.kr" selected>arex.or.kr</option>
								<option value="gmail.com">gmail.com</option> 
								<option value="naver.com">naver.com</option> 
								<option value="daum.net">daum.net</option> 
								<option value="nate.com">nate.com</option> 
								<option value="1">직접입력</option> 
							</select>
						</div>					
					</td>
				</tr>
			</table>
		</div>
		<div class="btnDiv" style="float:none;position: relative;top: 0px;left: 350px;margin-top: 50px;">
			<button id="btnSub" class="btn-small">등록</button>
			<button class="btn-small" id="btnCancel">취소</button>
		</div>
	</form>
	
</body>
</html>