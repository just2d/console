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
					$("*").stop();
					if(res.auditStep==5||res.auditStep==6){
						$("#photoRejectInfo").html(res.rejectReason == undefined ? '' : "" + res.rejectReason);
					}
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

</script>
</head>
<body>
	<div id="tabs" class="tabs">

		<ul>

			<li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/1?per=1">标题描述审核记录</a></li>
			<li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/2?per=1">图片审核记录</a></li>
			<li class="${type==3?'tabs_active':'' }" style="${type==3?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/3?per=1">经纪人审核记录</a></li>
			<!-- <li><a href="javascript:;" style="background: url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%">经纪人审核记录</a></li> -->

		</ul>
	</div>
	
	<form action="/auditHistory/list/${type }?per=1" method="post" id="form">
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
							<c:if test="${type==3 }"><c:if test="${item.auditResult==-1 }">通过　　</c:if>
							<c:if test="${item.auditResult==-2 }">违规　　</c:if>
							</c:if>
							<c:if test="${type==1 }">
							<c:if test="${item.auditResult==1 }">通过　　</c:if>
							<c:if test="${item.auditResult==2 }">违规　　</c:if>
							</c:if>
							
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%--是否是个人查看 --%>
		<input name="per" value="1" type="hidden"/>

		
		<div id="divCheck" class="todo" style="text-align: center; display: none"></div>
		
		<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
</body>
</html>
<div id="toShowPicDiv" style="display: none">
	<img id="toShowPicImg"></img><br></br> <font id="photoInfo"></font><br/><font id="photoRejectInfo" color="red"></font>
</div>
<div id="toShowInfoDiv" style="display: none">
	<font id="title"></font><br></br> <font id="describe"></font><br></br> <font id="reason"></font><br></br>
</div>