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
<script type="text/javascript">
	$(function() {
		$(':input').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				var ahForm = document.getElementById("form");
				ahForm.submit();
			}
		});
		
		var estateCount = 0;
		$("[name='estate']").each(function(){
			estateCount += parseInt($(this).text());
		});
		$("#estate").text(estateCount);
		
		var houseHold = 0;
		$("[name='houseHold']").each(function(){
			houseHold += parseInt($(this).text());
		});
		$("#houseHold").text(houseHold);
		
		var indoor = 0;
		$("[name='indoor']").each(function(){
			indoor += parseInt($(this).text());
		});
		$("#indoor").text(indoor);
		
		var baseInfo = 0;
		$("[name='baseInfo']").each(function(){
			baseInfo += parseInt($(this).text());
		});
		$("#baseInfo").text(baseInfo);
		var agent = 0;
		$("[name='agent']").each(function(){
			agent += parseInt($(this).text());
		});
		$("#agent").text(agent);
	});
	
	function search() {
		var ahForm = document.getElementById("form");
		if (($.trim($("input[name='startTime']").val()) != "" && $.trim($("input[name='endTime']").val()) == "")
				|| ($.trim($("input[name='startTime']").val()) == "" && $.trim($("input[name='endTime']").val()) != "")) {
			alert("请完成日期选择!");
			return;
		}
		//var pageNo = document.getElementById("page.pageNo");
		//pageNo.value = 1;
		if (ahForm != null) {
			ahForm.submit();
		}
	}
	
	function toDetails(id){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var url = "/auditHistory/auditErrorDetail?auditId="+id+"&startTime="+startTime+"&endTime="+endTime;
		window.open(url);
	}
</script>
</head>
<body>
	<form action="/auditHistory/statist" method="post" id="form">
		<div class="mag" style="padding-top: 10px;">
			时间：
			<input id="startTime" name="startTime" value="${startTime }" style="width: 80px;" maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" />
			 ---- 
			 <input id="endTime" name="endTime" value="${endTime }" style="width: 80px;" maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" /> 
			 
			 <input type="button" onclick="search();" value="查找" />
		</div>
		<div class="todo">
			<table width="100%">
				<tr>
					<th>姓名（错审数量）</th>
					<th>标题审核个数</th>
					<th>户型图审核个数</th>
					<th>室内图审核个数</th>
					<th>小区图审核个数</th>
					<th>经纪人审核个数</th>
				</tr>
				<c:forEach items="${list }" var="item">
					<tr>
						<td><a href="#" onclick="toDetails(${item.user.id})">${item.user.chnName }（${item.totalBadCount }）</a></td>
						<td name="baseInfo">${item.auditBaseInfoCount}</td>
						<td name="houseHold">${item.auditHouseholdPhotoCount }</td>
						<td name="indoor">${item.auditIndoorPhotoCount }</td>
						<td name="estate">${item.auditEstatePhotoCount }</td>
						<td name="agent">${item.auditAgentCount }</td>
					</tr>
				</c:forEach>
				<tr>
					<td>总量</td>
					<td id="baseInfo"></td>
					<td id="houseHold"></td>
					<td id="indoor"></td>
					<td id="estate"></td>
					<td id="agent"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>