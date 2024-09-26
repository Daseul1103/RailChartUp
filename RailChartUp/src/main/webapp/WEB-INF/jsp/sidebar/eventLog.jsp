<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function() {
		//console.log("사이드메뉴 이벤트 로그 진입");
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
			<h2>로그 조회</h2>
			<ul>
				<li id="/eventLog/eventLogList.do" class="goUrlMenu"><a>이벤트로그 조회</a></li>
				<li id="/eventLog/tcmsRcvList.do" class="goUrlMenu"><a>TCMS 수신 로그 조회</a></li>
				<li id="/eventLog/hseSndList.do" class="goUrlMenu"><a>HSE 송신 로그 조회</a></li>
			</ul>
		</li>
	</ul>
</div>