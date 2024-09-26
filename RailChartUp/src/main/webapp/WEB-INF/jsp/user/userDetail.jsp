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
	<script type="text/javascript">
	//이메일 체크
	$(document).ready( function() {
		var tagId='${data.userId}';
		//console.log("상세정보 진입 : "+tagId);
		//목록 버튼 클릭 시
		$("#btnList").click(function(){
			$("#content").load("/user/userList.do"); 
		});
		
		//수정 버튼 클릭 시
		$("#btnUpdate").click(function(){
			 $("#content").load("/user/userUpdate.do",{"userId":tagId}); 
		});
		//삭제 버튼 클릭 시
		$("#btnDelete").click(function(){
			if(confirm("선택하신 회원을 삭제하시겠습니까?")==true){
				var idArr=[]; // 회원 id값 배열
				idArr.push(tagId);//배열에 아이디 값 삽입
//				//console.log("보낼 값 : "+ idArr);
				var url="/user/userDelete.ajax";
				var data = {"idArr":idArr};
				var callback= "/user/userList.do";
				ajaxMethod(url, data, callback);
    		}
		});//btnDelte

	});//ready

</script>
</head>
<body>
<!-- APP 설명 및 다운로드 -->
	<h3>사용자 상세정보</h3>
		<div class="tbMng-nonbt">
			<table class='tbList-nonbt'>
				<tr>
					<td>ID</td>
					<td class="td-detail">${data.userId}</td>
				</tr>
				<tr>
					<td>이름</td>
					<td class="td-detail">${data.userName}</td>
				</tr>
				<tr>
					<td>직급</td>
					<td class="td-detail">${data.userRank}</td>
				</tr>
				<tr>
					<td>부서</td>
					<td class="td-detail">${data.userDept}</td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td class="td-detail">${data.userPhone}</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td class="td-detail">${data.userEmail}</td>
				</tr>
				<tr>
					<td>가입일</td>
					<td class="td-detail">${data.regDt}</td>
				</tr>
			</table>
		</div>
	
	<div class="btnDiv" style="float:none;position: relative;top: 0px;left:200px;margin-top: 50px;">
		<button  id="btnList"  class="btn-small">목록</button>
		<button  id="btnUpdate"  class="btn-small">수정</button>
		<button  id="btnDelete"  class="btn-small">삭제</button>
	</div>
</body>
</html>