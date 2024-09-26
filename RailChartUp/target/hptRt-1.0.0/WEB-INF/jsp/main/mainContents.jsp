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
			//타이머 변수 초기화
			clearInterval(timeRefresh);
			timeRefresh=null;
			console.log("메인화면 진입 차트 소환");
			
			//화면 갱신 주기
			freshRange=5*1000;
			//시간 범위
			timeRange=5;
			
			var ajaxData=ajaxMethod("/statistic/mainYesToday.ajax", {"timeRange":timeRange});
			
			chart = c3.generate({
				size: {
			        height: 500,
			        width: 1300,
			    },
				
				data: {
					x:'x',
					columns: [
						ajaxData.stList.tNum,
						ajaxData.stList.yesCare,
						ajaxData.stList.yesCwd,
						ajaxData.stList.toCare,
						ajaxData.stList.toCwd
			        ]
					,type: 'bar'
					/* ,labels:true */
					,colors: {
						'전일 주의': '#FFC1F6',
						'전일 혼잡': '#9F3FFF',
						'금일 주의': '#FF970D',
						'금일 혼잡': '#FF0000'
			        }
					,groups: [
						['전일 주의','전일 혼잡'],['금일 주의','금일 혼잡'],
					]
					,order: null
				}//data
				,axis: {
					x: {
			            type: 'category'
			        },
			        y: {
			            min: 0,
			            tick: {
			            	format: function(d) {
			                    if (Math.floor(d) != d){
			                      return;
			                    }
			                    return d;
		                  	}
			            },
			            //max: 8,
			            padding: {top: 0, bottom: 0}
			        }
			    }
			});//chart
			
			timeRefresh = setInterval(function () {
				console.log("메인화면 갱신");
				ajaxData=ajaxMethod("/statistic/mainYesToday.ajax", {"timeRange":timeRange});
			    chart.load({
			        columns: [
						ajaxData.stList.tNum,
						ajaxData.stList.yesCare,
						ajaxData.stList.yesCwd,
						ajaxData.stList.toCare,
						ajaxData.stList.toCwd
			        ]
			    });
			}, freshRange);
			
			//화면 갱신 주기 변경시
			$("#freshRange").on("change", function(){
				freshRange=$(this).val()*1000;
				console.log("변경된 갱신주기 : "+freshRange);
				//타이머 변수 초기화
				clearInterval(timeRefresh);
				timeRefresh=null;
				timeRefresh = setInterval(function () {
					console.log("갱신 메인화면 갱신");
					ajaxData=ajaxMethod("/statistic/mainYesToday.ajax", {"timeRange":timeRange});
				    chart.load({
				        columns: [
							ajaxData.stList.tNum,
							ajaxData.stList.yesCare,
							ajaxData.stList.yesCwd,
							ajaxData.stList.toCare,
							ajaxData.stList.toCwd
				        ]
				    });
				}, freshRange);
				
			});
			//시간 주기 변경시
			$("#timeRange").on("change", function(){
				timeRange=$(this).val();
				ajaxData=ajaxMethod("/statistic/mainYesToday.ajax", {"timeRange":timeRange});
			    chart.load({
			        columns: [
						ajaxData.stList.tNum,
						ajaxData.stList.yesCare,
						ajaxData.stList.yesCwd,
						ajaxData.stList.toCare,
						ajaxData.stList.toCwd
			        ]
			    });
			});
			
		});//ready
	</script>
</head>
<body class='antialiased'>
	<h1 class='title' style='text-align:center; font-weight:bold;'>전일 편성별 호차별 혼잡도</h1>
	<div class="main-st">
		<div class="st-range">
			<p>시간범위(초)</p>
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
	<div class='chart'>
		<div class="lb-ct-y">량 수</div>
		<div id='chart' style='height: 700px;'></div>
		<div class="lb-ct-x">전 체 편 성</div>
	</div>
<!-- <script src="../js/samples/chart_combination-764a0701.js" type="text/javascript"></script>
 -->
</body>
</html>
