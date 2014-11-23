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
	
	$(function() {
		var h = $(window).height();
		// 初始化弹出窗口
		$("#divCheck").dialog({show : "slide",bgiframe : true,autoOpen : false,height :h*0.8,width : '80%',title : "查看房源",modal : true,resizable : false});
	});
	//显示审核窗口
	function openCheck(id) {
		$("*").stop();
		//$("#resaleId").html(id);
		$("#divCheck").html($("#box"+id).html());
		$("#divCheck").dialog("close");
		$("#divCheck").dialog("option", "position", "center");
		$("#divCheck").dialog("open");
	}
	
</script>
</head>
<body>
	<form action="/auditHistory/auditErrorDetail" method="post" id="form">
		<input id="auditId" name="auditId" type="hidden" value="${auditId }" />
		<div class="mag" style="padding-top: 10px;">
			时间：
			<input id="startTime" name="startTime" value="${startTime }" style="width: 80px;" maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" />
			 ---- 
			 <input id="endTime" name="endTime" value="${endTime }" style="width: 80px;" maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" /> 
			 
			 <input type="button" onclick="search();" value="查找" />
		</div>
		<div class="todo">
			<table width="100%">
				<thead>
					<th>房源/经纪人ID</th>
					<th>审核原因</th>
					<th>审核结果</th>
					<th>审核时间</th>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="item">
						<tr>
							<td>
								<a href="javascript:void(0)" class="link" onclick="openCheck('${item.houseId}')"  >${item.houseId }</a>
								<c:if test="${item.houseType != 3}">
								<div id="box${item.houseId}" style="display: none;" >
										<table style="width: 95%;" >
											<tr>
												<th style="width: 25%; text-align: center">房源信息</th>
												<th style="width: 25%; text-align: center">户型图</th>
												<th style="width: 25%; text-align: center">室内图</th>
												<th style="width: 25%; text-align: center">小区图</th>
											</tr>
											<tr style="background-color: #FFFFFF">
												<td style="width: 25%; text-align: left; vertical-align: top;">
													<div style="width: 315px; padding: 5px; white-space: normal; line-height: 18px; text-align: left; font-size: 12px;">
														<h3>${item.houseObj.title}(id:${item.houseId})</h3>
														<ul>
															<li>总价：<strong>${item.houseObj.price}</strong>万</li>
															<li class="price">单价：<span><fmt:formatNumber
																		type="number" pattern="0"
																		value="${item.houseObj.price/item.houseObj.area*10000}"
																		maxFractionDigits="0" />
															</span>/㎡</li>
															<li>户型：${item.houseObj.flattype}</li>
															<li class="area">面积：<span>${item.houseObj.area}</span>平米</li>
															<li>楼层：${item.houseObj.floorLabel}</li>
															<li>年份：${item.houseObj.completion}年</li>
															<li>小区：${item.houseObj.estatename}</li>
															<li>地址：${item.houseObj.brandAddress}</li>
															<c:if test="${item.houseObj.extinfo.length() == 0 }">
																<li class="isempty">没写描述信息</li>
															</c:if>
															<c:if test="${item.houseObj.extinfo.length() > 0 }">
																<li>房屋描述：${item.houseObj.extinfo}</li>
															</c:if>
														</ul>
													</div>
												</td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${item.layoutImgUrls}" var="img">
														<img src="${img.mURL}" width="200px" height="150" />
													</c:forEach></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${item.innerImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${item.estateImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
	
											</tr>
										</table>
									</div>
								</c:if>
								<c:if test="${item.houseType == 3 }">
									<div id="box${item.houseId}" style="display: none;" >
										<table style="width: 95%;" >
											<tr>
												<th style="width: 25%; text-align: center">经纪人信息</th>
												<th style="width: 25%; text-align: center">身份证图</th>
												<th style="width: 25%; text-align: center">头像图</th>
												<th style="width: 25%; text-align: center">名片图</th>
											</tr>
											<tr style="background-color: #FFFFFF">
												<td style="width: 25%; text-align: left; vertical-align: top;">
													<div style="width: 315px; padding: 5px; white-space: normal; line-height: 18px; text-align: left; font-size: 12px;">
														<h3>${item.houseObj.name}(id:${item.houseObj.agentId})</h3>
														<ul>
															<li>公司：${item.houseObj.company}</li>
															<li>门店：${item.houseObj.store }</li>
															<%-- <li>城市：${item.houseObj.}</li>
															<li>区域：${item.houseObj.area}</li>
															<li>板块：${item.houseObj.floorLabel}</li>
															<li>可用视频数目：${item.houseObj.availableVcrNum}</li>
															<li>年份：${item.houseObj}</li> --%>
															<li>可用房源数目：${item.houseObj.availableHouseNum}</li>
															<li>可用标签数目：${item.houseObj.availableLabelNum}</li>
															
															<li>房源数目：${item.houseObj.houseNum}</li>
															<li>标签数目：${item.houseObj.labelNum}</li>
															<li>付费状态：<c:if test="${item.houseObj.payStatus == 2}">付费</c:if><c:if test="${item.houseObj.payStatus == 1}">曾经付费</c:if><c:if test="${item.houseObj.payStatus == 0}">未付费</c:if></li>
															<li>认证状态：<c:if test="${item.houseObj.verifyStatus == 1}">认证</c:if><c:if test="${item.houseObj.verifyStatus == 0}">未认证</c:if></li>
														</ul>
													</div>
												</td>
												<td style="width: 25%; text-align: center; vertical-align: top;">
															<img src="${item.houseObj.msgIdcard}" width="200px" height="150" />
														
												</td>
												<td style="width: 25%; text-align: center; vertical-align: top;">
															<img src="${item.houseObj.msgHead}" width="200px" height="150" />
												</td>
												<td style="width: 25%; text-align: center; vertical-align: top;">
															<img src="${item.houseObj.msgNamecard}" width="200px" height="150" />
												</td>
	
											</tr>
										</table>
									</div>
								</c:if>
							</td>
							<td>${item.rejectReason }</td>
							<td>
								<c:if test="${item.auditResult == -1 }">通过</c:if>
								<c:if test="${item.auditResult == -2 }">拒绝</c:if>
							</td>
							<td>${item.auditTimeStr }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	<div id = "divCheck" class="todo" style="text-align: center;" >
	
	</div>
</body>
</html>