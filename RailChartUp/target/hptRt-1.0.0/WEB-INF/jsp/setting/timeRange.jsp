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
		console.log("시간/기간범위설정 진입 : ");
		
		//수정 버튼 클릭 시
		$("#btnUpdate").click(function(){
			 $("#content").load("/setting/timeRangeUpdate.do"); 
		});

	});//ready

</script>
</head>
<body>
	<h3>시간/기간 범위 설정</h3>
	<div class="time-wrapper">
		<div class="setTbDiv">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/gowork.png) no-repeat; background-size: contain;"></i>출근</label></td>
					<td>${data.goworkSt} ~ ${data.goworkEd}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/dayTime.png) no-repeat; background-size: contain;"></i>주간</label></td>
					<td>${data.daySt} ~ ${data.dayEd}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/gohome.png) no-repeat; background-size: contain;"></i>퇴근</label></td>
					<td>${data.gohomeSt} ~ ${data.gohomeEd}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/night.png) no-repeat; background-size: contain;"></i>야간</label></td>
					<td>${data.nightSt} ~ ${data.nightEd}</td>
				</tr>
			</table>
		</div>
	
		<div class="setTbDiv">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/spring.png) no-repeat; background-size: contain;"></i>봄</label></td>
					<td>${data.springSt} ~ ${data.springEd} 월</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/summer.png) no-repeat; background-size: contain;"></i>여름</label></td>
					<td>${data.summerSt} ~ ${data.summerEd} 월</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/autumn.png) no-repeat; background-size: contain;"></i>가을</label></td>
					<td>${data.autumnSt} ~ ${data.autumnEd} 월</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/winter.png) no-repeat; background-size: contain;"></i>겨울</label></td>
					<td>${data.winterSt} ~ ${data.winterEd} 월</td>
				</tr>
			</table>
		</div>	
	</div>
	
	<div class="btnDiv" style="float:none;position: relative;top: 0px;left:800px;margin-top: 50px;">
		<button  id="btnUpdate"  class="buttonDf">수정</button>
	</div>
</body>
</html>