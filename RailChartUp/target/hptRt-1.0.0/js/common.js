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
function startPing(){
	//로그인 됬을 시
	//10초 간격으로 web에서 was로 ping을 전송하는 timer 생성 
	loginTimer = setInterval(function(){
		loginPing();
	}, 10000);

}

function getJSessionId(){
    var jsId = document.cookie.match(/JSESSIONID=[^;]+/);
    if(jsId != null) {
        if (jsId instanceof Array)
            jsId = jsId[0].substring(11);
        else
            jsId = jsId.substring(11);
    }
    return jsId;
}

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
function loginPing(){
	var dd = new Date();
	console.log("sessionId : "+getJSessionId());
	console.log("서버로 핑 전송 요청 : "+dd.getHours()+":"+dd.getMinutes()+":"+dd.getSeconds()+"."+dd.getMilliseconds());
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
}

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
function endPing(){
	clearInterval(loginTimer);
	loginTimer=null;
}

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
//			console.log("아작스 서버 연동 성공");
//			console.log(json);
//			console.log(json.msg);
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
			console.log("에러가 발생하였습니다.");
		},
		//finally 기능 수행
		complete : function() {

		}
	});
	return output;
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
			if(form[i].value.length<8 || !(regExp.test(form[i].value)) ){
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
	console.log("대메뉴 항목 클릭 : "+$(that).attr('id'));
	
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
	
	$("#content").css("width","1300px");
	$("#content").empty();
	console.log("메뉴에서 이동 클릭");
	console.log($(that).attr("id"));
	goUrl= $(that).attr("id");
	//기존에 액티브 되있던 항목 초기화(대메뉴)
	$(".menu li a").each(function(i,list){
		//console.log($(this).text());
		$(this).removeClass('active');
	});
	
	var nowSide=goUrl.split("/")[1];
	
	if(nowSide!=pastSide){
		console.log("사이드바 변경");
		pastSide=nowSide;
		var sideUrl="/sidebar/"+nowSide+".do";
		$("#sideDiv").load(sideUrl);
	}else{
		$(".sidebar-nonbt li li").each(function(i,list){
			if($(this).attr('id')==goUrl){
				$(this).css('background','#d2d2d2');
				$(this).children('a').css('color','black');
			}else{
				$(this).css('background','#fff');
				$(this).children('a').css('color','#808080');
			}
		});
	}
	
	if(goUrl=="/statistic/dayStc.do"){
		console.log("사이드바 분기 메뉴에서 단일편성테이블 소환");
		$("#trainDiv").load("/sidebar/trainNum.do");
	}else{
		$("#trainDiv").empty();
	}
	
	//각 기능별 사이드메뉴 분기처리
	if(nowSide.indexOf("statistic")>-1){//사용자관리
		console.log("통계 사이드바 사이드메뉴");
		$(".menu li").find("#statistic").addClass('active');
	}else if(nowSide.indexOf("comuFail")>-1){
		console.log("통신장애 사이드메뉴");
		$(".menu li").find("#comuFail").addClass('active');
	}else if(nowSide.indexOf("terminal")>-1){
		console.log("차상단말기 사이드메뉴");
		$(".menu li").find("#terminal").addClass('active');
	}else if(nowSide.indexOf("user")>-1){
		console.log("사용자관리 사이드메뉴");
		$(".menu li").find("#user").addClass('active');
	}else{
		console.log("설정 사이드메뉴");
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
		console.log("전체선택");
		//체크된 경우
		if ($("#chkAll").is(":checked")){
			console.log("전체 체크박스 선택됨");
			console.log("현재 페이지 넘버 : ");
			//하위 체크박스들도 모두 선택
			$("tbody input:checkbox").prop("checked", true);
			
		}else{//선택->취소 : 전체 체크 해지시
			console.log("전체 체크박스 해지됨");
		    $("tbody input:checkbox").prop("checked", false);
		}
		 
	}else{//단일 선택일 경우
		console.log("단일체크박스 클릭"+$(that).val());
		console.log("@@@체크된 값들 길이: "+$('input:checkbox[name="chk"]:checked').length);
		console.log("@@@tbody 길이: "+$("tbody tr").length);
		//단일선택->전체
		if($('input:checkbox[name="chk"]:checked').length==$("tbody tr").length){
    		$("#chkAll").prop("checked", true);
    	}else{//단일해지->전체해지
    		$("#chkAll").prop("checked", false);
    	}
		if($(that).is(":checked")){//체크박스가 체크된 경우
			console.log("단일선택"+$(that).val());
		}else{//체크 해지된 경우
			console.log("단일해지"+$(that).val());
			
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
			$("#content").load(paramUrl,{"trainNum":tagId});
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
function tbDelete(that,paramUrl,callback){
	var idArr=[]; 
	for(i=0;i<$("tbody tr").length;i++){
		if($("#chk"+i).is(":checked")){
			idArr.push($("#chk"+i).val());//배열에 아이디 값 삽입
		}
	}
	if(typeof idArr.length==="undefined" || idArr.length==0){
		alert("삭제할 항목을 선택해 주세요");
	}else{
		if(confirm("선택하신 항목을 삭제하시겠습니까?")==true){
			
			var url=paramUrl;
			var data = {"idArr":idArr};
			ajaxMethod(url, data, callback);
			chkArr=[];
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
	$.fn.dataTable.ext.search.push(
        function(settings, data, dataIndex){
		//console.log("계산함수 들어옴 idx4 : "+iidx);
            var min = Date.parse($('#fromDate').val());
            var max = Date.parse($('#toDate').val());
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
함수명 : cctvSb
설 명 : 제어항목 (PTZ, Z)에 떠라 PT 활성/비활성
인 자 : idx(날짜가 존재하는 칼럼)
사용법 : 
작성일 : 2020-08-24
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.08.26   정다빈       최초작성
************************************************************************/
function cctvSb(){
	var tagS = $("#ptzType option:selected").val();
	if(tagS.indexOf("PTZ")!=-1){//제어 항목이 PTZ
		$("#panLeft").removeAttr("disabled");
		$("#panRight").removeAttr("disabled");
		$("#tiltUp").removeAttr("disabled");
		$("#tiltDown").removeAttr("disabled");
	}else{//제어 항목이 Z
		$("#panLeft").attr("disabled",true);
		$("#panRight").attr("disabled",true);
		$("#tiltUp").attr("disabled",true);
		$("#tiltDown").attr("disabled",true);
	}
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
	console.log("exportChartToPng 차트아이디: "+chartID);
	d3.select('#'+chartID).selectAll("path").attr("fill", "none");
	//fix no axes
	d3.select('#'+chartID).selectAll("path.domain").attr("stroke", "black");
	//fix no tick
	d3.select('#'+chartID).selectAll(".tick line").attr("stroke", "black");
	var svgElement = $('#'+chartID).find('svg')[0];
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
	console.log("통계 단일/전체편성 테이블 함수 : "+sdate);
	
	var ajaxData=ajaxMethod("/statistic/trainTable.ajax", {"sDate":sdate});
	var alData = ajaxData.tbList;
	var tbFirst="<tr>"
	var tbEnd="</tr>";
	var tbM;
	var firstId;
	for (var i = 0; i < alData.length; i++) {
		//맨 처음 차량 선택
		if(i==0 && alData[i].rst =='Y'){
			tbM= "<td id='"+alData[i].trainNum+"' class='tn-active'>"+alData[i].idx+"<br>"+alData[i].trainNum+"</td>";
			firstId=alData[i].trainNum;
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
					console.log("마지막열");
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
	return firstId;
}


/************************************************************************
함수명 : stcReNew
설 명 : 통계 데이터+하단 데이터테이블 생성 또는 갱신
인 자 : alData(was에서 받은 data)
사용법 : 
작성일 : 2020-12-28
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.12.28   정다빈       최초작성
 ************************************************************************/
function stcReNew(alData){
	//막대그래프에 쓰이는 배열
	titleArr=	['x'];
	rlxArr	=	['여유'];
	usArr	=	['보통'];
	careArr	=	['주의'];
	cwdArr	=	['혼잡'];
	
	$(alData).each(function(i,list) {
		titleArr.push(alData[i].title);
		
		//혼잡도 변화 건수가 0일 경우 숫자가 그래프에 안나오게 함
		if(list.rlxCnt==0){
			rlxArr.push(null);
		}else{
			rlxArr.push(alData[i].rlxCnt);	
		}
		
		if(list.usCnt==0){
			usArr.push(null);
		}else{
			usArr.push(alData[i].usCnt);
		}

		if(list.careCnt==0){
			careArr.push(null);
		}else{
			careArr.push(alData[i].careCnt);
		}

		if(list.cwdCnt==0){
			cwdArr.push(null);
		}else{
			cwdArr.push(alData[i].cwdCnt);
		}

	});
	//테이블 갱신
    $("#tbody").empty();
    var tbCont="";
    	$(alData).each(function(i,list) {
    		
    		tbCont += 	 "<tr><td>"+list.title+"</td>"	
						+"<td>"+list.rlxCnt+"</td>"	
						+"<td>"+list.usCnt+"</td>"		
						+"<td>"+list.careCnt+"</td>"		
						+"<td>"+list.cwdCnt+"</td>"	
						+"<td>"+list.avgCwd+"</td>"	
						+"<td>"+list.minCwd+"</td>"	
						+"<td>"+list.minCnum+"</td>"		
						+"<td>"+list.minDt+"</td>"		
						+"<td>"+list.maxCwd+"</td>"	
						+"<td>"+list.maxCnum+"</td>"		
						+"<td>"+list.maxDt+"</td></tr>";
    	});
    $("#tbody").append(tbCont);
}
   
