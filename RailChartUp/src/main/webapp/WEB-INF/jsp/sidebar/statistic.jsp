<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function() {
		console.log("사이드메뉴 통계 진입 goUrl : "+goUrl);
		var ndt = moment().format("YYYY-MM-DD");
		fid = trainOne(ndt);
		if(goUrl=="/statistic/dayStc.do"){
			//console.log("사이드바 주소 단일편성시");
			$("#trainDiv").show();
		}else{
			//console.log("사이드바 주소 단일편성이 아닐 시");
			$("#trainDiv").hide();
		}
		
		// 메뉴 항목 클릭 시
		$(".sidebar-nonbt .goUrlMenu").on("click",function(){
			goMenuSite(this);
		});
		
		//기존에 액티브 되있던 항목 초기화(소메뉴)
		$(".sidebar-nonbt li li").each(function(i,list){
			if($(this).attr('id')==goUrl){
				$(this).css('background','#d2d2d2');
				$(this).children('a').css('color','black');
			}else{
				$(this).css('background','#111');
				$(this).children('a').css('color','#fff');
			}
		});

		//활성화 가능한 td(편성) 선택 시	
		$("#trainTbd").on("click","td",function(){
			//console.log("선택한 편성번호 : "+$(this).attr('id'));
			if($(this).attr('class')=="tn-y"){
				var $old=$("#trainTbd").find('.tn-active');
				$old.attr('class','tn-y');
				$(this).attr('class','tn-active');
				fid = $(this).attr('id');
				
				//console.log("편성 갱신");
			 	var txtDt=$("#sdate").val();
			 	renewalRule("day");
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
				//console.log("단일편성 탭 클릭");
				$("#trainOne").addClass('active');
				$('#trainAll').removeClass('active');
				$("#trainNum").removeClass("disabledbutton");
				//선택된 열차를 fid로
				if($("#trainTbd tr").find(".tn-active").attr('id')!==undefined){
					fid=$("#trainTbd tr").find(".tn-active").attr('id');
				}else{
					fid=0;
				}
			 	var txtDt=$("#sdate").val();
			 	renewalRule("day");
			}
		});
		
		//전체편성 탭 클릭
		$("#trainAll").on("click",function(){
			////console.log($(this).attr('class'));
			if($(this).attr('class')!="active"){
				//console.log("전체편성 탭 클릭");
				fid=null;
				$("#trainAll").addClass('active');
				$('#trainOne').removeClass('active');
				$("#trainNum").addClass("disabledbutton");
				//console.log("편성 갱신");
			 	var txtDt=$("#sdate").val();
			 	renewalRule("day");
			}
		});
		
	});
</script>
<div id="sidebar-nonbt" class="sidebar-nonbt">
	<ul>
		<li>
			<h2>혼잡도 통계</h2>
			<ul>
				<li id="/statistic/dayStc.do" class="goUrlMenu"><a>일일 통계</a></li>
				<li id="/statistic/mdStc.do" class="goUrlMenu"><a>월간(일별) 통계</a></li>
				<li id="/statistic/monthStc.do" class="goUrlMenu"><a>월별 통계</a></li>
				<li id="/statistic/yearStc.do" class="goUrlMenu"><a>연도별 통계</a></li>
				<li id="/statistic/seasonStc.do" class="goUrlMenu"><a>계절 통계</a></li>
				<li id="/statistic/dataLog.do" class="goUrlMenu"><a>로그 데이터 관리</a></li>
			</ul>
		</li>
	</ul>
</div>

<div id="trainDiv">
	<div class="train-div">
		<div class="settingMenu">
			<ul class="settingTabBtn">
				<li class="tab_1"><a class="active" id="trainOne">단일 열차</a></li>
				<li class="tab_2"><a id="trainAll">전체 열차</a></li>
			</ul>
		</div>
		<div id="trainNum">
			<table id="trainTb" class="train-table">
				<tbody id="trainTbd"></tbody>
			</table>
		</div>
	</div>
	
	<%-- <div>
		<table id="trainCm" class="train-comm">
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/trainA-c.png">선택된 열차</td>
				<td><img src="<%=request.getContextPath()%>/images/train-c.png">선택가능</td>
				<td><img src="<%=request.getContextPath()%>/images/trainN-c.png">데이터 없음</td>
			</tr>
		</table>
	</div> --%>
</div>
