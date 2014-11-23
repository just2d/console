<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="width: 100%;text-align: left;">当前位置：信息质量控制>><b>内链关键词管理</b></div>
<form id="linkWordForm" action="/control/linkword/list" method="get" enctype="multipart/form-data">
<div class="mag" style="border:1px solid #CAD9EA">
	<label class="def">关键字:</label>
	<input class="def" type="text" id="keyword" name="keyword" value="请输入关键字" style="width: 163px;"/>
	<input class="def" name="startDate" id="startDate" class="dateCss" style="width:150px;" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="起始日期">
	- <input class="def" name="endDate" id="endDate" class="dateCss" style="width:150px;" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="终止日期">
	<input class="def" type="submit" value="搜索" style="width: 60px;"/>
	<input class="def" type="reset" value="重置" style="width: 60px;"/>
	<input class="def" id="addBtn" type="button" value="添加内链关键词" style="width: 110px;"/>
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(linkWords)==0}"><th>关键词列表为空</th></c:if>
	<c:if test="${fn:length(linkWords)!=0}"> 
	<th><input type="checkbox" id="selectAll" name="selectAll"/></th><th>ID</th><th>关键词</th><th>URL</th><th>添加时间</th><th colspan="2">编辑</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${linkWords}" var="linkWord">
		<tr>
		<td><input type="checkbox" name="idChk" value="${linkWord.id}"/></td>
		<td>${linkWord.id}</td> 
		<td>${linkWord.keyword}</td>
		<td>${linkWord.url}</td>
		<td>${linkWord.createTimeStr}</td>
		<td><a href="javascript:modify('${linkWord.id}','${linkWord.keyword}','${linkWord.url}')">修改</a></td>
		<td><a href="javascript:deleteKeyword('${linkWord.id}')">删除</a></td>
		</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	</div>
	<c:if test="${fn:length(linkWords)!=0}"> 
		<div class="mag" style="float:left;">
			<input class="def" type="button" id="delBtn" value="批量删除"  style="width: 80px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="file" name="file" />
			<input type="button" value="批量导入" onclick="addBatch()"/>
		</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	<div id="divAdd">
	<p>
		<label class="lbl">关键词:</label><input type="text" name="keywordAdd" id="keywordAdd" style="margin-left: -175px;width:200px;"/><br />
	</p>
	<p>
		<label class="lbl">关键词URL:</label><input type="text" name="urlAdd" id="urlAdd" style="margin-left: -196px;width:200px;"/><br />
	</p>
	</div>
	<div id="divEdit">
		<input type="hidden" name="idEdit" id="idEdit"/>
	<p>
	<label class="lbl">关键词:</label><input type="text" name="keywordEdit" id="keywordEdit" style="margin-left: -175px;width:200px;"/><br />
	</p>
	<p>
	<label class="lbl">关键词URL:</label><input type="text" name="urlEdit" id="urlEdit" style="margin-left: -196px;width:200px;"/><br />
	</p>
	</div>
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
	<script type="text/javascript" src="/js/common/jsdate.js"></script>
	<script type="text/javascript">
$(function() {
	//反添查询条件
	var keyword = "${searchView.keyword}";
	var startDate = "${searchView.startDate}";
	var endDate = "${searchView.endDate}";
	if(keyword!=null&&keyword!=""){$("#keyword").val(keyword);}
	if(startDate!=null&&startDate!=""){$("#startDate").val(startDate);}
	if(endDate!=null&&endDate!=""){$("#endDate").val(endDate);}
	//为控件绑定事件
	$("#keyword").bind("blur", txtBlur);
	$("#keyword").bind("focus", txtFocus);
	$("#addBtn").bind("click",add);
	$("#selectAll").bind("click",selAll);
	$("#delBtn").bind("click",deleteMore);
	$("#no").bind("click", function() {$.unblockUI();return false;});
	//初始化弹出窗口
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 290,width : 500,title : "添加关键词",modal : true,resizable : false,buttons : {"添加" : sensitiveWordAdd,"取消" : clearAddDiv}});
	$("#divEdit").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 310,width : 500,title : "修改关键词",modal : true,resizable : false,buttons : {"修改" : sensitiveWordEdit,"取消" : clearEditDiv}});
	//checkbox状态重置，未被选中
	clearCheckBox();
});

// 为搜索输入框绑定焦点事件
function txtFocus() {
	if($('#keyword').val() == "请输入关键字") {
		$("#keyword").val("");
	}
}

// 为搜索输入框绑定失去焦点事件
function txtBlur() {
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	var val = $("#keyword").val().Trim();
	if (val == "") {
		$("#keyword").val("请输入关键字");
		$("#keyword").css("color", "gray");
	}
}

// 全选信息
function selAll() {
	if ($("#selectAll").attr("checked")) {
		$("input[name=idChk]").attr("checked", true);
	} else {
		$("input[name=idChk]").attr("checked", false);
	}
}

// 显示增加关键词窗口
function add() {
	$("*").stop();
	$("#divAdd").dialog("close");
	$("#divAdd").dialog("option", "position", "center");
	$("#divAdd").dialog("open");
}

// ajax增加关键词，显示增加操作结果，刷新当前页面
function sensitiveWordAdd() {
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var keyword = $("#keywordAdd").val().Trim();
	var url = $("#urlAdd").val().Trim();
	if (keyword == "") {
		blockAlert("请输入关键词", 2000);
	} else if(url == "") {
		blockAlert("请输入关键词URL", 2000);
	} else{
		$.ajax({
			type : "GET",
			async : false,
			url : "/control/linkword/add",
			dataType : "json",
			data : {"keyword" : keyword,"url" : url},
			success : function(data) {
				blockAlert(data.message, 2000);
			}
		});
		clearAddDiv();
		window.location.reload();
	}
}

//清空添加弹出窗口
function clearAddDiv(){
	$("#keywordAdd").val("");
	$("#urlAdd").val("");
	$("#divAdd").dialog("close");;
}

//提示弹出层，msg要显示的消息，sec显示停留的时间
function blockAlert(msg, sec) {
	$.blockUI({
		css : {border : "none",padding : "15px",backgroundColor : "#000",'-webkit-border-radius' : "10px",'-moz-border-radius' : "10px",opacity : .6,color : "#fff"},
		message : msg
	});
	setTimeout($.unblockUI, sec);
}

//弹出修改关键词窗口
function modify(id,keyword, url){	
	$("#idEdit").val(id);
	$("#keywordEdit").val(keyword);
	$("#urlEdit").val(url);
	$("*").stop();
	$("#divEdit").dialog("close");
	$("#divEdit").dialog("option", "position", "center");
	$("#divEdit").dialog("open");
}

//ajax修改关键词，显示修改结果，刷新当前页面
function sensitiveWordEdit(){
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var id = $("#idEdit").val();
	var keyword = $("#keywordEdit").val();
	var url = $("#urlEdit").val();
	if (keyword == "") {
		blockAlert("请输入关键词", 2000);
	} else if(url == "") {
		blockAlert("请输入关键词URL", 2000);
	} else{
		$.ajax({
			type : "GET",
			async : false,
			url : "/control/linkword/edit",
			dataType : "json",
			data : {"id" : id,"keyword" : keyword, "url": url},
			success : function(data) {
				blockAlert(data.message, 2000);
			}
		});
		clearEditDiv();
		window.location.reload();
	}
}

//清空修改窗口
function clearEditDiv(){
	$("#divEdit").dialog("close");;
}

//删除关键词，显示操作结果，刷新当前页面
function deleteKeyword(id){
	$.blockUI({message : $("#question"),css : {width : "275px"}});
	$("#yes").click(function() {
		$.ajax({
			type : "get",
			async : false,
			url : "/control/linkword/delete/" + id,
			dataType : "json",
			cache : false,
			success : function(data) {
				blockAlert(data.message, 2000);
			}
		});
		$.unblockUI();
		clearCheckBox();
		window.location.reload();
	});
}

//清空checkbox选中状态，全部置为未被选中状态
function clearCheckBox(){
	$("input[name=selectAll]").attr("checked",false);
	$("input[name=idChk]").attr("checked",false);
}

//删除多个关键词
function deleteMore(){
	var $ids = $("input[name=idChk]:checked");
	if ($ids.size() == 0) {
		blockAlert("请选择要删除的关键词", 2000);
	} else {
		var ids = "";
		$("input[name=idChk]:checked").each(function() {
			ids += $(this).val() + ",";
		});
		deleteKeyword(ids);
	}
}

function addBatch() {
	$("#linkWordForm").attr("action", "/control/linkword/addbatch");
	$("#linkWordForm").attr("method", "post");
	$("#linkWordForm").submit();
}
</script>