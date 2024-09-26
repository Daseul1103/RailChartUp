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

		$("input[type=number]").on("change",function(){
			console.log("월별 혼잡도 범위 조절");
			var tVal = Number($(this).val());
			if(tVal>=560||tVal<=0){
				alert("혼잡도 변위값은 0보다 크고 560보다 작아야 합니다");
				$(this).val("");
			}else{
				$(this).val(tVal);
			}
		});
		
		var form = document.seaCrowdRangeUpdateForm;
		
		$("#seaCrowdRangeUpdateForm").submit( function(event){
			var conUrl;
			//console.log("일일혼잡도 범위 수정 버튼 클릭");
			event.preventDefault();
			//submit버튼이 여러개일경우 식별할 수 있게 해줌
			var keyButton = event.originalEvent.submitter.id;
			
			if(keyButton=="btnSubAll"){//전 범위 변경
				conUrl="/setting/seaCrowdRangeUpdate/all_"+loginId+".ajax";
			}else{//현재 화면만 변경
				conUrl="/setting/seaCrowdRangeUpdate/normal_"+loginId+".ajax";
			}
			//유효성 체크
			if(boardWriteCheck(form)){
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: conUrl,	
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						//console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#content").load("/setting/seaCrowdRange.do");
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
			$("#content").load("/setting/seaCrowdRange.do");
		});

	});//ready

</script>
</head>
<body>
	<h3>계절별 혼잡도 변위 설정 (단위 : KPA)</h3>
	<form id="seaCrowdRangeUpdateForm" name="seaCrowdRangeUpdateForm" method="post" enctype="multipart/form-data">
		<div class="setTbDiv">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/relax.png) no-repeat; background-size: contain;"></i>여유</label></td>
					<td>
						<div class= inputSet>
							<input type="number" min="0" max="560" id ="seaWgtRlxDv" name="seaWgtRlxDv" value=${data.seaWgtRlxDv} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="1" required>
						</div>
					</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/usually.png) no-repeat; background-size: contain;"></i>보통</label></td>
					<td>
						<div class= inputSet>
							<input type="number" min="0" max="560" id ="seaWgtUsDv" name="seaWgtUsDv" value=${data.seaWgtUsDv} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="3" required>
						</div>
					</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/care.png) no-repeat; background-size: contain;  margin-top: 15px;"></i>주의</label></td>
					<td>
						<div class= inputSet>
							<input type="number" min="0" max="560" id ="seaWgtCauDv" name="seaWgtCauDv" value=${data.seaWgtCauDv} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="5" required>
						</div>
					</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/crowd.png) no-repeat; background-size: contain;"></i>혼잡</label></td>
					<td>
						<div class= inputSet>
							<input type="number" min="0" max="560" id ="seaWgtCwdDv" name="seaWgtCwdDv" value=${data.seaWgtCwdDv} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="5" required>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<label style="font-size: 15px; line-height: 17px; text-align: center;">
							<i class="setIconImg" style="background: url(../images/kg.png) no-repeat; background-size: contain; margin-top: 15px;"></i>
							<p style="line-height: 1.7; text-align: center;">오차<br>허용범위<br>(KG)</p>
						</label>
					</td>
					<td>
						<div class= inputSet>
							<input type="number" min="0" max="1000" id ="seaWgtMargin" name="seaWgtMargin" value=${data.seaWgtMargin} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
						</div>
					</td>
				</tr>
			</table>
		</div>
	
		<div class="btnDiv" style="float:none;position: relative;top: 0px; margin-top: 50px; left: 70px;">
			<button id="btnSubAll" class="buttonDf" style="font-size:12px;">전체일괄변경</button>
			<button id="btnSub" class="buttonDf">변경</button>
			<button class="buttonDf" id="btnCancel">취소</button>
		</div>
	</form>
</body>
</html>