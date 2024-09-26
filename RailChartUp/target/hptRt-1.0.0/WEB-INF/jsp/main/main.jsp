<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
	var loginId;
	var chart;
	var goUrl;
	
	//데이터 테이블 관련
	var iidx;//날짜컬럼 인덱스
	var selectlang;
	var lang_kor;
	var lang_eng;
	var fid;//단일편성 id
	
	//통계 관련
	var titleArr;
	var rlxArr;
	var usArr;
	var careArr;
	var cwdArr;
	
	$(document).ready(function() {
		loginId = '${login.userId}';
		var loginName='${login.userName}';
		
		//데이터테이블 셋팅
		dtTbSetting();
		selectlang=lang_kor;
		
		console.log("메 인 화 면 : "+loginId);
		console.log("메 인 화 면 : "+loginName);
		$("#content").load("/main/mainContents.do");
		
		//로고 클릭 시 메인화면
		$("#header").on("click", function(){
			$("#changeBody").empty();
			$("#changeBody").load("${path}");
		});
		
		//대메뉴 관련
		$(".menu li").hover(function(){
		    $('ul:first',this).show();
		  }, function(){
		    $('ul:first',this).hide();
		  });
		  $(".menu>li:has(ul)>a").each( function() {
		    $(this).html( $(this).html()+' &or;' );
		  });
		  $(".menu ul li:has(ul)")
		    .find("a:first")
		    .append("<p style='float:right;margin:-3px'>&#9656;</p>");
		  
		// 메뉴 항목 클릭 시
		$(".goUrlMenu").on("click",function(){
			goMenuSite(this);
		});

	});
</script>
	<div class="wrapper">
		<div id="header-wrapper">
			<div id="header"></div>
			<div class="login-user">
				<ul class="nav-icons" style="margin-top: 0px;">
					<li style="display: flex; width: 230px;">
						<i class="ion-person"></i>
						<div style="margin-left: 10px;">${login.userName}님 환영합니다.</div>
						<a href="${path}/user/logout.do" style="margin-left: 10px;">logout</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- end #header -->
		<div class="menu-bar" style="padding-left: 50px;">
			<ul class="menu">
				<!--<li class="current_page_item"><a href="#">Home</a></li>-->
				<li><!-- <a class="active">혼잡도 통계</a> -->
					<a id="statistic">혼잡도 통계</a>
					<ul>
						<li id="/statistic/dayStc.do" class="goUrlMenu"><a>일일 통계</a></li>
						<li id="/statistic/monthStc.do" class="goUrlMenu"><a>월별 통계</a></li>
						<li id="/statistic/yearStc.do" class="goUrlMenu"><a>연도별 통계</a></li>
						<li id="/statistic/seasonStc.do" class="goUrlMenu"><a>계절 통계</a></li>
						<li id="/statistic/dataLog.do" class="goUrlMenu"><a>로그 데이터 관리</a></li>
					</ul>
				</li>
				<li><a id="comuFail">통신장애 이력조회</a>
					<ul>
						<li id="/comuFail/comuFailList.do" class="goUrlMenu"><a>장애이력 목록</a></li>
					</ul>
				</li>
				<li><a id="terminal">차상단말기 관리</a>
					<ul>
						<li id="/terminal/terminalList.do" class="goUrlMenu"><a>차상단말기 목록</a></li>
						<li id="/terminal/terminalInsert.do" class="goUrlMenu"><a>차상단말기 등록</a></li>
					</ul>
				</li>
				<li><a id="user">사용자 관리</a>
					<ul>
						<li id="/user/userList.do" class="goUrlMenu"><a>사용자 목록</a></li>
						<li id="/user/userInsert.do" class="goUrlMenu"><a>사용자 등록</a></li>
					</ul>
				</li>
				<li><a id="setting">설정</a>
					<ul>
						<li id="/setting/timeRange.do" class="goUrlMenu"><a>시간/기간 범위 설정</a></li>
						<li><a>혼잡도 범위 설정</a>
							<ul>
								<li id="/setting/dayCrowdRange.do" class="goUrlMenu"><a>일일 혼잡도 범위</a></li>
								<li id="/setting/monCrowdRange.do" class="goUrlMenu"><a>월별 혼잡도 범위</a></li>
								<li id="/setting/yearCrowdRange.do" class="goUrlMenu"><a>연도별 혼잡도 범위</a></li>
								<li id="/setting/seaCrowdRange.do" class="goUrlMenu"><a>계절별 혼잡도 범위</a></li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- end #menu -->
		<div id="page">
			<div id="page-bgtop">
				<div id="page-bgbtm">

					<!-- end #content -->
					<div id="sideAll" class="sideDiv-nonbt">
						<div id="sideDiv"></div>
						<div id="trainDiv"></div>
					</div>

					<!-- contents -->
					<div id="content">
					</div>

					<!-- end #sidebar -->
					<div style="clear: both;">&nbsp;</div>
				</div>
			</div>
		</div>
		<!-- end #page -->

	<!-- 	<div id="footer">
			<p>
				&copy; Untitled. All rights reserved. Design by <a
					href="http://templated.co" rel="nofollow">TEMPLATED</a>.
			</p>
		</div> -->
		<!-- end #footer -->
	</div>
</html>