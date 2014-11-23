<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<script type="text/javascript">
<!--
function searchMapping() {
	$('#estateMappingForm').submit();
}

function addEstateMapping(){
	document.location.href="/estate/mapping/add";
}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：小区映射管理 </div> 
<form action="/estate/mapping/list" id="estateMappingForm" method="post">
	<label>淘房小区ID或名字：</label><input type="text" id="ownEstate" name="ownEstate" value="${ownEstate}"/>
	<label>联盟小区ID或名字：</label><input type="text" id="unionEstate" name="unionEstate" value="${unionEstate}"/>
	<label>来源：</label>
	<select name="sourceId">
	<option value="1" <c:if test="${sourceId ==1 }">selected</c:if>>58</option>
	<option value="13" <c:if test="${sourceId ==13 }">selected</c:if>>安居客</option>
	</select>
	<input type="button" value="查询" onClick="searchMapping();"/>
	<input type="button" value="添加映射" onClick="addEstateMapping();" />

<div class="todo">
	<table style="text-align:center">
		<thead>
			<tr>
				<th style="width:10%;">淘房小区ID</th>
				<th style="width:10%;">淘房小区名字</th>
				<th style="width:10%;">区域</th>
				<th style="width:10%;">商圈</th>
				<th style="width:10%;">联盟小区ID</th>
				<th style="width:10%;">联盟小区名字</th>
				<th style="width:10%;">联盟小区来源</th>
				<th style="width:10%;">创建时间</th>
				<th style="width:10%;">最后更新时间</th>
				<th style="width:10%;">最后修改人ID</th>
				<th style="width:10%;">操作</th>
			</tr>
		</thead>
		<tbody align="center" >
			<c:forEach items="${mappings }" var="mapping">
				<c:if test="${mapping != null }">
						 <tr>
						     <td>${mapping.estateId}</td>
						     <td>${mapping.estateName}</td>
						     <td>${func:getName(mapping.estateDistId)}</td>
						    <td>${func:getName(mapping.estateBlockId)}</td>
						     <td>${mapping.uEstateId}</td>
						     <td>${mapping.uEstateName}</td>
						     <td><c:if test="${mapping.sourceId ==1 }">58</c:if><c:if test="${mapping.sourceId ==13 }">安居客</c:if></td>
						     <td>${mapping.createTimeStr}</td>
						     <td>${mapping.lastUpdateTimeStr}</td>
						     <td>${mapping.lastUpdateUserId}</td>
						     <td><a href="/estate/mapping/edit?id=${mapping.id }">编辑</a></td>
						 </tr>
				</c:if>	
			</c:forEach>		 
		</tbody>
	</table>
</div>
</form>