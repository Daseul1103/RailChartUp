<%--
	Class Name : mainContents.jsp
	Description : 메인화면 내용
	Modification Information
	수정일         	수정자        수정내용
	----------  ------ ---------------------------
	2020.11.30     정다빈	최초 생성
	author : 미래전략사업팀 정다빈
	since : 2020.11.30 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
	$(document).ready(function(){
		
		//console.log("hse 송신로그  화면 진입");
		iidx = 0;
		
		var nowLogDt = moment().format("YYYY-MM-DD");
		var ajaxData = ajaxMethod("/eventLog/hseSndList.ajax", {"sDate":nowLogDt,"eDate":nowLogDt, "tagId":1});

		var apStr=filterSbT();
		
		var tb=$("#tableList").DataTable({
            data:ajaxData.data,
            columnDefs: [ 
                {className: "dt-center",targets: "_all"}
                ,{
                    'targets': 0,
                    'createdCell':  function (td, cellData, rowData, row, col) {
                       $(td).attr('id', 'datetime'); 
                       $(td).css("font-size","10px"); 
                       
                    }
                 }
                ,{
                    'targets': 3,
                    'createdCell':  function (td, cellData, rowData, row, col) {
                       $(td).attr('id', 'datetime'); 
                       $(td).css("font-size","10px"); 
                    }
                 }
             ],
            columns: [
            	{"data":"sndDt"},
                {"data":"carNum"},
                {"data":"trainNum"},
                {"data":"rcvDt"},
                {"data":"trainType"},
                {"data":"trainDirect"},
                {"data":"avgCrwd"},
                {"data":"carWgt1"},
                {"data":"carWgt2"},
                {"data":"carWgt3"},
                {"data":"carWgt4"},
                {"data":"carWgt5"},
                {"data":"carWgt6"},
                {"data":"carWgt7"},
                {"data":"carWgt8"}
            ],
            order: [[ 0, 'desc' ]],
            "lengthMenu": [ [20, 50, 100], [20, 50, 100] ]
            ,language : selectlang //lang_kor //or lang_eng
            ,buttons:['excel']
		});
		
		//$("#tableList th").css("font-size","10px");
		
		//날짜 검색 기능 추가
		$('#tableList_filter').prepend('<div id ="btnDiv" class="btnDiv" style="margin:0;"><input type="button" id="btnDownload"  style="width: 80px;padding-bottom: 5px;" value="다운로드"></div>');
		$('#tableList_filter').prepend('<div id="filterSbDiv" style="margin-right:50px;"> 열차번호 : '+apStr+' <div>');
		$('#tableList_filter').prepend('<label style="margin-bottom: 0px; margin-right: 50px;"><div class="input-group date" id="datetimepicker1" style="width: 256px;"><input type="text" id="fromDate"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></label>');
 	    
		$("input[type=search]").parent().hide();
		
		//데이트타임피커
		var toDate = new Date();
		 $('#datetimepicker1').datetimepicker({
			 format:"YYYY-MM-DD" ,
			 defaultDate:moment(),
			 maxDate : moment()
		}).on('dp.change', function (e) {
			//calculDate();
			$('#tableList').dataTable().fnClearTable(); 
			var nwData2 = ajaxMethod("/eventLog/hseSndList.ajax", {"sDate":$("#fromDate").val(),"eDate":$("#toDate").val(),"tagId":$("#filterSb").val()});
			if(nwData2.data.length!=0){
				$('#tableList').dataTable().fnAddData(nwData2.data);
			}
			//tb.draw();
		});
		 /* $('#datetimepicker2').datetimepicker({
			 format:"YYYY-MM-DD",
			 defaultDate:moment(),
			 maxDate : moment()
		}).on('dp.change', function (e) { 
			//calculDate();
			$('#tableList').dataTable().fnClearTable(); 
			var nwData2 = ajaxMethod("/eventLog/hseSndList.ajax", {"sDate":$("#fromDate").val(),"eDate":$("#toDate").val(),"tagId":$("#filterSb").val()});
			if(nwData2.data.length!=0){
				$('#tableList').dataTable().fnAddData(nwData2.data);
			}
			//tb.draw();
		}); */
		 
		 calculDate();

		 //열차번호 변경 시
		 $("#filterSb").on("change", function(){
			 $('#tableList').dataTable().fnClearTable(); 
			 var nwData = ajaxMethod("/eventLog/hseSndList.ajax", {"sDate":$("#fromDate").val(),"eDate":$("#toDate").val(),"tagId":$("#filterSb").val()});
			if(nwData.data.length!=0){
				$('#tableList').dataTable().fnAddData(nwData.data);
			}
		 });
		 
		//다운로드 버튼 클릭 시
		$("#btnDownload").click(function() {
			//console.log("다운로드 버튼 클릭");
			//엑셀 다운로드 후 언로드 방지
			rkFlag = true;
			c3Title="hse";
			c3TagId="_("+$("#fromDate").val()+"~"+$("#toDate").val()+")";
 			var frm = document.captureForm;
			frm.action =  "/eventLog/download/"+c3Title+c3TagId+".ajax";
			frm.submit();
			//ajaxMethod("/eventLog/download/"+c3Title+c3TagId+".ajax");
			//console.log("일일통계 페이지 차트다운완료");
		});
	});//ready
</script>
</head>
<body>
	<div class="title">
		<h3>HSE 송신 로그 조회</h3>
	</div>
	<div class="page-description">
		<div class="rows">
			<table id="tableList" class="table table-bordered" style="width: 100%;">
				<thead>
					<tr>
						<th>송신<br>시간</th>
						<th>열차<br>번호</th>
						<th>운행<br>번호</th>
						<th>수신<br>시간</th>
						<th>열차<br>타입</th> 
						<th>열차<br>방향</th> 
						<th>평균<br>혼잡도</th> 
						<th>차량<br>무게1</th>
						<th>차량<br>무게2</th>
						<th>차량<br>무게3</th>
						<th>차량<br>무게4</th>
						<th>차량<br>무게5</th>
						<th>차량<br>무게6</th>
						<th>차량<br>무게7</th>
						<th>차량<br>무게8</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
		
	<form method="POST" id="captureForm" name="captureForm">
	</form>
	
	<!-- <div id ="btnDiv" class="btnDiv" style="margin: 30px 50px 0 0;">
		<input type='button' id='btnDownload'  style="width: 80px;padding-bottom: 5px;" value='다운로드'>
		<div id="btnIns" style="float: right;"></div>
	</div> -->
</body>
</html>