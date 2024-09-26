<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function() {
		//console.log("사이드메뉴 설정 진입 : "+goUrl);
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
	});
</script>
<div id="sidebar-nonbt" class="sidebar-nonbt">
	<ul>
		<li>
			<h2>설정</h2>
			<ul>
				<li id="/setting/timeRange.do" class="goUrlMenu"><a>시간/기간 범위 설정</a></li>
				<li class="eff-none"><a>혼잡도 변위 설정</a></li>
					<li id="/setting/dayCrowdRange.do" class="goUrlMenu"><a> > 일일 혼잡도 변위</a></li>
					<li id="/setting/mdCrowdRange.do" class="goUrlMenu"><a> > 월간(일별) 혼잡도 변위</a></li>
					<li id="/setting/monCrowdRange.do" class="goUrlMenu"><a> > 월별 혼잡도 변위</a></li>
					<li id="/setting/yearCrowdRange.do" class="goUrlMenu"><a> > 연도별 혼잡도 변위</a></li>
					<li id="/setting/seaCrowdRange.do" class="goUrlMenu"><a> > 계절별 혼잡도 변위</a></li>
			</ul>
		</li>
	</ul>
</div>
