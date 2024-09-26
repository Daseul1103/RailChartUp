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

		$("input[type=number]").on("change",function(){
			console.log("일일 혼잡도 범위 조절");
			//현재 변경값 class 숫자로 치환
			var tnt = Number($(this).attr('class'));
			var bst=tnt-1;//현재 범위의 시작점
			var nst=tnt+1;//다음 범위의 시작점
			var ned=tnt+2;//다음 범위의 끝점
			
			var bstVal = Number($(".inputSet").find("."+bst).val());
			var tVal = Number($(this).val());
			var nstVal = Number($(".inputSet").find("."+nst).val());
			var nedVal = Number($(".inputSet").find("."+ned).val());
			
			//비 유효한 값 입력 (현재 값이 다음 끝 범위값보다 큰 경우)
			if(tVal >= nedVal || tVal < bstVal){
				$(this).val(Number($(".inputSet").find("."+nst).val())-1);
			}else{
				//nedVal-nstVal 값이 0보다 작거나 같을경우
				if(nedVal-nstVal <= 0){
					//현재값을 다음 값의 -1
					//$(".inputSet").find("."+nst).val($(".inputSet").find("."+ned).val());
					if(nstVal - tVal >= 1){
						$(".inputSet").find("."+nst).val(Number($(this).val())+1);
					}else{
						$(this).val(Number($(".inputSet").find("."+nst).val())-1);
					}
				}else{
					//다음 값을 현재 값의 +1
					$(".inputSet").find("."+nst).val(Number($(this).val())+1);
				}				
			}
		});
		
		var form = document.seaCrowdRangeUpdateForm;
		
		$("#seaCrowdRangeUpdateForm").submit( function(event){
			console.log("차상단말기 수정 버튼 클릭");
			event.preventDefault();
			//유효성 체크
			if(boardWriteCheck(form)){
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: "/setting/seaCrowdRangeUpdate.ajax",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#content").load("/setting/seaCrowdRange.do");
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
			$("#content").load("/setting/seaCrowdRange.do");
		});

	});//ready

</script>
</head>
<body>
	<h3>일일 혼잡도 범위 설정 (단위 : KPA)</h3>
	<form id="seaCrowdRangeUpdateForm" name="seaCrowdRangeUpdateForm" method="post" enctype="multipart/form-data">
		<div class="setTbDiv">
			<table class='setTbList-nonbt'>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/relax.png) no-repeat; background-size: contain;"></i>여유</label></td>
					<td>
					<%-- ${data.seaWgtRlxSt} ~ ${data.seaWgtRlxEd} --%>
						<div class= inputSet>
							<input type="text" min="50" max="560" style="border:none; text-align:center" id ="seaWgtRlxSt" name="seaWgtRlxSt" value=${data.seaWgtRlxSt} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="0" readonly>
							<p> ~ </p>
							<input type="number" min="50" max="560" id ="seaWgtRlxEd" name="seaWgtRlxEd" value=${data.seaWgtRlxEd} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="1" required>
						</div>
					</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/usually.png) no-repeat; background-size: contain;"></i>보통</label></td>
					<td>
					<%-- ${data.seaWgtUsSt} ~ ${data.seaWgtUsEd} --%>
						<div class= inputSet>
							<input type="text" min="50" max="560" style="border:none; text-align:center" id ="seaWgtUsSt" name="seaWgtUsSt" value=${data.seaWgtUsSt} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="2" readonly>
							<p> ~ </p>
							<input type="number" min="50" max="560" id ="seaWgtUsEd" name="seaWgtUsEd" value=${data.seaWgtUsEd} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="3" required>
						</div>
					</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/care.png) no-repeat; background-size: contain;  margin-top: 15px;"></i>주의</label></td>
					<td>
					<%-- ${data.seaWgtCauSt} ~ ${data.seaWgtCauEd} --%>
						<div class= inputSet>
							<input type="text" min="50" max="560" style="border:none; text-align:center" id ="seaWgtCauSt" name="seaWgtCauSt" value=${data.seaWgtCauSt} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="4" readonly>
							<p> ~ </p>
							<input type="number" min="50" max="560" id ="seaWgtCauEd" name="seaWgtCauEd" value=${data.seaWgtCauEd} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="5" required>
						</div>
					</td>
				</tr>
				<tr>
					<td><label><i class="setIconImg" style="background: url(../images/crowd.png) no-repeat; background-size: contain;"></i>혼잡</label></td>
					<td>
					<%-- ${data.seaWgtCwdSt} ~ ${data.seaWgtCwdEd} --%>
						<div class= inputSet>
							<input type="text" min="50" max="560" style="border:none; text-align:center" id ="seaWgtCwdSt" name="seaWgtCwdSt" value=${data.seaWgtCwdSt} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="6" readonly>
							<p> ~ </p>
							<input type="text" min="50" max="560" style="border:none; text-align:center" id ="seaWgtCwdEd" name="seaWgtCwdEd" value=${data.seaWgtCwdEd} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="7" readonly>
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
					<%-- ${data.seaWgtMargin} --%>
						<div class= inputSet>
							<input type="number" min="0" max="1000" id ="seaWgtMargin" name="seaWgtMargin" value=${data.seaWgtMargin} maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
						</div>
					</td>
				</tr>
			</table>
		</div>
	
		<div class="btnDiv" style="float:none;position: relative;top: 0px;left:400px;margin-top: 50px;">
			<button id="btnSub" class="btn-small">수정</button>
			<button class="btn-small" id="btnCancel">취소</button>
		</div>
	</form>
</body>
</html>