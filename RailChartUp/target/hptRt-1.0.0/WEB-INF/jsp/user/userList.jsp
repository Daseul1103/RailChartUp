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
	var updUrl="/user/userUpdate.do";
	var delUrl="/user/userDelete.ajax";
	var delbak="/user/userList.do";
	$(document).ready( function() {
		iidx = 3;
		console.log("사용자 목록 화면 진입");
		  
		var idxTb =0;
		
		var tb=$("#tableList").DataTable({
			
			ajax : {
                "url":"/user/userList.ajax",
                "type":"POST",
                "dataType": "json",
            },  
            columns: [
            	{
            		data:   "userId",
                    render: function (data) {
                        return '<input type="checkbox" id="chk" name="chk" value="'+data+'">';
                    }
            		/* ,className: "select-checkbox" */
                },
                {data:"userId"},
                {data:"userName"},
                {data:"regDt"}
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
		
		//날짜 검색 기능 추가
		/* $('#tableList_filter').prepend('<label style="margin-right: 50px;"><div class="input-group date" id="datetimepicker2" style="width: 256px;"><input type="text" id="toDate" ><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></label>');
 	    $('#tableList_filter').prepend('<label><div class="input-group date" id="datetimepicker1" style="width: 256px;"><input type="text" id="fromDate" placeholder="날짜를 선택해주세요 ->"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span><p style="margin-left:15px; margin-bottom:0px">~</p></div></label>');
		 */
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
			console.log("데이터테이블 값 변경");
			//인덱스 번호 재설정
			$('#tableList input:checkbox[name="chk"]').each(function(i,list) {
				$(this).attr("id","chk"+i)
			});
			
			//행개수에 따라 수정삭제버튼 생성여부
			//행 개수 0개일때
			if($('input:checkbox[name="chk"]').length !=0 && typeof $('input:checkbox[name="chk"]').length !== "undefined"){
				if(typeof $("#btnUpdate").val()==="undefined"){
					$("#btnIns").append("<input type='button' id='btnUpdate' class='btn btn-info btn-sm btn-rounded' value='수정' onclick='tbUpdate(this,updUrl)'>");
				}
				if(typeof $("#btnDelete").val()==="undefined"){
					$("#btnIns").append("<input type='button' id='btnDelete' class='btn btn-info btn-sm btn-rounded' value='삭제' onclick='tbDelete(this,delUrl,delbak)'>");
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
			$("#content").load("/user/userInsert.do");
		});
		
		//상세 화면 조회
		$("#tableList").on("click", "tbody td:not(':first-child')", function(){
			console.log("목록에서 상세요소 클릭");
			var tagId = $(this).parent().children().first().children().first().val();
				$(this).attr('id');
				if(tagId!="chkTd"){
					$("#content").load("/user/userDetail.do",{"userId":tagId}); 
				}
		});
		

		//데이트타임피커
		/* var toDate = new Date();
		 $('#datetimepicker1').datetimepicker({
			 format:"YYYY-MM-DD" ,
			 //defaultDate:moment().subtract(6, 'months'),
			 maxDate : moment()
		}).on('dp.change', function (e) {
			calculDate();
			tb.draw();
		});
		 $('#datetimepicker2').datetimepicker({
			 format:"YYYY-MM-DD",
			 defaultDate:moment()
			 ,maxDate : moment()
		}).on('dp.change', function (e) {
			calculDate();
			tb.draw();
		}); */
		 
	});
</script>
</head>
<body>
	<div class="title">
		<h3>사용자 목록</h3>
	</div>
	<div class="page-description">
		<div class="rows">
			<table id="tableList" class="table table-bordered" style="width: 100%;">
				<thead>
					<tr>
						<th><input type="checkbox" id="chkAll" class="chk"></th>
						<th>ID</th>
						<th>사용자명</th>
						<th>등록일</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id ="btnDiv" class="btnDiv" style="margin: 30px 200px 0 0;">
		<input type='button' id='btnInsert' class='btn btn-info btn-sm btn-rounded' value='등록'>
		<div id="btnIns" style="float: right;"></div>
	</div>
</body>
</html>