<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link type="text/css" rel="stylesheet" href="/css/manage.css"/>
<c:set var="url" scope="page" value="http://${citydir}.taofang.com"></c:set>
<div style="width: 100%;text-align: left;">当前位置：经纪人管理>>经纪人管理>><b>经纪人房源</b></div>
<form>
<div class="tit">
<span style="font-weight: bold;">${name}</span>(id:${id}) ---
本页共有<span style="font-weight: bold;">${fn:length(houseList)}</span>套房源
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<th>ID</th><th>小区名称</th><th>房源标题</th><th>标签类别</th><th>户型</th><th>价格</th><th>分数</th><th>录入日期</th><th>房源类型</th>
	<th><select id="houseSt" style="border:0px solid #CAD9EA;white-space: nowrap;background-color: #B6E6F9;line-height:30px; font-size:12px; font-family:微软雅黑;font-weight: bold;">
		<option value="0">房源状态</option>
		<option value="1">在线房源</option>
		<option value="5">在线房源(未审核)</option>
		<option value="6">在线房源(通过)</option>
		<option value="2">待发布房源</option>
		<option value="3">过期房源</option>
		<option value="4">违规房源</option>
		<option value="9">未审核经纪人房源</option>
		<option value="7">删除房源</option>
		<option value="8">草稿箱</option>
	</select></th>
	</tr>
	<c:if test="${fn:length(houseList)==0}"><tr><td colspan="8">该用户没有发布房源</td></tr></c:if>
	</thead>
	<tbody>
		<c:forEach items="${houseList}" var="house">
		<tr>
		<td><a href="${url}/${house.houseType}/${house.id}-0.html" target="_blank">${house.id}</a></td>
		<td>${house.name}</td><td>${house.title}</td><td>${house.houseLabelName }</td><td>${house.flattype}</td>
		<c:if test="${house.houseType=='zufang'}"><td>${house.price}元/月</span></c:if>
		<c:if test="${house.houseType=='ershoufang'}"><td>${house.price}/万元</c:if>
		<td>${house.score}</td>
		<td>${house.enterdate}</td>
		<c:if test="${house.houseType=='zufang'}"><td>出租房源</span></c:if>
		<c:if test="${house.houseType=='ershoufang'}"><td>二手房源</c:if>
		
		<td style="color: #F91104">${house.houseStatusLabel}</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
	</div>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	
	<script type="text/javascript">
		$(function(){
			$("#houseSt").val(${type});
			$("#houseSt").bind("change",selChange);
		});
		
		function selChange(){
			var citydir = "${citydir}";
			var id = ${id};
			var type = $("#houseSt").val();
			window.location.href = "/agentManage/house/" + citydir + "/" + id + "/" + type;
		}
	</script>