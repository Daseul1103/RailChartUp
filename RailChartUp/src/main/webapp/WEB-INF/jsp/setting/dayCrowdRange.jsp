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
		trVal="gw";
		$("#gh").hide();
		$("#dayNgt").hide();
		
		//수정 버튼 클릭 시
		$("#btnUpdate").click(function(){
			 $("#content").load("/setting/dayCrowdRangeUpdate.do"); 
		});

		//시간범위 수정 셀렉트박스 클릭 시
		$("#timeRangeSB").on("change",function(){
			trVal = $(this).val();
			//console.log("셀렉트박스 선택값 : "+trVal);
			$(".setTbDiv").each(function(i,list){
				//console.log($(this).attr("id"));
				if($(this).attr("id")==trVal){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		});
		
	});//ready

</script>
</head>
<body>
	<h3>일일 혼잡도 변위 설정 (단위 : KPA)</h3>
	<div class="st-range">
		<p>시간범위</p>
		<select id="timeRangeSB">
			<option value="gw" selected>출근</option>
			<option value="gh">퇴근</option>
			<option value="dayNgt">일반</option>
		</select>
	</div>
	
	<div id="sbAll">
		<div class="setTbDiv" id="gw">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/relax.png) no-repeat; background-size: contain;"></i>여유</label></td>
					<td>${data.gwWgtRlxDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/usually.png) no-repeat; background-size: contain;"></i>보통</label></td>
					<td>${data.gwWgtUsDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/care.png) no-repeat; background-size: contain;  margin-top: 15px;"></i>주의</label></td>
					<td>${data.gwWgtCauDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/crowd.png) no-repeat; background-size: contain;"></i>혼잡</label></td>
					<td>${data.gwWgtCwdDv}</td>
				</tr>
				<tr>
					<td>
						<label style="font-size: 15px; line-height: 17px; text-align: center;">
							<i class="setIconImg" style="background: url(../images/kg.png) no-repeat; background-size: contain; margin-top: 15px;"></i>
							<p style="line-height: 1.7; text-align: center;">오차<br>허용범위<br>(KG)</p>
						</label>
					</td>
					<td>${data.gwWgtMargin}</td>
				</tr>
			</table>
		</div>
		
		<div class="setTbDiv" id="gh">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/relax.png) no-repeat; background-size: contain;"></i>여유</label></td>
					<td>${data.ghWgtRlxDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/usually.png) no-repeat; background-size: contain;"></i>보통</label></td>
					<td>${data.ghWgtUsDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/care.png) no-repeat; background-size: contain;  margin-top: 15px;"></i>주의</label></td>
					<td>${data.ghWgtCauDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/crowd.png) no-repeat; background-size: contain;"></i>혼잡</label></td>
					<td>${data.ghWgtCwdDv}</td>
				</tr>
				<tr>
					<td>
						<label style="font-size: 15px; line-height: 17px; text-align: center;">
							<i class="setIconImg" style="background: url(../images/kg.png) no-repeat; background-size: contain; margin-top: 15px;"></i>
							<p style="line-height: 1.7; text-align: center;">오차<br>허용범위<br>(KG)</p>
						</label>
					</td>
					<td>${data.ghWgtMargin}</td>
				</tr>
			</table>
		</div>
		
		<div class="setTbDiv" id="dayNgt">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/relax.png) no-repeat; background-size: contain;"></i>여유</label></td>
					<td>${data.dayWgtRlxDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/usually.png) no-repeat; background-size: contain;"></i>보통</label></td>
					<td>${data.dayWgtUsDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/care.png) no-repeat; background-size: contain;  margin-top: 15px;"></i>주의</label></td>
					<td>${data.dayWgtCauDv}</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/crowd.png) no-repeat; background-size: contain;"></i>혼잡</label></td>
					<td>${data.dayWgtCwdDv}</td>
				</tr>
				<tr>
					<td>
						<label style="font-size: 15px; line-height: 17px; text-align: center;">
							<i class="setIconImg" style="background: url(../images/kg.png) no-repeat; background-size: contain; margin-top: 15px;"></i>
							<p style="line-height: 1.7; text-align: center;">오차<br>허용범위<br>(KG)</p>
						</label>
					</td>
					<td>${data.dayWgtMargin}</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="btnDiv" style="float:none;position: relative;top: 0px;left:400px;margin-top: 50px;">
		<button  id="btnUpdate"  class="buttonDf">수정</button>
	</div>
</body>
</html>