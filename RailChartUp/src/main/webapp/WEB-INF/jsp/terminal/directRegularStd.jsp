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
	var updUrl="/terminal/directRegularStdUpdate.do";
	$(document).ready( function() {
		iidx = 3;
		//console.log("사용자 목록 화면 진입");
		  
		var idxTb =0;
		var tb=$("#tableList").DataTable({
			
			ajax : {
                "url":"/terminal/directRegularStd.ajax",
                "type":"POST",
                "dataType": "json",
            },  
            columns: [
            	{
            		data:   "carType",
                    render: function (data) {
                        return '<input type="checkbox" id="chk" name="chk" value="'+data+'">';
                    }
                }
                ,{data:"carType"}
                ,{data:"st_wgt_1"}
                ,{data:"st_wgt_2"}
                ,{data:"st_wgt_3"}
                ,{data:"st_wgt_4"}
                ,{data:"st_wgt_5"}
                ,{data:"st_wgt_6"}
            ],
            "lengthMenu": [ [5, 10, 20], [5, 10, 20] ],
          //"pageLength": 5,
            pagingType : "full_numbers",
            columnDefs: [ 
            	{ orderable: false, targets: [0] }//특정 열(인덱스번호)에 대한 정렬 비활성화
            	,{className: "dt-center",targets: "_all"} 
            ],
            select: {
                style:    'multi',
                selector: 'td:first-child'
            },
            order: [[ 3, 'desc' ]]
            ,responsive: true
           ,language : selectlang //lang_kor //or lang_eng
		});
		
		$("input[type=search]").parent().hide();
		
		/* //마우스 올릴시 
		$("#tableList").on("mouseenter", "tbody tr", function(){
			$(this).addClass('active');
		});
		//마우스 내릴시
		$("#tableList").on("mouseleave", "tbody tr", function(){
			$(this).removeClass('active');
		}); */
		
		//체크박스영역 제외 마우스 올릴시 포인터로
		$("#tableList").on("mouseleave", "tbody td:not(':first-child')", function(){
			$(this).css('cursor','pointer');
		});
		
		//페이지 이동이나 열 개수 변경시 전체체크박스 관련 이벤트
		$('#tableList').on('draw.dt', function(){
			//console.log("데이터테이블 값 변경");
			//인덱스 번호 재설정
			$('#tableList input:checkbox[name="chk"]').each(function(i,list) {
				$(this).attr("id","chk"+i)
			});
			
			//행개수에 따라 수정삭제버튼 생성여부
			//행 개수 0개일때
			if($('input:checkbox[name="chk"]').length !=0 && typeof $('input:checkbox[name="chk"]').length !== "undefined"){
				if(typeof $("#btnUpdate").val()==="undefined"){
					$("#btnIns").append("<input type='button' id='btnUpdate' value='수정' onclick='tbUpdate(this,updUrl)'>");
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

	});
</script>
</head>
<body>
	<div class="title">
		<h3>직통/일반열차 기준값 관리 목록</h3>
	</div>
	<div class="page-description">
		<div class="rows">
			<table id="tableList" class="table table-bordered" style="width: 100%;">
				<thead>
					<tr>
						<th><!-- <input type="checkbox" id="chkAll" class="chk"> --></th>
						<th>열차 타입</th>
						<th>1호차</th>
						<th>2호차</th>
						<th>3호차</th>
						<th>4호차</th>
						<th>7호차</th>
						<th>8호차</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id ="btnDiv" class="btnDiv" style="margin: 30px 200px 0 0;">
		<div id="btnIns" style="float: right;"></div>
	</div>
</body>
</html>