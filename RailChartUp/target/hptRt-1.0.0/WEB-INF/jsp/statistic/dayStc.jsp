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
			console.log('일일통계');
			
			var ajaxData=ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":"2018-01-01","trainNum":fid});
			var alData = ajaxData.data;
			//최초 생성
			stcReNew(alData);
			
			chart = c3.generate({
				size: {
			        height: 300,
			        width: 900,
			    },
				data: {
					x:'x',
					columns: [
						titleArr,
						rlxArr,
						usArr,	
						careArr,	
						cwdArr	
			        ]
			    	,line: {
			            connectNull: false,
			        }
					,type: 'bar'
					,labels:true
					,colors: {
						'여유': '#50E94F',
						'보통': '#F5E001',
						'주의': '#F68F00',
						'혼잡': '#FE0000'
			        }
					,order: null
				}//data
				,legend: {//범주(여유,보통,주의,혼잡)
			        position: 'right'
			    }
				,axis: {
					x: {
			            type: 'category'
			        }
					,y: {
			            //padding: {top: 200,left:50,right:50, bottom: 0},
						 tick: {
			                //format: d3.format("$,")
			                format: function (d) {
			                	if(((d/1000)%1)==0){
			                		return d/1000;			                		
			                	}
			                }
			         	}
			        }
			    }
				,tooltip: {
			        format: {
			            value: function (value, ratio, id) {
			                var format= d3.format(',');
			                return format(value);
			            }
			        }
			    }
			});//chart

			 $('#datetimepicker1').datetimepicker({
				 format:"YYYY-MM-DD",
				 maxDate : moment(), 
				 defaultDate:"2018-01-01"
			 }).on('dp.change', function (e) {
				//날짜 변경시
			 	console.log("달력 갱신");
			 	$("#trainTbd").empty();
			 	var txtDt=$("#sdate").val();
				//편성테이블 갱신
			 	fid=trainOne(txtDt);
			 	//변경일시에 해당하는 데이터로 호출
			 	ajaxData=ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":txtDt,"trainNum":fid});
				alData = ajaxData.data;
				//막대그래프 갱신
				stcReNew(alData);
				//데이터에 맞게 차트 다시그림
				chart.load({
			        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
			    });
			});
			
			//다운로드 버튼 클릭 시
			$("#btnDownload").click(function() {
				console.log("다운로드 버튼 클릭");
				c3Title="DAY";
				c3TagId="_("+$("#sdate").val()+")";
				exportChartToPng('chart');
			});

			var nodeList    = $('svg .c3-chart path.c3-shape.c3-shape.c3-line');
			var fillArea    = Array.from(nodeList);
			
			fillArea.forEach(function(element){
			  element.style.fill = "none";
			}); 
			
			$('svg').css("background","#fff");

			//활성화 가능한 td(편성) 선택 시	
			$("#trainTbd").on("click","td",function(){
				console.log("선택한 편성번호 : "+$(this).attr('id'));
				if($(this).attr('class')=="tn-y"){
					var $old=$("#trainTbd").find('.tn-active');
					$old.attr('class','tn-y');
					$(this).attr('class','tn-active');
					fid = $(this).attr('id');
					
					console.log("편성 갱신");
				 	var txtDt=$("#sdate").val();
				 	ajaxData=ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":txtDt,"trainNum":fid});
					alData = ajaxData.data;
					stcReNew(alData);
					//데이터에 맞게 차트 다시그림
					chart.load({
				        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
				    });
				}
			});
			
			//활성화 가능한 td(편성) mouseover
			$("#trainTbd").on("mouseover","td",function(){
				if($(this).attr('class')!="tn-n"){
					$(this).css("cursor", "pointer");
				}
			});
			
			//단일편성 탭 클릭 시		
			$("#trainOne").on("click",function(){
				if($(this).attr('class')!="active"){
					console.log("단일편성 탭 클릭");
					$("#trainOne").addClass('active');
					$('#trainAll').removeClass('active');
					$("#trainNum").removeClass("disabledbutton");
					
				 	var txtDt=$("#sdate").val();
				 	ajaxData=ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":txtDt,"trainNum":fid});
					alData = ajaxData.data;
					stcReNew(alData);
					//데이터에 맞게 차트 다시그림
					chart.load({
				        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
				    });
				}
			});
			
			//전체편성 탭 클릭
			$("#trainAll").on("click",function(){
				//console.log($(this).attr('class'));
				if($(this).attr('class')!="active"){
					console.log("전체편성 탭 클릭");
					$("#trainAll").addClass('active');
					$('#trainOne').removeClass('active');
					$("#trainNum").addClass("disabledbutton");

					console.log("편성 갱신");
				 	var txtDt=$("#sdate").val();
				 	ajaxData=ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":txtDt});
					alData = ajaxData.data;
					stcReNew(alData);
					//데이터에 맞게 차트 다시그림
					chart.load({
				        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
				    });
				}
			});
			
		});//ready
	</script>
</head>
	<h1 class='title' style='text-align:center; font-weight:bold;'>일일 통계</h1>
	<div class="main-st">
		<!-- datetimepicker -->
		<div class='input-group date' id='datetimepicker1'>
			<input type='text' class="form-cont" name="sdate" id="sdate" style="width: 120px;"/>
			<span class="input-group-addon">
				<span class="glyphicon glyphicon-calendar"></span>
			</span>
		</div>
	</div>
	<div id='chtImg' class='chart' style="margin:50px;">
		<div class="lbp-y-axis">
			<p>각<br>량<br>별</p>
			<p>혼<br>잡<br>도<br>차<br>량<br>의</p>
			<p>갯<br>수</p>
			<p style="font-size: 12px;">(단위:천)</p>
		</div>
		<div id='chart' style='height: 400px;'></div>
		<div class="lbp-x-axis">기준 (출근/퇴근/주간/야간)</div>
	</div>
	<!-- 이미지 캡쳐 폼 -->
	<form method="POST" id="captureForm" name="captureForm">
	  <input type="hidden" name="img_val" id="img_val" value="" />
	</form>
	
	<!-- 다운로드 기능 -->
	<div id ="btnDiv" class="btnDiv" style="position: absolute;top: 550px; right: 300px;">
		<input type='button' id='btnDownload' class='btn btn-info btn-sm btn-rounded' style="width: 80px;padding-bottom: 5px;" value='다운로드'>
		<div id="btnIns" style="float: right;"></div>
	</div>
	
	<div style="padding: 0 100px;">
		<table id="tableList" class="table table-bordered" style="width: 100%;">
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
