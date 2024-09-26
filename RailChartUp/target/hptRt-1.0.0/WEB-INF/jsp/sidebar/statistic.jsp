<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function() {
		console.log("사이드메뉴 통계 진입");
		// 메뉴 항목 클릭 시
		$(".goUrlMenu").on("click",function(){
			goMenuSite(this);
		});
		
		//기존에 액티브 되있던 항목 초기화(소메뉴)
		$(".sidebar-nonbt li li").each(function(i,list){
			if($(this).attr('id')==goUrl){
				$(this).css('background','#d2d2d2');
				$(this).children('a').css('color','black');
			}else{
				$(this).css('background','#fff');
				$(this).children('a').css('color','#808080');
			}
		});
	});
</script>
<div id="sidebar-nonbt" class="sidebar-nonbt">
	<ul>
		<li>
			<h2>통계 관리</h2>
			<ul>
				<li id="/statistic/dayStc.do" class="goUrlMenu"><a>일일 통계</a></li>
				<li id="/statistic/monthStc.do" class="goUrlMenu"><a>월별 통계</a></li>
				<li id="/statistic/yearStc.do" class="goUrlMenu"><a>연도별 통계</a></li>
				<li id="/statistic/seasonStc.do" class="goUrlMenu"><a>계절 통계</a></li>
				<li id="/statistic/dataLog.do" class="goUrlMenu"><a>로그 데이터 관리</a></li>
			</ul>
		</li>
	</ul>
</div>
