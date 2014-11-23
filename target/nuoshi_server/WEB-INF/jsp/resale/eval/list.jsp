<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<div style="width: 100%;text-align: left;">当前位置：二手房星级评价管理</div> 

<form action="/resale/eval/list" method="post" id="resaleForm" style="margin-top:0px;margin-top:0px;">
	<div style="text-align:left;">
		<label>城市：</label><select id="city" name="cityId" style="width:100px;" onchange="changeCity(this.value);"></select>&nbsp;&nbsp;&nbsp;&nbsp;
		<label>房源来源：</label>
		<select id="sourceId" name="sourceId" style="width:150px;">
			<option value="0" <c:if test="${sourceId == 0}"> selected="selected" </c:if> >淘房房源</option>
			<option value="1" <c:if test="${sourceId == 1}"> selected="selected" </c:if> >58房源</option>
			<option value="7" <c:if test="${sourceId == 7}"> selected="selected" </c:if> >信义</option>
			<option value="13" <c:if test="${sourceId == 13}"> selected="selected" </c:if> >安居客</option>
		</select>&nbsp;&nbsp;&nbsp;&nbsp;
		<label>筛选条件：</label>
		<select id="queryType" name="queryType">
			<option value="-1" <c:if test="${queryType == -1}"> selected="selected" </c:if> >筛选条件-全部</option>
			<option value="0" <c:if test="${queryType == 0}"> selected="selected" </c:if> >房源ID</option>
			<option value="1" <c:if test="${queryType == 1}"> selected="selected" </c:if> >经纪人姓名</option>
			<option value="2" <c:if test="${queryType == 2}"> selected="selected" </c:if> >经纪人手机</option>
			<option value="3" <c:if test="${queryType == 3}"> selected="selected" </c:if> >经纪人ID</option>
		</select>
		<input type="text" name="content" value="${requestScope.content}" />
		
		<label>有无评价内容：</label>
		<select id="evalConType" name="evalConType">
			<option value="0" <c:if test="${evalConType == 0}"> selected="selected" </c:if> >全部</option>
			<option value="1" <c:if test="${evalConType == 1}"> selected="selected" </c:if> >有</option>
			<option value="2" <c:if test="${evalConType == 2}"> selected="selected" </c:if> >无</option>
		</select> 
		<input type="submit" value="搜索" />
	</div>
	<div class="todo">
		<table id="resaleTable" class="c" style="text-align:center">
			<thead>
				<tr>
					<th style="width:10%;">房源ID</th>
					<th style="widht:10%">所在城市</th>
					<th style="width:10%;">经纪人ID</th>
					<th style="width:10%;">姓名</th>
					<th style="width:10%;">手机</th>
					<th style="width:10%;">评价人数</th>
					<th style="width:10%;">评价星级</th>
					<th style="width:30%;">操作</th>
				</tr>
			</thead>
			<tbody align="center" >
			<c:choose>
				<c:when test="${not empty resales}">
					<c:forEach items="${resales}" var="resale">
						<tr>
							<td><a target="_blank" href="http://${func:getCode(resale.cityid)}.taofang.com/ershoufang/${resale.id}-${resale.sourceId}.html">${resale.id}</a></td>
							<td>${resale.cityName}</td>
							<td>${resale.authorid}</td>
							<td>${resale.author}</td>
							<td>${resale.authorPhone}</td>
							<td>${resale.evaluationCount}</td>
							<td>${resale.evaluationPoint}</td>
							<td>
								<c:if test="${resale.sourceId==0 && resale.falseHouseReport==2 && resale.appealExpiredTime != null }">
								<a href="javascript:realHouse(${resale.id },${resale.sourceId },1);">真实房源</a>-
								<a href="javascript:realHouse(${resale.id },${resale.sourceId },2);">虚假房源</a>-
								</c:if>
								<a href="/resale/eval/evalList/${resale.id}/${resale.sourceId}" target="_blank">删除部分评价</a>-
								<span id="span${resale.id}">
									<c:choose>
										<c:when test="${resale.evaluationVisible == 0}">
											<a href="javascript:changeVisibleStatus('${resale.id}','${sourceId}','1');">开启评价</a>
										</c:when>
										<c:otherwise>
											<a href="javascript:changeVisibleStatus('${resale.id}','${sourceId}','0');">关闭评价</a>
										</c:otherwise>
									</c:choose>	
								</span>
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
	</div>
	<jsp:include page="/WEB-INF/snippets/page.jsp" />
	
</form>

<script type="text/javascript">
	//ajax获取城市、区域 
	function getDist(selectId) {
		var selectElem = "#" + selectId;
		$.ajax({
			type : "GET",
			async : false,
			url : "/agentManage/ajax/zone/0",
			dataType : "json",
			success : function(data) {
				$(selectElem).empty();
				var list = data.distList;
				if (list != null && list.length > 0) {
					var defaultOption = "<option value='0'>请选择城市</option>";
					$(defaultOption).appendTo(selectElem);
					for (i in list) {
						var local = list[i];
						var option = "<option value='" + local.localid + "'>" + local.dirName + local.localname + "</option>";
						$(option).appendTo(selectElem);
					}
				}
			}
		});
	}
	
	function changeVisibleStatus(houseId, sourceId, status) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/resale/eval/changeVisible/" + houseId + "/" + sourceId +  "/" + status,
			dataType : "json",
			success : function(data) {
				if(data.result) {
					if(status == 1) {
						$("#span" + houseId).html("<a href='javascript:changeVisibleStatus("+houseId+","+sourceId+",0);'>关闭评价</a>");
					} else {
						$("#span" + houseId).html("<a href='javascript:changeVisibleStatus("+houseId+","+sourceId+",1);'>开启评价</a>");
					}
					alert("操作成功");
				} else {
					alert("操作失败");
				}
			}
		});
	}
	
	function realHouse(houseId, sourceId, status) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/resale/eval/realHouse/" + houseId + "/" + sourceId +  "/" + status,
			dataType : "json",
			success : function(data) {
				if(data.result) {
					alert("操作成功");
					$("#resaleForm").submit();
				} else {
					alert("操作失败");
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
	
	var cityId = ${requestScope.cityId};
	$(function(){
		getDist("city");
		if(cityId >= 0) {
			setSelected("city",cityId);
		}
	});
</script>