var timeRefresh=null;
var pastSide="";//이전 사이드바 메뉴

/************************************************************************
함수명 : startPing
설 명 : 로그인 시 web <->was 간 전송 타이머 생성
인 자 : 
사용법 : 
작성일 : 2020-09-01
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.09.01   정다빈       최초작성
************************************************************************/
/*function startPing(){
	//로그인 됬을 시
	//10초 간격으로 web에서 was로 ping을 전송하는 timer 생성 
	loginTimer = setInterval(function(){
		loginPing();
	}, 10000);

}*/

/*function getJSessionId(){
    var jsId = document.cookie.match(/JSESSIONID=[^;]+/);
    if(jsId != null) {
        if (jsId instanceof Array)
            jsId = jsId[0].substring(11);
        else
            jsId = jsId.substring(11);
    }
    return jsId;
}*/

/************************************************************************
함수명 : loginPing
설 명 : 로그인 시 web <-> was 간 핑 연계 기능 구현
인 자 : 
사용법 : 
작성일 : 2020-09-01
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.09.01   정다빈       최초작성
************************************************************************/
/*function loginPing(){
	var dd = new Date();
	//console.log("sessionId : "+getJSessionId());
	//console.log("서버로 핑 전송 요청 : "+dd.getHours()+":"+dd.getMinutes()+":"+dd.getSeconds()+"."+dd.getMilliseconds());
	var nowUrl = window.location.href;
	var ajaxData=ajaxMethod("/main/loginPing.ajax",{"loginId":loginId});
	if(typeof ajaxData.data === "undefined"  || ajaxData.data == "error"){
		location.replace(nowUrl);
		//로그아웃 시 타이머 종료
		endPing();
		alert("서버와의 연결이 끊어졌습니다");
	}else{
		if(ajaxData.data == "sessionEnd"){
			location.replace(nowUrl);
			alert("세션이 만료되었습니다 다시 로그인 해 주세요");
		}
	}
}*/

/************************************************************************
함수명 : endPing
설 명 : 로그아웃 시 타이머 종료
인 자 : 
사용법 : 
작성일 : 2020-09-01
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.09.01   정다빈       최초작성
************************************************************************/
/*function endPing(){
	clearInterval(loginTimer);
	loginTimer=null;
}*/

/************************************************************************
함수명 : ajaxMethod
설 명 : ajax 처리 함수 
인 자 : url(컨트롤러에 매핑된 주소), data(전송 값), 
	   callback(ajax 통신 후 이동 주소), 
	   message(전송 성공 시 메시지)
사용법 : 화면에서 서버로 ajax통신으로 값 전송시 사용
작성일 : 2020-07-30
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.07.30   정다빈       최초작성
************************************************************************/
function ajaxMethod(url, data, callback, message){
	var output = new Array();
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data : data,
		/*ajax는 비동기 식이지만 함수형태로 호출 시 success 이전에 
		*화면이 구성되어 값을 제대로 전송받지 못함
		*함수로 호출할 경우 false로 지정
		*/
		async : false,
		success : function(json) {
//			//console.log("아작스 서버 연동 성공");
//			//console.log(json);
//			//console.log(json.msg);
			if(json.msg==undefined){//서버 단에서 에러가 없을 경우
				if(message!="" && message!=undefined){
					alert(message);
				}
				//콜백 화면 이동 : div 값 변경일경우 사용
				//주소 자체 이동, body 전체 화면 변환일경우 수정 필요
				if(callback!="" && callback!=undefined){
					$("#content").load(callback);
				}
			}else{
				//검색 결과가 없는 것이라면
				if(json.msg="search_not"){
					json="";
				}else{
					alert(json.msg);
				}
			}
			output = json;
		},
		error : function() {
			//console.log("에러가 발생하였습니다.");
		},
		//finally 기능 수행
		complete : function() {

		}
	});
	return output;
}

/************************************************************************
함수명 : reloadOrKill
설 명 : ajax 처리 함수 
인 자 : url(컨트롤러에 매핑된 주소), data(전송 값), 
	   callback(ajax 통신 후 이동 주소), 
	   message(전송 성공 시 메시지)
사용법 : 화면에서 서버로 ajax통신으로 값 전송시 사용
작성일 : 2020-07-30
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.07.30   정다빈       최초작성
************************************************************************/
function reloadOrKill(isClose){
	//console.log("reloadOrKill 커먼 메소드 진입");
	ajaxMethod('/user/reloadOrKill.do',{"tagId":isClose});
}

/************************************************************************
함수명 : boardWriteCheck
설 명 : 입력정보 null 체크
인 자 : form
사용법 : 로그인 회원가입, 등록 등의 입력정보 체크시 사용
작성일 : 2020-07-30
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.07.30   정다빈       최초작성
************************************************************************/
function boardWriteCheck(form) {
	//특수문자 정규식
	var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	
	$(form).find('span').text("");	
	
	for (var i = 0; i < form.length; i++) {
		//id의 경우 6자 이상인지
		if(form[i].name =='userId'){
			if(form[i].value.length<6){
				alert("id 형식이 올바르지 않습니다.");
				form[i].focus();
				return false;

			}
		}
		//pw : (영문 특수문자 포함 8자이상 10자 이하)
		if(form[i].name =='userPw' && form[i].value.length >0){
			if(form[i].value.length<8 /*|| !(regExp.test(form[i].value)) */){
				alert("비밀번호 형식이 올바르지 않습니다.");
				form[i].focus();
				return false;
			}
		}
	}
	//확인 비밀번호와 비밀번호가 다를 때
	if($("#userPw").val()!=$("#userPw2").val()){
		alert("비밀번호가 서로 일치하지 않습니다.");
		return false;
	}
	return true;
}

/************************************************************************
함수명 : onlyNumber
설 명 : 숫자만 입력
인 자 : 
사용법 : 
작성일 : 2020-08-25
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.25   정다빈       최초작성
************************************************************************/
function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
        return;
    else
        return false;
}

/************************************************************************
함수명 : removeChar
설 명 : 불필요 문자열 제거
인 자 : 
사용법 : 
작성일 : 2020-08-25
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.25   정다빈       최초작성
************************************************************************/
function removeChar(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){
    	return;
    }else{
    	event.target.value = event.target.value.replace(/[^0-9]/g, "");
    }
}

/************************************************************************
함수명 : inputPtz
설 명 : 숫자만 입력가능하게 제한, 범위 제한
인 자 : 
사용법 : 
작성일 : 2020-09-11
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.25   정다빈       최초작성
 ************************************************************************/
function inputPtz(event,that) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){
		return;
	}else{
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
	}
	if(that.name=="zoom"){
		if(parseInt($(that).val())<0 || parseInt($(that).val())>20){
			event.target.value = event.target.value.replace("");
		}else{
			return;
		}
	}else{
		if(parseInt($(that).val())<0 || parseInt($(that).val())>90){
			event.target.value = event.target.value.replace("");
		}else{
			return;
		}
	}
}

/************************************************************************
함수명 : telChk
설 명 : 전화번호 체크
인 자 : 
사용법 : 
작성일 : 2020-08-25
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.25   정다빈       최초작성
 ************************************************************************/
function telChk() {
	//부분 전화번호에 하나라도 값 기입시
	if($("#userPhone2").val().length>0 || $("#userPhone3").val().length>0){
		//1,2,3번째가 지정 자리수 이상일때만 값 주입
		if($("#userPhone1").val().length>1 && $("#userPhone2").val().length>2 && $("#userPhone3").val().length>3){
			var phone = $("#userPhone1").val()+"-"+$("#userPhone2").val()+"-"+$("#userPhone3").val();
			$("#userPhone").val(phone);
		}else{
			alert("전화번호 형식이 올바르지 않습니다.");
			return false;
		}
	}else{
		$("#userPhone").val("");
	}
	return true;
}

/************************************************************************
함수명 : spaceChk
설 명 : 공백 및 특수문자를 입력방지해주는 함수(영문,숫자 입력 가능)
인 자 : 
사용법 : 
작성일 : 2020-08-25
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.25   정다빈       최초작성
************************************************************************/
function spaceChk(obj){//공백입력방지
	var str_space = /\s/; //공백체크변수선언
	
	//특수문자 정규식
	var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	
	if (str_space.exec(obj.value)){ //공백체크
        obj.focus();
        obj.value = obj.value.replace(' ',''); // 공백제거
        return false;	
	}
	//패스워드,시험코드 제외 특수문자 입력 불가
	if(!(obj.name=="userPw" || obj.name == "userPw2" || obj.name == "testCode")){
		if(regExp.test(obj.value)){
			obj.focus();
	        obj.value = obj.value.replace(obj.value,''); // 공백제거
	        return false;
		}
	}
	//이름,직급,부서, 회사명의 경우 제외하고 한글입력 불가능
	if(!(obj.name == "userName" || obj.name == "userRank"
		|| obj.name == "userDept" || obj.name == "empName")){
		//좌우 방향키, 백스페이스, 딜리트, 탭키에 대한 예외
		if(event.keyCode == 8  || event.keyCode == 9 
		|| event.keyCode == 37 || event.keyCode == 39){
			return false;
		}
		obj.value=obj.value.replace(/[ㄱ-ㅎㅏ-ㅡ가-핳]/g,'');
	}
	
}

/************************************************************************
함수명 : chkMenu
설 명 : 메뉴 관련 항목 페이지 이동 시 메뉴 선택 보여주기 
인 자 : 
사용법 : 
작성일 : 2020-12-30
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.12.30   정다빈       최초작성
************************************************************************/
function chkMenu(that){
	//console.log("대메뉴 항목 클릭 : "+$(that).attr('id'));
	
	//클릭한 항목 액티브
	//단일메뉴와 다중메뉴 분기
	if($(this).parent().parent().children('a').text().indexOf('혼잡도 범위 설정')!=-1){
		$(this).parent().parent().parent().parent().children('a').addClass('active');
	}else{
		$(this).parent().parent().children('a').addClass('active');
	}
}


/************************************************************************
함수명 : goMenuSite
설 명 : 메뉴 관련 항목 페이지 이동 
인 자 : 
사용법 : 
작성일 : 2020-07-30
작성자 : 미래전략사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.07.30   정다빈       최초작성
************************************************************************/
function goMenuSite(that){
	//타이머 변수 초기화
	clearInterval(timeRefresh);
	timeRefresh=null;
	goUrl= $(that).attr("id");
	console.log("goUrl : "+goUrl);
	
	//메인화면 제외 나머지 화면 여백
	$("#page-bgbtm").css("padding","40px 30px 0px 30px");
	
	$("#content").css("width","1400px");
	$("#content").empty();
	if(goUrl=="/statistic/dayStc.do"||typeof goUrl==="undefined"){
		$("#content").css("padding","0px");
		$("#content").css("padding-left","0px");
		$("#content").css("height","700px");
	}else{
		$("#content").css("padding","50px 30px 30px 50px");
	}
	
	//console.log("메뉴에서 이동 클릭");
	//console.log($(that).attr("id"));
	
	//기존에 액티브 되있던 항목 초기화(대메뉴)
	$(".menu li a").each(function(i,list){
		////console.log($(this).text());
		$(this).removeClass('active');
	});
	
	var nowSide=goUrl.split("/")[1];
	
	if(nowSide!=pastSide){
		//console.log("사이드바 변경");
		pastSide=nowSide;
		var sideUrl="/sidebar/"+nowSide+".do";
		$("#sideDiv").load(sideUrl);
	}else{
		$(".sidebar-nonbt li li").each(function(i,list){
			if($(this).attr('id')==goUrl){
				$(this).css('background','#d2d2d2');
				$(this).children('a').css('color','black');
			}else{
				$(this).css('background','#111');
				$(this).children('a').css('color','#fff');
			}
		});
	}
	
	//각 기능별 사이드메뉴 분기처리
	if(nowSide.indexOf("statistic")>-1){//사용자관리
		//console.log("통계 사이드바 사이드메뉴");
		$(".menu li").find("#statistic").addClass('active');
	}else if(nowSide.indexOf("eventLog")>-1){
		//console.log("통신장애 사이드메뉴");
		$(".menu li").find("#eventLog").addClass('active');
	}else if(nowSide.indexOf("terminal")>-1){
		//console.log("차상단말기 사이드메뉴");
		$(".menu li").find("#terminal").addClass('active');
	}else if(nowSide.indexOf("user")>-1){
		//console.log("사용자관리 사이드메뉴");
		$(".menu li").find("#user").addClass('active');
	}else{
		//console.log("설정 사이드메뉴");
		$(".menu li").find("#setting").addClass('active');
	}
	$("#content").load(goUrl);
}

/************************************************************************
함수명 : chkBoxFunc
설 명 : 테이블 내 체크박스 관련 이벤트 처리
인 자 : 테이블(this)를 받음(that으로)
사용법 : 
작성일 : 2020-08-17
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.17   정다빈       최초작성
************************************************************************/
function chkBoxFunc(that){
	var chkId=$(that).attr("id");
	var chkVal=$(that).val();
	var temp =[];//지금 체크한 체크박스에 관한 배열
	console.log("tbody의 체크된 체크박스 수 : "+$('input:checkbox[name="chk"]:checked').length);
	//클릭한게 전체선택일 경우
	if(chkId=="chkAll"){
		//console.log("전체선택");
		//체크된 경우
		if ($("#chkAll").is(":checked")){
			//console.log("전체 체크박스 선택됨");
			//console.log("현재 페이지 넘버 : ");
			//하위 체크박스들도 모두 선택
			$("tbody input:checkbox").prop("checked", true);
			
		}else{//선택->취소 : 전체 체크 해지시
			//console.log("전체 체크박스 해지됨");
		    $("tbody input:checkbox").prop("checked", false);
		}
		 
	}else{//단일 선택일 경우
		console.log("단일체크박스 클릭"+$(that).val());
		console.log("@@@체크된 값들 길이: "+$('input:checkbox[name="chk"]:checked').length);
		console.log("@@@tbody 길이: "+$("tbody tr").length);
		//단일선택->전체
		if($('input:checkbox[name="chk"]:checked').length==$("input:checkbox[name='chk']").length){
    		$("#chkAll").prop("checked", true);
    	}else{//단일해지->전체해지
    		$("#chkAll").prop("checked", false);
    	}
		if($(that).is(":checked")){//체크박스가 체크된 경우
			//console.log("단일선택"+$(that).val());
		}else{//체크 해지된 경우
			//console.log("단일해지"+$(that).val());
			
		}
	}
	
}

/************************************************************************
함수명 : chkArrValiF
설 명 : 체크박스 배열내 선택 구별 함수
인 자 : 
사용법 : 
작성일 : 2020-08-19
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.19   정다빈       최초작성
************************************************************************/
function chkArrValiF(objArr,chkVal){
	for(var i =0;i<objArr.length;i++){
		if(chkVal==objArr[i]){
			return false;
		}
	}
	return true;
}

/************************************************************************
함수명 : schChkKey
설 명 : 검색 값 유효성 검사
인 자 : 
사용법 : 
작성일 : 2020-08-30
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.30   정다빈       최초작성
 ************************************************************************/
function schChkKey(that,schFlag){
	var sndUrl='';
	var dat;
	//키워드가 무엇인지 판별
	var schId= $(that).attr("name").split("_")[1];
	//텍스트에 값을 입력 안했다면
	if(schId!="empCode" && $(that).parent().children().first().val()==""){
		$(that).parent().parent().children().last().css("color","red");
		$(that).parent().parent().children().last().text("값을 입력해주세요");
	}else{
		//검색버튼이 2개이상일때는 어디로 보낼지 값이 무엇인지 분기처리
		if(schId=="userId"){
			sndUrl="/user/findUserId.do";
			dat={"userId":$(that).parent().children().first().val()};
			
			var schData=ajaxMethod(sndUrl, dat);
			//id일 경우는 값이 없을때 사용가능하고 시험코드는 값이 있을때 사용가능함
			if(schData == "" || typeof schData.data === "undefined"){//db에 값 미존재
				$(that).parent().parent().children().last().css("color","blue");
				$(that).parent().parent().children().last().text("사용 가능한 id입니다.");
				schFlag=true;
			}else{//db에 값 존재
				$(that).parent().parent().children().last().css("color","red");
				$(that).parent().parent().children().last().text("이미 사용중인 id입니다.");
				schFlag=false;
			}
		}
	}
	return schFlag;
}

/************************************************************************
함수명 : tbUpdate
설 명 : 수정 기능 함수(동적 버튼)
인 자 : that(테이블 this), paramUrl(이동할 주소)
사용법 : 
작성일 : 2020-08-24
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.24   정다빈       최초작성
************************************************************************/
function tbUpdate(that,paramUrl){
	var tagId;
	var cnt = $('input:checkbox[name="chk"]:checked').length;
	if($('input:checkbox[name="chk"]:checked').length==0){
		alert("수정할 항목을 선택해 주세요");
	}else if($('input:checkbox[name="chk"]:checked').length!=1){
		alert("수정할 항목을 하나만 선택해 주세요");
	}else{
		for(i=0;i<$("tbody tr").length;i++){
			if($("#chk"+i).is(":checked")){
				tagId = $("#chk"+i).val();
			}
		}
		if(paramUrl.indexOf("user")!=-1){
			$("#content").load(paramUrl,{"userId":tagId});
		}
		if(paramUrl.indexOf("terminal")!=-1){
			if(paramUrl.indexOf("directRegularStd")!=-1){
				$("#content").load(paramUrl,{"carType":tagId});
			}else{
				$("#content").load(paramUrl,{"trainNum":tagId});
			}
		}
		
		 
	}
}

/************************************************************************
함수명 : tbDelete
설 명 : 삭제 기능 함수(동적 버튼)
인 자 : that(테이블 this), paramUrl(이동할 주소)
사용법 : 
작성일 : 2020-08-24
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.24   정다빈       최초작성
************************************************************************/
function tbDelete(that,paramUrl,callback,tb){
	var idArr=[]; 

	for(i=0;i<$("tbody tr").length;i++){
		if($("#chk"+i).is(":checked")){
			idArr.push($("#chk"+i).val());//배열에 아이디 값 삽입
		}
	}
	if(typeof idArr.length==="undefined" || idArr.length==0){
		alert("삭제할 항목을 선택해 주세요");
	}else{
		if(goUrl=="/statistic/dataLog.do"){
			if(confirm("선택하신 항목을 삭제하시겠습니까? \n <주의> 삭제하신 데이터는 복구할 수 없습니다!")==true){
				var url=paramUrl;
				var data = {"idArr":idArr};
				ajaxMethod(url, data);
				chkArr=[];
				if (callback=="tcms") {
					console.log("콜백 tcms");
					tb.ajax.url("/statistic/dataLogList/tcms.do").load();
				} else {
					console.log("콜백 hse");
					tb.ajax.url("/statistic/dataLogList/hse.do").load();
				}
			}
		}else{
			if(confirm("선택하신 항목을 삭제하시겠습니까?")==true){
				var url=paramUrl;
				var data = {"idArr":idArr};
				ajaxMethod(url, data, callback);
				chkArr=[];
			}
		}
	}
}


/************************************************************************
함수명 : calculDate
설 명 : 삭제 기능 함수(동적 버튼)
인 자 : idx(날짜가 존재하는 칼럼)
사용법 : 
작성일 : 2020-08-24
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.26   정다빈       최초작성
************************************************************************/
function calculDate(){
	//console.log("계산함수 들어옴 idx : "+iidx);
	$.fn.dataTable.ext.search.push(
        function(settings, data, dataIndex){
		////console.log("계산함수 들어옴 idx4 : "+iidx);
            var min = Date.parse($('#fromDate').val()+" 00:00:00");
            var max = Date.parse(moment($('#toDate').val()).add(1,'days').format('YYYY-MM-DD'))
            var targetDate = Date.parse(data[iidx]);

            if( (isNaN(min) && isNaN(max) ) || 
                (isNaN(min) && targetDate <= max )|| 
                ( min <= targetDate && isNaN(max) ) ||
                ( targetDate >= min && targetDate <= max) ){ 
                    return true;
            }
            return false;
        }
    )
}

function dtTbSetting(){
    var col_eng = [
        { title: "Name" },
        { title: "Position" },
        { title: "Office" },
        { title: "Extn." },
        { title: "Start date" },
        { title: "Salary" }
    ];
 
    var col_kor = [
        { title: "이름" },
        { title: "직위" },
        { title: "오피스" },
        { title: "내선" },
        { title: "입사일" },
        { title: "연봉" }
    ];
 
    // DataTables Default
    lang_eng = {
        "decimal" : "",
        "emptyTable" : "No data available in table",
        "info" : "Showing _START_ to _END_ of _TOTAL_ entries",
        "infoEmpty" : "Showing 0 to 0 of 0 entries",
        "infoFiltered" : "(filtered from _MAX_ total entries)",
        "infoPostFix" : "",
        "thousands" : ",",
        "lengthMenu" : "Show _MENU_ entries",
        "loadingRecords" : "Loading...",
        "processing" : "Processing...",
        "search" : "Search : ",
        "zeroRecords" : "No matching records found",
        "paginate" : {
        	 "first" : "<<",
             "last" : ">>",
             "next" : ">",
             "previous" : "<"
        },
        "aria" : {
            "sortAscending" : " :  activate to sort column ascending",
            "sortDescending" : " :  activate to sort column descending"
        }
    };
 
    // Korean
    lang_kor = {
        "decimal" : "",
        "emptyTable" : "데이터가 없습니다.",
        "info" : "_START_ - _END_ (총 _TOTAL_ 행)",
        "infoEmpty" : "0행",
        "infoFiltered" : "(전체 _MAX_ 행 중 검색결과)",
        "infoPostFix" : "",
        "thousands" : ",",
        "lengthMenu" : "_MENU_ 개씩 보기",
        "loadingRecords" : "로딩중...",
        "processing" : "처리중...",
        "search" : "검색 : ",
        "zeroRecords" : "검색된 데이터가 없습니다.",
        "paginate" : {
            "first" : "<<",
            "last" : ">>",
            "next" : ">",
            "previous" : "<"
        },
        "aria" : {
            "sortAscending" : " :  오름차순 정렬",
            "sortDescending" : " :  내림차순 정렬"
        }
    };
}


/************************************************************************
함수명 : exportChartToPng
설 명 : 차트 svg를 이미지로 변환
인 자 : chartid
사용법 : 
작성일 : 2020-12-21
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.12.21   정다빈       최초작성
************************************************************************/

function exportChartToPng(chartID){
	//fix weird back fill
	//console.log("exportChartToPng 차트아이디: "+chartID);
	d3.select('#'+chartID).selectAll("path").attr("fill", "none");
	//fix no axes
	d3.select('#'+chartID).selectAll("path.domain").attr("stroke", "black");
	//fix no tick
	d3.select('#'+chartID).selectAll(".tick line").attr("stroke", "black");
	fcnt = $('#'+chartID).find('svg').length;
	var svgElement = $('#'+chartID).find('svg')[downCnt];
	saveSvgAsPng(svgElement, chartID+'.png');
}


/************************************************************************
함수명 : trainOne
설 명 : 통계-단일편성 관련
인 자 : 
사용법 : 
작성일 : 2020-12-28
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.12.28   정다빈       최초작성
************************************************************************/
function trainOne(sdate){//"2018-01-01"
	//console.log("통계 단일/전체편성 테이블 함수 : "+sdate);
	
	var ajaxData=ajaxMethod("/statistic/trainTable.ajax", {"sDate":sdate});
	var alData = ajaxData.tbList;
	var tbFirst="<tr>";
	var tbEnd="</tr>";
	var tbM;
	var firstId;
	for (var i = 0; i < alData.length; i++) {
		//맨 처음 차량 선택
		if(i==0 && alData[i].rst =='Y'){
			tbM= "<td id='"+alData[i].trainNum+"' class='tn-active'>"+alData[i].idx+"<br>"+alData[i].trainNum+"</td>";
			if($("#trainNum").attr("class")=="disabledbutton"){
				firstId=null;
			}else{
				firstId=alData[i].trainNum;
			}	
		}else{
			//배경 선택가능
			if(alData[i].rst =='Y'){
				tbM+= "<td id='"+alData[i].trainNum+"' class='tn-y'>"+alData[i].idx+"<br>"+alData[i].trainNum+"</td>";
			}else{//배경 비선택
				tbM+= "<td id='"+alData[i].trainNum+"' class='tn-n'>"+alData[i].idx+"<br>"+alData[i].trainNum+"</td>";
			}
			//7열 초과시 줄바꿈
			if(parseInt((i+1)%4)==0){
				var tbCont = tbFirst+tbM+tbEnd;
				$("#trainTbd").append(tbCont);	
				tbM="";
			}else{
				//데이터의 마지막
				if((i+1)==alData.length){
					//다찰경우 제수(나누는 수 4)에서 나머지를 뺀만큼 공백임
					//console.log("마지막열");
					var cnt = 4-parseInt((i+1)%4);
					for (var j = 0; j < cnt; j++) {
						tbM+= "<td class='ndata'></td>";
					}
					var tbCont = tbFirst+tbM+tbEnd;
					$("#trainTbd").append(tbCont);	
					tbM="";
				}
			}	
		}
	}
	if($("#trainNum").attr("class")!="disabledbutton" 
		&& $("#trainTbd tr").find(".tn-active").attr('id')===undefined){
		console.log("이프절 firstid : "+firstId);
		firstId=0;
	}	
	console.log("firstid : "+firstId);
	return firstId;
}


/************************************************************************
함수명 : stcReNew
설 명 : 통계 데이터+하단 데이터테이블 생성 또는 갱신
인 자 : 
사용법 : 
작성일 : 2020-12-28
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.12.28   정다빈       최초작성
 ************************************************************************/
function stcReNew(alData){
	//console.log("데이터 값 변환 alData ; "+alData);
	//막대그래프에 쓰이는 배열
	titleArr=	['x'];
	rlxArr	=	['여유'];
	usArr	=	['보통'];
	careArr	=	['주의'];
	cwdArr	=	['혼잡'];
	
	rlxSum=0;
	usSum=0;
	careSum=0;
	cwdSum=0;
	
	$(alData).each(function(i,list) {
		titleArr.push(alData[i].title);
		
		//혼잡도 변화 건수가 0일 경우 숫자가 그래프에 안나오게 함
		if(list.rlxCnt==0){
			rlxArr.push(null);
		}else{
			rlxArr.push(alData[i].rlxCnt);
			rlxSum+=alData[i].rlxCnt;
		}
		
		if(list.usCnt==0){
			usArr.push(null);
		}else{
			usArr.push(alData[i].usCnt);
			usSum+=alData[i].usCnt;
		}

		if(list.careCnt==0){
			careArr.push(null);
		}else{
			careArr.push(alData[i].careCnt);
			careSum+=alData[i].careCnt;
		}

		if(list.cwdCnt==0){
			cwdArr.push(null);
		}else{
			cwdArr.push(alData[i].cwdCnt);
			cwdSum+=alData[i].cwdCnt;
		}

	});
	//테이블 갱신
    $("#tbody").empty();
    var tbCont="";
    	$(alData).each(function(i,list) {
    		if(
    			list.rlxCnt!=0||list.usCnt
    			||list.careCnt!=0||list.cwdCnt
    		){
    			tbCont += 	 "<tr><td>"+list.title+"</td>"	
				+"<td>"+list.rlxCnt+"</td>"	
				+"<td>"+list.usCnt+"</td>"		
				+"<td>"+list.careCnt+"</td>"		
				+"<td>"+list.cwdCnt+"</td>"	
				+"<td>"+list.avgCwd+"</td>"	
				+"<td>"+list.minWgt+"</td>"	
				+"<td>"+list.minTnum+"</td>"		
				+"<td>"+list.minDt+"</td>"		
				+"<td>"+list.maxWgt+"</td>"	
				+"<td>"+list.maxTnum+"</td>"		
				+"<td>"+list.maxDt+"</td></tr>";
    		}
    	});
    $("#tbody").append(tbCont);
}

/************************************************************************
함수명 : filterSbT
설 명 : 데이터테이블에서 열차별로 셀렉트박스 구성
인 자 : 
사용법 : 
작성일 : 2021-12-28
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.12.28   정다빈       최초작성
 ************************************************************************/
function filterSbT(){
	var ajaxData = ajaxMethod("/terminal/terminalList.ajax");
	var apStr='<select id="filterSb"><option value="1" selected>전체</option>';
	for (var i = 0; i < ajaxData.data.length; i++) {
		apStr+='<option value="'+ajaxData.data[i].trainNum+'">'+ajaxData.data[i].trainNum+'</option>';
	}
	apStr+='</select>';
	return apStr;
}

function cssChart(){
//	console.log("화면에 맞게 차트요소 색상변경");
	$("text").css("fill","#fff");
	
	$(".c3-axis c3-axis-x text").css("fill","#fff");
	$("tspan").css("fill","#fff");
	
	$(".c3-axis-x path").css("stroke","#fff");
	$(".c3-axis-x line").css("stroke","#fff");
	
	$(".c3-axis-y path").css("stroke","#fff");
	$(".c3-axis-y line").css("stroke","#fff");
	
	$(".c3-target-여유").children().css("fill","black");
	$(".c3-target-보통").children().css("fill","black");
	$(".c3-target-주의").children().css("fill","black");
	$(".c3-target-혼잡").children().css("fill","black");
	$(".c3-chart-arc").css("font-weight","bold");
}

function cssNonChart(){
//	console.log("화면에 맞게 차트요소 색상변경");
	$("text").css("fill","");
	
	$(".c3-axis c3-axis-x text").css("fill","");
	$(".c3-axis c3-axis-y text").css("fill","");
	$("tspan").css("fill","");
	
	$(".c3-axis-x path").css("stroke","");
	$(".c3-axis-x line").css("stroke","");
	
	$(".c3-axis-y path").css("stroke","");
	$(".c3-axis-y line").css("stroke","");
	
	//$(".c3-chart-arc").css("font-weight","bold");
}

/************************************************************************
함수명 : renewalRule
설 명 : 날짜나 열차 변경 시 룰셋 변경
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/
function renewalRule(flag){
	//변경일시에 해당하는 데이터로 호출
 	ajaxData = ajaxMethod("/statistic/dayTermByRange.ajax", {"sDate":$("#sdate").val(),"trainNum":fid});
 	//막대그래프 갱신
	stcReNew(ajaxData.data);
	//데이터에 맞게 차트 다시그림
	chart.load({
        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
    });
 	if(flag==="day"){
 		//산점도 데이터 재호출
 		sctData = ajaxMethod("/statistic/scatterChart.ajax", {"sDate":$("#sdate").val(),"trainNum":fid, "carWgt":"1"});
 		var scat = sctData.data;
 		//누적영역 데이터 재호출
 		saData = ajaxMethod("/statistic/stkAreaChart.ajax", {"sDate":$("#sdate").val(),"trainNum":fid, "carWgt":"1"});
 		var areaS = saData.data
 		for (var i = 0; i < scat.length; i++) {
 			scat[i].무게=scat[i].wgt;
 			delete scat[i].wgt;
 		}
 		for (var i = 0; i < areaS.length; i++) {
 			areaS[i].누적총계=areaS[i].sumCnt;
 			delete areaS[i].sumCnt;
 		}
 		//산점도 로드
 		scatChart.load({
 	        json: scat,
 	        keys: {
 	        	x: 'xDate',
 	            value: ["무게"]
 	        }
 	    });
 		//원형 차트 로드
 		pieChart.load({
 			columns: [['여유', rlxSum],['보통', usSum],['주의', careSum],['혼잡', cwdSum]]
 		});
 		//영역차트 로드
 		saChart.load({
 	        json: areaS,
 	        keys: {
 	        	x: 'hh',
 	            value: ["누적총계"]
 	        }
 	    });
 		
 	}
	
	//차트 디자인 재설정
	cssChart();
	/*$(".c3-target-여유").children().css("fill","black");
	$(".c3-target-보통").children().css("fill","black");
	$(".c3-target-주의").children().css("fill","black");
	$(".c3-target-혼잡").children().css("fill","black");*/
}


/************************************************************************
함수명 : dayBarChart
설 명 : 막대그래프 생성(일일 통계)
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function dayBarChart(divId,w,h,tltip){
	console.log("막대그래프 차트 생성함수 진입");
	var rstC = c3.generate({
		bindto: '#'+divId,
		
		size: {
	        height: h,
	        width: w,
	    },
		data: {
			x:'x',
			columns: [
				titleArr,
				rlxArr,
				usArr,	
				careArr,	
				cwdArr	
	        ]
	    	,line: {
	            connectNull: false,
	        }
			,type: 'bar'
			,labels:{
				format: function (v, id, i, j) {
					if(v!=null){
						//console.log("값이 0이 아닐경우 : "+v,id,i,j,d);
						var format= d3.format(',');
		                return format(v);
					}else{
						//console.log("값이 0일경우 : "+v,id,i,j,d);
						return "";
					}
	            }
			}
			
			,colors: {
				'여유': '#50E94F',
				'보통': '#F5E001',
				'주의': '#F68F00',
				'혼잡': '#FE0000'
	        }
			,order: null
		}//data
		,legend: {//범주(여유,보통,주의,혼잡)
	        position: 'right'
	    }
		,axis: {
			x: {
	            type: 'category'
//	            ,label:{
//	            	text: '시간대',
//	            	position:'outer-top'
//	            }
//				,width:100
//				,height:50
	        }
			,y: {
	            //padding: {top: 200,left:50,right:50, bottom: 0},
				 tick: {
	                //format: d3.format("$,")
	                format: function (d) {
	                	if(((d/100)%2)==0){
	                		var format= d3.format(',');
	    	                return format(d);
	                	}
	                }
					//count:7
					 
	         	},
//				label:{
//					text: '량별 혼잡도 수',
//	            	position:'outer-top'
//	            },
//	         	width:100
//				,height:150
	        }
	    }
		,tooltip: {
	        format: {
	        	title: function (d) {
	        		/* //console.log("d : "+d);
	        		//console.log("d.id : "+d.id); */
	        		if(typeof tltip !== "undefined"){
	        			return titleArr[d+1]+tltip; 
	        		}else{
	        			return titleArr[d+1];
	        		}
	        	},
	            value: function (value, ratio, id) {
	            	/* //console.log("value : "+value);
	            	//console.log("ratio : "+ratio);
	            	//console.log("id : "+id); */
	            	var format= d3.format(',');
	                return format(value);
	            }
	        }
	    }
		,onrendered: function () {
	        d3.select(this.config.bindto).selectAll(".c3-chart-text text").style("display", function (d) {
	            if (d && d.value === 0){
	            	return "none"	
	            }else{
	            	d.color
		            return d;
	            }
	            
	        });
//	        d3.select(this.config.bindto).select(".c3-axis-x-label").attr("dy", "60px");
//	        d3.select(this.config.bindto).select(".c3-axis-y-label").attr("dx", "100px");
	    }
	});
		
	return rstC;
}


/************************************************************************
함수명 : barChart
설 명 : 막대그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function barChart(divId,w,h,tltip){
	console.log("막대그래프 차트 생성함수 진입");
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
			height: h,
			width: w,
		},
		data: {
			x:'x',
			columns: [
				titleArr,
				rlxArr,
				usArr,	
				careArr,	
				cwdArr	
				]
		,line: {
			connectNull: false,
		}
		,type: 'bar'
			,labels:{
				format: function (v, id, i, j) {
					if(v!=null){
						//console.log("값이 0이 아닐경우 : "+v,id,i,j,d);
						var format= d3.format(',');
						return format(v);
					}else{
						//console.log("값이 0일경우 : "+v,id,i,j,d);
						return "";
					}
				}
			}
		
		,colors: {
			'여유': '#50E94F',
			'보통': '#F5E001',
			'주의': '#F68F00',
			'혼잡': '#FE0000'
		}
		,order: null
		}//data
		,legend: {//범주(여유,보통,주의,혼잡)
			position: 'right'
		}
		,axis: {
			x: {
				type: 'category'
			}
		,y: {
			//padding: {top: 200,left:50,right:50, bottom: 0},
			tick: {
				//format: d3.format("$,")
				format: function (d) {
					if(((d/1000)%1)==0){
						return d/1000;			                		
					}
				}
			}
		}
		}
		,tooltip: {
			format: {
				title: function (d) {
					/* //console.log("d : "+d);
	        		//console.log("d.id : "+d.id); */
					if(typeof tltip !== "undefined"){
						return titleArr[d+1]+tltip; 
					}else{
						return titleArr[d+1];
					}
				},
				value: function (value, ratio, id) {
					/* //console.log("value : "+value);
	            	//console.log("ratio : "+ratio);
	            	//console.log("id : "+id); */
					var format= d3.format(',');
					return format(value);
				}
			}
		}
		,onrendered: function () {
			d3.select(this.config.bindto).selectAll(".c3-chart-text text").style("display", function (d) {
				if (d && d.value === 0){
					return "none"	
				}else{
					d.color
					return d;
				}
				
			});
		}
	});
	
	return rstC;
}


/************************************************************************
함수명 : lineChart
설 명 : 꺽은선 그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function lineChart(divId,w,h,tltip){
	console.log("꺽은선 차트 생성함수 진입");
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
			height: 300,
	        width: 1200,
	    },
		
		data: {
			x:'x',
			columns: [
				titleArr,
				rlxArr,
				usArr,	
				careArr,	
				cwdArr	
	        ]
			,type: 'line'
			,labels:false
			,colors: {
				'여유': '#50E94F',
				'보통': '#F5E001',
				'주의': '#F68F00',
				'혼잡': '#FE0000'
	        }
			,order: null
		}//data
		,legend: {
	        position: 'right'
	    }
		,axis: {
			x: {
	            type: 'category'
	        }
			,y: {
				 tick: {
					format: function (d) {
	                	if(((d/1000)%1)==0){
	                		return d/1000;			                		
	                	}
                	}
		        }
	        }
	    }
		,tooltip: {
			format: {
	        	title: function (d) {
	        		if(typeof tltip !== "undefined"){
	        			return titleArr[d+1]+tltip; 
	        		}else{
	        			return titleArr[d+1];
	        		}
	        	},
	            value: function (value, ratio, id) {
	            	var format= d3.format(',');
	                return format(value);
	            }
	        }
	    }
	});
	return rstC;
}


/************************************************************************
함수명 : scatterChart
설 명 : 산포도 그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/
function scatterChart(divId,w,h,aData){
	//bindto: '#'+divId,
	console.log("산점도 차트 생성함수 진입");
	var jsData = null;
	jsData = aData.data;
	for (var i = 0; i < jsData.length; i++) {
		jsData[i].무게=jsData[i].wgt;
		delete jsData[i].wgt;
	}
	
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
			height: h,
	        width: w,
	    },
	    data: {
	    	//'%Y-%m-%d %H:%M:%S'
	    	x: 'xDate',
	        xFormat: '%Y-%m-%d %H:%M:%S',
	        json:jsData, 
	        keys: {
	            x: 'xDate',
	            value: ["무게"]
	        }
	        ,type: 'scatter'
	        ,labels: false	
	    },
	    
	    color: {
	        pattern: ['#04fff9']
	    },
	    legend: {//범주(여유,보통,주의,혼잡)
	    	show: false
	    }
	    ,tooltip: {
	        format: {
	        	title: function (d) {
	        		console.log("d 툴팁 : "+d);
	        		return moment(d).format('HH:mm'); 
	        	},
	            value: function (value, ratio, id) {
	            	/*console.log("value : "+value);
	            	console.log("ratio : "+ratio);
	            	console.log("id : "+id); */
	            	var format= d3.format(',');
	                return format(value);
	            }
	        }
	    }
	    ,axis: {
	        x: {
	            type: "timeseries",
	            tick: {
	            	fit: false,
	            	//'%Y-%m-%d %H:%M:%S'
	            	format: '%H'	
                }
	        }
		    ,y: {
				tick: {
					 format: function (d) {
		            	if((d%20)==0){
		            		var format= d3.format(',');
			                return format(d);
		            	}
			        }
		        }
		   }
	    }
	});
	return rstC;
}


/************************************************************************
함수명 : dountChart
설 명 : 도넛그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function dountChart(divId,w,h){
	console.log("도넛 차트 생성함수 진입");
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
	        height: 200,
	        width: 600,
	    },
	    data: {
	        columns: [
	            ['여유', rlxSum],
	            ['보통', usSum],
	            ['주의', careSum],
	            ['혼잡', cwdSum],
	        ],
	        type : 'donut'
        	,colors: {
    			'여유': '#50E94F',
    			'보통': '#F5E001',
    			'주의': '#F68F00',
    			'혼잡': '#FE0000'
            }
	        /*onclick: function (d, i) { console.log("onclick", d, i); },
	        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
	        onmouseout: function (d, i) { console.log("onmouseout", d, i); }*/
	    },
	    donut: {
	        title: "혼잡도 비율"
	    }
	    
		,legend: {//범주(여유,보통,주의,혼잡)
	        position: 'right'
	    }
	});
	return rstC;
}


/************************************************************************
함수명 : stkAreaChart
설 명 : 영역 차트 그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function stkAreaChart(divId,w,h,sData){
	console.log("누적영역 차트 생성함수 진입");
	var jsData = sData.data;
	for (var i = 0; i < jsData.length; i++) {
		jsData[i].누적총계=jsData[i].sumCnt;
		delete jsData[i].sumCnt;
	}
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
	        height: 200,
	        width: 600,
	    },
	    data: {
	        json:jsData, 
	        keys: {
	            x: 'hh',
	            value: ["누적총계"]
	        }
	        ,type: 'area'
	        ,labels: false	
	    },
	    
	    color: {
	        pattern: ['#bb9dff']
	    },
	    legend: {//범주(여유,보통,주의,혼잡)
	    	show: false
	    },
	    axis: {
	        x: {
	            type: "category"
	            /*,tick: {
	            	fit: false,
                    format: '%H'
                }*/
	        }
	    }
	    
	});
	return rstC;
}


/************************************************************************
함수명 : stkBarChart
설 명 : 스택 막대 그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),
사용법 : 
작성일 : 2021-05-10
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.10   정다빈       최초작성
 ************************************************************************/

function stkBarChart(divId,w,h,ajaxData){
	console.log("스택 막대 차트 생성함수 진입");
	//json 키 한글화
	for (var i = 0; i < ajaxData.length; i++) {
		ajaxData[i].열차번호=ajaxData[i].trainNum;
		ajaxData[i].여유=ajaxData[i].rlxCnt;
		ajaxData[i].보통=ajaxData[i].usCnt;
		ajaxData[i].주의=ajaxData[i].careCnt;
		ajaxData[i].혼잡=ajaxData[i].cwdCnt;
		
		delete ajaxData[i].trainNum;
		delete ajaxData[i].rlxCnt;
		delete ajaxData[i].usCnt;
		delete ajaxData[i].careCnt;
		delete ajaxData[i].cwdCnt;
	}
	
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
	        height: h,
	        width: w,
	    },
		
		data: {
	        json:ajaxData, 
	        keys: {
	            x: '열차번호',
	            value: ["여유","보통","주의","혼잡"]
	        }
			,type: 'bar'
        	,colors: {
    			'여유': '#50E94F',
    			'보통': '#F5E001',
    			'주의': '#F68F00',
    			'혼잡': '#FE0000'
            }
			,groups: [
				['여유','보통','주의','혼잡'],
			]
			,order: null
	        ,onclick: function (d, i) { 
	        	console.log("onclick", d, d.x);
	        	$(".c3-bars-여유").find("path").css("fill","#50E94F");
	        	$(".c3-bars-보통").find("path").css("fill","#F5E001");
	        	$(".c3-bars-주의").find("path").css("fill","#F68F00");
	        	$(".c3-bars-혼잡").find("path").css("fill","#FE0000");
	        	$(".c3-bar-"+d.x).css("fill","#04fff9");
	        	subChart(d.x);
	        }
	        //,onmouseover: function (d, i) { console.log("onmouseover", d, i); },
	        //onmouseout: function (d, i) { console.log("onmouseout", d, i); }
		}//data
	    ,legend: {//범주(여유,보통,주의,혼잡)
	        position: 'right'
	    }
		,axis: {
			x: {
	            type: 'category',
	            //글자 개행 안되게 제한
	            tick: {
	            	fit: true,
	            	outer: false,
	            	multiline: false,
            	}
	        },
	        y: {
	            min: 0,
	            tick: {
	            	format: function(d) {
	                    if (Math.floor(d) != d){
	                      return;
	                    }
	                    return d;
                  	}
	            },
	            //max: 8,
	            padding: {top: 0, bottom: 0}
	        }
	    }
	});//chart
/*	$(".c3-legend-item-여유 line").css("stroke","#50E94F");
	$(".c3-legend-item-보통 line").css("stroke","#F5E001");
	$(".c3-legend-item-주의 line").css("stroke","#F68F00");
	$(".c3-legend-item-혼잡 line").css("stroke","#FE0000");*/
	return rstC;
}


/************************************************************************
함수명 : gaugeChart
설 명 : 스택 막대 그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),
사용법 : 
작성일 : 2021-05-10
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.10   정다빈       최초작성
 ************************************************************************/

function gaugeChart(divId,w,h,per){
	console.log("스택 막대 차트 생성함수 진입");
	//var jsData = sData.data;
	var rstC =  c3.generate({
			bindto: '#'+divId,
			size: {
		        height: h,
		        width: w,
		    },
		    data: {
		        columns: [
		            ['주의/혼잡 비율', per]
		        ],
		        type: 'gauge',
//		        onclick: function (d, i) { console.log("onclick", d, i); },
//		        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
//		        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
		    },
		    gauge: {
		    },
		    color: {
		        pattern: ['#50E94F', '#F5E001', '#F68F00', '#FE0000'], // the three color levels for the percentage values.
		        threshold: {
		            values: [25, 50, 75, 100]
		        }
		    },
		});
	return rstC;
}

/************************************************************************
함수명 : ryBChart
설 명 : 량별 막대그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function ryBChart(divId,w,h,tltip){
	console.log("막대그래프 차트 생성함수 진입");
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
	        height: h,
	        width: w,
	    },
		data: {
			x:'x',
			columns: [
				titleArr,
				rlxArr,
				usArr,	
				careArr,	
				cwdArr	
	        ]
	    	,line: {
	            connectNull: false,
	        }
			,type: 'bar'
			,groups: [
				['여유','보통','주의','혼잡'],
			]
			,labels:{
				format: function (v, id, i, j) {
					if(v!=null){
						//console.log("값이 0이 아닐경우 : "+v,id,i,j,d);
						var format= d3.format(',');
		                return format(v);
					}else{
						//console.log("값이 0일경우 : "+v,id,i,j,d);
						return "";
					}
	            }
			}
			
			,colors: {
				'여유': '#50E94F',
				'보통': '#F5E001',
				'주의': '#F68F00',
				'혼잡': '#FE0000'
	        }
			,order: null
		}//data
		,legend: {//범주(여유,보통,주의,혼잡)
	        position: 'right'
	    }
		,bar: {
	        width: {
	            ratio: 0.3 // this makes bar width 50% of length between ticks
	        }
	        // or
	        //width: 100 // this makes bar width 100px
	    }
		,axis: {
			x: {
	            type: 'category'
	        }
			,y: {
	            //padding: {top: 200,left:50,right:50, bottom: 0},
				 tick: {
	                //format: d3.format("$,")
	                format: function (d) {
	                	if(((d/1000)%1)==0){
	                		return d/1000;			                		
	                	}
	                }
	         	}
	        }
	    }
		,tooltip: {
	        format: {
	        	title: function (d) {
	        		/* //console.log("d : "+d);
	        		//console.log("d.id : "+d.id); */
	        		if(typeof tltip !== "undefined"){
	        			return titleArr[d+1]+tltip; 
	        		}else{
	        			return titleArr[d+1];
	        		}
	        	},
	            value: function (value, ratio, id) {
	            	/* //console.log("value : "+value);
	            	//console.log("ratio : "+ratio);
	            	//console.log("id : "+id); */
	            	var format= d3.format(',');
	                return format(value);
	            }
	        }
	    }
		,onrendered: function () {
	        d3.select(this.config.bindto).selectAll(".c3-chart-text text").style("display", function (d) {
	            if (d && d.value === 0){
	            	return "none"	
	            }else{
	            	d.color
		            return d;
	            }
	        });
	    }
	});
	return rstC;
}

/************************************************************************
함수명 : stkAreaChart
설 명 : 영역 차트 그래프 생성
인 자 : divId(그려지는 div의 id값),w(가로길이),h(세로길이),tltip(툴팁 관련)
사용법 : 
작성일 : 2021-05-06
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.06   정다빈       최초작성
 ************************************************************************/

function twoAreaChart(divId,w,h,sData){
	console.log("누적영역 차트 생성함수 진입");
	var jsData = sData.data;
	for (var i = 0; i < jsData.length; i++) {
		jsData[i].전일=jsData[i].yesC;
		jsData[i].금일=jsData[i].todC;
		delete jsData[i].yesC;
		delete jsData[i].todC;
	}
	var rstC = c3.generate({
		bindto: '#'+divId,
		size: {
	        height: h,
	        width: w,
	    },
	    data: {
	        json:jsData, 
	        keys: {
	            x: 'hh',
	            value: ["전일","금일"]
	        }
	        ,types: {
	        	전일:"line",
	       		금일:"area-spline"
	        }
	        ,labels: false	
	    },
	    
	    color: {
	    	pattern: ['#04fff9', '#bb9dff']
	    }
	    ,legend: {//범주(전일/금일)
	        position: 'right'
	    }
	    ,axis: {
	        x: {
	            type: "category"
	            /*,tick: {
	            	fit: false,
                    format: '%H'
                }*/
	        }
	    },
	    point: {
	        show: false
	    }
	});
	return rstC;
}
//마우스 클릭시 서브함수 반응
function subChart(xval){
	var cnum=0;
	if(xval<6){
		cnum=xval+101;
	}else{
		cnum=xval+195;
	}
	var subData = ajaxMethod('/statistic/oneTrAllRyang.ajax', {"cnum":cnum});
	disgustF(subData.data);
	cssChart();
}

//게이지 차트 퍼센트 계산
function percentage(pdata){
	var bun=ajaxMethod('/statistic/bunmo.ajax');
	var bunmo =bun.data;
	var bunja=0;
	for (var i = 0; i < pdata.length; i++) {
		if(pdata[i].todC!=null){
			bunja+=parseInt(pdata[i].todC);
		}
	}
	var perc=0;
	if(bunmo!=0){
		perc = (bunja/bunmo)*100
	}
	console.log("게이지 퍼센트 : "+perc);
	return perc;
}


//각량별 혼잡도 관련 데이터 세팅
function disgustF(fuckData){
	titleArr=	['x',"1량","2량","3량","4량","5량","6량"];
	rlxArr	=	['여유'];
	usArr	=	['보통'];
	careArr	=	['주의'];
	cwdArr	=	['혼잡'];
	////
	for (var i = 0; i < fuckData.length; i++) {
		if(fuckData[i].c1=="여유"){
			rlxArr.push(fuckData[i].cw1);usArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c1=="보통"){
			usArr.push(fuckData[i].cw1);rlxArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c1=="주의"){
			careArr.push(fuckData[i].cw1);rlxArr.push(0);usArr.push(0);cwdArr.push(0);
		}else{
			cwdArr.push(fuckData[i].cw1);rlxArr.push(0);usArr.push(0);careArr.push(0);
		}
		if(fuckData[i].c2=="여유"){
			rlxArr.push(fuckData[i].cw2);usArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c2=="보통"){
			usArr.push(fuckData[i].cw2);rlxArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c2=="주의"){
			careArr.push(fuckData[i].cw2);rlxArr.push(0);usArr.push(0);cwdArr.push(0);
		}else{
			cwdArr.push(fuckData[i].cw2);rlxArr.push(0);usArr.push(0);careArr.push(0);
		}
		if(fuckData[i].c3=="여유"){
			rlxArr.push(fuckData[i].cw3);usArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c3=="보통"){
			usArr.push(fuckData[i].cw3);rlxArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c3=="주의"){
			careArr.push(fuckData[i].cw3);rlxArr.push(0);usArr.push(0);cwdArr.push(0);
		}else{
			cwdArr.push(fuckData[i].cw3);rlxArr.push(0);usArr.push(0);careArr.push(0);
		}
		if(fuckData[i].c4=="여유"){
			rlxArr.push(fuckData[i].cw4);usArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c4=="보통"){
			usArr.push(fuckData[i].cw4);rlxArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c4=="주의"){
			careArr.push(fuckData[i].cw4);rlxArr.push(0);usArr.push(0);cwdArr.push(0);
		}else{
			cwdArr.push(fuckData[i].cw4);rlxArr.push(0);usArr.push(0);careArr.push(0);
		}
		if(fuckData[i].c5=="여유"){
			rlxArr.push(fuckData[i].cw5);usArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c5=="보통"){
			usArr.push(fuckData[i].cw5);rlxArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c5=="주의"){
			careArr.push(fuckData[i].cw5);rlxArr.push(0);usArr.push(0);cwdArr.push(0);
		}else{
			cwdArr.push(fuckData[i].cw5);rlxArr.push(0);usArr.push(0);careArr.push(0);
		}
		if(fuckData[i].c6=="여유"){
			rlxArr.push(fuckData[i].cw6);usArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c6=="보통"){
			usArr.push(fuckData[i].cw6);rlxArr.push(0);careArr.push(0);cwdArr.push(0);
		}else if(fuckData[i].c6=="주의"){
			careArr.push(fuckData[i].cw6);rlxArr.push(0);usArr.push(0);cwdArr.push(0);
		}else{
			cwdArr.push(fuckData[i].cw6);rlxArr.push(0);usArr.push(0);careArr.push(0);
		}
	}
	//
	if(typeof chart !=="undefined"){
		chart.load({
	        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
	    });
		cssChart();
	}
}

//량 상위함수 데이터 변경시 량 변경시
function changeRyang(sbD){
//	console.log("량 상위함수 데이터 변경시 량 변경시 : "+sbD);
	nowArr=[];
	for (var i = 0; i < sbD.length; i++) {
		if(sbD[i].cnum!=null){
			nowArr.push(sbD[i].cnum);	
		}
	}
	
	//여기서 실제 값 뽑고 처음부터 주기적으로 돌려야함 cnum
	var rdta = ajaxMethod("/statistic/oneTrAllRyang.ajax", {"cnum":nowArr[0]});
	var xx;
	if(nowArr[0]<200){
		xx=nowArr[0]-101;
	}else{
		xx=nowArr[0]-195;
	}
	//부모차트 색상변경
	$(".c3-bars-여유").find("path").css("fill","#50E94F");
	$(".c3-bars-보통").find("path").css("fill","#F5E001");
	$(".c3-bars-주의").find("path").css("fill","#F68F00");
	$(".c3-bars-혼잡").find("path").css("fill","#FE0000");
	$(".c3-bar-"+xx).css("fill","#04fff9");
	//량데이터 여보주혼에 맞게 재조합
	disgustF(rdta.data);
}
//메인화면 갱신시
function reloadMain(){
	//1
	var stkBarData=ajaxMethod("/statistic/stkBarTod.ajax");
	var sbD = stkBarData.data;
//	$(".c3-bars-여유").find("path").css("fill","#50E94F");
//	$(".c3-bars-보통").find("path").css("fill","#F5E001");
//	$(".c3-bars-주의").find("path").css("fill","#F68F00");
//	$(".c3-bars-혼잡").find("path").css("fill","#FE0000");
//	$(".c3-bar-"+xx).css("fill","#04fff9");
	//2
	changeRyang(sbD);
	//3
	var yesToData = ajaxMethod("/statistic/yesTodStkAr.ajax");	
	var jsData = yesToData.data;
	//4
	var per = percentage(jsData);
	
	
	//json 키 한글화
	//1
	for (var i = 0; i < sbD.length; i++) {
		sbD[i].열차번호=sbD[i].trainNum;
		sbD[i].여유=sbD[i].rlxCnt;
		sbD[i].보통=sbD[i].usCnt;
		sbD[i].주의=sbD[i].careCnt;
		sbD[i].혼잡=sbD[i].cwdCnt;
		
		delete sbD[i].trainNum;
		delete sbD[i].rlxCnt;
		delete sbD[i].usCnt;
		delete sbD[i].careCnt;
		delete sbD[i].cwdCnt;
	}
	//3
	for (var i = 0; i < jsData.length; i++) {
		jsData[i].전일=jsData[i].yesC;
		jsData[i].금일=jsData[i].todC;
		delete jsData[i].yesC;
		delete jsData[i].todC;
	}
	//1
	stkBarChart.load({
		json: sbD,
		keys: {
			x: '열차번호',
			value: ["여유","보통","주의","혼잡"]
		}
	});
	console.log("end 1");
	//2
	chart.load({
        columns: [titleArr,rlxArr,usArr,careArr,cwdArr]
    });
	console.log("end 2");
	//3
	twoAreaChart.load({
		json: jsData,
		keys: {
			x: 'hh',
			value: ["전일","금일"]
		}
	});
	console.log("end 3");
	console.log("퍼는 : "+per);
	//4
	gaugeChart.load({
		 columns: [
	            ['주의/혼잡 비율', per]
	    ]
    });
	console.log("end 4");
}

function ryangTrain(tid,tc){
	console.log("일일통계 량 산포도 갱신 : "+tid,tc);
	var tbFirst="<tr>"
	var tbM;
	var tbEnd="</tr>";
	var fSame=true;
	var fUrl;
	var bfId;
	if(tid==0&&tc==0){
		//최초 테이블 생성
		for (var i = 1; i <= 6; i++) {
			if(i==1){
				tbM+= "<td id='"+i+"' class='active' style='background:url(../images/at_rang_"+i+".png) no-repeat;background-size: contain;'>"+i+"량"+"</td>";
			}else{
				tbM+= "<td id='"+i+"' class='non' style='background:url(../images/non_rang_"+i+".png) no-repeat;background-size: contain;'>"+i+"량"+"</td>";
			}
		}
		var tbCont = tbFirst+tbM+tbEnd;
		$("#ryangT").append(tbCont);	
		$('#1').css("color","#fff906");
	}else{
		//테이블 순회해서 기존에 체크된거랑 현재 체크한거랑
		//같으면 현상유지
		//아니면 갱신
		$(".rgDiv tr td").each(function(i,list){
			if($(this).attr("class")=="active"){
				bfId=$(this).attr("id");
				if(bfId==tid){
					fSame=false;
				}
			}
		});
		//아이콘 변경
		if(fSame){
			//기존 것 비활성
			$('#'+bfId).css({"background":"url(../images/non_rang_"+bfId+".png)","background-repeat" : "no-repeat"});
			$('#'+bfId).css("color","#fff");
			$('#'+bfId).attr("class","non");
			
			console.log("기존거 : "+$('#'+bfId).text());
			
			//선택한 것 활성
			$('#'+tid).css({"background":"url(../images/at_rang_"+tid+".png)","background-repeat" : "no-repeat"});
			$('#'+tid).css("color","#fff906");
			$('#'+tid).attr("class","active");

			console.log("신규 : "+$('#'+tid).text());
			
			//산점도 데이터 재호출
			sctData = ajaxMethod("/statistic/scatterChart.ajax", {"sDate":$("#sdate").val(),"trainNum":fid, "carWgt":tid});
			var scat = sctData.data;
			for (var i = 0; i < scat.length; i++) {
	 			scat[i].무게=scat[i].wgt;
	 			delete scat[i].wgt;
	 		}
			//산점도 로드
			scatChart.load({
		        json: scat,
		        keys: {
		        	x: 'xDate',
		            value: ["무게"]
		        }
		    });
			
			//사이즈 조정
			$(".rgDiv tr td").css("background-size","contain");
			
		}
	}
	cssChart();
}
