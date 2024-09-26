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
		var tagId='${data.evtTime}';
		//console.log("상세정보 진입 : "+tagId);
		//목록 버튼 클릭 시
		$("#btnList").click(function(){
			$("#content").load("/eventLog/eventLogDetail.do"); 
		});

	});//ready

</script>
</head>
<body>
<!-- APP 설명 및 다운로드 -->
	<h3>사용자 상세정보</h3>
	<div class="tbMng-nonbt">
		<table class='tbList-nonbt'>
			<tr>
				<td>발생일시</td>
				<td class="td-detail">${data.evtTime}</td>
			</tr>
			<tr>
				<td>발생대상</td>
				<td class="td-detail">${data.evtName}</td>
			</tr>
			<tr>
				<td>이벤트타입</td>
				<td class="td-detail">${data.evtNum}</td>
			</tr>
			<tr>
				<td>발생항목명</td>
				<td class="td-detail">${data.evtTgt}</td>
			</tr>
			<tr>
				<td>비고(변경 항목)</td>
				<td class="td-detail">
				<textarea rows="10" cols="50" readonly>${data.comment}</textarea>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="btnDiv" style="float:none;position: relative;top: 0px;left:200px;margin-top: 50px;">
		<button  id="btnList"  class="btn-small">목록</button>
	</div>
</body>
</html>