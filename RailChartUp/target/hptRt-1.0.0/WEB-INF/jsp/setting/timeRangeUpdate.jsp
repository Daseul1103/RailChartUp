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
		console.log("시간/기간범위설정 수정 진입 : ");
		
		var form = document.timeRangeUpdateForm;
		/* 받아온 값 분할 */
		/* 출근 */
		var tagGoworkSt='${data.goworkSt}';
		$("#goworkSt1").val(tagGoworkSt.split(':')[0]);
		$("#goworkSt2").val(tagGoworkSt.split(':')[1]);
		var tagGoworkEd='${data.goworkEd}';
		$("#goworkEd1").val(tagGoworkEd.split(':')[0]);
		$("#goworkEd2").val(tagGoworkEd.split(':')[1]);
		
		/* 주간 */
		var tagDaySt='${data.daySt}';
		$("#daySt1").val(tagDaySt.split(':')[0]);
		$("#daySt2").val(tagDaySt.split(':')[1]);
		var tagDayEd='${data.dayEd}';
		$("#dayEd1").val(tagDayEd.split(':')[0]);
		$("#dayEd2").val(tagDayEd.split(':')[1]);
		
		/* 퇴근 */
		var tagGohomeSt='${data.gohomeSt}';
		$("#gohomeSt1").val(tagGohomeSt.split(':')[0]);
		$("#gohomeSt2").val(tagGohomeSt.split(':')[1]);
		var tagGohomeEd='${data.gohomeEd}';
		$("#gohomeEd1").val(tagGohomeEd.split(':')[0]);
		$("#gohomeEd2").val(tagGohomeEd.split(':')[1]);

		/* 야간 */
		var tagNightSt='${data.nightSt}';
		$("#nightSt1").val(tagNightSt.split(':')[0]);
		$("#nightSt2").val(tagNightSt.split(':')[1]);
		var tagNightEd='${data.nightEd}';
		$("#nightEd1").val(tagNightEd.split(':')[0]);
		$("#nightEd2").val(tagNightEd.split(':')[1]);
		
		/* 월 범위 조절 */
		$("input[type=number]").on("change",function(){
			console.log("월 범위 조절");
			var nst;//다음 계절의 시작점
			var ned;//다음 계절의 끝점
			var mm;
			//변경구간의 마지막 지점일 경우(겨울 ed)
			if($(this).attr('class')==7){
				nst=0;
				ned=1;
				/* mm=moment("3").format("MM");
				console.log(mm); */
				nstVal = $(".inputIp").find("."+nst).val();
				nedVal = $(".inputIp").find("."+ned).val();
				
				mnv = moment(nstVal).format("MM").add(1,'months');
				mnnv = moment(nedVal).format("MM").add(1,'months');
				
				console.log(mnv);
				console.log(mnnv);
				
				
				//nnv-nv 값이 0보다 작거나 같을경우
				if($(".inputIp").find("."+nnv).val() - $(".inputIp").find("."+nv).val() <= 0){
					//다음 값의 -1
					$(this).val($(".inputIp").find("."+nv).val()-1);
				}else{
					//월이 12이상 입력 시 다음 시작항목 값보다 -1
					if($(this).val()>12){
						//다음 값의 -1
						$(this).val($(".inputIp").find("."+nv).val()-1);
					}
				}
				
			}else{
				//현재 변경값 class 숫자로 치환
				var tnt = Number($(this).attr('class'));
				nst=tnt+1;
				ned=tnt+2;
				/* mm=moment("3").format("MM");
				console.log(mm); */
				nstVal = $(".inputIp").find("."+nst).val();
				nedVal = $(".inputIp").find("."+ned).val();
				
				mnv = moment(nstVal).format("MM").add(1,'months');
				mnnv = moment(nedVal).format("MM").add(1,'months');
				
				console.log(mnv);
				console.log(mnnv);
				
				
				//다음 항목보다 수정값이 크거나 같을 경우
				if($(this).val() >= $(".inputIp").find("."+nv).val()){
					//다음 값의 -1
					$(this).val($(".inputIp").find("."+nv).val()-1);
				}else{
					//월이 12이상 입력 시 다음 시작항목 값보다 -1
					if($(this).val()>12){
						//다음 값의 -1
						$(this).val($(".inputIp").find("."+nv).val()-1);
					}
				}
			}
			console.log("변경된 항목 : "+$(this).attr('class'));
			console.log("변경된 값 : "+$(this).val());
			console.log(Number($(this).attr('class'))+1);
		});
		
		
		$("#timeRangeUpdateForm").submit( function(event){
			console.log("차상단말기 수정 버튼 클릭");
			event.preventDefault();
			
			//분리된 항목 통합
			/* 출근 */
			var goworkSt = $("#goworkSt1").val()+":"+$("#goworkSt2").val();
			$("#goworkSt").val(goworkSt);
			var goworkEd = $("#goworkEd1").val()+":"+$("#goworkEd2").val()
			$("#goworkEd").val(goworkEd);
			/* 주간 */
			var daySt = $("#daySt1").val()+":"+$("#daySt2").val();
			$("#daySt").val(daySt);
			var dayEd = $("#dayEd1").val()+":"+$("#dayEd2").val()
			$("#dayEd").val(dayEd);
			/* 퇴근 */
			var gohomeSt = $("#gohomeSt1").val()+":"+$("#gohomeSt2").val();
			$("#gohomeSt").val(gohomeSt);
			var gohomeEd = $("#gohomeEd1").val()+":"+$("#gohomeEd2").val()
			$("#gohomeEd").val(gohomeEd);
			/* 야간 */
			var nightSt = $("#nightSt1").val()+":"+$("#nightSt2").val();
			$("#nightSt").val(nightSt);
			var nightEd = $("#nightEd1").val()+":"+$("#nightEd2").val()
			$("#nightEd").val(nightEd);
			
			//유효성 체크
			if(boardWriteCheck(form)){
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: "/setting/timeRangeUpdate.ajax",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#content").load("/setting/timeRange.do");
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
			$("#content").load("/setting/timeRange.do");
		});
	});//ready

</script>
</head>
<body>
	<h3>시간/기간 범위 설정 수정페이지</h3>
	<form id="timeRangeUpdateForm" name="timeRangeUpdateForm" method="post" enctype="multipart/form-data">
		<div class="time-wrapper">
			<div class="setTbDiv">
				<table class='setTbList-nonbt'>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/gowork.png) no-repeat; background-size: contain;"></i>출근</label></td>
						<td>
						<%-- ${data.goworkSt} ~ ${data.goworkEd} --%>
						<input type="hidden" id ="goworkSt" name="goworkSt" class="form-control">
						<input type="hidden" id ="goworkEd" name="goworkEd" class="form-control">
							<div class= inputIp>
								<input type="number" min="0" max="23"  id="goworkSt1" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="goworkSt2" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> ~ </p>
								<input type="number" min="0" max="23"  id="goworkEd1" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="goworkEd2" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/dayTime.png) no-repeat; background-size: contain;"></i>주간</label></td>
						<td>
							<%-- ${data.daySt} ~ ${data.dayEd} --%>
							<input type="hidden" id ="daySt" name="daySt" class="form-control">
							<input type="hidden" id ="dayEd" name="dayEd" class="form-control">
							<div class= inputIp>
								<input type="number" min="0" max="23"  id="daySt1" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="daySt2" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> ~ </p>
								<input type="number" min="0" max="23"  id="dayEd1" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="dayEd2" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/gohome.png) no-repeat; background-size: contain;"></i>퇴근</label></td>
						<td>
						<%-- ${data.gohomeSt} ~ ${data.gohomeEd} --%>
							<input type="hidden" id ="gohomeSt" name="gohomeSt" class="form-control">
							<input type="hidden" id ="gohomeEd" name="gohomeEd" class="form-control">
							<div class= inputIp>
								<input type="number" min="0" max="23"  id="gohomeSt1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="gohomeSt2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> ~ </p>
								<input type="number" min="0" max="23"  id="gohomeEd1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="gohomeEd2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/night.png) no-repeat; background-size: contain;"></i>야간</label></td>
						<td>
						<%-- ${data.nightSt} ~ ${data.nightEd} --%>
							<input type="hidden" id ="nightSt" name="nightSt" class="form-control">
							<input type="hidden" id ="nightEd" name="nightEd" class="form-control">
							<div class= inputIp>
								<input type="number" min="0" max="23"  id="nightSt1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="nightSt2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> ~ </p>
								<input type="number" min="0" max="23"  id="nightEd1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="nightEd2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="form-control" required>
							</div>
						</td>
					</tr>
				</table>
			</div>
		
			<div class="setTbDiv">
				<table class='setTbList-nonbt' id ='ssRTb'>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/spring.png) no-repeat; background-size: contain;"></i>봄</label></td>
						<td>
						<%-- ${data.springSt} ~ ${data.springEd} 월 --%>
							<div class= inputIp>
								<input type="text" id ="springSt" name="springSt" value=${data.springSt} maxlength="2" style="border:none; text-align:center" class="0" readonly>
								<p> ~ </p>
								<input type="number" min="1" max="12" id ="springEd" name="springEd" value=${data.springEd} maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="1" required>
								<p> 월 </p>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/summer.png) no-repeat; background-size: contain;"></i>여름</label></td>
						<td>
						<%-- ${data.summerSt} ~ ${data.summerEd} 월 --%>
							<div class= inputIp>
								<input type="text" id ="summerSt" name="summerSt" value=${data.summerSt} maxlength="2" style="border:none; text-align:center" class="2" readonly>
								<p> ~ </p>
								<input type="number" min="1" max="12" id ="summerEd" name="summerEd" value=${data.summerEd} maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="3" required>
								<p> 월 </p>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/autumn.png) no-repeat; background-size: contain;"></i>가을</label></td>
						<td>
						<%-- ${data.autumnSt} ~ ${data.autumnEd} 월 --%>
							<div class= inputIp>
								<input type="text" id ="autumnSt" name="autumnSt" value=${data.autumnSt} maxlength="2" style="border:none; text-align:center" class="4" readonly>
								<p> ~ </p>
								<input type="number" min="1" max="12" id ="autumnEd" name="autumnEd" value=${data.autumnEd} maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="5" required>
								<p> 월 </p>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/winter.png) no-repeat; background-size: contain;"></i>겨울</label></td>
						<td>
						<%-- ${data.winterSt} ~ ${data.winterEd} 월 --%>
							<div class= inputIp>
								<input type="text" style="border:none; text-align:center" id ="winterSt" name="winterSt" value=${data.winterSt} maxlength="2" class="6" readonly>
								<p> ~ </p>
								<input type="number" min="1" max="12" id ="winterEd" name="winterEd" value=${data.winterEd} maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="7" required>
								<p> 월 </p>
							</div>
						</td>
					</tr>
				</table>
			</div>	
		</div>
		
		<div class="btnDiv" style="float:none;position: relative;top: 0px;left:800px;margin-top: 50px;">
			<button id="btnSub" class="btn-small">수정</button>
			<button class="btn-small" id="btnCancel">취소</button>
		</div>
	</form>
</body>
</html>