<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<div style="width: 100%;text-align: left;">当前位置：个人用户管理>><b>个人用户管理</b></div> 
<form id="searchForm" action="/tfuserManage/list" method="get">
<div class="mag" style="border:1px solid #CAD9EA">
<input type="hidden" value="0" id="currentOperationAgentId" />
<select class="cs" name="type" id="type" style="width: 170px;">
		<option value="请选择">请选择搜索类型</option>
		<option value="id">ID</option>
		<option value="name">用户名</option>
		<option value="email">邮箱</option>
		<option value="mobile">手机号码</option>
	</select>
	<input class="def" type="text" id="searchtxt" name="searchtxt" value="请输入搜索内容" style="width: 163px;">
	<select class="cs" id="city" name="city" style="width: 170px;"><option value='请选择'>请选择城市</option></select>
	<input class="def" type="submit" value="搜索" style="width: 60px;">
	<input class="def" type="button" id="resetBtn" value="重置" style="width: 60px;">
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(tuserList)==0}"><th>个人用户列表为空</th></c:if>
	<c:if test="${fn:length(tuserList)!=0}"> 
	<th><input type="checkbox" id="selectAll"/></th><th>ID</th><th>姓名</th><th>用户名</th><th>邮箱</th><th>手机号</th><th>城市</th><th>管理员操作</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${tuserList}" var="tuser">
		<tr>
			<td><input type="checkbox" name="msgChk" value="${tuser.id}"/></td>
			<td>${tuser.id}</td>
			<td>${tuser.name}</td>
			<td>${tuser.userName}</td>
			<td>${tuser.email}</td>
			<td>${tuser.mobile}</td>
			<td>${func:getName(tuser.cityId)}</td>
			<td>
				<a href="javascript:confirmDelete('${tuser.id}')">删除</a>
			</td> 
		</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	</div>
	<c:if test="${fn:length(tuserList)!=0}"> 
	<div class="mag">
		<input class="def" type="button" id="delBtn" value="批量删除"  style="width: 80px;"/>
	</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	
	
	
	
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
$(function() {
	// 初始化搜索条件，将搜索条件反填
	var searchType = "${agentSearch.type}";
	var searchTxt = "${agentSearch.searchtxt}";
	var searchCity = "${agentSearch.city}";
	$("#type").val(searchType);
	getDist("city", "/agentManage/ajax/zone/0", searchCity);
	if (searchTxt != "请输入搜索内容" && searchTxt != "") {$("input[name=searchtxt]").val(searchTxt);}
	// 为控件绑定事件
	$("#delBtn").bind("click", showDelDiv);
	$("#selectAll").bind("click", selAll);
	$("#resetBtn").bind("click", resetSearch);
	$("input[name=searchtxt]").bind("blur", txtBlur);
	$("input[name=searchtxt]").bind("focus", txtFocus);
	$("#no").bind("click", function() {$.unblockUI();return false;});
	$("#delBtn").bind("click",deleteMore);
	// 初始化弹出窗口
	$("#divDel").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 130,width : 330,title : "删除个人用户",modal : true,buttons : {"删除" : delAgn,"取消":closeDel}});
});


//删除多个关键词
function deleteMore(){
	var $ids = $("input[name=msgChk]:checked");
	if ($ids.size() == 0) {
		blockAlert("请选择要删除的个人用户", 2000);
	} else {
		var ids = "";
		$("input[name=msgChk]:checked").each(function() {
			ids += $(this).val() + ",";
		});
		confirmDelete(ids);
	}
}



// ajax删除经纪人
function confirmDelete(id) {
	var msg;
	$.blockUI({message : $("#question"),css : {width : "275px"}});
	$("#yes").click(function() {
		$.ajax({
			type : "get",
			async : false,
			url : "/tfuserManage/delete/" + id,
			dataType : "json",
			cache : false,
			success : function(data) {
				msg = data.message;
			},
			error : function(data) {
				blockAlert("调用后台服务错误,请稍后重试", 2000);
			}
		});
		$.unblockUI();
		blockAlert(msg, 2000);
		$("#searchForm").submit();
		
	});
}

// ajax获取城市、区域、商圈
function getDist(selectId, _url, selectedValue) {
	_url = encodeURI(_url);
	_url = encodeURI(_url);
	var selectElem = "#" + selectId;
	var def;
	if (selectId == "city" || selectId == "editcity") {
		def = "请选择城市";
	} else if (selectId == "dist" || selectId == "editdist") {
		def = "请选择区域";
	} else {
		def = "请选择商圈";
	}
	$.ajax({
		type : "GET",
		async : false,
		url : _url,
		dataType : "json",
		success : function(data) {
			$(selectElem).empty();
			var option = "<option value='0'>" + def + "</option>";
			$(option).appendTo(selectElem);
			var list = data.distList;
			if (list != null && list.length > 0) {
				for (i in list) {
					var local = list[i];
					var option;
					if (local.localid == selectedValue) {
						option = "<option value='" + local.localid
								+ "' selected=\"selected\">" + local.localname
								+ "</option>";
					} else {
						option = "<option value='" + local.localid + "'>"
								+ local.localname + "</option>";
					}
					$(option).appendTo(selectElem);
				}
			}
		}
	});
}


// 为搜索输入框绑定焦点事件
function txtFocus() {
	$("input[name=searchtxt]").val("");
}

// 为搜索输入框绑定失去焦点事件
function txtBlur() {
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	var val = $("input[name=searchtxt]").val().Trim();
	if (val == "") {
		$("input[name=searchtxt]").val("请输入搜索内容");
		$("input[name=searchtxt]").css("color", "gray");
	}
}

// 全选信息
function selAll() {
	if ($("#selectAll").attr("checked")) {
		$("input[name=msgChk]").attr("checked", true);
	} else {
		$("input[name=msgChk]").attr("checked", false);
	}
}

// 重置搜索条件
function resetSearch() {
	$("#type option:first").attr("selected", "selected");
	$("#city option:first").attr("selected", "selected");
	$("#searchtxt").val("请输入搜索内容").css("color", "gray");
}

//关闭编辑弹出层
function closeEdit(){
	$("*").stop();
	$("#divEdit").dialog("close");
}
//关闭编辑弹出层
function closeShow(){
	$("*").stop();
	$("#showHistory").dialog("close");
}

// 设置jquery blockUI弹出窗口
function blockAlert(msg, sec) {
	$.blockUI({
		css : {border : "none",padding : "15px",backgroundColor : "#000",'-webkit-border-radius' : "10px",'-moz-border-radius' : "10px",opacity : .6,color : "#fff"},
		message : msg
	});
	setTimeout($.unblockUI, sec);
}

//显示删除经纪人窗口
function showDelDiv(){
	$("*").stop();
	$("#divDel").dialog("close");
	$("#divDel").dialog("option", "position", "center");
	$("#divDel").dialog("open");
}

//删除经纪人
function delAgn(){
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var id = $("#delid").val().Trim();
	if(id == ""){
		blockAlert("请输入经纪人ID",2000);
	}else{
		confirmDelete(id);
	}
}

//关闭删除窗口
function closeDel(){
	$("#delid").val("");
	$("*").stop();
	$("#divDel").dialog("close");
}
</script>