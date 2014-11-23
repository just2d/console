<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	
	$(function() {
		$("#rejectReasonDiv").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 130,width : 600,title : "选择删除理由",modal : true,buttons : {"确定" : reject,"取消":closeRejectReason}});
	});
	function doSearch() {
		var type = $("#houseType").val();
		var cityId = $("#city").val();
		$("#vcrForm").attr("action", "/audit/vcr/list/" + type + "/" + cityId);
		$("#vcrForm").submit();
	}
	
	function approve(houseId) {
		$("#auditResult_" +  houseId).val(houseId + "_1");
		$("#approveButton" + houseId).attr("disabled", true);
		$("#rejectButton" + houseId).attr("disabled", false);
	}
	
	function reject() {
		var houseId = $("#operationHouseId").val();
		var rejectReason = $("input[name=rejectReason]:checked").val();
		$("#auditResult_" +  houseId).val(houseId + "_2_" + rejectReason);
		$("#rejectButton" + houseId).attr("disabled", true);
		$("#approveButton" + houseId).attr("disabled", false);
		closeRejectReason();
	}
	
	function submitResult(){
		var type = $("#houseType").val();
		var cityId = $("#city").val();
		var totalResult = "";
		$(".auditResult").each(function(){
			if($(this).val() != "") {
				totalResult += ($(this).val() + ",");
			}
		});
		$("#totalResult").val(totalResult.substring(0, totalResult.length - 1));
		$("#vcrForm").attr("action", "/audit/vcr/check/" + type + "/" + cityId);
		$("#vcrForm").submit();
	}
	
	function showRejectReason(houseId){
		$("#operationHouseId").val(houseId);
		$("*").stop();
		$("#rejectReasonDiv").dialog("close");
		$("#rejectReasonDiv").dialog("option", "position", "center");
		$("#rejectReasonDiv").dialog("open");
	}

	function closeRejectReason(){
		$("#operationHouseId").val("");
		$("*").stop();
		$("#rejectReasonDiv").dialog("close");
		$("#rejectButton" + houseId).attr("disabled", false);
	}
	
	// ajax获取城市、区域 
	function getDist(selectId,pid) {
		var selectElem = "#" + selectId;
		$.ajax({
			type : "GET",
			async : false,
			url : "/agentManage/ajax/zone/"+pid,
			dataType : "json",
			success : function(data) {
				$(selectElem).empty();
				var global = "<option value='-1'>www全国</option>";
				$(global).appendTo(selectElem);
				var list = data.distList;
				if (list != null && list.length > 0) {
					for (i in list) {
						var local = list[i];
						var option = "<option value='" + local.localid + "'>" + local.dirName + local.localname + "</option>";
						$(option).appendTo(selectElem);
					}
				}
			}
		});
	}

	//设定选中菜单
	function setSelected(selectItem, selectedValue){
		var $opt = $("#"+selectItem+" option[value="+selectedValue+"]");
		if($opt.size()){
			setTimeout(function(){
				$opt[0].selected = true;
			},100);
		}
		
	}
	
</script>

<form id="vcrForm" action="/audit/vcr/list/1" method="post">
	<input type="hidden" id="totalResult" name="totalResult" />
	<div style="text-align:left;">
		房源类型：
		<select id="houseType" style="width:120px;">
			<option value="1" <c:if test="${houseType == 1}">selected="selected"</c:if> >二手房</option>
			<option value="2" <c:if test="${houseType == 2}">selected="selected"</c:if> >租房</option>
		</select>
		房源ID:
		<input id="houseId" name="houseId" value="${houseId }" style="width:100px" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /> 
		城市：
		<select id="city" name="cityId" style="width:100px; position:relative; z-index:9"/>
		<input type="button" value="查询" onclick="doSearch();"/>
	</div>
	
	<div class="todo">
		<table style="width:100%;">
			<thead>
				<tr>
					<th>经纪人ID</th>
					<th>房源编号</th>
					<th>投诉次数</th>
					<th>上传时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody align="center" >
				<c:choose>
					<c:when test="${not empty tasks}">
						<c:forEach items="${tasks}" var="task">
							<tr>
								<input type="hidden" id="auditResult_${task.houseId}" class="auditResult"/>
								<td>${task.agentId}</td>
								<td><a href="${task.url}" target="_blank">${task.houseId}</a></td>
								<td>${task.complainCount}</td>
								<td>${task.vcrTime}</td>
								<td>
								<c:if test="${task.auditResult==0 }">	
									<input type="button" id="approveButton${task.houseId}" value="通过" onclick="approve(${task.houseId})" />
									<input type="button" id="rejectButton${task.houseId}" value="删除" onclick="showRejectReason(${task.houseId})" />
								</c:if>
								<c:if test="${task.auditResult==1 }">
									此视频已通过
									<c:if test="${sessionScope.sessionUser.user.userName=='taofangadmin'  || sessionScope.sessionUser.user.userName=='yangyang' || sessionScope.sessionUser.user.userName=='zy'}">
									<input type="button" id="rejectButton${task.houseId}" value="删除" onclick="showRejectReason(${task.houseId})" />
									</c:if>
								</c:if>
								<c:if test="${task.auditResult== 2 }">
									此视频已删除
								</c:if>
								<c:if test="${task.auditResult== -1 }">
									视频不存在
								</c:if>				
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div style="text-align:center;">
			<input type="button" value="提交审核结果" onclick="submitResult();" />
		</div>
	</div>
</form>
<div id="rejectReasonDiv" style="width:600px;">
	<input type="hidden" id="operationHouseId"/>
	<input type="radio" value="视频中房屋概况不真实或不存在" id="rejectReason1" name="rejectReason" checked="checked"/><label for="rejectReason1">视频中房屋概况不真实或不存在</label>
	<input type="radio" value="非房源视频" id="rejectReason2" name="rejectReason"/><label for="rejectReason2">非房源视频</label>
	<input type="radio" value="视频已过期或不存在" id="rejectReason3" name="rejectReason"/><label for="rejectReason3">视频已过期或不存在</label>
</div>
<script type="text/javascript">
	var cityId = ${requestScope.cityId};
	$(function(){
		getDist("city",0);
		if(cityId > 0) {
			setSelected("city",cityId);
		}
	});
</script>