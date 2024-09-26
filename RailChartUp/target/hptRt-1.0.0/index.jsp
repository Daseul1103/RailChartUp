<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" user-scalable="no" />
		
		<title>혼잡도 관리 시스템</title>
		<!-- Custom style -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	
		<!-- Bootstrap -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/bootstrap/bootstrap.min.css">
 
 		<!-- IonIcons -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/ionicons/css/ionicons.min.css">
		<!-- Toast -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/toast/jquery.toast.min.css">
		<!-- OwlCarousel -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/owlcarousel/dist/assets/owl.carousel.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/owlcarousel/dist/assets/owl.theme.default.min.css">
		<!-- Magnific Popup -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/magnific-popup/dist/magnific-popup.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/sweetalert/dist/sweetalert.css">
		<!-- Custom style -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	
		<!-- JS -->
		<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.migrate.js"></script>
		<script src="<%=request.getContextPath()%>/js/demo.js"></script>
		<script  type="text/javascript" charset="utf-8"  src="<%=request.getContextPath()%>/js/common.js"></script>
		
		<!-- DataTable -->
		<link rel="stylesheet" type="text/css" href="DataTables/datatables.min.css"/>
		<script type="text/javascript" src="DataTables/datatables.min.js"></script>
		
		<!-- DateTimePicker -->
		<script src="<%=request.getContextPath()%>/calender/moment.js"></script>
		<script src="<%=request.getContextPath()%>/calender/mo_ko.js"></script>
		<script src="<%=request.getContextPath()%>/calender/bootstrap-datetimepicker.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/calender/datetimepickerstyle.css" />
		
		<!-- c3.js chart -->
		<link href="../css/normalize-e465cb86.css" media="screen" rel="stylesheet" type="text/css" />
		<!-- <link href="../css/foundation.min-978d4ce8.css" media="screen" rel="stylesheet" type="text/css" /> -->
		<link href="../css/tomorrow-d7cf0921.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="../css/c3-eb4b9be8.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="../css/style-99fb8989.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="../css/samples/chart_combination-da39a3ee.css" media="screen" rel="stylesheet" type="text/css" />
		
		<script src="../js/vendor/modernizr-2.6.1.min-68fdcc99.js" type="text/javascript"></script>
		<script src="../js/foundation.min-1dfe8110.js" type="text/javascript"></script>
		<script src="../js/highlight.pack-4af5004d.js" type="text/javascript"></script>
		<script src="../js/d3-5.8.2.min-c5268e33.js" type="text/javascript"></script>
		<script src="../js/c3.min-d2a1f852.js" type="text/javascript"></script>

		<script src=../js/saveSvgAsPng.js></script>
		<script src=../js/html2canvas.js></script>
		<script src=../js/html2canvas.js></script>
		<script src=../js/html2canvas.min.js></script>

		<script>
			var c3Title="";//일월연계 분기처리
			var c3TagId="";//기준 일시 또는 연, 월
			$(document).ready(function(){
				console.log("인덱스 페이지 들어감");
				
				var sessionVo = "<%=session.getAttribute("login") %>"
				
				console.log("세션체크 : "+sessionVo);
				if(sessionVo=="null"){
					console.log("로그인 세션X");//로그인 안되있음
					//로그인 페이지로 이동
					$("#changeBody").load("/user/login.do");
				}else{
					//메인 화면으로 이동
					$("#changeBody").load("/main/main.do");	
				}
			});
		</script>
</head>

	<body>
		<div id="changeBody"></div>
	</body>
</html>