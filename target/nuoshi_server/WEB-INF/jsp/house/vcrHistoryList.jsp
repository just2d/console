<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function doSearch() {
		var type = $("#houseType").val();
		var result = $("#auditResult").val();
		var cityId = $("#city").val();
		$("#vcrForm").attr("action", "/audit/vcr/history/" + type + "/" + cityId + "/0/" + result);
		$("#vcrForm").submit();
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

<form id="vcrForm" action="/audit/vcr/history/${houseType}/${userId}/${cityId}/${result}" method="post">
	<input type="hidden" id="totalResult" name="totalResult" />
	<div style="text-align:left;">
		<label>城市：</label>
		<select id="city" name="cityId" style="width:100px; position:relative; z-index:9"></select>
		<label>房源类型：</label>
		<select id="houseType" style="width:120px;">
			<option value="0" <c:if test="${houseType == 0}">selected="selected"</c:if> >不限</option>
			<option value="1" <c:if test="${houseType == 1}">selected="selected"</c:if> >二手房</option>
			<option value="2" <c:if test="${houseType == 2}">selected="selected"</c:if> >租房</option>
		</select>
		<label>审核结果：</label>
		<select id="auditResult" style="width:120px;">
			<option value="0" <c:if test="${result == 0}">selected="selected"</c:if> >不限</option>
			<option value="1" <c:if test="${result == 1}">selected="selected"</c:if> >通过</option>
			<option value="2" <c:if test="${result == 2}">selected="selected"</c:if> >删除</option>
		</select>
		<input type="button" value="查询" onclick="doSearch();"/>
	</div>
	
	<div class="todo">
		<table style="width:100%;">
			<thead>
				<tr>
					<th>经纪人ID</th>
					<th>房源编号</th>
					<th>投诉次数</th>
					<th>审核人</th>
					<th>上传时间</th>
					<th>审核时间</th>
					<th>删除原因</th>
					<th>审核结果</th>
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
								<td>${task.auditorName}</td>
								<td>${task.vcrTime}</td>
								<td>${task.auditTime}</td>
								<td>${task.reason}</td>
								<td>
									<c:choose>
										<c:when test="${task.auditResult == 1}">
											通过
										</c:when>
										<c:otherwise>
											删除
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="8">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="page_and_btn" style="text-align:center;">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</div>
	
</form>
<script type="text/javascript">
	var cityId = ${requestScope.cityId};
	$(function(){
		getDist("city",0);
		if(cityId > 0) {
			setSelected("city",cityId);
		}
	});
</script>