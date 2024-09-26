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
		//console.log("시간/기간범위설정 진입 : ");
		
		//수정 버튼 클릭 시
		$("#btnUpdate").click(function(){
			 $("#content").load("/setting/yearCrowdRangeUpdate.do"); 
		});

	});//ready

</script>
</head>
<body>
	<h3>연도별 혼잡도 변위 설정 (단위 : KPA)</h3>
		<div class="setTbDiv">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/relax.png) no-repeat; background-size: contain;"></i>여유</label></td>
					<td>${data.yearWgtRlxDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/usually.png) no-repeat; background-size: contain;"></i>보통</label></td>
					<td>${data.yearWgtUsDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/care.png) no-repeat; background-size: contain;  margin-top: 15px;"></i>주의</label></td>
					<td>${data.yearWgtCauDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/crowd.png) no-repeat; background-size: contain;"></i>혼잡</label></td>
					<td>${data.yearWgtCwdDv}</td>
				</tr>
				<tr>
					<td>
						<label style="font-size: 15px; line-height: 17px; text-align: center;">
							<i class="setIconImg" style="background: url(../images/kg.png) no-repeat; background-size: contain; margin-top: 15px;"></i>
							<p style="line-height: 1.7; text-align: center;">오차<br>허용범위<br>(KG)</p>
						</label>
					</td>
					<td>${data.yearWgtMargin}</td>
				</tr>
			</table>
		</div>
	
	<div class="btnDiv" style="float:none;position: relative;top: 0px;left:400px;margin-top: 50px;">
		<button  id="btnUpdate"  class="buttonDf">수정</button>
	</div>
</body>
</html>