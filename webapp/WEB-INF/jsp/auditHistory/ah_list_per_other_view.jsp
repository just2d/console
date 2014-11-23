<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘房网后台管理系统</title>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<style type="text/css">
.changed {
	color: red;
	background-color: pink;
}

.unchanged {
	color: black;
	background-color: white;
}
</style>
<script type="text/javascript">
	$(function() {
		$(':input').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				var ahForm = document.getElementById("form");
				ahForm.submit();
			}
		});
		var h = $(window).height();
		$("#divCheck").dialog({
			show : "slide",
			bgiframe : true,
			autoOpen : false,
			height : h * 0.7,
			width : '60%',
			title : "查看信息",
			modal : true,
			resizable : false
		});
		$("#divBatchReject").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规房源",modal : true,resizable : false,buttons : {"确认" : rejectBatchSave}});
		$("#rejectAgentIdPhotoDiv").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规图片",modal : true,resizable : false,buttons : {"确认" : rejectBatchSave5}});
		$("#rejectAgentHeadPhotoDiv").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规图片",modal : true,resizable : false,buttons : {"确认" : rejectBatchSave6}});
		$("#rejectHousePhotoDiv").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规图片",modal : true,resizable : false,buttons : {"确认" : rejectBatchSave7}});
	});
	function search() {
		var ahForm = document.getElementById("form");
		if (($.trim($("input[name='startTime']").val()) != "" && $.trim($("input[name='endTime']").val()) == "")
				|| ($.trim($("input[name='startTime']").val()) == "" && $.trim($("input[name='endTime']").val()) != "")) {
			alert("请完成日期选择!");
			return;
		}
		var pageNo = document.getElementById("page.pageNo");
		pageNo.value = 1;
		if (ahForm != null) {
			ahForm.submit();
		}
	}

	// 显示信息dialog
	function openDialog(str) {
		var id_url = str.split("^^");
		var headPrefix = '${applicationScope.headPrefix}';
		$.ajax({
			type : "post",
			async : false,
			url : "/auditHistory/info/" + id_url[0] + "?ranNum=" + Math.random(),
			dataType : "json",
			data : {},
			success : function(data) {
				if (data.error) {
					alert(data.error);
					return;
				}
				var res = JSON.parse(data.info);
				if (res.auditStep != 4) {
					if(id_url[1]==''){
						id_url[1]='/m/house_m.png';
					}
					if(res.auditStep == 5){
						headPrefix = '${applicationScope.adminpicPrefix}';
					}
					$("#toShowPicImg").attr("src", headPrefix + id_url[1]);
					$("#photoInfo").html(res.photoDescribe == undefined ? '' : "" + res.photoDescribe);
					$("#photoRejectInfo").html(res.rejectReason == undefined ? '' : "" + res.rejectReason);
//					if(res.auditStep==5||res.auditStep==6){
						$("#photoRejectInfo").html(res.rejectReason == undefined ? '' : "" + res.rejectReason);
//					}
					$("*").stop();
					$("#divCheck").html($("#toShowPicDiv").html());
				}
				if (res.auditStep == 4) {
					$("#title").html(res.extra == undefined ? '' : "<b>房源标题:</b>" + res.extra);
					$("#describe").html(res.houseDescribe == undefined ? '' : "<b>房源描述:</b>" + res.houseDescribe);
					$("#reason").html(res.rejectReason == undefined ? '' : "<b>打回原因:</b>" + res.rejectReason);
					$("*").stop();
					$("#divCheck").html($("#toShowInfoDiv").html());
				}
				$("#divCheck").dialog("option", "position", "center");
				$("#divCheck").dialog("open");
			}
		});
	}

	// 复审
	function reAuditIt(str, obj) {
		var subId_auditId_result_step = str.split("_");
		var subId = subId_auditId_result_step[0];
		var result = subId_auditId_result_step[2];
		var auditStep = subId_auditId_result_step[3];

		// 要打回原因
		if (result == -2) {
			$("*").stop();
			//当前操作的id
			var reasonsId = subId;
			$("#currentId").val(reasonsId);
			if(auditStep==4){
				$("#divBatchReject").dialog("option", "position", "center");
				$("#divBatchReject").dialog("open");
			}else if(auditStep==5){
				$("#rejectAgentIdPhotoDiv").dialog("option", "position", "center");
				$("#rejectAgentIdPhotoDiv").dialog("open");
			}else if(auditStep==6){
				$("#rejectAgentHeadPhotoDiv").dialog("option", "position", "center");
				$("#rejectAgentHeadPhotoDiv").dialog("open");
			}else if(auditStep==1||auditStep==2||auditStep==3){
				$("#rejectHousePhotoDiv").dialog("option", "position", "center");
				$("#rejectHousePhotoDiv").dialog("open");
			}
				return;
		}
		
		$.ajax({
			type : "post",
			async : false,
			url : "/auditHistory/reaudit/" + subId + "/" + result + "?ranNum=" + Math.random(),
			dataType : "json",
			success : function(data) {
				if (data.error) {
					alert(data.error);
					return;
				}
				if (data.info == "success") {
					changeColourAndContent(obj, str);
				}
			}
		});
	}

	// 改变背景和颜色
	function changeColourAndContent(obj, str) {
		var parentTR = $(obj).parent().parent();
		if ($(parentTR).attr("class") == "" || $(parentTR).attr("class") == undefined || $(parentTR).attr("class") == "unchanged") {
			$(parentTR).attr("class", "changed");
		} else if ($(parentTR).attr("class") == "changed") {
			$(parentTR).attr("class", "unchanged");
		}

		var subId_auditId_result_step = str.split("_");
		var subId = subId_auditId_result_step[0];
		var auditId = subId_auditId_result_step[1];
		var result = subId_auditId_result_step[2];
		var auditStep = subId_auditId_result_step[3];
		var content = "";
		if (result == -1) { // 点击处要变成违规  俩字
			content += "通过　　<a href='#' onclick=reAuditIt('" + subId + "_" + auditId + "_-2_" + auditStep + "',this);>删除</a>";
		} else if (result == -2) { // 点击处要变成通过  俩字
			content += "违规　　<a href='#' onclick=reAuditIt('" + subId + "_" + auditId + "_-1_" + auditStep + "',this);>恢复</a>";
		}
		
		content += "<input type='hidden' name='reason'  id='reason_"+subId+"' value=''>";

		$(obj).parent().html(content);
	}

	//这是dialg关闭事件的回调函数

	function closeDialog() {
		var reasonsId = $("#currentId").val();
		var reason = $.trim($("#reason_" + reasonsId).val());
		if (reason == '') {
			alert("没有打回原因,将不打回.");
			return;
		}
		$.ajax({
			type : "post",
			async : false,
			url : "/auditHistory/reaudit/" + reasonsId + "/-2?ranNum=" + Math.random(),
			dataType : "json",
			data : {"reason" : reason},
			success : function(data) {
				if (data.error) {
					alert(data.error);
					return;
				}
				if (data.info == "success") {
					var obj = $("#"+reasonsId).children();
					var str = $("#"+reasonsId).attr("lang");
					changeColourAndContent(obj, str);
				}
			}
		});
	}

	//保存违规原因
	function rejectBatchSave() {
		var reasons = "";
		var i = 1;
		while (true) {
			var reason = $("#batchReasons_" + i);
			if (reason[0]) {
				if (reason.is(':checked')) {
					reasons += $("#batchReasons_" + i + "_value").html() + "<br /> ";
				}
			} else {
				break;
			}
			i++;
			if (i > 30) {
				break;
			}
		}
		reasons += $("#batchReasons").val().trim();
		if (reasons.trim() == "") {
			alert("请选择或填写违规原因！");
			return;
		}
		var reasonsId = $("#currentId").val();
		$("#reason_" + reasonsId).val(reasons.trim());
		//取消选择
		j = 1;
		while (true) {
			var reason = $("#batchReasons_" + j);
			reason.attr("checked", false);
			j++;
			if (j > 30) {
				break;
			}
		}
		$("#batchReasons").val("");
		//关闭打回原因选择
		$("#divBatchReject").dialog("close");
	}
	
	//保存违规原因(新的)
	function rejectBatchSave5() {
		var reasons = "";
		var i = 1;
		while (true) {
			var reason = $("#batchReasons2_" + i);
			if (reason[0]) {
				if (reason.is(':checked')) {
					reasons += $("#batchReasons2_" + i + "_value").html() + "<br /> ";
				}
			} else {
				break;
			}
			i++;
			if (i > 30) {
				break;
			}
		}
		reasons += $("#batchReasons2").val().trim();
		if (reasons.trim() == "") {
			alert("请选择或填写违规原因！");
			return;
		}
		var reasonsId = $("#currentId").val();
		$("#reason_" + reasonsId).val(reasons.trim());
		//取消选择
		j = 1;
		while (true) {
			var reason = $("#batchReasons2_" + j);
			reason.attr("checked", false);
			j++;
			if (j > 30) {
				break;
			}
		}
		$("#batchReasons2").val("");
		//关闭打回原因选择
		$("#rejectAgentIdPhotoDiv").dialog("close");
	}

	//保存违规原因(新的)
	function rejectBatchSave6() {
		var reasons = "";
		var i = 1;
		while (true) {
			var reason = $("#batchReasons3_" + i);
			if (reason[0]) {
				if (reason.is(':checked')) {
					reasons += $("#batchReasons3_" + i + "_value").html() + "<br /> ";
				}
			} else {
				break;
			}
			i++;
			if (i > 30) {
				break;
			}
		}
		reasons += $("#batchReasons3").val().trim();
		if (reasons.trim() == "") {
			alert("请选择或填写违规原因！");
			return;
		}
		var reasonsId = $("#currentId").val();
		$("#reason_" + reasonsId).val(reasons.trim());
		//取消选择
		j = 1;
		while (true) {
			var reason = $("#batchReasons3_" + j);
			reason.attr("checked", false);
			j++;
			if (j > 30) {
				break;
			}
		}
		$("#batchReasons3").val("");
		//关闭打回原因选择
		$("#rejectAgentHeadPhotoDiv").dialog("close");
	}
	
	//保存违规原因(新的)
	function rejectBatchSave7() {
		var reasons = "";
		var i = 1;
		while (true) {
			var reason = $("#batchReasons4_" + i);
			if (reason[0]) {
				if (reason.is(':checked')) {
					reasons += $("#batchReasons4_" + i + "_value").html() + "<br /> ";
				}
			} else {
				break;
			}
			i++;
			if (i > 30) {
				break;
			}
		}
		reasons += $("#batchReasons4").val().trim();
		if (reasons.trim() == "") {
			alert("请选择或填写违规原因！");
			return;
		}
		var reasonsId = $("#currentId").val();
		$("#reason_" + reasonsId).val(reasons.trim());
		//取消选择
		j = 1;
		while (true) {
			var reason = $("#batchReasons4_" + j);
			reason.attr("checked", false);
			j++;
			if (j > 30) {
				break;
			}
		}
		$("#batchReasons4").val("");
		//关闭打回原因选择
		$("#rejectHousePhotoDiv").dialog("close");
	}
</script>
</head>
<body>
	<div id="tabs" class="tabs">

		<ul>

			<li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/1?userId=${userId}">标题描述审核记录</a></li>
			<li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/2?userId=${userId}">图片审核记录</a></li>
			<li class="${type==3?'tabs_active':'' }" style="${type==3?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/3?userId=${userId}">经纪人审核记录</a></li>
			
			<!-- <li><a href="javascript:;" style="background: url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%">经纪人审核记录</a></li> -->

		</ul>
	</div>
	
	<form action="/auditHistory/list/${type }?userId=${userId}" method="post" id="form">
		<div class="mag" style="padding-top: 10px;">
			<c:if test="${type==1||type==2 }">房源</c:if><c:if test="${type==3 }">经纪人</c:if>id：<input name="houseid" value="${houseid }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /> 时间：<input name="startTime" value="${startTime }" style="width: 80px;"
				maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" /> <select name="sHour">
				<c:forEach var="hour" begin="0" end="23">
					<c:if test="${hour<10}">
						<option value="0${hour}" ${sHour== "0"+hour?'selected=selected':'' }>0${hour}时</option>
					</c:if>
					<c:if test="${hour>=10}">
						<option value="${hour}" ${sHour==hour? 'selected=selected':'' }>${hour}时</option>
					</c:if>
				</c:forEach>
			</select> ---- <input name="endTime" value="${endTime }" style="width: 80px;" maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" /> <select name="eHour"><c:forEach var="hour" begin="0" end="23">
					<c:if test="${hour<10}">
						<option value="0${hour}" ${eHour== "0"+hour?'selected=selected':'' }>0${hour}时</option>
					</c:if>
					<c:if test="${hour>=10}">
						<option value="${hour}" ${eHour==hour? 'selected=selected':'' }>${hour}时</option>
					</c:if>
				</c:forEach></select> <input type="button" onclick="search();" value="查找" />
		</div>
		<div class="todo">
			<table width="100%">
				<tr>
					<th><c:if test="${type==1||type==2 }">房源</c:if><c:if test="${type==3 }">经纪人</c:if>id</th>
					<c:if test="${type==1||type==2 }">
						<th>房源类型</th>
					</c:if>
					<th>用户名id</th>
					<th>处理人</th>
					<th>描述</th>
					<th>审核日期</th>
					<th><select name="auditResult" onchange="search();">
							<option value="0" <c:if test="${auditResult== '0'}">selected</c:if> >全部结果</option>
							<option value="-1" <c:if test="${auditResult== '-1'}">selected</c:if> >通过</option>
							<option value="-2" <c:if test="${auditResult== '-2'}">selected</c:if> }>违规</option>
					</select></th>
				</tr>
				<c:forEach items="${list }" var="item">
					<tr>
						<td>${item.houseId }</td>
						<c:if test="${type==1||type==2 }">
							<td>${item.houseType==2?'出租房':'二手房' }</td>
						</c:if>
						<td>${item.authorId }</td>
						<td>${item.dealerName }</td>
						<td><a onclick="openDialog('${item.id}^^${item.photoUrl }');"> <c:choose>
									<c:when test="${fn:length(item.extra) >= 20 }">${fn:substring(item.extra, 0, 20)}...</c:when>
									<c:otherwise>${item.extra }</c:otherwise>
								</c:choose>
						</a></td>
						<td><fmt:formatDate value="${item.auditTime }" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate></td>
						<td id="${item.id }" lang="${item.id}_${item.auditId }_-2_${item.auditStep }">
							<c:if test="${type==3 }"><c:if test="${item.auditResult==-1 }">通过　　<a href="#" onclick="reAuditIt('${item.id}_${item.auditId }_-2_${item.auditStep }',this);">删除</a></c:if>
							<c:if test="${item.auditResult==-2 }">违规　　<a href="#" onclick="reAuditIt('${item.id}_${item.auditId }_-1_${item.auditStep }',this);">恢复</a></c:if>
							</c:if>
							<c:if test="${type==1 }">
							<c:if test="${item.auditResult==1 }">通过　</c:if>
							<c:if test="${item.auditResult==2 }">违规　</c:if>　
							　</c:if>
							<c:if test="${type==1||type==2||type==3 }"><input type="hidden" name="reason"  id="reason_${item.id}" value=""></c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%--是否是个人查看 --%>
		<input name="userId" value="${userId }" type="hidden"/>
		
		<input id="currentId" value="" type="hidden"/>

		
		<div id="divCheck" class="todo" style="text-align: center; display: none"></div>
		
		<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
</body>
</html>
<div id="toShowPicDiv" style="display: none">
	<img id="toShowPicImg"></img><br></br> <font id="photoInfo"></font><br></br> <font id="photoRejectInfo" color="red"></font>
</div>
<div id="toShowInfoDiv" style="display: none">
	<font id="title"></font><br></br> <font id="describe"></font><br></br> <font id="reason"></font><br></br>
</div>
<div id="divBatchReject">
	<table>
		<tr>
			<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach items="${reasons}" var="list" varStatus="vs">
					<input type="checkbox" id="batchReasons_${vs.index+1 }"><label id="batchReasons_${vs.index+1 }_value">${list.reason}</label><br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea" id="batchReasons" name="batchReasons" cols="40" rows="3"></textarea></td>
		</tr>
	</table>
</div>
<div id="rejectAgentIdPhotoDiv">
	<table>
		<tr>
			<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach items="${agentIdReasons}" var="list" varStatus="vs">
					<input type="checkbox" id="batchReasons2_${vs.index+1 }"><label for="batchReasons2_${vs.index+1 }" id="batchReasons2_${vs.index+1 }_value">${list.reason}</label><br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea" id="batchReasons2" name="batchReasons" cols="40" rows="3"></textarea></td>
		</tr>
	</table>
</div>
<div id="rejectAgentHeadPhotoDiv">
	<table>
		<tr>
			<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach items="${agentHeadReasons}" var="list" varStatus="vs">
					<input type="checkbox" id="batchReasons3_${vs.index+1 }"><label for="batchReasons3_${vs.index+1 }" id="batchReasons3_${vs.index+1 }_value">${list.reason}</label><br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea" id="batchReasons3" name="batchReasons" cols="40" rows="3"></textarea></td>
		</tr>
	</table>
</div>
<div id="rejectHousePhotoDiv">
	<table>
		<tr>
			<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach items="${photoReasons}" var="list" varStatus="vs">
					<input type="checkbox" id="batchReasons4_${vs.index+1 }"><label for="batchReasons4_${vs.index+1 }" id="batchReasons4_${vs.index+1 }_value">${list.reason}</label><br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea" id="batchReasons4" name="batchReasons" cols="40" rows="3"></textarea></td>
		</tr>
	</table>
</div>