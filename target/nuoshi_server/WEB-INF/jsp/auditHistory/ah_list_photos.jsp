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
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/common/jquery.colorbox-min.js"></script>
<script type="text/javascript">
	$(function(){
		$(':input').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				search();
			}
		});
	});
	function getphotos(obj,photoids,audit_ids,audit_step,audit_results,thisId,house_type){
		var url = "/auditHistory/getphotos?photoids=" + photoids +"&audit_ids=" + audit_ids + "&auditStep=" + audit_step + "&results=" + audit_results +"&thisHistoryId=" + thisId+"&house_type=" + house_type;
		$.colorbox({href:url,transition:"elastic",open:true});
	}
	function search(){
		$("#form").submit();
	}
	function showSelect(obj){
		$(obj).hide();
		$("#show1").show();
		$("#show1").attr("disabled",false);
		$("#show2").show();
	}
</script>
</head>
<body>
	<div id="tabs" class="tabs">
		<ul>
			<li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/1${not empty per?'?per=1':'' }">标题描述审核记录</a></li>
			<li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/2${not empty per?'?per=1':'' }">图片审核记录</a></li>
			<li class="${type==3?'tabs_active':'' }" style="${type==3?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/auditHistory/list/3${not empty per?'?per=1':'' }">经纪人审核记录</a></li>
		</ul>

	</div>
	<form action="/auditHistory/list/${type }" method="post" id="form">
	<input type="hidden" name="per" value="${per }"/>
	<input type="hidden" name="userId" value="${userId }"/>
		<div class="mag" style="padding-top: 10px;">
			房源ID：<input id="fakeInput" onclick="showSelect(this);"></input>
			<select id="show1" style="display: none;" disabled="disabled" name="house_type" onchange="document.getElementById('show2').focus();">
				<option value="2">二手房</option>
				<option value="1">出租房</option>
			</select>
			<input id="show2" style="display: none;" name="houseid" value="${houseid }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /> 
			经纪人ID：<input name="authorOrId" value="${authorOrId }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /> 
			处理人名字：<input name="dealerName" value="${dealerName }" /> <br></br>时间：<input name="startTime" value="${startTime }" style="width: 100px;"
				maxLength="10" class="Wdate" onclick="WdatePicker({lang:'zh-cn',minDate:'2012-04-25'});" readonly="readonly" /> <select name="sHour">
				<c:forEach var="hour" begin="0" end="23">
					<c:if test="${hour<10}">
						<option value="0${hour}" ${sHour== "0"+hour?'selected=selected':'' }>0${hour}时</option>
					</c:if>
					<c:if test="${hour>=10}">
						<option value="${hour}" ${sHour==hour? 'selected=selected':'' }>${hour}时</option>
					</c:if>
				</c:forEach>
			</select> ---- <input name="endTime" value="${endTime }" style="width: 100px;" maxLength="10" class="Wdate" onclick="WdatePicker({lang:'zh-cn',minDate:'2012-04-25'});" readonly="readonly" /> <select name="eHour"><c:forEach var="hour" begin="0" end="23">
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
					<th>审核人名称(ID)</th>
					<th>房源类型</th>
					<th>图片类型(可点击)</th>
					<th>审核日期</th>
				</tr>
				<c:forEach items="${list }" var="item">
					<tr>
						<td><a href="/auditHistory/list/${type }?userId=${item.dealerId }" target="_blank">${item.dealerName }（${item.dealerId }）</a></td>
						<td>
							<c:choose>
								<c:when test="${empty houseType }">
									<c:if test="${item.houseType != 1 && item.houseType != 2}">暂无信息</c:if><c:if test="${item.houseType == 1}">出租房</c:if><c:if test="${item.houseType == 2}">二手房</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${houseType != 1 && houseType != 2}">暂无信息</c:if><c:if test="${houseType == 1}">出租房</c:if><c:if test="${houseType == 2}">二手房</c:if>
								</c:otherwise>
							</c:choose>
						</td>
						<td style="cursor: pointer;" ><c:if test="${item.auditStep==2 }">户型图</c:if><c:if test="${item.auditStep==3 }">室内图</c:if><c:if test="${item.auditStep==4 }">小区图</c:if></td>
						<td><fmt:formatDate value="${item.auditTime }" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
</body>
</html>