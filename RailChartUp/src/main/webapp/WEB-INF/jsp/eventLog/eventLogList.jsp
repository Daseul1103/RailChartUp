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
<script>
	$(document).ready(function(){
		
		//console.log("이벤트 로그 화면 진입");
		iidx = 0;
		
		var tb=$("#tableList").DataTable({
			//"pageLength": 10,
			ajax : {
                "url":"/eventLog/eventLogList.ajax",
                "type":"POST",
                "dataType": "json",
            },  
            columnDefs: [ 
            	{className: "dt-center",targets: "_all"}
            	,{
                    'targets': 0,
                    'createdCell':  function (td, cellData, rowData, row, col) {
                    	
                    	////console.log("td, cellData, rowData, row, col : "+td+" / "+cellData+" / "+rowData+" / "+row+" / "+col);
                       $(td).attr('id', cellData); 
                       //$(td).val(cellData);
                       //$(td).css("font-size","10px"); 
                    }
                 }
            ],
            columns: [
                {"data":"evtTime"},
                {"data":"evtTgt"},
                {"data":"evtNum"},
                {"data":"evtName"},
                {"data":"comment"}
            ],
            order: [[ 0, 'desc' ]],
            "lengthMenu": [ [5, 10, 20], [5, 10, 20] ]
            ,language : selectlang //lang_kor //or lang_eng
		});
		//$("#tableList th").css("font-size","10px");
		
		//날짜 검색 기능 추가
		$('#tableList_filter').prepend('<label style="margin-bottom: 0px;margin-right: 50px;"><div class="input-group date" id="datetimepicker2" style="width: 256px;"><input type="text" id="toDate" ><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></label>');
 	    $('#tableList_filter').prepend('<label style="margin-bottom: 0px;"><div class="input-group date" id="datetimepicker1" style="width: 256px;"><input type="text" id="fromDate"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span><p style="margin-left:15px; margin-bottom:0px">~</p></div></label>');

		//데이트타임피커
		var toDate = new Date();
		 $('#datetimepicker1').datetimepicker({
			 format:"YYYY-MM-DD" ,
			 defaultDate:moment(),
			 maxDate : moment()
		}).on('dp.change', function (e) {
			calculDate();
			tb.draw();
		});
		 $('#datetimepicker2').datetimepicker({
			 format:"YYYY-MM-DD",
			 defaultDate:moment(),
			 maxDate : moment()
		}).on('dp.change', function (e) { 
			calculDate();
			tb.draw();
		});
		 calculDate();

		//상세 화면 조회
		/* $("#tableList").on("click", "tbody td", function(){
			//console.log("목록에서 상세요소 클릭");
			var tagId = $(this).attr('id');
				$("#content").load("/eventLog/eventLogDetail.do",{"evtTime":tagId}); 
		}); */
	});
</script>
</head>
<body>
	<div class="title">
		<h3>이벤트 로그 조회</h3>
	</div>
	<div class="page-description">
		<div class="rows">
			<table id="tableList" class="table table-bordered" style="width: 100%;">
				<thead>
					<tr>
						<th>발생일시</th>
						<th>발생대상</th>
						<th>이벤트타입</th>
						<th>발생항목명</th>
						<th>비고</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>