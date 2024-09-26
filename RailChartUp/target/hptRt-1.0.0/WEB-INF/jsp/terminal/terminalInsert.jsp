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
		console.log("차상단말기 등록 페이지");
		
		var form = document.terminalInsertForm;
		form.trainNum.focus();
		
		$("#terminalInsertForm").submit( function(event){
			console.log("차상단말기 등록 버튼 클릭");
			event.preventDefault();
			
			//분리된 email,전화번호 통합
			var fip = $("#fIp1").val()+"."+$("#fIp2").val()+"."+$("#fIp3").val()+"."+$("#fIp4").val();
			$("#fIp").val(fip);
			var tIp = $("#tIp1").val()+"."+$("#tIp2").val()+"."+$("#tIp3").val()+"."+$("#tIp4").val();
			$("#tIp").val(tIp);
			
			//유효성 체크
			if(boardWriteCheck(form)){
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: "/terminal/terminalInsert.ajax",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#content").load("/terminal/terminalList.do");
						}else{
							alert(json.msg);
						}
					},
					error : function() {
						console.log("에러가 발생하였습니다."+json.msg);
					},
					//finally 기능 수행
					complete : function() {
						console.log("파이널리.");
					}
				});
			}
		});
		
		//취소
		$("#btnCancel").on("click",function(){
			$("#content").empty();
			$("#content").load("/terminal/terminalList.do");
		});
		
		
	});//ready

</script>
</head>
<body>
	<div class="title">
		<h3>차상단말기 등록</h3>
	</div>
	<form id="terminalInsertForm" name="terminalInsertForm" method="post" enctype="multipart/form-data">
		<div class="tbMng-nonbt">
			<table class='tbList-nonbt'>
				<tr>
					<td><label><span class="required"></span>편성번호</label></td>
					<td>
						<input type="text" id="trainNum" name="trainNum" maxlength="4" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
						<span class="valChk" id="span0"></span>
					</td>
				</tr>
				<!-- 전 후열 ip 주석 처리 -->
				<!-- <tr>
					<td><label><span class="required"></span>전열 IP</label></td>
					<td>
					<input type="hidden" id ="fIp" name="fIp" class="form-control">
						<div class= inputIp>
							<input type="number" min="0" max="255"  id="fIp1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							<p> . </p>
							<input type="number" min="0" max="255"  id="fIp2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							<p> . </p>
							<input type="number" min="0" max="255"  id="fIp3" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							<p> . </p>
							<input type="number" min="0" max="255"  id="fIp4" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
						</div>					
					</td>
				</tr>
				<tr>
					<td><label><span class="required"></span>후열 IP</label></td>
					<td>
					<input type="hidden" id ="tIp" name="tIp" class="form-control">
						<div class= inputIp>
							<input type="number" class="form-control" min="0" max="255"  id="tIp1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							<p> . </p>
							<input type="number" class="form-control" min="0" max="255"  id="tIp2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							<p> . </p>
							<input type="number" class="form-control" min="0" max="255"  id="tIp3" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							<p> . </p>
							<input type="number" class="form-control" min="0" max="255"  id="tIp4" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
						</div>					
					</td>
				</tr> -->
				<tr>
					<td><label><span class="required"></span>량 수</label></td>
					<td>
						<select style="width:100px; height:20px;" name="carCnt" id="carCnt">
							<option value="6" selected>6</option>
							<option value="8">8</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label><span class="required"></span>운행여부</label></td>
					<td>
						<select style="width:100px; height:20px;" name="raceYn" id="raceYn">
							<option value="Y" selected>운행중</option>
							<option value="N">미운행</option>
						</select>
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