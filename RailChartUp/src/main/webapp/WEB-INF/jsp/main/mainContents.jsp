<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class='no-js'>
<head>
<meta charset="utf-8">
	<script>
		//화면 갱신 주기
		var freshRange;
		//시간 범위
		var timeRange;
		$(document).ready( function() {
			$("#content").css("width","100%");
			$("#content").css("padding","0px");
			//타이머 변수 초기화
			clearInterval(timeRefresh);
			timeRefresh=null;
			//console.log("메인화면 진입 차트 소환");
			//현재 시간이 어떤주기?
			var nowTR=ajaxMethod('/statistic/whenNowTime.ajax');
			//화면 갱신 주기
			freshRange=5*1000*60;
			//시간 범위
			timeRange=5;
			
			//최초 값 호출(데이트피커 다음에 호출해야함)
			console.log("메인화면 최초 값 호출");
			var stkBarData=ajaxMethod("/statistic/stkBarTod.ajax", {"nowTR":nowTR});
			var sbD = stkBarData.data;
			stkBarChart = stkBarChart("stkBarC",900,300,sbD);
			
			var yesToData = ajaxMethod("/statistic/yesTodStkAr.ajax");
			var per = percentage(yesToData.data);
			
			changeRyang(sbD);
			chart = ryBChart("twoBarC",900,300);
			//최초 생성
			//stcReNew(alData);
			
			twoAreaChart = twoAreaChart("twoAreaC",900,300,yesToData); 
			gaugeChart = gaugeChart("gaugeC",900,300,per);
			
			$(this).attr("font-size", "14");	
			
			timeRefresh = setInterval(function () {
				//console.log("메인화면 갱신");
				//ajaxData=ajaxMethod("/statistic/mainYesToday.ajax", {"timeRange":timeRange});
				
				
				
			}, freshRange);
			
			//화면 갱신 주기 변경시
			$("#freshRange").on("change", function(){
				freshRange=$(this).val()*1000*60;
				//console.log("변경된 갱신주기 : "+freshRange);
				//타이머 변수 초기화
				clearInterval(timeRefresh);
				timeRefresh=null;
				timeRefresh = setInterval(function () {
					console.log("화면 갱신 주기 변경시");
					reloadMain();
				}, freshRange);
				
			});
			//시간 주기 변경시
			$("#timeRange").on("change", function(){
				timeRange=$(this).val();
				
				console.log("시간 주기 변경시");
				reloadMain();
				
			});
			
			cssChart();
		});//ready
	</script>
</head>
<body class='antialiased'>
	<!-- <h1 class='title' style='text-align:center; font-weight:bold;'>전/금일 열차별 혼잡도</h1> -->
	<div class="main-st" style="top: 30px; right: -150px;">
		<div class="st-range">
			<p>시간범위(분)</p>
			<select id="timeRange">
				<option value="5" selected>5</option>
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="60">60</option>
			</select>
		</div>
		<div class="st-range">
			<p>갱신주기(분)</p><!-- 실제로는 초단위로 갱신 -->
			<select id="freshRange">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="5">5</option>
				<option value="10">10</option>
			</select>
		</div>
	</div>
	<div id='chtImg' class='chart-div-main' style="margin-left: 50px;position: relative;top: -30px;">
		<!-- 금일 차량별 혼잡도 -->	
		<div class="mfdiv">
			<h4 class='subT'>금일 차량별 혼잡도</h4>
			<div class="flb-y"><p>혼잡도별 <br>차량 수</p></div>
			<div id='stkBarC'></div>
			<div class="flb-x" style="right: 50px; bottom: 10px;"><p>열차번호</p></div>
		</div>
		<!-- 주의/혼잡 비율(게이지) -->	
		<div class="mfdiv" style="margin-top:30px">
			<div id='gaugeC' class='gaugeC'></div>
		</div>
		<!-- 량별 혼잡도 -->	
		<div class="mfdiv">
			<h4 class='subT'>량별 혼잡도 </h4>
			<div class="flb-y"><p><br>무게(KPA)</p></div>
			<div id='twoBarC' class='twoBarC'></div>
			<div class="flb-x" style="right: 50px; bottom: 10px;"><p>량</p></div>
		</div>
		<!-- 전/금일 주의 혼잡 데이터량 -->	
		<div class="mfdiv" style="margin-top:15px">
			<h4 class='subT'>전/금일 주의 혼잡 데이터량 </h4>
			<div class="flb-y"><p>데이터 수</p></div>
			<div id='twoAreaC' class='twoAreaC'></div>
			<div class="flb-x" style="right: 50px; bottom: 10px;"><p>시간</p></div>
		</div>
	</div>
	<!-- <div class='chart'>
		<div class="lb-ct-y">량 수</div>
		<div id='chart' style='height: 700px;'></div>
		<div class="lb-ct-x">전 체 열 차</div>
	</div> -->
<!-- <script src="../js/samples/chart_combination-764a0701.js" type="text/javascript"></script>
 -->
</body>
</html>
