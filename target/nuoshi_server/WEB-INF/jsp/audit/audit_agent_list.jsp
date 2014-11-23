<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>

<script type="text/javascript">

$(function() {
	$("#addAgentListDiv").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 350,width : 400,title : "添加图片设置名单",modal : true,buttons : {"确定" : addAgentList,"取消":close}});
});

function addAgentList(){
	var auditRule = $("#auditRule").val();
	var agentIds = $("#agentIds").val();
	if(auditRule<=0){
		alert("请选择要添加的名单类型");
		return ;
	}
	if(agentIds==""){
		alert("请填写经纪人ID");
		return ;
	}
	
	$.ajax({
		type : "post",
		async : false,
		data:$("#addAgentListForm").serialize(),
		url : "/audit/photo/namelist/add/"+auditRule,
		dataType : "json",
		success : function(data) {
			if(data.error){
				alert(data.error);
				return ;
			}
			if(data.success){
				alert("添加成功");
				$("#myform").submit();
			}
			
		}
	});
	
	
}
function deleteAgentList(agentId){
	
	$.ajax({
		type : "post",
		async : false,
		url : "/audit/photo/namelist/add/0?agentIds="+agentId,
		dataType : "json",
		success : function(data) {
			if(data.error){
				alert(data.error);
				return ;
			}
			if(data.success){
				alert("删除成功");
				$("#myform").submit();
			}
			
		}
	});
	
	
}

function add(){
	$("*").stop();
	$("#addAgentListDiv").dialog("close");
	$("#addAgentListDiv").dialog("option", "position", "center");
	$("#addAgentListDiv").dialog("open");
	
}
function close(){
	$("*").stop();
	$("#addAgentListDiv").dialog("close");
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
	
	function doSearch(){
		var cityId = $("#city").val();
		var type = $("#type").val();
		if(cityId<=0){
			cityId = 0 ;
		}
		$("#myform").attr("action","/audit/photo/manelist/"+cityId+"/"+type);
		$("#myform").submit();
	}
</script>

<form id="myform" action="/audit/photo/manelist/${cityId}/${type}" method="post">
	<input type="hidden" id="totalResult" name="totalResult" />
	<div style="text-align:left;">
		
		城市：
		<select id="city" name="cityId" style="width:100px; position:relative; z-index:9" >
		</select>
		<select id="type" style="width:120px;">
			<option value="2" <c:if test="${type == 2}">selected="selected"</c:if> >免审名单</option>
			<option value="1" <c:if test="${type == 1}">selected="selected"</c:if> >严审名单</option>
		</select>
		ID&nbsp;<input type="text" id="agentId" name="agentId" value="${agentId }" />
		<input type="button" value="查询" onclick="doSearch();"/>
		<input type="button" value="添加" onclick="add();"/>
	</div>
	
	<div class="todo">
		<table style="width:100%;">
			<thead>
				<tr>
					<th>城市</th>
					<th>经纪人ID</th>
					<th>姓名</th>
					<th>手机号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody align="center" >
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach items="${list}" var="t">
							<tr>
								 <td>${func:getName(t.cityId)}</td>
								<td>${t.agentId}</td>
								<td>${t.name}</td>
								<td>${t.mobile}</td>
								<td>
									<input type="button" id="rejectButton${task.agentId}" value="删除" onclick="deleteAgentList(${t.agentId})" />
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
		<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</div>
</form>
 
 
 
 <div id="addAgentListDiv" style="width:600px;display: none">
 <form id="addAgentListForm">
 <table>
  <tr>
   <td> 类型:
   
   </td>
   <td align="left">
   	<select id="auditRule">
 		<option value="0">名单类型</option>
 		<option value="2">免审名单</option>
 		<option value="1">严审名单</option>
 	</select>
   </td>
  </tr>
   <tr>
   <td valign="top">
   经纪人ID:
   </td>
   <td>
   <textarea id="agentIds" name="agentIds" cols="35" rows="12"  ></textarea>
   </td>
  </tr>
 </table>
 </form>
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