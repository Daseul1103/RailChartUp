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
			console.log('일일통계1');
			$("#trainDiv").show();
			//최대일과 현재일이 같을 경우 발생할수 있는 문제에 대해 최대일에 +1초 
			//먼저 변수 선언
			var maxD = moment().add(1, 'seconds').format("YYYY-MM-DD");
			var defaultD  = moment().format("YYYY-MM-DD");
			
			$('.bootstrap-datatimepicker-widget').hover(function(){
				console.log('달력 체크1');
			});
			
			$('.glyphicon-calendar').hover(function(){
				console.log('달력 체크2');
			});
			
			$('#datetimepicker1').datetimepicker({
				 format:"YYYY-MM-DD",
				 maxDate : maxD, 
				 defaultDate: defaultD
			 }).on('dp.change', function (e) {
				//날짜 변경시
			 	//console.log("달력 갱신");
			 	$("#trainTbd").empty();
			 	var txtDt=$("#sdate").val();
				//편성테이블 갱신
			 	fid=trainOne(txtDt);
				//날짜나 열차 변경 시 룰셋 변경
				renewalRule("day");
			});
			
			//차트 최초 값 호출(데이트피커 다음에 호출해야함)
			console.log("차트 최초 값 호출");
			var ajaxData=ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":$("#sdate").val(),"trainNum":fid});
			var sctData = ajaxMethod("/statistic/scatterChart.ajax", {"sDate":$("#sdate").val(),"trainNum":fid, "carWgt":"1"});	
			var saData = ajaxMethod("/statistic/stkAreaChart.ajax", {"sDate":$("#sdate").val(),"trainNum":fid});	
			var alData = ajaxData.data;

			//최초 생성
			stcReNew(alData);
			ryangTrain(0,0);
			
			chart = dayBarChart("barChart",600,180);
			scatChart = scatterChart("sctPlot",600,180,sctData);
			pieChart = dountChart("pieChart",600,180);
			saChart = stkAreaChart("staChart",550,180,saData);
			$("#pieChart .c3-legend-item").parent().css('transform','translate(430px,0px)');
			
			//$('svg').css("background","");
			$("#btnDownload").click(function() {
				console.log("다운로드 버튼 클릭");
				//엑셀 다운로드 후 언로드 방지
				cssNonChart();
				$(".c3-target-여유").children().css("fill","black");
				$(".c3-target-보통").children().css("fill","black");
				$(".c3-target-주의").children().css("fill","black");
				$(".c3-target-혼잡").children().css("fill","black");
				
				c3Title="DAY";
				c3TagId="_("+$("#sdate").val()+")";
				exportChartToPng('chtImg');
				rkFlag = true;
				console.log("다운로드 버튼 클릭 완료");
			});
			
			//열차 량 선택 시
			$(".rgDiv tr td").on( "click" , function(){
				ryangTrain($(this).attr("id"),$(this).attr("class"));
			});
			
			cssChart();
			
		});//ready
	</script>
</head>
	<h1 class='title' style="position: relative;top: -15px;">일일 통계</h1>
	<div class="main-st" style="float: left;top: -65px; left: 200px;">
		<!-- datetimepicker -->
		<div class='input-group date' id='datetimepicker1'>
			<input type='text' class="form-cont" name="sdate" id="sdate" style="width: 120px;" required/>
			<span class="input-group-addon">
				<span class="glyphicon glyphicon-calendar"></span>
			</span>
		</div>
	</div>
	<!-- 량별이미지 -->
	<div class="rgDiv">
		<table id ="ryangT"></table>
	</div>
	<div id='chtImg' class='chart-div' style='width:1248px; height:480px; position:relative; top:-80px;'>
		<!-- 막대 차트 -->	
		<div class="fdiv">
			<h4 class='subT'>시간대별 혼잡도</h4>
			<div class="flb-y"><p>혼잡도별 <br> 차량 수</p></div>
			<div id='barChart'></div>
			<div class="flb-x" style="right: 50px; bottom: 10px;"><p>시간대</p></div>
		</div>
		<!-- 산포도 -->
		<div class="fdiv">
			<h4 class='subT'>량별 무게값 분포도</h4>
			<div class="flb-y"><p><br>무게(KPA)</p></div>
			<div id='sctPlot' class='sct-plot'></div>
			<div class="flb-x"><p>시간</p></div>
		</div>
		<!-- 영역차트 -->		
		<div class="fdiv">
			<h4 class='subT' style="top: 50px;">누적 데이터 집계</h4>
			<div class="flb-y"><p>데이터 수</p></div>
			<div id='staChart' class='sta-chart'></div>
			<div class="flb-x"><p>시간</p></div>
		</div>
		<!-- 도넛 -->	
		<div class="fdiv" style="margin-top: 30px;">
			<div id='pieChart' class='pie-chart'></div>
		</div>	
	</div>
	<!-- 이미지 캡쳐 폼 -->
	<form method="POST" id="captureForm" name="captureForm">
	  <input type="hidden" name="img_val0" id="img_val0" value="" />
	  <input type="hidden" name="img_val1" id="img_val1" value="" />
	  <input type="hidden" name="img_val2" id="img_val2" value="" />
	  <input type="hidden" name="img_val3" id="img_val3" value="" />
	</form>
	
	<!-- 다운로드 기능 -->
	<div id ="btnDiv" class="btnDiv" style="position: relative; top: -150px; left: -130px;">
		<input type='button' id='btnDownload'  style="width: 80px;padding-bottom: 5px;" value='다운로드'>
		<div id="btnIns" style="float: right;"></div>
	</div>
	
	<div style="padding-right: 150px; position: relative; top: -50px;">
		<table id="tableList" class="table table-bordered" style="width: 100%; margin: 0px;">
			<thead class="thead">
				<tr>
					<th rowspan="2" style="text-align:center; vertical-align: middle;">기준</th>
					<th colspan="4" style="text-align:center; vertical-align: middle;">혼잡도 변화 건수(량당)</th>
					<th rowspan="2" style="text-align:center; vertical-align: middle;">평균 혼잡도 (KPA)</th>
					<th colspan="3" style="text-align:center; vertical-align: middle;">최소</th>
					<th colspan="3" style="text-align:center; vertical-align: middle;">최대</th>
				</tr>
				<tr>
					<th style="text-align:center;">여유</th>
					<th style="text-align:center;">보통</th>
					<th style="text-align:center;">주의</th>
					<th style="text-align:center;">혼잡</th>
					<th style="text-align:center;">중량</th>
					<th style="text-align:center;">열번</th>
					<th style="text-align:center;">시간</th>
					<th style="text-align:center;">중량</th>
					<th style="text-align:center;">열번</th>
					<th style="text-align:center;">시간</th>
				</tr>
			</thead>
			<tbody id="tbody" style="text-align:center;"></tbody>
		</table>
	</div>
</html>
