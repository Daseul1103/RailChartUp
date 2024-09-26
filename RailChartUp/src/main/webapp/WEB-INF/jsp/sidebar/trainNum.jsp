<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	$(document).ready(function() {
		//console.log("단일편성 목록 배치");
		fid = trainOne("2018-01-01");
	});
</script>
<div id="trainDiv">
	<div class="train-div">
		<div class="settingMenu">
			<ul class="settingTabBtn">
				<li class="tab_1"><a class="active" id="trainOne">단일 편성</a></li>
				<li class="tab_2"><a id="trainAll">전체 편성</a></li>
			</ul>
		</div>
		<div id="trainNum">
			<table id="trainTb" class="train-table">
				<tbody id="trainTbd"></tbody>
			</table>
		</div>
	</div>
	
	<div>
		<table id="trainCm" class="train-comm">
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/trainA-c.png">선택된 편성</td>
				<td><img src="<%=request.getContextPath()%>/images/train-c.png">선택가능</td>
				<td><img src="<%=request.getContextPath()%>/images/trainN-c.png">데이터 없음</td>
			</tr>
		</table>
	</div>
</div>
 --%>