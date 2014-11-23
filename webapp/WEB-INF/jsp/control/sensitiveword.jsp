<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="width: 100%;text-align: left;">当前位置：信息质量控制>><b>敏感词管理</b></div>
<form id="blacklistForm" action="/control/sensitiveWord/search" method="get">
<div class="mag" style="border:1px solid #CAD9EA">
<select class="cs" name="type" id="type" style="width: 170px;">
		<option value="-1">所属类别</option>
		<option value="0">全局关键词</option>
		<option value="1">房源信息</option>
		<option value="2">评论</option>
		<option value="3">小区内容</option>
	</select>
	<select class="cs" id="sensitiveWordType" name="sensitiveWordType" style="width: 170px;">
		<option value="-1">关键字类型</option>
		<option value="0">敏感词</option>
		<option value="1">非法词</option>
	</select>
	<select class="cs" id="userType" name="userType" style="width: 170px;">
		<option value="-1" <c:if test="${search.userType == -1}"> selected="selected" </c:if> >用户类型</option>
		<option value="0" <c:if test="${search.userType == 0}"> selected="selected" </c:if> >所有用户</option>
		<option value="1" <c:if test="${search.userType == 1}"> selected="selected" </c:if> >普通用户</option>
		<option value="2" <c:if test="${search.userType == 2}"> selected="selected" </c:if> >经纪人</option>
	</select>
	<label class="def">关键字:</label>
	<input class="def" type="text" id="content" name="content" value="请输入关键字" style="width: 163px;"/>
	<input class="def" type="submit" value="搜索" style="width: 60px;"/>
	<input class="def" type="reset" value="重置" style="width: 60px;"/>
	<input class="def" id="addBtn" type="button" value="添加关键词" style="width: 110px;"/>
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(sensitiveWordList)==0}"><th>关键词列表为空</th></c:if>
	<c:if test="${fn:length(sensitiveWordList)!=0}"> 
	<th><input type="checkbox" id="selectAll" name="selectAll"/></th><th>ID</th><th>所属类别</th><th>用户类型</th><th>关键字类型</th><th>非法类型</th><th>内容</th><th>管理员</th>
	<th>添加时间</th><th colspan="2">编辑</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${sensitiveWordList}" var="sensitiveWord">
		<tr>
		<td><input type="checkbox" name="idChk" value="${sensitiveWord.id}"/></td>
		<td>${sensitiveWord.id}</td> 
		<c:if test="${sensitiveWord.type==0}"><td>全局关键字</td></c:if>
		<c:if test="${sensitiveWord.type==1}"><td>房源信息</td></c:if>
		<c:if test="${sensitiveWord.type==2}"><td>评论</td></c:if>
		<c:if test="${sensitiveWord.type==3}"><td>小区内容</td></c:if>
		<c:choose>
			<c:when test="${sensitiveWord.userType == 0}">
				<td>所有用户</td>
			</c:when>
			<c:when test="${sensitiveWord.userType == 1}">
				<td>普通用户</td>
			</c:when>
			<c:otherwise>
				<td>经纪人</td>
			</c:otherwise>
		</c:choose>
		<c:if test="${sensitiveWord.sensitiveWordType==0}"><td>敏感词</td></c:if>
		<c:if test="${sensitiveWord.sensitiveWordType==1}"><td>非法词</td></c:if>
		<c:if test="${sensitiveWord.illegalType==0}"><td>涉政</td></c:if>
		<c:if test="${sensitiveWord.illegalType==1}"><td>黄赌毒</td></c:if>
		<c:if test="${sensitiveWord.illegalType==2}"><td>灌水</td></c:if>
		<c:if test="${sensitiveWord.illegalType==3}"><td>其他</td></c:if>
		<td>${sensitiveWord.content}</td><td>${sensitiveWord.madeby}</td><td>${sensitiveWord.entryDate}</td>
		<td><a href="javascript:modify('${sensitiveWord.id}','${sensitiveWord.type}','${sensitiveWord.userType}','${sensitiveWord.content}','${sensitiveWord.sensitiveWordType}','${sensitiveWord.illegalType}')">修改</a></td>
		<td><a href="javascript:deleteKeyword('${sensitiveWord.id}')">删除</a></td>
		</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	</div>
	<c:if test="${fn:length(sensitiveWordList)!=0}"> 
	<div class="mag">
		<input class="def" type="button" id="delBtn" value="批量删除"  style="width: 80px;"/>
	</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	<div id="divAdd">
	<p><label class="lbl">所属类别:</label>
	<select class="cs" name="typeAdd" id="typeAdd" style="width: 170px;margin-left: -203px;">
		<option value="0">全局关键词</option>
		<option value="1">房源信息</option>
		<option value="2">评论</option>
		<option value="3">小区内容</option>
	</select>
	</p>
	<p><label class="lbl">用户类型:</label>
	<select class="cs" name="userTypeAdd" id="userTypeAdd" style="width: 170px;margin-left: -203px;">
		<option value="0">所有用户</option>
		<option value="1">普通用户</option>
		<option value="2">经纪人</option>
	</select>
	</p>
	<p>
	<label class="lbl">关键词:</label><input type="text" name="contentAdd" id="contentAdd" style="margin-left: -196px;"/><br />
	<span style="color: gray;margin-left: 76px;">注:关键词使用简体,多个关键词用 | 分隔,使用联合关键词用","隔开</span>
	</p>
	<p><label class="lbl">关键字类型:</label>
	<select class="cs" id="sensitiveWordTypeAdd" name="sensitiveWordTypeAdd" style="width: 170px;margin-left: -212px;">
		<option value="0">敏感词</option>
		<option value="1">非法词</option>
	</select>
	</p>
	<p>
	<label class="lbl">非法类型:</label>
	<select class="cs" id="illegalTypeAdd" name="illegalTypeAdd" style="width: 170px;margin-left: -197px;">
		<option value="3">其他</option>
	</select>
	</p>
	</div>
	<div id="divEdit">
		<input type="hidden" name="idEdit" id="idEdit"/>
		<p><label class="lbl">所属类别:</label>
	<select class="cs" name="typeEdit" id="typeEdit" style="width: 170px;margin-left: -203px;">
		<option value="0">全局关键词</option>
		<option value="1">房源信息</option>
		<option value="2">评论</option>
		<option value="3">小区内容</option>
	</select>
	</p>
	<p><label class="lbl">用户类型:</label>
	<select class="cs" name="userTypeEdit" id="userTypeEdit" style="width: 170px;margin-left: -203px;">
		<option value="0">所有用户</option>
		<option value="1">普通用户</option>
		<option value="2">经纪人</option>
	</select>
	</p>
	<p>
	<label class="lbl">关键词:</label><input type="text" name="contentEdit" id="contentEdit" style="margin-left: -196px;"/><br />
	<span style="color: gray;margin-left: 76px;">注:关键词使用简体,多个关键词用 | 分隔,使用联合关键词用","隔开</span>
	</p>
	<p><label class="lbl">关键字类型:</label>
	<select class="cs" id="sensitiveWordTypeEdit" name="sensitiveWordTypeEdit" style="width: 170px;margin-left: -212px;">
		<option value="0">敏感词</option>
		<option value="1">非法词</option>
	</select>
	</p>
	<p>
	<label class="lbl">非法类型:</label>
	<select class="cs" id="illegalTypeEdit" name="illegalTypeEdit" style="width: 170px;margin-left: -197px;">
		<option value="3">其他</option>
	</select>
	</p>
	</div>
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
	<script type="text/javascript">
$(function() {
	//反添查询条件
	var type = "${search.type}";
	var sensitiveWordType = "${search.sensitiveWordType}";
	var content = "${search.content}";
	$("#type").val(type);
	$("#sensitiveWordType").val(sensitiveWordType);
	if(content!=null&&content!=""){$("#content").val(content);}
	//为控件绑定事件
	$("#content").bind("blur", txtBlur);
	$("#content").bind("focus", txtFocus);
	$("#addBtn").bind("click",add);
	$("#sensitiveWordTypeAdd").bind("click",function(){changeSelVal("sensitiveWordTypeAdd","illegalTypeAdd")});
	$("#sensitiveWordTypeEdit").bind("click",function(){changeSelVal("sensitiveWordTypeEdit","illegalTypeEdit")});
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
	if($('#content').val() == "请输入关键字") {
		$("#content").val("");
	}
}

// 为搜索输入框绑定失去焦点事件
function txtBlur() {
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	var val = $("#content").val().Trim();
	if (val == "") {
		$("#content").val("请输入关键字");
		$("#content").css("color", "gray");
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

//根据关键字类型动态添加非法类型
function changeSelVal(key,illegal){
	var key = "#" + key;
	var illegal = "#" + illegal;
	var val = $(key).val();
	var op;
	$(illegal).empty();
	if(val==0){
		op = "<option value='3'>其他</option>";
		$(illegal).append(op);
	}else{
		op = "<option value='0'>涉政</option>";
		$(illegal).append(op);
		op = "<option value='1'>黄赌毒</option>";
		$(illegal).append(op);
		op = "<option value='2'>灌水</option>";
		$(illegal).append(op);
		op = "<option value='3'>其他</option>";
		$(illegal).append(op);
	}
}

// ajax增加关键词，显示增加操作结果，刷新当前页面
function sensitiveWordAdd() {
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var type = $("#typeAdd").val();
	var userType = $("#userTypeAdd").val();
	var content = $("#contentAdd").val().Trim();
	var sensitiveWordType = $("#sensitiveWordTypeAdd").val();
	var illegalType = $("#illegalTypeAdd").val();
	if (content == "") {
		blockAlert("请输入关键词", 2000);
	}else{
		$.ajax({
			type : "GET",
			async : false,
			url : "/control/sensitiveWord/add",
			dataType : "json",
			data : {"type" : type, "userType": userType, "content" : content,"sensitiveWordType" : sensitiveWordType,"illegalType" : illegalType},
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
	$("#typeAdd").val(0);
	$("#userTypeAdd").val(0);
	$("#contentAdd").val("");
	$("#sensitiveWordTypeAdd").val(0);
	$("#illegalTypeAdd").empty();
	var op = "<option value='3'>其他</option>";
	$("#illegalTypeAdd").append(op);
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
function modify(id,type,userType, content,sensitiveWordType,illegalType){	
	$("#idEdit").val(id);
	$("#typeEdit").val(type);
	$("#contentEdit").val(content);
	$("#userTypeEdit").val(userType);
	$("#sensitiveWordTypeEdit").val(sensitiveWordType);
	changeSelVal("sensitiveWordTypeEdit","illegalTypeEdit")
	$("#illegalTypeEdit").val(illegalType);
	$("*").stop();
	$("#divEdit").dialog("close");
	$("#divEdit").dialog("option", "position", "center");
	$("#divEdit").dialog("open");
}

//ajax修改关键词，显示修改结果，刷新当前页面
function sensitiveWordEdit(){
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var id = $("#idEdit").val();
	var type = $("#typeEdit").val();
	var userType = $("#userTypeEdit").val();
	var content = $("#contentEdit").val().Trim();
	var sensitiveWordType = $("#sensitiveWordTypeEdit").val();
	var illegalType = $("#illegalTypeEdit").val();
	if (content == "") {
		blockAlert("请输入关键词", 2000);
	}else{
		$.ajax({
			type : "GET",
			async : false,
			url : "/control/sensitiveWord/edit",
			dataType : "json",
			data : {"id" : id,"type" : type, "userType": userType, "content" : content,"sensitiveWordType" : sensitiveWordType,"illegalType" : illegalType},
			success : function(data) {
				blockAlert(data.message, 2000);
			}
		});
		clearAddDiv();
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
			url : "/control/sensitiveWord/delete/" + id,
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
		blockAlert("请选择要删除的敏感词", 2000);
	} else {
		var content = $ids.val();
		var ids = "";
		$("input[name=idChk]:checked").each(function() {
			ids += $(this).val() + ",";
		});
		deleteKeyword(ids);
	}
}
</script>