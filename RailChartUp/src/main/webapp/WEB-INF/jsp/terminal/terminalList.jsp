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
	var updUrl="/terminal/terminalUpdate.do";
	var delUrl="/terminal/terminalDelete.ajax";
	var delbak="/terminal/terminalList.do";
	$(document).ready( function() {
		iidx = 3;
		//console.log("차상단말기 목록 화면 진입");
		  
		var idxTb =0;
		
		var tb=$("#tableList").DataTable({
			
			ajax : {
                "url":"/terminal/terminalList.ajax",
                "type":"POST",
                "dataType": "json",
            },  
            columns: [
            	{
            		data:   "trainNum",
                    render: function (data) {
                        return '<input type="checkbox" id="chk" name="chk" value="'+data+'">';
                    }
            		/* ,className: "select-checkbox" */
                },
                {data:"idx"},
                {data:"trainNum"},
                //전 후열 ip 주석 처리
                /* {data:"fIp"},
                {data:"tIp"}, */
                {data:"carCnt"},
                {data:"raceYn"}
            ],
            "lengthMenu": [ [5, 10, 20], [5, 10, 20] ],
          //"pageLength": 5,
            pagingType : "full_numbers",
            columnDefs: [ 
            	{ orderable: false, targets: [0] }//특정 열(인덱스번호)에 대한 정렬 비활성화
            	,{className: "dt-center",targets: "_all"} 
            	,{name: "test-name",targets: [1]} 
            ],
            select: {
                style:    'multi',
                selector: 'td:first-child'
            },
            order: [[ 1, 'asc' ]]
            ,responsive: true
           ,language : selectlang //lang_kor //or lang_eng
		});
		
		//체크박스 클릭 시 이벤트
		$("#tableList").on("click", 'input:checkbox', function() {
			chkBoxFunc(this);
		});
		//마우스 올릴시 
		$("#tableList").on("mouseenter", "tbody tr", function(){
			$(this).addClass('active');
		});
		//마우스 내릴시
		$("#tableList").on("mouseleave", "tbody tr", function(){
			$(this).removeClass('active');
		});
		
		//체크박스영역 제외 마우스 올릴시 포인터로
		$("#tableList").on("mouseleave", "tbody td:not(':first-child')", function(){
			$(this).css('cursor','pointer');
		});
		
		
		//페이지 이동이나 열 개수 변경시 전체체크박스 관련 이벤트
		$('#tableList').on('draw.dt', function(){
			////console.log("데이터테이블 값 변경");
			//인덱스 번호 재설정
			$('#tableList input:checkbox[name="chk"]').each(function(i,list) {
				$(this).attr("id","chk"+i)
			});
			
			//행개수에 따라 수정삭제버튼 생성여부
			//행 개수 0개일때
			if($('input:checkbox[name="chk"]').length !=0 && typeof $('input:checkbox[name="chk"]').length !== "undefined"){
				if(typeof $("#btnUpdate").val()==="undefined"){
					$("#btnIns").append("<input type='button' id='btnUpdate'  value='수정' onclick='tbUpdate(this,updUrl)'>");
				}
				if(typeof $("#btnDelete").val()==="undefined"){
					$("#btnIns").append("<input type='button' id='btnDelete'  value='삭제' onclick='tbDelete(this,delUrl,delbak)'>");
				}
			}else{
				$("#btnIns").empty();	
			}
			
			if($('input:checkbox[name="chk"]:checked').length==$("tbody tr").length){
	    		$("#chkAll").prop("checked", true);
	    	}else{
	    		$("#chkAll").prop("checked", false);
	    	}
		});


		//등록 화면 조회
		$("#btnInsert").click(function() {
			$("#content").load("/terminal/terminalInsert.do");
		});
		
	});
</script>
</head>
<body>
	<div class="title">
		<h3>열차 목록</h3>
	</div>
	<div class="page-description">
		<div class="rows">
			<table id="tableList" class="table table-bordered" style="width: 100%;">
				<thead>
					<tr>
						<th><input type="checkbox" id="chkAll" class="chk"></th>
						<th>No</th>
						<th>열차번호</th>
						<!-- //전 후열 ip 주석 처리 
						<th>전열 IP</th>
						<th>후열 IP</th> -->
						<th>량 수</th>
						<th>금일 운행여부</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id ="btnDiv" class="btnDiv" style="margin: 30px 200px 0 0;">
		<input type='button' id='btnInsert'  value='등록'>
		<div id="btnIns" style="float: right;"></div>
	</div>
</body>
</html>