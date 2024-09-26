<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function() {
		//console.log("사이드메뉴 차상단말기 진입");
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
			<h2>열차 관리</h2>
			<ul>
				<li id="/terminal/terminalList.do" class="goUrlMenu"><a>열차 목록</a></li>
				<li id="/terminal/terminalInsert.do" class="goUrlMenu"><a>열차 등록</a></li>
				<li id="/terminal/directRegularStd.do" class="goUrlMenu"><a>직통/일반 기준값 관리</a></li>
			</ul>
		</li>
	</ul>
</div>
