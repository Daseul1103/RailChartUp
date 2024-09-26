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
	var bfSeaArr;
	//이메일 체크
	$(document).ready( function() {
		console.log("시간/기간범위설정 수정 진입 : ");
		
		//이전 계절 범위 값 관련
		bfSeaArr =
		[
			 Number($("#springSt").val())
			,Number($("#springEd").val())
			,Number($("#summerSt").val())
			,Number($("#summerEd").val())
			,Number($("#autumnSt").val())
			,Number($("#autumnEd").val())
			,Number($("#winterSt").val())
			,Number($("#winterEd").val())
		]; 
		
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
		
		/* 시간범위 조절 */
		$("#ssRTb1 input[type=number]").on("change",function(){
			console.log("시간 범위 조절 현재 바꾼값 : "+$(this).val());
			
			var nowC = $(this).val();
			if(nowC.length==1){
				$(this).val("0"+nowC);
			}
			
			//분리된 항목 통합
			//출근 
			var goworkSt = $("#goworkSt1").val()+":"+$("#goworkSt2").val();
			$("#goworkSt").val(goworkSt);
			var goworkEd = $("#goworkEd1").val()+":"+$("#goworkEd2").val()
			$("#goworkEd").val(goworkEd);
			//주간
			var daySt = $("#daySt1").val()+":"+$("#daySt2").val();
			$("#daySt").val(daySt);
			var dayEd = $("#dayEd1").val()+":"+$("#dayEd2").val()
			$("#dayEd").val(dayEd);
			//퇴근
			var gohomeSt = $("#gohomeSt1").val()+":"+$("#gohomeSt2").val();
			$("#gohomeSt").val(gohomeSt);
			var gohomeEd = $("#gohomeEd1").val()+":"+$("#gohomeEd2").val()
			$("#gohomeEd").val(gohomeEd);
			//야간
			var nightSt = $("#nightSt1").val()+":"+$("#nightSt2").val();
			$("#nightSt").val(nightSt);
			var nightEd = $("#nightEd1").val()+":"+$("#nightEd2").val()
			$("#nightEd").val(nightEd);
			
			var ymd = "2021-01-01 ";
			var snd = ":00";
			
			var tcla = $(this).attr("class");
			var $par = $(this).parent();
			
			var gwSt = moment(goworkSt,'HH:mm');
			var gwEd = moment(goworkEd,'HH:mm');
			
			var dSt = moment(daySt,'HH:mm');
			var dEd = moment(dayEd,'HH:mm');

			var ghSt = moment(gohomeSt,'HH:mm');
			var ghEd = moment(gohomeEd,'HH:mm');
			
			var nSt = moment(nightSt,'HH:mm');
			var nEd = moment(nightEd,'HH:mm');
			
			//출근 퇴근 시간의 시작 종료점 기간 한정
			if(tcla=="goworkSt"){//출근 시작점은 6~8시
				console.log("출근 시간점 변경");
				if(gwSt<moment('06:00','HH:mm')||gwSt>moment('08:00','HH:mm')){
					//범위값이 틀리므로 야간 종료점+1지점으로
					alert("출근 시작시간은 6~8시 사이의 값을 입력하셔야 합니다");
					console.log("범위틀림");
					gwSt=moment(nightEd,'HH:mm').add(1,'m');
					var nnVal=gwSt.format("HH:mm");
					$("#goworkSt1").val(nnVal.split(':')[0]);
					$("#goworkSt2").val(nnVal.split(':')[1]);
					
				}else{
					console.log("범위맞음");
					//범위가 맞다면 주간 시작점을 -1
					nEd=moment(goworkSt,'HH:mm').subtract(1,'m');
					var nnVal=nEd.format("HH:mm");
					$("#nightEd1").val(nnVal.split(':')[0]);
					$("#nightEd2").val(nnVal.split(':')[1]);
				}
			}else if(tcla=="goworkEd"){//출근 종료점은 8시~12시
				console.log("출근 종료점 변경");
				if(gwEd<moment('08:00','HH:mm')||gwEd>moment('12:00','HH:mm')){
					alert("출근 종료시간은 8~12시 사이의 값을 입력하셔야 합니다");
					//범위값이 틀리므로 주간 시작점-1지점으로
					console.log("범위틀림");
					gwEd=moment(dSt,'HH:mm').subtract(1,'m');
					var nnVal=gwEd.format("HH:mm");
					$("#goworkEd1").val(nnVal.split(':')[0]);
					$("#goworkEd2").val(nnVal.split(':')[1]);
					
				}else{
					console.log("범위맞음");
					//범위가 맞다면 주간 시작점을 현재값 +1
					dSt=moment(goworkEd,'HH:mm').add(1,'m');
					var nnVal=dSt.format("HH:mm");
					$("#daySt1").val(nnVal.split(':')[0]);
					$("#daySt2").val(nnVal.split(':')[1]);
					
				}
			}else if(tcla=="gohomeSt"){//퇴근 시작점은 16~18시
				console.log("퇴근 시작점 변경");
				if(ghSt<moment('16:00','HH:mm')||ghSt>moment('18:00','HH:mm')){
					alert("퇴근 시작시간은 16~18시 사이의 값을 입력하셔야 합니다");
					//범위값이 틀리므로 현값을 주간 종료점+1지점으로
					console.log("범위틀림");
					ghSt=moment(dayEd,'HH:mm').add(1,'m');
					var nnVal=ghSt.format("HH:mm");
					$("#gohomeSt1").val(nnVal.split(':')[0]);
					$("#gohomeSt2").val(nnVal.split(':')[1]);
					
				}else{
					console.log("범위맞음");
					//범위가 맞다면 주간 종료점을 현재-1
					dEd=moment(gohomeSt,'HH:mm').subtract(1,'m');
					var nnVal=dEd.format("HH:mm");
					$("#dayEd1").val(nnVal.split(':')[0]);
					$("#dayEd2").val(nnVal.split(':')[1]);
				}
			}else{//퇴근 종료점은 18시~21시
				if(ghEd<moment('18:00','HH:mm')||ghEd>moment('22:00','HH:mm')){
					alert("퇴근 종료시간은 18~22시 사이의 값을 입력하셔야 합니다");
					//범위값이 틀리므로 야간시작점+1지점으로
					console.log("범위틀림");
					ghEd=moment(nightSt,'HH:mm').subtract(1,'m');
					var nnVal=ghEd.format("HH:mm");
					$("#gohomeEd1").val(nnVal.split(':')[0]);
					$("#gohomeEd2").val(nnVal.split(':')[1]);
					
				}else{
					console.log("범위맞음");
					//범위가 맞다면 야간 시작점을 현재-1
					nSt=moment(gohomeEd,'HH:mm').add(1,'m');
					var nnVal=nSt.format("HH:mm");
					$("#nightSt1").val(nnVal.split(':')[0]);
					$("#nightSt2").val(nnVal.split(':')[1]);
					
				}
			}
			
		}); //시간범위 수정끗
		
		/* 월 범위 조절 */
		$("#ssRTb2 input[type=number]").on("change",function(){
			//console.log("일일 혼잡도 범위 조절");
			//현재 변경값 class 숫자로 치환
			var tnt = Number($(this).attr('class'));
			var bst;//현재 범위의 시작점
			var nst;//다음 범위의 시작점
			var ned;//다음 범위의 끝점
			
			//겨울 범위 수정일 시에는 다음 범위가 봄 항목
			if(tnt==7){
				bst=6;
				nst=0;
				ned=1;				
			}else{
				bst=tnt-1;
				nst=tnt+1;
				ned=tnt+2;	
			}
			
			var bstVal = Number($("#ssRTb2").find("."+bst).val());//현재 시작범위 값
			var tVal = Number($(this).val());//현재 값
			var nstVal = Number($("#ssRTb2").find("."+nst).val());//다음 시작범위 값
			var nedVal = Number($("#ssRTb2").find("."+ned).val());//다음 종료범위 값
			
			//tVal-bstVal<0 or nedVal - nstVal <0 -> 현재 범위나 다음 범위가 내년을 넘어가는 경우 예외
			if(tVal ==12 || bfSeaArr[tnt]-bfSeaArr[bst]<0 || bfSeaArr[ned]-bfSeaArr[nst] <0 ){
				//console.log("현재 범위나 다음 범위가 내년을 넘어가는 경우");	
				if(tVal > 12 || tVal == 0){
					//console.log("유효하지 않음");
					$(this).val(Number($("#ssRTb2").find("."+nst).val())-1);
				}else{
					if(nedVal-nstVal == 0){
						//현재값을 다음 값의 -1
						if(nstVal - tVal >= 1){
							$("#ssRTb2").find("."+nst).val(Number($(this).val())+1);
							bfSeaArr[tnt]=Number($(this).val());
							bfSeaArr[nst]=Number($(this).val())+1;
						}else{//증가값이 다음 값과 같아지면 현재증가값을 복원
							//다음 값이 1일 경우
							if( Number($("#ssRTb2").find("."+nst).val()) == 1 ){
								$(this).val(12);
								bfSeaArr[tnt]=Number($(this).val());
							}else{
								$(this).val(Number($("#ssRTb2").find("."+nst).val())-1);
								bfSeaArr[tnt]=Number($(this).val());
							}
						}
					}else{
						//다음 값을 현재 값의 +1
						if(Number($(this).val())+1>12){
							$("#ssRTb2").find("."+nst).val(1);
							bfSeaArr[tnt]=Number($(this).val());
							bfSeaArr[nst]=1;
						}else{
							$("#ssRTb2").find("."+nst).val(Number($(this).val())+1);
							bfSeaArr[tnt]=Number($(this).val());
							bfSeaArr[nst]=Number($(this).val())+1;
						}
					}
				}
			}else{
				//console.log("내년 안넘는 경우");	
				//비 유효한 값 입력 (현재 값이 다음 끝 범위값보다 큰 경우)
				if(tVal >= nedVal || tVal < bstVal || tVal > 12 || tVal == 0){
					if(nstVal==1){
						if(bstVal!=12){
							$("#ssRTb2").find("."+nst).val(12);
							$(this).val(11);
							bfSeaArr[tnt]=Number($(this).val());
							bfSeaArr[nst]=Number($(this).val())+1;
						}else{
							if(nedVal!=1 && tVal < nedVal){
								$("#ssRTb2").find("."+nst).val(1);
								$(this).val(12);
								bfSeaArr[tnt]=Number($(this).val());
								bfSeaArr[nst]=Number($(this).val())+1;
							}else{ //현재시작 12 현재값 12 다음시작 1 다음종료 1인경우
								$(this).val(bfSeaArr[tnt]);
							}							
						}
					}else{
						$(this).val(Number($("#ssRTb2").find("."+nst).val())-1);
						bfSeaArr[tnt]=Number($(this).val());
					}
				}else{
					//nedVal-nstVal 값이 0보다 작거나 같을경우
					if(nedVal-nstVal <= 0){
						//현재값을 다음 값의 -1
						if(nstVal - tVal >= 1){
							$("#ssRTb2").find("."+nst).val(Number($(this).val())+1);
							bfSeaArr[tnt]=Number($(this).val());
							bfSeaArr[nst]=Number($(this).val())+1;
						}else{//증가값이 다음 값과 같아지면 현재증가값을 복원
							//다음 값이 1일 경우
							if( Number($("#ssRTb2").find("."+nst).val()) == 1 ){
								$(this).val(12);
								bfSeaArr[tnt]=12;
							}else{
								$(this).val(Number($("#ssRTb2").find("."+nst).val())-1);
								bfSeaArr[tnt]=Number($(this).val());
							}
						}
					}else{
						//다음 값을 현재 값의 +1
						if(Number($(this).val())+1>12){
							$("#ssRTb2").find("."+nst).val(1);
							bfSeaArr[nst]=1;
						}else{
							$("#ssRTb2").find("."+nst).val(Number($(this).val())+1);
							bfSeaArr[tnt]=Number($(this).val());
							bfSeaArr[nst]=Number($(this).val())+1;
						}
					}				
				}
			}
			
		});//월범위 수정끗
		
		
		$("#timeRangeUpdateForm").submit( function(event){
			//console.log("차상단말기 수정 버튼 클릭");
			event.preventDefault();
			
			//분리된 항목 통합
			/* 출근 */
			var goworkSt = $("#goworkSt1").val()+":"+$("#goworkSt2").val();
			$("#goworkSt").val(goworkSt);
			var goworkEd = $("#goworkEd1").val()+":"+$("#goworkEd2").val();
			$("#goworkEd").val(goworkEd);
			/* 주간 */
			var daySt = $("#daySt1").val()+":"+$("#daySt2").val();
			$("#daySt").val(daySt);
			var dayEd = $("#dayEd1").val()+":"+$("#dayEd2").val();
			$("#dayEd").val(dayEd);
			/* 퇴근 */
			var gohomeSt = $("#gohomeSt1").val()+":"+$("#gohomeSt2").val();
			$("#gohomeSt").val(gohomeSt);
			var gohomeEd = $("#gohomeEd1").val()+":"+$("#gohomeEd2").val();
			$("#gohomeEd").val(gohomeEd);
			/* 야간 */
			var nightSt = $("#nightSt1").val()+":"+$("#nightSt2").val();
			$("#nightSt").val(nightSt);
			var nightEd = $("#nightEd1").val()+":"+$("#nightEd2").val();
			$("#nightEd").val(nightEd);
			
			//유효성 체크
			if(boardWriteCheck(form)){
				// serialize는 form의 <input> 요소들의 name이 배열형태로 그 값이 인코딩되어 URL query string으로 하는 메서드
				let queryString = $(this).serialize();
				$.ajax({
					url: "/setting/timeRangeUpdate/"+loginId+".ajax",
					type: "POST",
					dataType: "json",
					data: queryString,
					// ajax 통신 성공 시 로직 수행
					success: function(json){
						//console.log("성공 msg : "+json.msg);
						if(json.msg=="" || typeof json.msg ==="undefined"){
							$("#content").load("/setting/timeRange.do");
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
				<table class='setTbList-nonbt' id ='ssRTb1'>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/gowork.png) no-repeat; background-size: contain;"></i>출근</label></td>
						<td>
						<%-- ${data.goworkSt} ~ ${data.goworkEd} --%>
							<div class= inputIp id ="calRange1">
								<input type="hidden" id ="goworkSt" name="goworkSt" class="0">
								<input type="hidden" id ="goworkEd" name="goworkEd" class="1">
									
								<input type="number" min="0" max="23"  id="goworkSt1" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="goworkSt" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="goworkSt2" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="goworkSt" required>
								<p> ~ </p>
								<input type="number" min="0" max="23"  id="goworkEd1" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="goworkEd" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="goworkEd2" maxlength="2" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="goworkEd" required>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/dayTime.png) no-repeat; background-size: contain;"></i>주간</label></td>
						<td>
							<%-- ${data.daySt} ~ ${data.dayEd} --%>
							
							<div class= inputIp  id ="calRange1">
							
								<input type="hidden" id ="daySt" name="daySt" class="2">
								<input type="hidden" id ="dayEd" name="dayEd" class="3">
							
								<input type="text" min="0" max="23"  id="daySt1" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="daySt" style=" border: none;text-align: center;" readonly>
								<p> : </p>
								<input type="text" min="0" max="59"  id="daySt2" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="daySt" style=" border: none;text-align: center;" readonly>
								<p> ~ </p>
								<input type="text" min="0" max="23"  id="dayEd1" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="dayEd" style=" border: none;text-align: center;" readonly>
								<p> : </p>
								<input type="text" min="0" max="59"  id="dayEd2" name="" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="dayEd" style=" border: none;text-align: center;" readonly>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/gohome.png) no-repeat; background-size: contain;"></i>퇴근</label></td>
						<td>
						<%-- ${data.gohomeSt} ~ ${data.gohomeEd} --%>
							
							<div class= inputIp  id ="calRange1">
							
								<input type="hidden" id ="gohomeSt" name="gohomeSt" class="4">
								<input type="hidden" id ="gohomeEd" name="gohomeEd" class="5">
							
								<input type="number" min="0" max="23"  id="gohomeSt1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="gohomeSt" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="gohomeSt2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="gohomeSt" required>
								<p> ~ </p>
								<input type="number" min="0" max="23"  id="gohomeEd1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="gohomeEd" required>
								<p> : </p>
								<input type="number" min="0" max="59"  id="gohomeEd2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="gohomeEd" required>
							</div>
						</td>
					</tr>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/night.png) no-repeat; background-size: contain;"></i>야간</label></td>
						<td>
						<%-- ${data.nightSt} ~ ${data.nightEd} --%>
							
							<div class= inputIp  id ="calRange1">
							
								<input type="hidden" id ="nightSt" name="nightSt" class="6">
								<input type="hidden" id ="nightEd" name="nightEd" class="7">
							
								<input type="text" min="0" max="23"  id="nightSt1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="nightSt" style=" border: none;text-align: center;" readonly>
								<p> : </p>
								<input type="text" min="0" max="59"  id="nightSt2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="nightSt" style=" border: none;text-align: center;" readonly>
								<p> ~ </p>
								<input type="text" min="0" max="23"  id="nightEd1" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="nightEd" style=" border: none;text-align: center;" readonly>
								<p> : </p>
								<input type="text" min="0" max="59"  id="nightEd2" maxlength="3" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' class="nightEd" style=" border: none;text-align: center;" readonly>
							</div>
						</td>
					</tr>
				</table>
			</div>
		
			<div class="setTbDiv">
				<table class='setTbList-nonbt' id ='ssRTb2'>
					<tr>
						<td><label><i class="setIconImg" style="background: url(../images/spring.png) no-repeat; background-size: contain;"></i>봄</label></td>
						<td>
						<%-- ${data.springSt} ~ ${data.springEd} 월 --%>
							<div class= inputIp id ="calRange2">
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
							<div class= inputIp id ="calRange2">
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
							<div class= inputIp id ="calRange2">
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
							<div class= inputIp id ="calRange2">
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
			<button id="btnSub" class="btn-small">변경</button>
			<button class="btn-small" id="btnCancel">취소</button>
		</div>
	</form>
</body>
</html>