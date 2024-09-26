<%--
	Class Name : mainContents.jsp
	Description : 메인화면 내용
	Modification Information
	수정일         	수정자        수정내용
	----------  ------ ---------------------------
	2021.03.09     정다빈	최초 생성
	author : 미래전략사업팀 정다빈
	since : 2021.03.09
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<script type="text/javascript">
	//이메일 체크
	$(document).ready( function() {
		var schChkId=false;
		//console.log("차상단말기 수정 페이지");
		
		var form = document.terminalUpdateForm;
		
		$("#terminalUpdateForm").submit( function(event){
			//console.log("차상단말기 수정 버튼 클릭");
			event.preventDefault();
			/* 
			if($("#carType").val()=="직통열차"){
				$("#carType").val()==1
			}else{
				$("#carType").val()==2
			} */
			
			//유효성 체크
			if(boardWriteCheck(form)){
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: "/terminal/directRegularStdUpdate/"+loginId+".ajax",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						//console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#content").load("/terminal/directRegularStd.do");
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
		
		//취소
		$("#btnCancel").on("click",function(){
			$("#content").empty();
			$("#content").load("/terminal/directRegularStd.do");
		});
		
		
	});//ready

</script>
</head>
<body>
	<div class="title">
		<h3>직통/일반열차 기준값 수정</h3>
	</div>
	<form id="terminalUpdateForm" name="terminalUpdateForm" method="post" enctype="multipart/form-data">
		<div class="tbMng-nonbt">
			<table class='tbList-nonbt'>
				<tr>
					<td><label>열차 타입</label></td>
					<td>
						<input type="text" id="carType" name="carType" class="form-control" value=${data.carType} maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" readonly>
					</td>
				</tr>
				<tr>
					<td><label>1호차</label></td>
					<td>
						<input type="text" name="st_wgt_1"  value=${data.st_wgt_1}  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>2호차</label></td>
					<td>
						<input type="text" name="st_wgt_2"  value=${data.st_wgt_2}  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>3호차</label></td>
					<td>
						<input type="text" name="st_wgt_3"  value=${data.st_wgt_3}  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>4호차</label></td>
					<td>
						<input type="text" name="st_wgt_4"  value=${data.st_wgt_4}  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>7호차</label></td>
					<td>
						<input type="text" name="st_wgt_5"  value=${data.st_wgt_5}  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
				<tr>
					<td><label>8호차</label></td>
					<td>
						<input type="text" name="st_wgt_6"  value=${data.st_wgt_6}  maxlength="10" onkeyup="spaceChk(this);" onkeydown="spaceChk(this);" class="form-control">					
					</td>
				</tr>
			</table>
		</div>
		<div class="btnDiv" style="float:none;position: relative;top: 0px;left: 350px;margin-top: 50px;">
			<button id="btnSub" class="btn-small">변경</button>
			<button class="btn-small" id="btnCancel">취소</button>
		</div>
	</form>
	
</body>
</html>