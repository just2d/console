<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/agent/agentVerify.js"></script>

<div style="width: 100%;text-align: left;">当前位置：经纪人管理&gt; 拒绝理由管理</div>

<div id="addReasonDiv" <c:if test="${addRecord == 1}"> style="display:block;"</c:if>>
	<form action="/agentVerify/addRejectReason" method="post" id="addRejectReasonForm">
		<input type="hidden" name="inputRecord" id="inputRecord"/>
		<table style="border-collapse: collapse;margin-bottom:10px;">
			<tr>
				<td style=" height:30px; margin-bottom:10px;">类型：</td>
				<td style=" text-align:left">
					<select id="reasonType" style="widht:150px;">
						<option value="0">身份证理由</option>
						<option value="1">头像理由</option>
						<option value="2">名片理由</option>
						<option value="3">二手房审核违规原因</option>
						<option value="4">二手房审核删除理由</option>
						<option value="5">租房审核违规原因</option>
						<option value="6">租房审核删除理由</option>
						<option value="7">图片删除理由</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>理由：</td>
				<td style=" margin-top:6px;"><textarea cols="25" rows="5" name="inputReason" id="inputReason" style="font-size:12px; margin-bottom:6px;"></textarea></td>
			</tr>
		</table>
		<input type="button" value="提交" onClick="submitInputReason();"/>
		<input type="button" value="退出" onClick="showAddReasonDiv(false);"/>
	</form>
</div>

<div id="updateReasonDiv">
	<form action="/agentVerify/updateRejectReason" method="post" id="updateRejectReasonForm">
		<input type="hidden" name="updateRecord" id="updateRecord"/>
		<input type="hidden" name="updateRecordId" id="updateRecordId"/>
		<table  style="border-collapse: collapse;">
			<tr>
				<td style=" height:30px; margin-bottom:10px;">类型：</td>
				<td style=" text-align:left">
					<select id="updateReasonType">
						<option value="0">身份证理由</option>
						<option value="1">头像理由</option>
						<option value="2">名片理由</option>
						<option value="3">二手房审核违规原因</option>
						<option value="4">二手房审核删除理由</option>
						<option value="5">租房审核违规原因</option>
						<option value="6">租房审核删除理由</option>
						<option value="7">图片删除理由</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>理由：</td>
				<td><textarea cols="25" rows="5" name="inputReason" id="updateInputReason" style="font-size:12px; margin-bottom:6px;"></textarea></td>
			</tr>
		</table>
		<input type="button" value="提交" onClick="submitUpdateReason();"/>
		<input type="button" value="退出" onClick="$('#updateReasonDiv').fadeOut('slow');"/>
	</form>
</div>

<form action="/agentVerify/delRejectReason" method="post" id="delRejectReasonForm">
	<input type="hidden" name="delReasonId" id="delReasonId"/>
</form>

<form action="/agentVerify/rejectReason" method="post" style="">
	<div class="todo">
		<table id="rejectReasonTable">
			<thead>
				<tr>
					<th style="width:10%;">编号</th>
					<th style="width:15%;">类型</th>
					<th style="width:65%;">理由/<span style=" color: #000099; text-decoration: underline; cursor:pointer" onClick="showAddReasonDiv(true);">添加理由</span></th>
					<th style="width:10%;">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${not empty reasons}">
					<c:forEach items="${reasons}" var="reason" varStatus="vs">
						<tr class="main_info">
							<td style="width:10%; text-align:center">
								${vs.index + 1}、
							</td>
							<td style="width:15%;text-align:center">
								<c:choose>
									<c:when test="${reason.type == 0}">
										<c:out value="身份证拒绝理由"></c:out>
									</c:when>
									<c:when test="${reason.type == 1}">
												<c:out value="头像拒绝理由"></c:out>
									</c:when>
									<c:when test="${reason.type == 2}">
												<c:out value="名片拒绝理由"></c:out>
									</c:when>
									<c:when test="${reason.type == 3}">
												<c:out value="二手房审核违规原因"></c:out>
									</c:when>
									<c:when test="${reason.type == 4}">
												<c:out value="二手房审核删除理由"></c:out>
									</c:when>
									<c:when test="${reason.type == 5}">
												<c:out value="租房审核违规原因"></c:out>
									</c:when>
									<c:when test="${reason.type == 6}">
												<c:out value="租房审核删除理由"></c:out>
									</c:when>
									<c:when test="${reason.type == 7}">
												<c:out value="图片删除理由"></c:out>
									</c:when>
								
								</c:choose>
							</td>
							<td style="width:65%;text-align:center">
								<c:out value="${reason.reason}"></c:out>
							</td>
							<td style="width:10%;text-align:center">
								<span class="linkSpan" onClick="showUpdateReasonDiv(${reason.id},${reason.type},'${reason.reason}');">修改</span> | <span class="linkSpan" onClick="delReason(${reason.id})">删除</span>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="7">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
	<div class="page_and_btn">
		<jsp:include page="/WEB-INF/snippets/page.jsp" />
	</div>
</form>
<script type="text/javascript">
	$(function(){
		var w = $(document).width();
		var h = $(document).height();
		var iwidth = $('#addReasonDiv').width();
		var iheight = $('#addReasonDiv').height();
		
		var left = (w - iwidth)/2;
		var top = (h - iheight)/3;
		$('#addReasonDiv').css({left:left+"px", top:top+"px"});
		$('#updateReasonDiv').css({left:left+"px", top:top+"px"});
	});
</script>