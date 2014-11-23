<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript">
<!--

	$(function() {
		$(":input").bind("keyup", function(event) {
			if (event.keyCode == "13") {
				search();
			}
		});
	});
	
function search() {
	var ahForm = document.getElementById("form");
	var pageNo = document.getElementById("page.pageNo");
	pageNo.value = 1;
	if (ahForm != null) {
		ahForm.submit();
	}
}
//-->
</script>

<div style="width: 100%; text-align: left;">
	当前位置：经纪人管理 &gt; 手机验证历史记录
</div>
<br />
<div id="tabs" class="tabs">
	<ul>
		<li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/agentVerify/verifyMobileList/1">手机验证审核 </a></li>
		<li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/agentVerify/verifyMobileList/2">手机验证审核记录</a></li>
	</ul>
</div>
<form id="form" action="/agentVerify/verifyMobileList/2" method="post">
<input type="hidden" name="type" value="${type }"/>
<div class="mag">
	经纪人ID：<input type="text" id="agentId" name="agentId" value="${agentId}" /> 手机号码:<input name="phone" id="phone" type="text" value="${phone }" /> <input type="button" value="搜索" onclick="search();" />
</div>

<div class="mag">
	<div class="todo">
	<c:if test="${not empty err }">
		<font color="red" size="5px">请检查您的搜索条件,输入有误!</font>
	</c:if>
		<c:if test="${not empty list }">
			<table>
				<thead>
					<tr>
						<th width="14.28%">经纪人ID</th>
						<th width="14.28%">手机号码</th>
						<th width="14.28%">姓名</th>
						<th width="14.28%">审核状态</th>
						<th width="14.28%">操作者</th>
						<th width="14.28%">提交日期</th>
						<th width="14.28%">审核日期</th>
					</tr>
				</thead>
				<tbody class="rasent">

					<c:forEach items="${list}" var="item" varStatus="status">
						<tr style="color: black" id="${item.agentId }">
							<td>${item.agentId }</td>
							<td>${item.mobile }</td>
							<td>${item.userName }</td>
							<td>
								<c:if test="${item.result==1 }">通过</c:if>
								<c:if test="${item.result==2 }">拒绝</c:if>
							</td>
							<td>${item.auditorName }</td>
							<td>
								<c:if test="${not empty item.insertDate }">
									<fmt:formatDate value="${item.insertDate }" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate>
								</c:if>
								<c:if test="${empty item.insertDate }">暂无信息</c:if>
							</td>
							<td><fmt:formatDate value="${item.date }" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</c:if>
		<c:if test="${empty list }">
			<h2>暂时没有数据.</h2>
		</c:if>
		<br>
	</div>
		<div class="page_and_btn" style="text-align: center;">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
</div>
</form>