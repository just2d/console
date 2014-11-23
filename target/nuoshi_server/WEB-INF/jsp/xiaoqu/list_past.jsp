<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
<!--
var auth_status = '${authStatus}';
//-->
</script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/list.js"></script>
<script type="text/javascript">
<!--
var estateId;
var auth_Status;
$(function(){
	$('#confirmDel').dialog({
        show : "slide",
        bgiframe : true,
        autoOpen : false,
        height : 250,
        width : 330,
        title : "删除小区",
        modal : true,
        resizable : false,
        buttons:{"确定" : confirmDelEstate,"取消":closeDialog}
    });
})


function confirmDelEstate(){
	$("#confirmDel").dialog("close");
	var url = "${contextPath}/estate/authAction?estateId="+estateId+"&authStatus="+auth_Status;
	if(auth_Status == "3"){
			url +="&origStatus=${authStatus}";
		}
	$.getJSON(url, null, function(json){
	  	if(json!=null&&json.length>0){
	  		alert("操作成功");
	  		clear("form_estate_id");
	  		$("#estateListForm").submit();
	  	}
	  	
	});
}

function closeDialog(){
	$("#confirmDel").dialog("close");
}

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

function auth(estate_id,authStatus){
	$("#confirmDel").dialog("close");
	$("#confirmDel").dialog("option", "position", "center");
    $('#confirmDel').dialog('open');
    
    estateId = estate_id;
    
    auth_Status = authStatus;
}

function delEstate(resaleCount,rentCount,photocnt,layoutcnt,estateId,auto_Status){
	$.getJSON("${contextPath}/estate/deleteAction?estateId="+estateId+"&authStatus="+auto_Status, null, function(json){
	  	if(json!=null&&json.length>0){
	  		alert("删除成功");
	  		clear("form_estate_id");
	  		$("#estateListForm").submit();
	  	}
	  	
	});
}
</script>

<%@ include file="tab.jsp" %>
<form action="/estate/list" method="post" id="estateListForm" >
<input type="hidden" name="isClicked"  id="isClicked" value="0"/>
<input type="hidden" name="authStatus" value="1"/>
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
	  小区ID:<input type="text" value="${estateId ne 0 ?estateId :''}" name="estateId" />
	<input class="def" type="button" value="搜索" style="width: 60px;" onclick="changePageNoAndSubmit()">
</div> 
<div id="statDiv"></div>
<div class="mag">
	<div class="todo">
		 <c:if test="${estateList.size()>0}">
				 	<table>
					<tr><td colspan="13" align="left"><input type="button" value="删除" class="delBt"/></td></tr>
					</table>
		</c:if>
		<table id="statTable">
			<thead>
				<tr>
					<th><input type="checkbox" name="allchk" id="allchk"/></th>
					<th>小区ID</th>
					<th>小区名称</th>
					<th>所在区域</th>
					<th>板块</th>
					<th>地址</th>
					<th>二手房源数</th>
					<th>出租房源数</th>
					<th>小区图片</th>
					 <th>户型图数量</th>
					<th>物业类型</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${estateList}" var="estate">
					<tr onclick="setName('${estate.estateName}','form_estate_id');" ondblclick="setTargetId('${estate.estateId}')">
						<td><input type="checkbox" name="sel_${estate.estateId}" class = 'sel' value="${estate.estateId}"/></td>
						<td>${estate.estateId}</td>
						<td><a href="http://www.${sysDomain}/xq_${estate.pinYin}/" target="_blank">${estate.estateName}</a></td>
						<td>${estate.cityName}--${estate.distName}</td>
						<td>${estate.blockName}</td>
						<td>${estate.address}</td>
						<td>${estate.resaleCount}</td>
						<td>${estate.rentCount}</td>
						<td>${estate.photocnt}</td>
						<td>${estate.layoutcnt}</td>
						<td>
							<c:if test="${estate.wyType eq '1'}">住宅</c:if>
							<c:if test="${estate.wyType eq '2'}">商铺</c:if>
							<c:if test="${estate.wyType eq '4'}">写字楼</c:if>
							<c:if test="${estate.wyType eq '3'}">别墅</c:if>
							<c:if test="${estate.wyType eq '5'}">其他</c:if>
						</td>
						<td><a href="javascript:void(0)" onclick="auth('${estate.estateId}','3')">删除</a>|<a target="_blank" href="${contextPath}/estate/form?id=${estate.estateId}&action=edit&authStatus=${authStatus}">编辑</a>|<a target="_blank" href="${contextPath}/estate/form?id=${estate.estateId}&action=look&authStatus=${authStatus}">查看</a></td>
					</tr>
				</c:forEach>
				
			</tbody> 
		</table>
		 <c:if test="${estateList.size()>0}">
				 	<table>
					<tr><td colspan="13" align="left"><input type="button" value="删除" class="delBt"/></td></tr>
					</table>
		</c:if>
</div>
<div class="page_and_btn" align="center">
	<jsp:include page="/WEB-INF/snippets/page_xq.jsp" />
</div>
</form>
<div id="confirmDel" class="todo" >亲，确定要删除这个小区吗？</div>