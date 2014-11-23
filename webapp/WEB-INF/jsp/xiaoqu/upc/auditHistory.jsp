<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
<!--
.main_estate{
	background-color:yellow;
}
-->
</style>
<script type="text/javascript">
<!--
contextPath = '${contextPath}';
var cityId = '${cityId}';
//-->
</script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/list.js"></script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/del_dialog.js"></script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/auth.js"></script>
<script type="text/javascript">


function setCityInfo(ch){
	if(ch != ""){
		$.getJSON("${contextPath}/local/cityList/"+ch, null, function(json){
		  	var options="<option value=''>-请选择-</option>";
		  	if(json!=null&&json.length>0){
		  		for(var i=0;i<json.length;i++){
			  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
			  	}
		  	}
		  	$("#cityLocale").remove("option");
		  	$("#cityLocale").html(options);
		});
	}
}

function setDistInfo(cityId){
	if(cityId != ""){
		$.getJSON("${contextPath}/local/distList/"+cityId, null, function(json){
	  	var options="<option value=''>-请选择-</option>";
	  	if(json!=null&&json.length>0){
	  		for(var i=0;i<json.length;i++){
		  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
		  	}
	  	}
	  	$("#distLocale").remove("option");
	  	$("#distLocale").html(options);
	});
	}
}

function setBlockInfo(distId){
	if(distId != ""){
		$.getJSON("${contextPath}/local/distList/"+distId, null, function(json){
	  	var options="<option value=''>-请选择-</option>";
	  	if(json!=null&&json.length>0){
	  		for(var i=0;i<json.length;i++){
		  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
		  	}
	  	}
	  	$("#blockLocale").remove("option");
	  	$("#blockLocale").html(options);
	});
	}
}

</script>
 <div id="tabs" class="tabs">  
     <ul> 
     	
        <li <c:if test="${actionType == 1 }"> class="tabs_active" </c:if> ><a <c:if test="${actionType == 2 }"> style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" </c:if>  href="/estateupcaudithistory/list?actionType=1&cityId=${condition.cityId }" >小区数据审核记录</a></li>  
     	<li <c:if test="${actionType == 2 }"> class="tabs_active" </c:if>><a <c:if test="${actionType == 1 }"> style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" </c:if> href="/estateupcaudithistory/list?actionType=2&cityId=${condition.cityId }"  >小区相册审核记录</a></li>  
     </ul> 

 </div> 
<div id="loading"></div>
<form action="/estateupcaudithistory/list?actionType=${actionType }" method="post" id="estateListForm" >
<div class="mag" style="border:1px solid #CAD9EA">
	 首字母:<select name="ch" id="ch" onchange="setCityInfo(this.value)">
	  			<option value="">-请选择-</option>
	  			<option value="a">A</option>
	  			<option value="b">B</option>
	  			<option value="c">C</option>
	  			<option value="d">D</option>
	  			<option value="e">E<J/option>
	  			<option value="f">F</option>
	  			<option value="g">G</option>
	  			<option value="h">H</option>
	  			<option value="i">I</option>
	  			<option value="j">J</option>
	  			<option value="k">K</option>
	  			<option value="l">L</option>
	  			<option value="m">M</option>
	  			<option value="n">N</option>
	  			<option value="o">O</option>
	  			<option value="p">P</option>
	  			<option value="q">Q</option>
	  			<option value="r">R</option>
	  			<option value="s">S</option>
	  			<option value="t">T</option>
	  			<option value="u">U</option>
	  			<option value="v">V</option>
	  			<option value="w">W</option>
	  			<option value="x">X</option>
	  			<option value="y">Y</option>
	  			<option value="z">Z</option>
	     </select>
	  城市:<select name="cityId" id="cityLocale" onchange="setDistInfo(this.value)">
	  		<option value="">-请选择-</option>
			<c:if test="${cityName ne null}">
	 			<option value="${condition.cityId}" selected="selected">${cityName}</option>
	 		</c:if>
	     </select>
	 区域:<select name="distId" id="distLocale" onchange="setBlockInfo(this.value)">
	 		<option value="">-请选择-</option>
			<c:forEach items="${distList}" var="dist">
	 			<option value="${dist.id}" ${dist.id eq condition.distId ? 'selected=selected' :''}>${dist.name}</option>
	 		</c:forEach>
	 	</select>
	 板块:<select name="blockId" id="blockLocale">
	 		<option value="">-请选择-</option>
	 		<c:forEach items="${blockList}" var="block">
	 			<option value="${block.id}" ${block.id eq condition.blockId ? 'selected=selected' :''}>${block.name}</option>
	 		</c:forEach>
	 	</select>
	 小区名:<input type="text" name="estateName" value="${condition.estateName}"  id="form_estate_id"/>
	 <div style="display: none" id="origEstateName">${condition.estateName}</div>
	  小区ID:<input type="text" value="${estateId ne 0 ? estateId :''}" name="estateId" />
	<input class="def" type="button" value="搜索" style="width: 60px;" onclick="changePageNoAndSubmit()">
</div> 
<div id="statDiv"></div>
<div class="mag">
	<div class="todo">
		<table id="statTable">
			<thead>
				<tr>
					<th>小区ID</th>
					<th>小区名称</th>
					<th>区域</th>
					<th>商圈</th>
					<th><c:if test="${actionType == 1 }">待审核信息数</c:if><c:if test="${actionType == 2 }">待审核照片数</c:if></th>
					<th>审核通过<c:if test="${actionType == 2 }">照片</c:if>数量</th>
					<th>经纪人</th>
					<th>审核人</th>
					<th>审核时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${estateList}" var="estate">
					<tr>
						<td>${estate.estateId}</td>
						<td>${estate.estateName}</td>
						<td>${estate.distName}</td>
						<td>${estate.blockName}</td>
						<td>${estate.checkCount}</td>
						<td>${estate.acceptCount}</td>
						<td>${estate.userName}</td>
						<td>${estate.auditorName}</td>
						<td>${estate.auditTimeStr}</td>
					</tr>
				</c:forEach>
			</tbody> 
		</table>
</div>
</div>
<div class="page_and_btn" >
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>